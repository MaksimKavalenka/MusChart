/*
	Amplitude.js
	Version: 2.2
*/
var Amplitude = (function () {
	/*
	|--------------------------------------------------------------------------
	| Initializers
	|--------------------------------------------------------------------------
	| When the document is ready, Amplitude goes through and finds the elements
	| that should have event listeners and binds them. Next it will set up all
	| of the song time visualizations.  These visualizations simply show the
	| proportion of the song time that has elapsed with respect to the container
	| element. These are NOT the visualizations for audio frequencies that are
	| artistic.  It's a div that fills with another div representing the song time.
	*/
	document.onreadystatechange = function () {
		if( document.readyState == "complete" ){
			privateInitializeEventHandlers();
			privateInitializeSongTimeVisualizations();
		}
	}

	/*
	|--------------------------------------------------------------------------
	| Module Variables
	|--------------------------------------------------------------------------
	| These variables make Amplitude run. The config is the most important
	| containing active settings and parameters. The Web Audio API variables
	| for visualizations are below.
	*/

	/*--------------------------------------------------------------------------
		The config JSON is the global settings for ALL of Amplitude functions.
		This is global and contains all of the user preferences. The default
		settings are set, and the user overwrites them when they initialize
		Amplitude.
	--------------------------------------------------------------------------*/
	var config = {
		/*
			The audio element we will be using to handle all of the audio. This
			is the javascript version of the HTML5 audio element.
		*/
		active_song: new Audio(),
		/*
			JSON object that contains the active metadata for the song.
		*/
		active_metadata: {},
		/*
			String to hold the active album name. Used to check and see if the
			album changed and run the album changed callback.
		*/
		active_album: '',
		/*
			Contains the index of the actively playing song.
		*/
		active_index: 0,
		/*
			Set to true to autoplay the song
		*/
		autoplay: false,
		/*
			Used to determine if the album has changed and run the callback if it
			has.
		*/
		album_change: false,
		/*
			The user can set dynamic_mode to true and dynamic mode will be enabled
			allowing the user to pass a JSON representation of a song when they
			want to play it and utilize the Amplitude events to control it. The 
			user also doesn't have to config songs to use dynamic mode.
		*/
		dynamic_mode: false,
		/*
			The user can pass a JSON object with a key => value store of callbacks
			to be run at certain events.
		*/
		callbacks: {},
		/*
			Object containing all of the songs the user has passed to Amplitude
			to use.
		*/
		songs: {},
		/*
			When repeat is on, when the song ends the song will replay itself.
		*/
		repeat: false,
		/*
			When shuffled, this gets populated with the songs the user provided
			in a random order.
		*/
		shuffle_list: {},
		/*
			When shuffled is turned on this gets set to true so when traversing
			through songs Amplitude knows whether or not to use the songs object
			or the shuffle_list.
		*/
		shuffle_on: false,
		/*
			When shuffled, this index is used to let Amplitude know where it's
			at when traversing.
		*/
		shuffle_active_index: 0,
		/*
			The user can set default album art to be displayed if the song they
			set doesn't contain album art.
		*/
		default_album_art: '',
		/*
			When set to true, Amplitude will print to the console any errors
			that it runs into providing helpful feedback to the user.
		*/
		debug: false,
		/*
			When Amplitude finishes initializing, this is set to true. When set
			to true, Amplitude cannot be initialized again preventing double
			event handlers.
		*/
		initialized: false,
		/*
			By default this is true, but if the user wanted to hard code elements
			with song data, they could set this to false and Amplitude wouldn't 
			edit the now playing information in elements.
		*/
		handle_song_elements: true,
		/*
			The user can set the initial volume to a number between 0 and 1
			overridding a default of .5.
		*/
		volume: .5,
		/*
			This is set on mute so that when a user un-mutes Amplitude knows
			what to restore the volume to.
		*/
		pre_mute_volume: .5,
		/*
			This is an integer between 1 and 100 for how much the volume should
			increase when the user presses a volume up button.
		*/
		volume_increment: 5,
		/*
			This is an integer between 1 and 100 for how much the volume should
			decrease when the user presses a volume down button.
		*/
		volume_decrement: 5,
		/*
			To use visualizations with Amplitude, the user will set this to true.
			It is assumed that the user has thoroughly tested thier songs and have
			a back up plan in place if the browser doesn't support the Web Audio API.
			By doing this, we bypass a lot of unforseen errors with auto binding
			the web audio API to songs that don't need visualizations.
		*/
		use_visualizations: false,
		/*
			Handles all of the user registered visualizations if the user chooses
			to use visualizations in their design.
		*/
		visualizations: new Array(),
		/*
			Is set to the active visualization so Amplitude can detect changes
			per song if necessary.
		*/
		active_visualization: '',
		/*
			Holds the information the user defined about the current visualization,
			such as preferences.
		*/
		current_visualization: {},
		/*
			When the visualization is started, this is set to true.
		*/
		visualization_started: false,
		/*
			If the browser doesn't support visualizations, the user can provide
			a back up.  'nothing' is the default which removes the visualization
			element from the document. 'album-art' has Amplitude inject the now
			playing album art into the element that would have contained the
			visualization.
		*/
		visualization_backup: '',
		/*
			When using SoundCloud, the user will have to provide their API Client
			ID
		*/
		soundcloud_client: '',
		/*
			The user can set this to true and Amplitude will use the album art
			for the song returned from the Soundcloud API
		*/
		soundcloud_use_art: false,
		/*
			Used on config to count how many songs are from soundcloud and
			compare it to how many are ready for when to move to the rest
			of the configuration.
		*/
		soundcloud_song_count: 0,
		/*
			Used on config to count how many songs are ready so when we get
			all of the data from the SoundCloud API that we need this should
			match the SoundCloud song count meaning we can move to the rest
			of the config.
		*/
		soundcloud_songs_ready: 0,
		/*--------------------------------------------------------------------------
			These are web audio API variables.  They connect the web audio
			api to the audio source allowing for visualization extensions.
			These variables are public and to be used for extensions.
			Initializes the variables if they are available.
		--------------------------------------------------------------------------*/
		context: '',
		analyser: '',
		source: ''
	};

	/*--------------------------------------------------------------------------
		Used with SoundCloud to copy over the user config and add
		extra data so it doesn't interfere with the actual user
		config.
	--------------------------------------------------------------------------*/
	var temp_user_config = {};
	
	/*
	|--------------------------------------------------------------------------
	| PUBLIC METHODS
	|--------------------------------------------------------------------------
	| These methods are available to the developer.  They allow the developer
	| to change certain attributes if needed and configure the library.
	*/

	/*--------------------------------------------------------------------------
		The main init function.  The user will call this through 
		Amplitude.init({}) and pass in their settings.
		
		Public Accessor: Amplitude.init( user_config_json );

	 	@param user_config A JSON object of user defined values that help 
	 	configure and initialize AmplitudeJS.
	--------------------------------------------------------------------------*/
	function publicInit( user_config ){
		/*
			Checks to see if Amplitude has been initialized.
			If it hasn't then we can initialize AmplitudeJS. 
			The reason we check is so the same event handler 
			isn't bound twice to the same element.
		*/
		if( !config.initialized ){
			/*
				Initializes debugging right away so we can use it for the rest
				of the configuration.
			*/
			config.debug = ( user_config.debug != undefined ? user_config.debug : false );

			/*
				Checks for dynamic mode right away.  This will determine whether
				not having songs is a critical error or not since dynamic mode
				allows you to play songs by passing them in dynamically.
			*/
			config.dynamic_mode = ( user_config.dynamic_mode != undefined ? user_config.dynamic_mode : false );
			
			/*
				To use visualizations with Amplitude, the user will have to explicitly state
				that their player uses visualizations.  Reason being is that the AudioContext
				and other filters can really mess up functionality if the user is not prepared
				to have them operate on their audio element.  If set to true, then the
				AudioContext and the other necessary elements will be bound for the Web Audio API
				to handle the visualization processing.
			*/
			config.use_visualizations = ( user_config.use_visualizations != undefined ? user_config.use_visualizations : false );
			
			/*
				If the browser supports it and the user wants to use
				visualizations, then they can run visualizations. If
				the browser does not support the Web Audio API and the
				user has debug turned on, write to the console.
			*/
			if( window.AudioContext && config.use_visualizations ){
				config.context = new AudioContext();
				config.analyser = config.context.createAnalyser();

				config.source = config.context.createMediaElementSource( config.active_song );
				config.source.connect( config.analyser );
				
				config.analyser.connect( config.context.destination );

				config.active_song.crossOrigin = "anonymous";
			}else{
				if( !window.AudioContext ){
					privateWriteDebugMessage( 'This browser does not support the Web Audio API' );
				}
			}

			/*
				The first step in setting up Amplitude is copying over all of 
				the song objects that the user wants to use.
			*/
			var ready = false;

			/*
				This copies over all of the user defined songs and adds them
				to the amplitude config.

				First check is to see if Amplitude is in Dynamic Mode which
				means that the user will be selecting sending songs dynamically
				and using a global control set to control the functionality.
				This is the ONLY scenario that doesn't require song(s) object.
			*/
			if( !user_config.dynamic_mode ){
				/*
					Checks to see if the user defined any songs.
					If there are no song definitions, then it's 
					a critical error since Amplitude needs that to
					run.
				*/
				if( user_config.songs ){
					/*
						Makes sure the songs length is not 0, meaning
						that there is at least 1 song.
					*/
					if( user_config.songs.length != 0 ){
						/*
							Copies over the user defined songs. and prepares
							Amplitude for the rest of the configuration.
						*/
						config.songs = user_config.songs;
						ready = true;
					}else{
						privateWriteDebugMessage( 'Please add some songs, to your songs object!' );
					}
				}else{
					privateWriteDebugMessage( 'Please provide a songs object for AmplitudeJS to run!' );
				}
			}else{
				/*
					We are ready to copy over the rest of the information
					since we are in dynamic mode.
				*/
				ready = true;
			}
			

			/*
				When the preliminary config is ready, we are rady to proceed.
			*/
			if( ready ){
				/*
					Copies over the soundcloud information to the global config
					which will determine where we go from there.
				*/
				config.soundcloud_client = ( user_config.soundcloud_client != undefined ? user_config.soundcloud_client : '' );
				config.soundcloud_use_art = ( user_config.soundcloud_use_art != undefined ? user_config.soundcloud_use_art : '' );
				
				/*
					If the user provides a soundcloud client then we assume that
					there are URLs in their songs that will reference SoundcCloud.
					We then copy over the user config they provided to the 
					temp_user_config so we don't mess up the global or their configs
					and load the soundcloud information.
				*/
				if( config.soundcloud_client != '' ){
					temp_user_config = user_config;

					/*
						Load up SoundCloud for use with AmplitudeJS.
					*/
					privateLoadSoundcloud();
				}else{
					/*
						The user is not using Soundcloud with Amplitude at this point
						so we just finish the configuration with the users's preferences.
					*/
					privateSetConfig( user_config );
				}
			}
		}
	}

	/*--------------------------------------------------------------------------
		Allows the user to turn on debugging.
		
		Public Accessor: Amplitude.setDebug( bool );
		
	 	@param BOOL state Turns debugging on and off.
	--------------------------------------------------------------------------*/
	function publicSetDebug( state ){
		/*
			Sets the global config debug on or off.
		*/
		config.debug = state;
	}

	/*--------------------------------------------------------------------------
		Returns the active song meta data for the user to do what is 
		needed.
		
		Public Accessor: Amplitude.getActiveSongMetadata();
		
	 	@returns JSON Object with the active song information
	--------------------------------------------------------------------------*/
	function publicGetActiveSongMetadata(){
		return config.active_metadata;
	}

	/*--------------------------------------------------------------------------
		Registers a visualization and sets that visualization's 
		preferences. When creating a visualization, you can set certain
		preferences that the user can overwrite similar to Amplitude.

		Public Accessor: Amplitude.registerVisualization( visualization, preferences )

		@param visualzation A visualization object that gets registered
		with Amplitude

		@param preferences A JSON object of preferences relating to the
		visualization
	--------------------------------------------------------------------------*/
	function publicRegisterVisualization( visualization, preferences ){
		/*
			Adds the visualization to the global config so it knows
			it can be used when playing songs.

			getID is a public function for getting a visualization's id.
			It becomes the key to access the visualization.
		*/
		config.visualizations[ visualization.getID ] = visualization;
		
		/*
			If defined, set the visualization preferences.
			setPreferences is a public function for connecting
			to a user defined visualization.
		*/
		if( preferences != undefined ){
			visualization.setPreferences( preferences );
		}
	}

	/*--------------------------------------------------------------------------
		Changes the active visualization. Could be called from a 
		user defined dropdown or whatever way the user wants to change a
		visualization dynamically.
		
		Public Accessor: Amplitude.changeVisualization( visualization )

		@param string visualization The name of the visualization
		that should be used.
	--------------------------------------------------------------------------*/
	function publicChangeActiveVisualization( visualization ){
		/*
			First we stop the active visualization. If the visualization
			is set up correctly, it should halt all callbacks, and clear
			the amplitude-visualization element.
		*/
		privateStopVisualization();

		/*
			Next we set the active visualization in the config.
		*/
		config.active_visualization = visualization;

		/*
			We then start the visualization hooks again.  This should
			insert itself into the amplitude-visualization element
			and bind the proper hooks.
		*/
		privateStartVisualization();
	}

	/*--------------------------------------------------------------------------
		Checks to see if the current browser is capable of running
		visualizations. If the AudioContext is available, then the browser
		can play the visualization.
		
		Public Accessor: Amplitude.visualizationCapable()
		
		@returns BOOL true if the browser can play the visualization and false
		if the browser cannot.
	--------------------------------------------------------------------------*/
	function publicVisualizationCapable(){
		if ( !window.AudioContext ) {
			return false;
		}else{
			return true;
		}
	}

	/*--------------------------------------------------------------------------
		Returns a song in the songs array at that index
		
		Public Accessor: Amplitude.getSongByIndex( song_index )

		@param int index The integer for the index of the
		song in the songs array.

		@returns JSON representation for the song at a specific index.
	--------------------------------------------------------------------------*/
	function publicGetSongByIndex( index ){
		return config.songs[index];
	}

	function publicSetSongs(songs) {
		config.songs = songs;
	}

	/*--------------------------------------------------------------------------
		Adds a song to the end of the config array.  This will allow Amplitude
		to play the song in a playlist type setting.
		
		Public Accessor: Amplitude.addSong( song_json )

		@param song JSON representation of a song.

		@returns int New index of the song.
	--------------------------------------------------------------------------*/
	function publicAddSong( song ){
		config.songs.push( song );
		return config.songs.length - 1;
	}

	/*--------------------------------------------------------------------------
		When you pass a song object it plays that song right awawy.  It sets
		the active song in the config to the song you pass in and synchronizes
		the visuals.
		
		Public Accessor: Amplitude.playNow( song_json )

		@param song JSON representation of a song.
	--------------------------------------------------------------------------*/
	function publicPlayNow( song ){
		/*
			Makes sure the song object has a URL associated with it
			or there will be nothing to play.
		*/
		if( song.url ){
			config.active_song.src = song.url;
			config.active_metadata = song;
			config.active_album = song.album;
		}else{
			privateWriteDebugMessage('The song needs to have a URL!');
		}
		

		/*
			Sets the main song control status visual
		*/
		privateChangePlayPauseState('playing');

		/*
			Calls the song change method that configures everything necessary for
			Amplitude when the song changes.
		*/
		privateAfterSongChanges();

	}

	function publicPlayNowById(id) {
		var index;
		var song = _.find(config.songs, function(song, ind) {
			index = ind;
			return song.id === id;
		});
		if (!angular.isUndefined(song)) {
			config.active_index = index;
			publicPlayNow(song);
		}
	}

	/*--------------------------------------------------------------------------
		Allows the user to play whatever the active song is directly
		through Javascript. Normally ALL of Amplitude functions that access
		the core features are called through event handlers. However when in
		dynamic mode is enabled, you will need to call this directly.

		WARNING: Accessible ONLY if dynmaic mode is turned on.

		Public Accessor: Amplitude.play();
	--------------------------------------------------------------------------*/
	function publicPlay(){
		if( config.dynamic_mode ){
			privatePlay();
		}
	}

	/*--------------------------------------------------------------------------
		Allows the user to pause whatever the active song is directly
		through Javascript. Normally ALL of Amplitude functions that access
		the core features are called through event handlers. However when in
		dynamic mode is enabled, you will need to call this directly.

		WARNING: Accessible ONLY if dynmaic mode is turned on.

		Public Accessor: Amplitude.pause();
	--------------------------------------------------------------------------*/
	function publicPause(){
		if( config.dynamic_mode ){
			privatePause();
		}
	}

	function publicGetAnalyser(){
		return config.analyser;
	}

	/*
	|--------------------------------------------------------------------------
	| INITIALIZATION FUNCTIONS
	|--------------------------------------------------------------------------
	| CALLED ON INITIALIZATION 
	| 
	| These functions are called on initialization and configure the base
	| functionality for Amplitude.  They init event handlers and set up the
	| song time visualizations.
	*/

	/*--------------------------------------------------------------------------
		Binds all AmplitudeJS event handlers to their respective elements.
		This is a special function that is ONLY called once the document
		is initialized.  If it is called more than once than more than one
		event handler will be bound to an individual element causing chaos.

		If the navigator is a mobile device, the event bound for user
		interaction is 'touchstart', otherwise it is 'click' for the majority 
		of the AmplitudeJS elements.  

		The 'input' interaction is used for the HTML5 Range song sliders.

		The events 'timeupdate' and 'ended' are bound to the javascript 
		audio element.
	--------------------------------------------------------------------------*/
	function privateInitializeEventHandlers(){
		/*
			On time update for the audio element, update visual displays that
			represent the time on either a visualized element or time display.
		*/
		config.active_song.addEventListener('timeupdate', privateUpdateTime );

		/*
			When the audio element has ended playing, we handle the song
			ending. In a single song or multiple modular song instance,
			this just synchronizes the visuals for time and song time
			visualization, but for a playlist it determines whether
			it should play the next song or not.
		*/
		config.active_song.addEventListener('ended', privateHandleSongEnded );

		/*
			Binds handlers for play classes
		*/
		var play_classes = document.getElementsByClassName("amplitude-play");

		for( var i = 0; i < play_classes.length; i++ ){
			if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
				play_classes[i].addEventListener('touchstart', privatePlayClickHandle );
			}else{
				play_classes[i].addEventListener('click', privatePlayClickHandle );
			}
		}

		/*
			Binds handlers for pause classes
		*/
		var pause_classes = document.getElementsByClassName("amplitude-pause");

		for( var i = 0; i < pause_classes.length; i++ ){
			if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
				pause_classes[i].addEventListener('touchstart', privatePauseClickHandle );
			}else{
				pause_classes[i].addEventListener('click', privatePauseClickHandle );
			}
		}

		/*
			Binds handlers for stop classes
		*/
		var stop_classes = document.getElementsByClassName("amplitude-stop");

		for( var i = 0; i < stop_classes.length; i++ ){
			if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
				stop_classes[i].addEventListener('touchstart', privateStopClickHandle );
			}else{
				stop_classes[i].addEventListener('click', privateStopClickHandle );
			}
		}

		/*
			Binds handlers for play/pause classes
		*/
		var play_pause_classes = document.getElementsByClassName("amplitude-play-pause");

		for( var i = 0; i < play_pause_classes.length; i++ ){
			if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
				play_pause_classes[i].addEventListener('touchstart', privatePlayPauseClickHandle );
			}else{
				play_pause_classes[i].addEventListener('click', privatePlayPauseClickHandle );
			}
		}

		/*
			Binds handlers for mute classes

			WARNING: If iOS, we don't do anything because iOS does not allow the
			volume to be adjusted through anything except the buttons on the side of
			the device.
		*/
		var mute_classes = document.getElementsByClassName("amplitude-mute");

		for( var i = 0; i < mute_classes.length; i++ ){
			if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
				/*
					Checks for an iOS device and displays an error message if debugging
					is turned on.
				*/
				if( /iPhone|iPad|iPod/i.test(navigator.userAgent) ) {
					privateWriteDebugMessage( 'iOS does NOT allow volume to be set through javascript: https://developer.apple.com/library/safari/documentation/AudioVideo/Conceptual/Using_HTML5_Audio_Video/Device-SpecificConsiderations/Device-SpecificConsiderations.html#//apple_ref/doc/uid/TP40009523-CH5-SW4' );
				}else{
					mute_classes[i].addEventListener('touchstart', privateMuteClickHandle );
				}
			}else{
				mute_classes[i].addEventListener('click', privateMuteClickHandle );
			}
		}

		/*
			Binds handlers for volume up classes

			WARNING: If iOS, we don't do anything because iOS does not allow the
			volume to be adjusted through anything except the buttons on the side of
			the device.
		*/
		var volume_up_classes = document.getElementsByClassName("amplitude-volume-up");

		for( var i = 0; i < volume_up_classes.length; i++ ){
			if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
				/*
					Checks for an iOS device and displays an error message if debugging
					is turned on.
				*/
				if( /iPhone|iPad|iPod/i.test(navigator.userAgent) ) {
					privateWriteDebugMessage( 'iOS does NOT allow volume to be set through javascript: https://developer.apple.com/library/safari/documentation/AudioVideo/Conceptual/Using_HTML5_Audio_Video/Device-SpecificConsiderations/Device-SpecificConsiderations.html#//apple_ref/doc/uid/TP40009523-CH5-SW4' );
				}else{
					volume_up_classes[i].addEventListener('touchstart', privateVolumeUpClickHandle );
				}
			}else{
				volume_up_classes[i].addEventListener('click', privateVolumeUpClickHandle );
			}
		}

		/*
			Binds handlers for volume down classes

			WARNING: If iOS, we don't do anything because iOS does not allow the
			volume to be adjusted through anything except the buttons on the side of
			the device.
		*/
		var volume_down_classes = document.getElementsByClassName("amplitude-volume-down");
		
		for( var i = 0; i < volume_down_classes.length; i++ ){
			if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
				/*
					Checks for an iOS device and displays an error message if debugging
					is turned on.
				*/
				if( /iPhone|iPad|iPod/i.test(navigator.userAgent) ) {
					privateWriteDebugMessage( 'iOS does NOT allow volume to be set through javascript: https://developer.apple.com/library/safari/documentation/AudioVideo/Conceptual/Using_HTML5_Audio_Video/Device-SpecificConsiderations/Device-SpecificConsiderations.html#//apple_ref/doc/uid/TP40009523-CH5-SW4' );
				}else{
					volume_down_classes[i].addEventListener('touchstart', privateVolumeDownClickHandle );
				}
			}else{
				volume_down_classes[i].addEventListener('click', privateVolumeDownClickHandle );
			}
		}

		/*
			Binds handlers for song slider classes. The song sliders are HTML 5 
			Range Elements.  This event fires everytime a slider has changed.
		*/
		var song_sliders = document.getElementsByClassName("amplitude-song-slider");

		for( var i = 0; i < song_sliders.length; i++ ){
			song_sliders[i].addEventListener('input', privateSongStatusBarInputHandle );
		}

		/*
			Binds handlers for volume slider classes. The volume sliders are HTML 5
			Range Elements. This event fires everytime a slider has changed.

			WARNING: If iOS, we don't do anything because iOS does not allow the
			volume to be adjusted through anything except the buttons on the side of
			the device.
		*/
		var volume_sliders = document.getElementsByClassName("amplitude-volume-slider");

		for( var i = 0; i < volume_sliders.length; i++ ){
			/*
				Checks for an iOS device and displays an error message if debugging
				is turned on.
			*/
			if( /iPhone|iPad|iPod/i.test(navigator.userAgent) ) {
				privateWriteDebugMessage( 'iOS does NOT allow volume to be set through javascript: https://developer.apple.com/library/safari/documentation/AudioVideo/Conceptual/Using_HTML5_Audio_Video/Device-SpecificConsiderations/Device-SpecificConsiderations.html#//apple_ref/doc/uid/TP40009523-CH5-SW4' );
			}else{
				volume_sliders[i].addEventListener('input', privateVolumeInputHandle );
			}
		}

		/*
			Binds handlers for next button classes.
		*/
		var next_classes = document.getElementsByClassName("amplitude-next");

		for( var i = 0; i < next_classes.length; i++ ){
			if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
				next_classes[i].addEventListener('touchstart', privateNextClickHandle );
			}else{
				next_classes[i].addEventListener('click', privateNextClickHandle );
			}
		}

		/*
			Binds handlers for previous button classes.
		*/
		var prev_classes = document.getElementsByClassName("amplitude-prev");

		for( var i = 0; i < prev_classes.length; i++ ){
			if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
				prev_classes[i].addEventListener('touchstart', privatePrevClickHandle );
			}else{
				prev_classes[i].addEventListener('click', privatePrevClickHandle );
			}
		}

		/*
			Binds handlers for shuffle button classes.
		*/
		var shuffle_classes = document.getElementsByClassName("amplitude-shuffle");

		for( var i = 0; i < shuffle_classes.length; i++ ){
			shuffle_classes[i].classList.remove('amplitude-shuffle-on');
			shuffle_classes[i].classList.add('amplitude-shuffle-off');

			if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
				shuffle_classes[i].addEventListener('touchstart', privateShuffleClickHandle );
			}else{
				shuffle_classes[i].addEventListener('click', privateShuffleClickHandle );
			}
		}

		/*
			Binds handlers for repeat button classes.
		*/
		var repeat_classes = document.getElementsByClassName("amplitude-repeat");

		for( var i = 0; i < repeat_classes.length; i++ ){
			repeat_classes[i].classList.remove('amplitude-repeat-on');
			repeat_classes[i].classList.add('amplitude-repeat-off');

			if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
				repeat_classes[i].addEventListener('touchstart', privateRepeatClickHandle );
			}else{
				repeat_classes[i].addEventListener('click', privateRepeatClickHandle );
			}
		}
	}

	/*--------------------------------------------------------------------------
		Sets up all of the song time visualizations.  This is the only time
		that AmplitudeJS will add an element to the page. AmplitudeJS will
		add an element inside of the song time visualization element that will
		expand proportionally to the amount of time elapsed on the active 
		audio, thus visualizing the song time.  This element is NOT user
		interactive.  To have the user scrub the time, they will have to 
		style and implement a song time slider with an HTML 5 Range Element.
	--------------------------------------------------------------------------*/
	function privateInitializeSongTimeVisualizations(){
		/*
			Sets up song time visualizations
		*/
		var song_time_visualizations = document.getElementsByClassName("amplitude-song-time-visualization");

		/*
			Iterates through all of the amplitude-song-time-visualization
			elements adding a new div with a class of
			'amplitude-song-time-visualization-status' that will expand
			inside of the 'amplitude-song-time-visualization' element.
		*/
		for( var i = 0; i < song_time_visualizations.length; i++ ){
			/*
				Creates new element
			*/
			var status = document.createElement('div');

			/*
				Adds class and attributes
			*/
			status.classList.add('amplitude-song-time-visualization-status');
			status.setAttribute( 'style', 'width: 0px' );

			/*
				Appends the element as a child element.
			*/
			song_time_visualizations[i].appendChild( status );
		}
	}

	/*
	|--------------------------------------------------------------------------
	| EVENT HANDLER FUNCTIONS
	|--------------------------------------------------------------------------
	| These functions handle the events that we bound to each element and
	| prepare for a function to be called. For example, if a click is on a song 
	| that doens't equal the index of the active song, it sets the active song 
	| to be what is needed and then play  is called. These kind of act 
	| like filters/middleware.
	*/

	/*--------------------------------------------------------------------------
		HANDLER FOR: 'amplitude-play-pause';

		Handles a click on a play/pause element.  This element toggles
		functionality based on the state of the song.

		TODO: Clean up this function and break out into helper functions
	--------------------------------------------------------------------------*/
	function privatePlayPauseClickHandle(){
		/*
			If there are multiple play/pause buttons on the screen,
			you can have them assigned to an individual song.

			With the amplitude-song-index attribute, you can assign 
			the play buttons a song to play out of the songs array.
		*/
		var playing_song_index = this.getAttribute('amplitude-song-index');

		/*
			If there is a new song to be played.  Since this handler 
			handles ALL events on a play-pause element, we have
			to check to see if there is a song that is being
			played already and the user clicked a different element
			of the same type that is supposed to play a new song.
		*/
		if( privateCheckNewSong( playing_song_index) ){
			privateChangeSong( playing_song_index );
			/*
				Sets all song status and play/pause button visuals
				to sync with the newly played song.
			*/	
			privateSetPlayPauseButtonsToPause();
			privateResetSongStatusSliders();


			privateChangePlayPauseState('playing');
			
			/*
				Starts the song visualization if there is one.
			*/
			privateStartVisualization();

			privatePlay();
		}else{
			if( config.active_song.paused ){
				
				privateChangePlayPauseState('playing');

				/*
					Starts the song visualization if there is one.
				*/
				privateStartVisualization();

				privatePlay( this.getAttribute('amplitude-song-index') );
			}else{
				privateChangePlayPauseState('paused');

				privatePause();
			}
		}
	}

	/*--------------------------------------------------------------------------
		HANDLER FOR: 'amplitude-play'

		Handles a click on a play element.

		TODO: Check to see if we should start the visualizations and run the 
		play or check to see if the song clicked is not new and paused before
		we run those otherwise it might cause an error like re-starting the
		song if play is clicked twice.
	--------------------------------------------------------------------------*/
	function privatePlayClickHandle(){
		/*
			Gets the attribute for song index so we can check if
			there is a need to change the song.  In some scenarios
			there might be multiple play classes on the page. In that
			case it is possible the user could click a different play
			class and change the song.
		*/
		var playing_song_index = this.getAttribute('amplitude-song-index');

		/*
			We set the new song if the user clicked a song with a different
			index. If it's the same as what's playing then we don't set anything. 
			If it's different we reset all song sliders.
		*/
		if( privateCheckNewSong( playing_song_index ) ){
			privateChangeSong( playing_song_index );

			privateResetSongStatusSliders();
		}

		/*
			Start the visualizations for the song.
		*/
		privateStartVisualization();
		
		/*
			Play the song through the core play function.
		*/
		privatePlay();
	}

	/*--------------------------------------------------------------------------
		HANDLER FOR: 'amplitude-pause'

		Handles a click on a pause element.

		TODO: Check to see that the pause element has an index and if that
		index matches the current song being played.  If it's different then
		we should disable it? If the user clicks on song-index=1 pause and 
		song-index=2 is being played, is it right to pause?
	--------------------------------------------------------------------------*/
	function privatePauseClickHandle(){
		/*
			Calls the core function for pause
		*/
		privatePause();
	}

	/*--------------------------------------------------------------------------
		HANDLER FOR: 'amplitude-stop'

		Handles a click on a stop element.
	--------------------------------------------------------------------------*/
	function privateStopClickHandle(){
		/*
			Calls the helper function to stop
			the visualization.
		*/
		privateStopVisualization();

		/*
			Calls the core function for stop
		*/
		privateStop();
	}

	/*--------------------------------------------------------------------------
		HANDLER FOR: 'amplitude-mute'

		Handles a click on a mute element.

		TODO: Add a class if muted to this element of amplitude-mute.  That way
		the designer can style the element if amplitude is muted like the typical
		volume with a line through it.

		TODO: Standardize the privateSetVolume parameter so it doesn't need
		to be converted by the privateSetVolume function.  Right now it converts
		up then down again which makes no sense.
	--------------------------------------------------------------------------*/
	function privateMuteClickHandle(){
		/*
			If the current volume in the config is 0, we set the volume to the 
			pre_mute level.  This means that the audio is already muted and
			needs to be restored to the pre_mute level.
			
			Otherwise, we set pre_mute volume to the current volume
			and set the config volume to 0, muting the audio.
		*/
		if( config.volume == 0 ){
			config.volume = config.pre_mute_volume;
		}else{
			config.pre_mute_volume = config.volume;
			config.volume = 0;
		}

		/*
			Calls the core function to set the volume to the computed value
			based on the user's intent.
		*/
		privateSetVolume( config.volume * 100 );

		/*
			Syncs the volume sliders so the visuals align up with the functionality.
			If the volume is at 0, then the sliders should represent that so the user
			has the right starting point.
		*/
		privateSyncVolumeSliders();
	}

	/*--------------------------------------------------------------------------
		HANDLER FOR: 'amplitude-volume-up'

		Handles a click on a volume up element.

		TODO: Standardize the privateSetVolume parameter so it doesn't need
		to be converted by the privateSetVolume function.  Right now it converts
		up then down again which makes no sense.
	--------------------------------------------------------------------------*/
	function privateVolumeUpClickHandle(){
		/*
			The volume range is from 0 to 1 for an audio element. We make this
			a base of 100 for ease of working with.

			If the new value is less than 100, we use the new calculated
			value which gets converted to the proper unit for the audio element.

			If the new value is greater than 100, we set the volume to 1 which
			is the max for the audio element.
		*/
		if( ( ( config.volume * 100 ) + config.volume_increment ) <= 100 ){
			config.volume = config.volume + ( config.volume_increment / 100 );
		}else{
			config.volume = 1;
		}

		/*
			Calls the core function to set the volume to the computed value
			based on the user's intent.
		*/
		privateSetVolume( config.volume * 100 );

		/*
			Syncs the volume sliders so the visuals align up with the functionality.
			If the volume is at 0, then the sliders should represent that so the user
			has the right starting point.
		*/
		privateSyncVolumeSliders();
	}

	/*--------------------------------------------------------------------------
		HANDLER FOR: 'amplitude-volume-down'

		Handles a click on a volume down element.

		TODO: Standardize the privateSetVolume parameter so it doesn't need
		to be converted by the privateSetVolume function.  Right now it converts
		up then down again which makes no sense.
	--------------------------------------------------------------------------*/
	function privateVolumeDownClickHandle(){
		/*
			The volume range is from 0 to 1 for an audio element. We make this
			a base of 100 for ease of working with.

			If the new value is less than 0, we use the new calculated
			value which gets converted to the proper unit for the audio element.

			If the new value is greater than 0, we set the volume to 0 which
			is the min for the audio element.
		*/
		if( ( ( config.volume * 100 ) - config.volume_decrement ) > 0 ){
			config.volume = config.volume - ( config.volume_decrement / 100 );
		}else{
			config.volume = 0;
		}
		/*
			Calls the core function to set the volume to the computed value
			based on the user's intent.
		*/
		privateSetVolume( config.volume * 100 );

		/*
			Syncs the volume sliders so the visuals align up with the functionality.
			If the volume is at 0, then the sliders should represent that so the user
			has the right starting point.
		*/
		privateSyncVolumeSliders();
	}

	/*--------------------------------------------------------------------------
		HANDLER FOR: 'amplitude-volume-slider'

		Handles an input change for a volume slider.

		TODO: Standardize the privateSetVolume parameter so it doesn't need
		to be converted by the privateSetVolume function.  Right now it converts
		up then down again which makes no sense.
	--------------------------------------------------------------------------*/
	function privateVolumeInputHandle(){
		/*
			The range slider has a range of 1 to 100 so we get the value and
			convert it to a range of 0 to 1 and set the volume.
		*/
		config.volume = ( this.value / 100 );

		/*
			Calls the core function to set the volume to the computed value
			based on the user's intent.
		*/
		privateSetVolume( this.value );

		/*
			Syncs the volume sliders so the visuals align up with the functionality.
			If the volume is at 0, then the sliders should represent that so the user
			has the right starting point.
		*/
		privateSyncVolumeSliders();
	}

	/*--------------------------------------------------------------------------
		HANDLER FOR: 'amplitude-song-slider'

		Handles an input change for a song slider.

		TODO: Make an attribute that allows for multiple main song sliders
		allowing the active playing song to be scrubbed from multiple locations
		on the page and is always in sync.
	--------------------------------------------------------------------------*/
	function privateSongStatusBarInputHandle(){
		/*
			We only adjust the time if the song is playing. It wouldn't make
			sense if we adjusted the time while it was paused.
		*/
		if( !config.active_song.paused ){
			/*
				We first check if the song slider is the only one on the page.
				If it is, we can safely assume that the slider is synced with
				the song's progression and adjust the song.
			*/
			if( this.getAttribute('amplitude-singular-song-slider') ){
				privateSetSongLocation( this.value );
			}

			/*
				If the song slider has a song index, we check to see if it matches
				the active song index. If it does, then adjust the song location.
				We do this so we can have multiple Amplitude players on the same page
				and have the slider relate to the song playing.
			*/
			if( this.getAttribute('amplitude-song-index') == config.active_index ){
				privateSetSongLocation( this.value );
			}
		}
	}

	/*--------------------------------------------------------------------------
		HANDLER FOR: 'amplitude-next'

		Handles a click for the next song.
	--------------------------------------------------------------------------*/
	function privateNextClickHandle(){
		/*
			Runs the before_next callback for the user to hook into.
		*/
		privateRunCallback('before_next');

		/*
			Stop active song since we are moving to the next song.
		*/
		privateStop();

		/*
			First we check if shuffle is on. If it is we use the shuffle array
			to select the next song. Otherwise, we go with the standard song 
			array.

			Loop around songs array if at the end. We need to check if the next
			song is within array. Otherwise we reset it to 0.

			Set new song
		*/
		if( config.shuffle_on ){
			if( parseInt( config.shuffle_active_index ) + 1 < config.shuffle_list.length ){
				/*
					Gets the new index in the shuffle array for the song we need.
				*/
				var newIndex = parseInt( config.shuffle_active_index ) + 1;

				/*
					Check new album
				*/
				privateCheckNewAlbum( config.suffle_list[ newIndex ].album );
				
				/*
					Sets the new song information in the config, so everything
					is ready to be changed.
				*/
				privateSetActiveSongInformation( newIndex, config.shuffle_on );

				/*
					Checks to see if there is a new album to be played. If there
					is we fire the after_album_change callback which allows the
					developer to handler album changes which could also mean multiple
					playlists.
				*/
				if( config.album_change ){
					privateRunCallback('after_album_change');
					config.album_change = false;
				}

				/*
					Sets the new shuffle active index to be used in the shuffled songs object.
				*/
				config.shuffle_active_index = newIndex;
			}else{
				/*
					Check new album
				*/
				privateCheckNewAlbum( config.suffle_list[0].album );

				/*
					Sets the new song information in the config, so everything
					is ready to be changed.
				*/
				privateSetActiveSongInformation( 0, config.shuffle_on );

				/*
					Checks to see if there is a new album to be played. If there
					is we fire the after_album_change callback which allows the
					developer to handler album changes which could also mean multiple
					playlists.
				*/
				if( config.album_change ){
					privateRunCallback('after_album_change');
					config.album_change = false;
				}

				/*
					Sets the new shuffle active index to be used in the shuffled songs object.
				*/
				config.shuffle_active_index = 0;
			}
		}else{
			if( parseInt(config.active_index) + 1 < config.songs.length ){

				var newIndex = parseInt( config.active_index ) + 1;
				/*
					Check new album
				*/
				privateCheckNewAlbum( config.songs[newIndex].album );

				/*
					Sets the new song information in the config, so everything
					is ready to be changed.
				*/
				privateSetActiveSongInformation( newIndex, config.shuffle_on );

				/*
					Checks to see if there is a new album to be played. If there
					is we fire the after_album_change callback which allows the
					developer to handler album changes which could also mean multiple
					playlists.
				*/
				if( config.album_change ){
					privateRunCallback('after_album_change');
					config.album_change = false;
				}

				/*
					Sets the new active index to be used with the songs object
				*/
				config.active_index = newIndex;
			}else{
				/*
					Check new album
				*/
				privateCheckNewAlbum( config.songs[0].album );
				
				/*
					Sets the new song information in the config, so everything
					is ready to be changed.
				*/
				privateSetActiveSongInformation( 0, config.shuffle_on );

				/*
					Checks to see if there is a new album to be played. If there
					is we fire the after_album_change callback which allows the
					developer to handler album changes which could also mean multiple
					playlists.
				*/
				if( config.album_change ){
					privateRunCallback('after_album_change');
					config.album_change = false;
				}
				/*
					Sets the new active index to be used with the songs object
				*/
				config.active_index = 0;
			}
		}
		
		/*
			Sets the main song control status to be in sync with the current state
			of the song.
		*/
		privateChangePlayPauseState( 'playing' );

		/*
			Runs the song change method to sync everything necessary.
		*/
		privateAfterSongChanges();

		/*
			Fires the after_next callback for users to hook into.
		*/
		privateRunCallback('after_next');
	}

	/*--------------------------------------------------------------------------
		HANDLER FOR: 'amplitude-prev'

		Handles a click for the previous song.
	--------------------------------------------------------------------------*/
	function privatePrevClickHandle(){
		/*
			Runs the before_prev callback for the user to hook into.
		*/
		privateRunCallback('before_prev');
		
		/*
			Stop active song since we are moving to the previous song.
		*/
		privateStop();

		/*
			First we check if shuffle is on. If it is we use the shuffle array to 
			select the previous song. Otherwise, we go with the sandard song array.

			Loop around songs array if at the beginning. We need to check if the next
			song is within array. Otherwise we reset it to the end of the songs.

			Set new song
		*/
		if( config.shuffle_on ){
			if( ( parseInt( config.shuffle_active_index ) - 1 ) >= 0 ){
				/*
					Gets the new index in the shuffle array for the song we need.
				*/
				var newIndex = parseInt( config.shuffle_active_index ) - 1;

				/*
					Check new album
				*/
				privateCheckNewAlbum( config.suffle_list[ newIndex ].album );

				/*
					Sets the new song information in the config, so everything
					is ready to be changed.
				*/
				privateSetActiveSongInformation( newIndex, config.shuffle_on );

				/*
					Checks to see if there is a new album to be played. If there
					is we fire the after_album_change callback which allows the
					developer to handler album changes which could also mean multiple
					playlists.
				*/
				if( config.album_change ){
					privateRunCallback('after_album_change');
					config.album_change = false;
				}

				/*
					Sets the new shuffle active index to be used in the shuffled songs object.
				*/
				config.shuffle_active_index = newIndex;
			}else{
				/*
					Gets the new index in the shuffle array for the song we need.
				*/
				var newIndex = parseInt( config.shuffle_list.length - 1 );

				/*
					Check new album
				*/
				privateCheckNewAlbum( config.suffle_list[ newIndex ].album );
				
				/*
					Sets the new song information in the config, so everything
					is ready to be changed.
				*/
				privateSetActiveSongInformation( newIndex, config.shuffle_on );

				/*
					Checks to see if there is a new album to be played. If there
					is we fire the after_album_change callback which allows the
					developer to handler album changes which could also mean multiple
					playlists.
				*/
				if( config.album_change ){
					privateRunCallback('after_album_change');
					config.album_change = false;
				}

				/*
					Sets the new shuffle active index to be used in the shuffled songs object.
				*/
				config.shuffle_active_index = newIndex;
			}
		}else{
			if( ( parseInt( config.active_index ) - 1 ) >= 0 ){

				var newIndex = parseInt( parseInt(config.active_index) - 1 );

				/*
					Check new album
				*/
				privateCheckNewAlbum( config.songs[ newIndex ].album );
				
				/*
					Sets the new song information in the config, so everything
					is ready to be changed.
				*/
				privateSetActiveSongInformation( newIndex, config.shuffle_on );

				/*
					Checks to see if there is a new album to be played. If there
					is we fire the after_album_change callback which allows the
					developer to handler album changes which could also mean multiple
					playlists.
				*/
				if( config.album_change ){
					privateRunCallback('after_album_change');
					config.album_change = false;
				}

				/*
					Sets the new active index to be used with the songs object
				*/
				config.active_index = newIndex;
			}else{

				var newIndex = config.songs.length - 1;
				/*
					Check new album
				*/
				privateCheckNewAlbum( config.songs[ newIndex ].album );
				
				/*
					Sets the new song information in the config, so everything
					is ready to be changed.
				*/
				privateSetActiveSongInformation( newIndex, config.shuffle_on );

				/*
					Checks to see if there is a new album to be played. If there
					is we fire the after_album_change callback which allows the
					developer to handler album changes which could also mean multiple
					playlists.
				*/
				if( config.album_change ){
					privateRunCallback('after_album_change');
					config.album_change = false;
				}

				/*
					Sets the new active index to be used with the songs object
				*/
				config.active_index = newIndex;
			}
		}
		
		/*
			Sets the main song control status to be in sync with the current state
			of the song.
		*/
		privateChangePlayPauseState( 'playing' );
		
		
		/*
			Runs the song change method to sync everything necessary.
		*/
		privateAfterSongChanges();

		/*
			Fires the after_prev callback for users to hook into.
		*/

		privateRunCallback('after_prev');
	}

	/*--------------------------------------------------------------------------
		HANDLER FOR: 'ended' on main audio element.

		When the song has ended, this method gets called.
		If it's a one song instance, then we don't do anything.
		If there are multiple songs, we check if shuffle is on
		or if we should use the original songs array. Then we set
		the next song and play it.
	--------------------------------------------------------------------------*/
	function privateHandleSongEnded(){
		/*
			Checks to see if repeat is on. If it's on, then we re-play the
			current song. Otherwise we begin the process of playing the
			next song in the list whether it's shuffle or regular list or
			single song.
		*/
		if( config.repeat ){
			/*
				Confirms stop of the active song
			*/
			privateStop();

			/*
				Without changing the index, just prepares the 
				next song to play.
			*/
			privateAfterSongChanges();
		}else{
			/*
				Checks to see if there is more than one song.
			*/
			if( config.songs.length > 1 ){
				/*
					Stops the active song
				*/
				privateStop();

				/*
					Checks to see if shuffle mode is turned on.
				*/
				if( config.shuffle_on ){
					/*
						Loop around shuffle array if at the end. We need to check if the next
						song is within array. Otherwise we reset it to 0.

						Set new song
					*/
					if( parseInt( config.shuffle_active_index) + 1 < config.shuffle_list.length ){
						var newIndex = parseInt( config.shuffle_active_index) + 1;

						/*
							Sets the active song information.
						*/
						privateSetActiveSongInformation( newIndex, config.shuffle_on );

						config.shuffle_active_index = parseInt(config.shuffle_active_index) + 1;
					}else{
						/*
							Sets the active song information to the beginning of the
							shuffle list
						*/
						privateSetActiveSongInformation( 0, config.shuffle_on );

						config.shuffle_active_index = 0;
					}
				}else{
					/*
						Loop around songs array if at the end. We need to check if the next
						song is within array. Otherwise we reset it to 0.

						Sets new song
					*/
					if( parseInt(config.active_index) + 1 < config.songs.length ){
						var newIndex = parseInt( config.active_index ) + 1;

						/*
							Sets the active song information
						*/
						privateSetActiveSongInformation( newIndex, config.shuffle_on );

						config.active_index = parseInt(config.active_index) + 1;
					}else{
						/*
							Sets the active song information to the beginning of the
							songs list
						*/
						privateSetActiveSongInformation( 0, config.shuffle_on );

						config.active_index = 0;
					}
				}

				/*
					Runs the song change function.
				*/
				privateAfterSongChanges();
			}else{
				/*
					If there is nothing coming up, pause the play
					button and sync the current times. This will set the play pause
					buttons to paused (stopped) state and the current times to
					0:00
				*/
				privateSetPlayPauseButtonsToPause();
				privateSyncCurrentTimes();			
			}
		}

		/*
			Fire song ended event handler.
		*/
		privateRunCallback('after_song_ended');
	}

	/*--------------------------------------------------------------------------
		HANDLER FOR: 'timeupdate' on main audio element.

		Everytime the active audio time updates, this function is called.
		It will update the visual display for everything related to the time
		and location compared to the duration of the song.

		TODO: Change all querySelector functions to querySelectorAll functions
	--------------------------------------------------------------------------*/
	function privateUpdateTime(){
		/*
			Gets the current time of the song in seconds and minutes.
		*/
		var current_seconds = ( Math.floor( config.active_song.currentTime % 60 ) < 10 ? '0' : '' ) + 
							    Math.floor( config.active_song.currentTime % 60 );

		var current_minutes = Math.floor( config.active_song.currentTime / 60 );

		/*
			Gets the current song's duration in seconds and minutes.
		*/

		var song_duration_seconds = ( Math.floor( config.active_song.duration % 60 ) < 10 ? '0' : '' ) + 
									  		Math.floor( config.active_song.duration % 60 );

		var song_duration_minutes = Math.floor( config.active_song.duration / 60 );
		
		/*
			Finds the current slider that represents the active song's time.
			If there is only one song slider, than that's the one we adjust
			to represent the song's current time, otherwise we find the one
			with the same song index as the active song.
		*/
		var current_slider = '';

		if( document.querySelector('[amplitude-singular-song-slider="true"]') ){
			current_slider = document.querySelector('[amplitude-singular-song-slider="true"]');
		}else{
			current_slider = document.querySelector('input[amplitude-song-index="'+config.active_index+'"]');
		}

		/*
			When we find the right visualization, we set the value to the percentage of 
			completion only if the song isn't live. Since it's 'infinite' if 
			it's a live source, it wouldn't make sense to update the time
			display.
		*/

		if( !config.active_metadata.live ){
			/*
				Finds the current song time visualization. If there is only one
				song time visualization, then that's the one we adjust to represent
				the current song time's visualization. Otherwise we find the one
				with the same song index as the active song.
			*/
			if( document.querySelectorAll('[amplitude-single-song-time-visualization="true"]').length > 0 ){
				current_song_time_visualization = document.querySelectorAll('[amplitude-single-song-time-visualization="true"]');

				for( var i = 0; i < current_song_time_visualization.length; i++ ){
					var current_song_time_visualization_status = current_song_time_visualization[i].querySelectorAll('.amplitude-song-time-visualization-status');
					var visualization_width = current_song_time_visualization[i].offsetWidth;

					current_song_time_visualization_status[0].setAttribute('style', 'width:' +( visualization_width * ( config.active_song.currentTime / config.active_song.duration ) ) + 'px'); 
				}
			}

			if( document.querySelectorAll('.amplitude-song-time-visualization[amplitude-song-index="'+config.active_index+'"]').length > 0 ){
				current_song_time_visualization = document.querySelectorAll('.amplitude-song-time-visualization[amplitude-song-index="'+config.active_index+'"]');
			
				for( var i = 0; i < current_song_time_visualization.length; i++ ){
					var current_song_time_visualization_status = current_song_time_visualization[i].querySelectorAll('.amplitude-song-time-visualization-status');
					var visualization_width = current_song_time_visualization[i].offsetWidth;

					current_song_time_visualization_status[0].setAttribute('style', 'width:' +( visualization_width * ( config.active_song.currentTime / config.active_song.duration ) ) + 'px'); 
				}
			}
		}


		/*
			Looks for an element with attributes that define which part of
			the time should be inserted into the html. If there is a singlular
			display, we update the singular. If there are multiple, we only do 
			the first one on the query, it wouldn't make sense to do all of them.
		*/
		if( document.querySelectorAll('[amplitude-single-current-minutes="true"]').length > 0 ){
			var mainCurrentMinuteSelectors = document.querySelectorAll('[amplitude-single-current-minutes="true"]');
			for( var i = 0; i < mainCurrentMinuteSelectors.length; i++ ){
				mainCurrentMinuteSelectors[i].innerHTML = current_minutes;
			}
		}

		if( document.querySelectorAll('.amplitude-current-minutes[amplitude-song-index="'+config.active_index+'"]').length > 0 ){
			var currentMinuteSelectors = document.querySelectorAll('.amplitude-current-minutes[amplitude-song-index="'+config.active_index+'"]');
			for( var i = 0; i < currentMinuteSelectors.length; i++ ){
				currentMinuteSelectors[i].innerHTML = current_minutes;
			}
		}

		if( document.querySelectorAll('[amplitude-single-current-seconds="true"]').length > 0 ){
			var mainCurrentSecondSelectors = document.querySelectorAll('[amplitude-single-current-seconds="true"]');
			for( var i = 0; i < mainCurrentSecondSelectors.length; i++ ){
				mainCurrentSecondSelectors[i].innerHTML = current_seconds;
			}
		}

		if( document.querySelectorAll('.amplitude-current-seconds[amplitude-song-index="'+config.active_index+'"]').length > 0 ){
			var currentSecondSelectors = document.querySelectorAll('.amplitude-current-seconds[amplitude-song-index="'+config.active_index+'"]');
			for( var i = 0; i < currentSecondSelectors.length; i++ ){
				currentSecondSelectors[i].innerHTML = current_seconds;
			}
		}
		
		if( !config.active_metadata.live ){
			if( document.querySelectorAll('[amplitude-single-duration-minutes="true"]').length > 0 ){
				var mainDurationMinuteSelectors = document.querySelectorAll('[amplitude-single-duration-minutes="true"]');
				for( var i = 0; i < mainDurationMinuteSelectors.length; i++ ){
					mainDurationMinuteSelectors[i].innerHTML = song_duration_minutes;
				}
			}

			if( document.querySelectorAll('.amplitude-duration-minutes[amplitude-song-index="'+config.active_index+'"]').length > 0 ){
				var durationMinuteSelectors = document.querySelectorAll('.amplitude-duration-minutes[amplitude-song-index="'+config.active_index+'"]');
				for( var i = 0; i < durationMinuteSelectors.length; i++ ){
					durationMinuteSelectors[i].innerHTML = song_duration_minutes;
				}
			}

			if( document.querySelectorAll('[amplitude-single-duration-seconds="true"]').length > 0 ){
				var mainDurationSecondSelectors = document.querySelectorAll('[amplitude-single-duration-seconds="true"]');
				for( var i = 0; i < mainDurationSecondSelectors.length; i++ ){
					mainDurationSecondSelectors[i].innerHTML = song_duration_seconds;
				}
			}

			if( document.querySelectorAll('.amplitude-duration-seconds[amplitude-song-index="'+config.active_index+'"]').length > 0 ){
				var durationSecondSelectors = document.querySelectorAll('.amplitude-duration-seconds[amplitude-song-index="'+config.active_index+'"]');
				for( var i = 0; i < durationSecondSelectors.length; i++ ){
					durationSecondSelectors[i].innerHTML = song_duration_seconds;
				}
			}
		}

		/*
			When we find the right slider, we set the value to the percentage of 
			completion only if the song isn't live. Since it's 'infinite' if 
			it's a live source, it wouldn't make sense to update the time
			display.
		*/
		if( !config.active_metadata.live && current_slider ){
			if( config.active_metadata.duration != undefined ){
				current_slider.value = ( ( config.active_song.currentTime / config.active_metadata.duration ) * 1000 ) * 100;
			}else{
				current_slider.value = ( config.active_song.currentTime / config.active_song.duration ) * 100;
			}	
		}
	}

	/*--------------------------------------------------------------------------
		HANDLER FOR: 'amplitude-shuffle'

		Handles a click for the shuffle element.
	--------------------------------------------------------------------------*/
	function privateShuffleClickHandle(){
		/*
			If the shuffle is already on, then swe turn it off
			and clear out the existing shuffle list. We also
			restore the active index back to 0.
		*/
		if( config.shuffle_on ){
			config.shuffle_on = false;
			config.shuffle_list = {};
			config.shuffle_active_index = 0;
		}else{
			/*
				If the shuffle is not on then we turn on shuffle
				and re-shuffle the songs.
			*/
			config.shuffle_on = true;
			privateShuffleSongs();
		}

		/*
			We then sync the visual shuffle button so it has the proper
			class representing the state of the shuffle functionality.
		*/
		privateSyncVisualShuffle();
	}

	/*--------------------------------------------------------------------------
		HANDLER FOR: 'amplitude-repeat'

		Handles a click for the repeat element.
	--------------------------------------------------------------------------*/
	function privateRepeatClickHandle(){
		/*
			If repeat is on, we turn it off. Othwerwise we turn repeat on.
		*/
		if( config.repeat ){
			config.repeat = false;
		}else{
			config.repeat = true;
		}

		privateSyncVisualRepeat();
	}

	/*
	|--------------------------------------------------------------------------
	| HELPER FUNCTIONS
	|--------------------------------------------------------------------------
	| For the sake of code clarity, these functions perform helper tasks 
	| assisting the logical functions with what they need such as setting
	| the proper song index after an event has occured.
	*/

	/*--------------------------------------------------------------------------
		Finishes the initalization of the config. Takes all of the user defined
		parameters and makes sure they override the defaults. The important
		config information is assigned in the publicInit() function.

		This function can be called from 2 different locations:
		1. Right away on init after the important settings are defined.
		2. After all of the Soundcloud URLs are resolved properly and
		soundcloud is configured.  We will need the proper URLs from Soundcloud
		to stream through Amplitude so we get those right away before we
		set the information and the active song

		@param user_config JSON representation of the user's settings when
		they init Amplitude.

		TODO: In all functions that call privateSetActiveSongInformation, have
		the active_index set there as well.

		TODO: Make sure that if the user sends a start_song that it's an integer
		and nothing else. Debug if NOT an integer.

		TODO: Make the user enter a volume between 0 and 100. It's a little
		easier sounding.

		TODO: Make sure the user enters a volume increment or decrement between
		1 and 100 to ensure something happens when they click the increment
		or decrement button.
	--------------------------------------------------------------------------*/
	function privateSetConfig( user_config ){
		/*
			If Amplitude is not in dynamic mode, we determine what the 
			start song should be. Dynamic mode doesn't have any songs on 
			config because the user will be sending them to Amplitude 
			dynamically.
		*/
		if( !config.dynamic_mode ){
			/*
				If the user provides a starting song index then we set
				the active song information to the song at that index.
			*/
			if( user_config.start_song != undefined ){
				privateSetActiveSongInformation( user_config.start_song, false );
				/*
					TODO: REMOVE Sets the user defined index.
				*/
				config.active_index = user_config.start_song;
			}else{
				privateSetActiveSongInformation( 0, false );

				/*
					TODO: REMOVE Sets the active index to the first song.
				*/
				config.active_index = 0;
			}
		}

		/*
			If live is not defined, assume it is false. The reason for
			this definition is if we play/pause we disconnect
			and re-connect to the stream so we don't fill up our cache
			with unused audio and we aren't playing outdated audio upon
			resume.
		*/
		if( config.active_metadata.live == undefined ){
			config.active_metadata.live = false;
		}

		/*
			If the user wants the song to be pre-loaded for instant
			playback, they set it to true. By default it's set to just
			load the metadata.
		*/
		config.active_song.preload = ( user_config.preload != undefined ? user_config.preload : "metadata" );
		
		/*
			Initializes the user defined callbacks. This should be a JSON
			object that contains a key->value store of the callback name
			and the name of the function the user needs to call.
		*/
		config.callbacks = ( user_config.callbacks != undefined ? user_config.callbacks : {} );

		/*
			The user can define a starting volume in a range of 0-1 with
			0 being muted and 1 being the loudest. After the config is set
			Amplitude sets the active song's volume to the volume defined
			by the user.
		*/
		config.volume = ( user_config.volume != undefined ? user_config.volume : .5 );
		config.active_song.volume = config.volume;

		/*
			The user can set the volume increment and decrement values between 1 and 100
			for when the volume up or down button is pressed.  The default is an increase
			or decrease of 5.
		*/
		config.volume_increment = ( user_config.volume_increment != undefined ? user_config.volume_increment : 5 );
		config.volume_decrement = ( user_config.volume_decrement != undefined ? user_config.volume_decrement : 5 );

		/*
			The user can turn off Amplitude handling the song elements (putting the meta data into
			certain fields when the song is playing or changed).  This would be if the user wanted
			to hard code this information which would probably be most popular in single song 
			instances.
		*/
		config.handle_song_elements = ( user_config.handle_song_elements != undefined ? user_config.handle_song_elements : true );

		/*
			If the user defines default album art, this image will display if the active
			song doesn't have album art defined.
		*/
		config.default_album_art = ( user_config.default_album_art != undefined ? user_config.default_album_art : '' );		
		
		/*
			The user can define a visualization backup to use if they are using
			visualizations (song visualizations not song time visualizations) and the
			browser doesn't support it.  This can be "nothing" meaning that the
			visualization element is removed otherwise it can be the album art
			of the song being played.
		*/
		config.visualization_backup = ( user_config.visualization_backup != undefined ? user_config.visualization_backup : 'nothing' );

		/*
			Sets initialized to true, so the user can't re-initialize
			and mess everything up.
		*/
		config.initialized = true;

		/*
			Since the user can define a start volume, we want our volume
			sliders to sync with the user defined start value.
		*/
		privateSyncVolumeSliders();

		/*
			Sets up the player if the browser doesn't have the audio context
		*/
		privateSyncNoAudioContext();

		/*
			Set all of the current time elements to 0:00 upon initialization
		*/
		privateSyncCurrentTimes();

		/*
			Syncs all of the song status sliders so the user can't set the
			HTML 5 range element to be something invalid on load like half
			way through the song by default.
		*/
		privateResetSongStatusSliders();

		privateCheckSongVisualization();

		/*
			Initialize the visual elements for the song if the user
			wants Amplitude to handle the changes. This is new 
			compared to previous versions where Amplitude automatically
			handled the song elements.
		*/
		if( config.handle_song_elements ){
			privateDisplaySongMetadata();
		}

		/*
			Removes any classes set by the user so any inconsistencies
			with start song and actual song are displayed correctly.
		*/
		privateSyncVisualPlayingContainers();

		/*
			Sets the active song container for the song that will be
			played. This adds a class to an element containing the
			visual representation of the active song .
		*/
		privateSetActiveContainer();

		/*
			Sets the temporary user conifg back to empty. We are done
			using it.
		*/
		temp_user_config = {};

		/*
			Run after init callback
		*/
		privateRunCallback("after_init");

		/*
			If the user turns on autoplay the song will play automatically.
		*/
		if( user_config.autoplay ){
			/*
				Gets the attribute for song index so we can check if
				there is a need to change the song.  In some scenarios
				there might be multiple play classes on the page. In that
				case it is possible the user could click a different play
				class and change the song.
			*/
			var playing_song_index = config.start_song;

			/*
				We set the new song if the user clicked a song with a different
				index. If it's the same as what's playing then we don't set anything. 
				If it's different we reset all song sliders.
			*/
			if( privateCheckNewSong( playing_song_index ) ){
				privateChangeSong( playing_song_index );

				privateResetSongStatusSliders();
			}

			/*
				Start the visualizations for the song.
			*/
			privateStartVisualization();
			
			/*
				If there are any play pause buttons we need
				to sync them to playing for auto play.
			*/
			privateChangePlayPauseState('playing');

			/*
				Play the song through the core play function.
			*/
			privatePlay();
		}
	}

	/*--------------------------------------------------------------------------
		Handles the back up functionality for visualizations. This happens
		if there is no AudioContext available or the song is live.

		The two settings are:
		1. "nothing" DEFAULT. It will remove the visualization element from the
		page.

		2. "album-art" Instead of the visualization, the element that would have
		container for the visualization will instead display the album
		art for the now playing song.

		TODO: Make sure this is only run if the user is using visualizations
		in their design.

		TODO: Change querySelector to querySelectorAll once again justifying
		the use of a global query all function for visual syncing.
	--------------------------------------------------------------------------*/
	function privateHandleVisualizationBackup(){
		switch( config.visualization_backup ){
			/*
				Removes the visualization element from the page.
			*/
			case "nothing":
				
				if( document.getElementById('amplitude-visualization') ){
					document.getElementById('amplitude-visualization').remove();
				}
			break;
			/*
				Sets up the old visualization element to contain the
				album art.
			*/
			case "album-art":
				/*
					Gets the old visualizationelement.
				*/
				var old_visualization = document.getElementById('amplitude-visualization');

				/*
					If there is a visualization element then we proceed.
				*/	
				if( old_visualization ){
					/*
						Gets the parent node to append the inner node to containing
						the album art.
					*/
					var parent_old_visualization = old_visualization.parentNode;

					var new_album_art = document.createElement('img');
					/*
						Sets the attribute to be the song infor for the cover
						art on the new element. Also apply the class 'amplitude-album-art'
					*/
					new_album_art.setAttribute('amplitude-song-info', 'cover');
					new_album_art.setAttribute('class', 'amplitude-album-art');

					/*
						TODO: is this the right place to do this? Shouldn't this happen
						AFTER we replace the visualization?
					*/
					if( document.querySelector('[amplitude-song-info="cover"]') ){

						if( config.active_metadata.cover_art_url != undefined){
							new_album_art.setAttribute( 'src', config.active_metadata.cover_art_url );
							document.querySelector('[amplitude-song-info="cover"]').setAttribute('src', config.active_metadata.cover_art_url);
						}else if( config.default_album_art != '' ){
							new_album_art.setAttribute( 'src', config.default_album_art );
						}else{
							new_album_art.setAttribute( 'src', '' );
						}
					}

					parent_old_visualization.replaceChild( new_album_art, old_visualization );
				}
			break;
		}
	}

	/*--------------------------------------------------------------------------
		Sets information relating to the active song.
	--------------------------------------------------------------------------*/
	function privateSetActiveSongInformation( index, shuffle ){
		if( shuffle ){
			/*
				If the current state is in shuffle mode, we set the
				active song to the song at the index in the shuffle list.
				Amplitude will also grab specific meta data from the song 
				in the shuffle list and set the attributes in the config 
				accordingly.
			*/
			config.active_song.src 	= config.shuffle_list[index].url;
			config.active_metadata 	= config.shuffle_list[index];
			config.active_album 	= config.shuffle_list[index].album;
		}else{
			/*
				If the current state is not in shuffle mode, we will
				grab the index from the songs array and set the active
				song to the source of that song. Amplitude will also 
				grab certain attributes from the song and use them
				in the config accordingly.
			*/
			config.active_song.src 	= config.songs[index].url;
			config.active_metadata 	= config.songs[index];
			config.active_album 	= config.songs[index].album;
		}
	}
	


	/*--------------------------------------------------------------------------
		Writes out debug message to the console if enabled.

		@param string message The string that gets printed to
		alert the user of a debugging error.
	--------------------------------------------------------------------------*/
	function privateWriteDebugMessage( message ){
		if( config.debug ){
			console.log( message );
		}
	}

	/*--------------------------------------------------------------------------
		Checks to see if a new song should be prepared for playing

		@param int new_song_index The integer index of the song
		that will be played. 
	--------------------------------------------------------------------------*/
	function privateCheckNewSong( new_song_index ){
		/*
			If the new song index is null, then nothing was defined, 
			so we assume it's in a single song scenario or a main control click
			and we don't need to worry about changing the song. If there was an
			index defined and the index and active_index are different then we 
			adjust the song.
		*/
		if( new_song_index != null && ( new_song_index != config.active_index ) ){
			return true;
		}else{
			return false;
		}
	}

	/*--------------------------------------------------------------------------
		Gets Amplitude ready for a song change. Syncs elements and runs
		necessary callbacks.

		@param int new_song_index The integer index of the song
		that will be played. 
	--------------------------------------------------------------------------*/
	function privateChangeSong( new_song_index ){
		/*
			Stops the currently playing song.
		*/
		privateStop();

		/*
			Checks to see if the new song is a different album.
		*/
		privateCheckNewAlbum( config.songs[new_song_index].album );

		/*
			Changes the active song index that is being played.
		*/
		config.active_index = new_song_index;

		/*
			Sets the active song information for the new song that will
			be played.
		*/
		privateSetActiveSongInformation( new_song_index, config.shuffle_on );


		if( config.album_change ){
			privateRunCallback('after_album_change');
			config.album_change = false;
		}

		

		/*
			If it's a new song and the user wants amplitude to handle
			the song elements, we need to set the information for
			the song.
		*/
		if( config.handle_song_elements ){
			privateDisplaySongMetadata();
		}

		/*
			We set the current times to 0:00 when song changes
			so all of the pages players will be synchronized.
		*/
		privateSyncCurrentTimes();

		privateCheckSongVisualization();

		privateSetActiveContainer();

	}

	/*--------------------------------------------------------------------------
		Checks to see if a new album is playing. This allows for
		multiple albums to be initialized on the same page.
		Through CSS you can show and hide albums and simulate
		multiple playlists. This method is called after there is a for
		sure change to see if the next song's album is different than
		the song that will soon to be previous' album.

		@param string new_album The string of the new album
		to see if it has changed.

		TODO: Research if we should return true/false instead of setting the
		config.

		TODO: Makes sure the song actually has an album before running.
	--------------------------------------------------------------------------*/
	function privateCheckNewAlbum( new_album ){
		/*
			If the new album isn't the same as the
			active album, we set the change to true
			and run the before_album_change callback.
		*/
		if( config.active_album != new_album ){
			config.album_change = true;
			privateRunCallback('before_album_change');
		}
	}

	/*--------------------------------------------------------------------------
		Called when a song changes. Synchronizes everything necessary on 
		the page to handle the song change.

		TODO: Check to see if using visualizations.
	--------------------------------------------------------------------------*/
	function privateAfterSongChanges(){
		/*
			After the new song is set, we see if we need to change
			the visualization. If it's different, we need to start 
			the new one.
		*/
		if( privateCheckSongVisualization() ){
			privateStartVisualization();
		}

		/*
			After the new song is set, Amplitude will update the
			visual elements containing information about the
			new song if the user wants Amplitude to.
		*/
		if( config.handle_song_elements ){
			privateDisplaySongMetadata();
		}

		/*
			Sync song status sliders. Sets them back to 0 because
			when the song is changing there won't be any songs currently
			playing.
		*/
		privateResetSongStatusSliders();

		/*
			We set the current times to 0:00 when song changes
			so all of the page's players will be synchronized.
		*/
		privateSyncCurrentTimes();

		/*
			Remove class from all containers
		*/
		privateSyncVisualPlayingContainers();

		/*
			Set new active song container by applying a class
			to the visual element containing the visual representation
			of the song that is actively playing.
		*/
		privateSetActiveContainer();

		/*
			Plays the new song.
		*/
		privatePlay();
	}

	/*--------------------------------------------------------------------------
		Shuffles songs.
		Based off of: http://www.codinghorror.com/blog/2007/12/the-danger-of-naivete.html
	--------------------------------------------------------------------------*/
	function privateShuffleSongs(){
		var shuffle_temp = new Array( config.songs.length );

		for( i = 0; i < config.songs.length; i++ ){
			shuffle_temp[i] = config.songs[i];
		}

		for( i = config.songs.length - 1; i > 0; i-- ){
			rand_num = Math.floor( ( Math.random() * config.songs.length ) + 1 );
			privateShuffleSwap( shuffle_temp, i, rand_num - 1 );
		}

		config.shuffle_list = shuffle_temp;
	}

	/*--------------------------------------------------------------------------
		Swaps and randomizes the song shuffle.

		@param JSON shuffle_list The list of songs that is going to
		be shuffled

		@param int original The original index of the song in the
		songs array.

		@param int random The randomized index that will be the
		new index of the song in the shuffle array.
	--------------------------------------------------------------------------*/
	function privateShuffleSwap( shuffle_list, original, random ){
		var temp = shuffle_list[ original ];
		shuffle_list[ original ] = shuffle_list[ random ];
		shuffle_list[ random ] = temp;
	}

	/*--------------------------------------------------------------------------
		Runs callback for specific function

		@param string The name of the call back. Also used as the index that
		the user can use in the callback array to define their callback method.
	--------------------------------------------------------------------------*/
	function privateRunCallback( callback_name ){
		if( config.callbacks[callback_name] ){
			var callback_function = window[ config.callbacks[ callback_name ] ];
			callback_function();
		}
	}

	/*--------------------------------------------------------------------------
		If there is a visualization specifically for a song, we set that
		as the active visualization. Only if one is specified, otherwise
		nothing changes and we continue using the active visualization.

		@returns BOOL Returns true if there is a specific visualization for
		the song.
	--------------------------------------------------------------------------*/
	function privateCheckSongVisualization(){
		var changed = false;

		/*
			Checks to see if the song actually has a specific visualization
			defined.
		*/
		if( config.active_metadata.visualization ){
			
			/*
				If the visualization is different and there is an active
				visualization. We must stop the active visualization
				before setting the new one.
			*/
			if( config.active_metadata.visualization != config.active_visualization && config.active_visualization != '' ){
				privateStopVisualization();
				
				/*
					Set the visualization changed to true
					so we return the status change.
				*/
				changed = true;

				/*
					Sets the active visualization to the new
					visualization that the song uses.
				*/
				config.active_visualization = config.active_metadata.visualization;
			}
		}
		/*
			Returns the status of the new song visualization.
			If there is a change it returns true and we will
			have to start the the visualization.
		*/
		return changed;
	}

	/*--------------------------------------------------------------------------
		Sets the visual elements containg the active song
		metadata
	--------------------------------------------------------------------------*/
	function privateDisplaySongMetadata(){
		/*
			Sets all elements that will contain the active song's name metadata
		*/
		if( document.querySelectorAll('[amplitude-song-info="name"]') ){
			var metaNames = document.querySelectorAll('[amplitude-song-info="name"]');
			for( i = 0; i < metaNames.length; i++ ){
				metaNames[i].innerHTML = config.active_metadata.name;
			}
		}

		/*
			Sets all elements that will contain the active song's artist metadata
		*/
		if( document.querySelectorAll('[amplitude-song-info="artist"]') ){
			var metaArtist = document.querySelectorAll('[amplitude-song-info="artist"]');
			for( i = 0; i < metaArtist.length; i++ ){
				metaArtist[i].innerHTML = config.active_metadata.artist;
			}
		}

		/*
			Sets all elements that will contain the active song's album metadata
		*/
		if( document.querySelectorAll('[amplitude-song-info="album"]') ){
			var metaAlbum = document.querySelectorAll('[amplitude-song-info="album"]');
			for( i = 0; i < metaAlbum.length; i++ ){
				metaAlbum[i].innerHTML = config.active_metadata.album;
			}
		}

		/*
			Sets all elements that will contain the active song's cover art metadata
		*/
		if( document.querySelectorAll('[amplitude-song-info="cover"]') ){
			var coverImages = document.querySelectorAll('[amplitude-song-info="cover"]');
			for( i = 0; i < coverImages.length; i++ ){
				/*
					Checks to see if first, the song has a defined cover art and uses
					that. If it does NOT have defined cover art, checks to see if there
					is a default.  Otherwise it just sets the src to '';
				*/
				if( config.active_metadata.cover_art_url != undefined){
					coverImages[i].setAttribute('src', config.active_metadata.cover_art_url);
				}else if( config.default_album_art != '' ){
					coverImages[i].setAttribute('src', config.default_album_art);
				}else{
					coverImages[i].setAttribute('src', '');
				}
			}
			
		}

		/*
			Station information for live streams
		*/

		/*
			Sets all of the elements that will contain the live stream's call sign metadata
		*/
		if( document.querySelectorAll('[amplitude-song-info="call-sign"]') ){
			var metaCallSign = document.querySelectorAll('[amplitude-song-info="call-sign"]');
			for( i = 0; i < metaCallSign.length; i++ ){
				metaCallSign[i].innerHTML = config.active_metadata.call_sign;
			}
		}

		/*
			Sets all of the elements that will contain the live stream's station name metadata
		*/
		if( document.querySelectorAll('[amplitude-song-info="station-name"]') ){
			var metaStationName = document.querySelectorAll('[amplitude-song-info="station-name"]');
			for( i = 0; i < metaStationName.length; i++ ){
				metaStationName[i].innerHTML = config.active_metadata.station_name;
			}
		}

		/*
			Sets all of the elements that will contain the live stream's location metadata
		*/
		if( document.querySelectorAll('[amplitude-song-info="location"]') ){
			var metaStationLocation = document.querySelectorAll('[amplitude-song-info="location"]');
			for( i = 0; i < metaStationLocation.length; i++ ){
				metaStationLocation[i].innerHTML = config.active_metadata.location; 
			}
		}

		/*
			Sets all of the elements that will contain the live stream's frequency metadata
		*/
		if( document.querySelectorAll('[amplitude-song-info="frequency"]') ){
			var metaStationFrequency = document.querySelectorAll('[amplitude-song-info="frequency"]');
			for( i = 0; i < metaStationFrequency.length; i++ ){
				metaStationFrequency[i].innerHTML = config.active_metadata.frequency;
			}	
		}

		/*
			Sets all of the elements that will contain the live stream's station art metadata
			TODO: Rename coverImages to stationArtImages
		*/
		if( document.querySelectorAll('[amplitude-song-info="station-art"]') ){
			var coverImages = document.querySelectorAll('[amplitude-song-info="station-art"]');
			/*
					Checks to see if first, the song has a defined station art and uses
					that. If it does NOT have defined station art, checks to see if there
					is a default.  Otherwise it just sets the src to '';
				*/
			for( i = 0; i < coverImages.length; i++ ){
				if( config.active_metadata.cover_art_url != undefined){
					coverImages[i].setAttribute('src', config.active_metadata.station_art_url);
				}else if( config.default_album_art != '' ){
					coverImages[i].setAttribute('src', config.default_album_art);
				}else{
					coverImages[i].setAttribute('src', '');
				}
			}
			
		}
	}

	/*--------------------------------------------------------------------------
		Applies the class 'amplitude-active-song-container' to the element 
		containing visual information regarding the active song.

		TODO: Make sure that when shuffling, this changes accordingly.
	--------------------------------------------------------------------------*/
	function privateSetActiveContainer(){
		var songContainers = document.getElementsByClassName('amplitude-song-container');

		/*
			Removes all of the active song containrs.
		*/
		for( i = 0; i < songContainers.length; i++ ){
			songContainers[i].classList.remove('amplitude-active-song-container');
		}

		/*
			Finds the active index and adds the active song container to the element
			that represents the song at the index. 
		*/
		if( document.querySelectorAll('.amplitude-song-container[amplitude-song-index="'+config.active_index+'"]') ){
			var songContainers = document.querySelectorAll('.amplitude-song-container[amplitude-song-index="'+config.active_index+'"]');

			for( i = 0; i < songContainers.length; i++ ){
				songContainers[i].classList.add('amplitude-active-song-container');
			}
		}
	}

	/*--------------------------------------------------------------------------
		Calls the start method on the active visualization.
	--------------------------------------------------------------------------*/
	function privateStartVisualization(){
		/*
			If the visualization is not started, and there are visualizations
			ready to be activated, we check to see if the user defined a 
			starting visualization.  If there is a starting visualization,
			then we start that one, otherwise we grab the first visualization
			defined and start that one.
		*/

		if( !config.visualization_started && Object.keys(config.visualizations).length > 0){
			if( config.active_visualization != '' ){
				config.visualizations[config.active_visualization].startVisualization(config.active_song);
				config.current_visualization = config.visualizations[config.active_visualization];
			}else{
				for(first_visualization in config.visualizations);

				config.visualizations[first_visualization].startVisualization(config.active_song);
				config.current_visualization = config.visualizations[first_visualization];
			}
			config.visualization_started = true;
		}
	}

	/*--------------------------------------------------------------------------
		Calls the stop method of the active visualization.
		If the visualization is started, we stop it.
	--------------------------------------------------------------------------*/
	function privateStopVisualization(){
		if( config.visualization_started && Object.keys(config.visualizations).length > 0){
			config.current_visualization.stopVisualization();
			config.visualization_started = false;
		}
	}

	/*
	|--------------------------------------------------------------------------
	| SOUNDCLOUD SPECIFIC HELPERS
	|--------------------------------------------------------------------------
	| These helpers wrap around the basic functions of the Soundcloud API
	| and get the information we need from SoundCloud to make the songs
	| streamable through Amplitude
	*/
	/*--------------------------------------------------------------------------
		Loads the soundcloud SDK for use with Amplitude so the user doesn't have
		to load it themselves.
		With help from: http://stackoverflow.com/questions/950087/include-a-javascript-file-in-another-javascript-file
	--------------------------------------------------------------------------*/
	function privateLoadSoundcloud(){
		var head = document.getElementsByTagName('head')[0];
		var script = document.createElement('script');

		script.type = 'text/javascript';
		/*
			URL to the remote soundcloud SDK
		*/
		script.src = 'https://connect.soundcloud.com/sdk.js';
		script.onreadystatechange = privateInitSoundcloud;
		script.onload = privateInitSoundcloud;

		head.appendChild( script );
	}

	/*--------------------------------------------------------------------------
		Initializes soundcloud with the key provided.
	--------------------------------------------------------------------------*/
	function privateInitSoundcloud(){
		/*
			Calls the SoundCloud initialize function
			from their API and sends it the client_id
			that the user passed in.
		*/
		SC.initialize({
			client_id: config.soundcloud_client
		});

		/*
			Gets the streamable URLs to run through Amplitue. This is
			VERY important since Amplitude can't stream the copy and pasted
			link from the SoundCloud page, but can resolve the streaming
			URLs from the link.
		*/
		privateGetSoundcloudStreamableURLs();
	}

	/*--------------------------------------------------------------------------
		Gets the streamable URL from the URL provided for
		all of the soundcloud links.  This will loop through
		and set all of the information for the soundcloud
		urls.
	--------------------------------------------------------------------------*/
	function privateGetSoundcloudStreamableURLs(){
		var soundcloud_regex = /^https?:\/\/(soundcloud.com|snd.sc)\/(.*)$/;
		
		for( var i = 0; i < config.songs.length; i++ ){
			/*
				If the URL matches soundcloud, we grab
				that url and get the streamable link
				if there is one.
			*/
			if( config.songs[i].url.match( soundcloud_regex ) ){
				config.soundcloud_song_count++;
				privateSoundcloudResolveStreamable(config.songs[i].url, i);
			}
		}
	}

	/*--------------------------------------------------------------------------
		Due to Soundcloud SDK being asynchronous, we need to scope the
		index of the song in another function. The privateGetSoundcloudStreamableURLs
		function does the actual iteration and scoping.
	--------------------------------------------------------------------------*/
	function privateSoundcloudResolveStreamable(url, index){
		SC.get('/resolve/?url='+url, function( sound ){
			/*
				If streamable we get the url and bind the client ID to the end
				so Amplitude can just stream the song normally. We then overwrite
				the url the user provided with the streamable URL.
			*/
			if( sound.streamable ){
				config.songs[index].url = sound.stream_url+'?client_id='+config.soundcloud_client;

				/*
					If the user want's to use soundcloud art, we overwrite the
					cover_art_url with the soundcloud artwork url.
				*/
				if( config.soundcloud_use_art ){
					config.songs[index].cover_art_url = sound.artwork_url;
				}

				/*
					Grab the extra metadata from soundcloud and bind it to the
					song.  The user can get this through the public function:
					getActiveSongMetadata
				*/
				config.songs[index].soundcloud_data = sound;
			}else{
				/*
					If not streamable, then we print a message to the user stating
					that the song with name X and artist X is not streamable. This
					gets printed ONLY if they have debug turned on.
				*/
				privateWriteDebugMessage( config.songs[index].name +' by '+config.songs[index].artist +' is not streamable by the Soundcloud API' );
			}
			/*
				Increments the song ready counter.
			*/
			config.soundcloud_songs_ready++;

			/*
				When all songs are accounted for, then amplitude is ready
				to rock and we set the rest of the config.
			*/
			if( config.soundcloud_songs_ready == config.soundcloud_song_count ){
				privateSetConfig( temp_user_config );
			}
		});
	}
	
	/*
	|--------------------------------------------------------------------------
	| VISUAL SYNCHRONIZATION METHODS
	|--------------------------------------------------------------------------
	| These methods keep the screen in sync.  For example if there are multiple
	| play/pause buttons and a song changes, we need to set all of the other
	| play/pause buttons to paused state.
	| 
	*/

	/*--------------------------------------------------------------------------
		Sets all of the play/pause buttons to the not playing state.  The 
		click handler will set the actual playing button to the playing state.
	--------------------------------------------------------------------------*/
	function privateSetPlayPauseButtonsToPause(){
		var play_pause_classes = document.getElementsByClassName("amplitude-play-pause");
		/*
			Iterates over all of the play pause classes removing
			the playing class and adding the paused class.
		*/
		for( var i = 0; i < play_pause_classes.length; i++ ){
			play_pause_classes[i].classList.add('amplitude-paused');
			play_pause_classes[i].classList.remove('amplitude-playing');
		}
	}

	/*--------------------------------------------------------------------------
		Changes the play pause state for all classes that need it. This
		iterates through all of the amplitude-play-pause classes for the 
		active index and all of the amplitude-main-play-puase attributes
		making sure everything stays in sync.
	--------------------------------------------------------------------------*/
	function privateChangePlayPauseState( state ){
		/*
			If the state is playing we set all of the classes accordingly.
		*/
		if( state == 'playing' ){
			$("#amplitude-play-pause").removeClass("action-play").addClass("action-pause");

			if( document.querySelectorAll('.amplitude-play-pause[amplitude-song-index="'+config.active_index+'"]').length > 0 ){
				var currentPlayPauseControls = document.querySelectorAll('.amplitude-play-pause[amplitude-song-index="'+config.active_index+'"]');
				
				/*
					Iterates over all of the play pause controls adding the
					'amplitude-playing' classes and removing the 'amplitude-paused'
					classes.
				*/
				for( var i = 0; i < currentPlayPauseControls.length; i++ ){
					currentPlayPauseControls[i].classList.add('amplitude-playing');
					currentPlayPauseControls[i].classList.remove('amplitude-paused');
				}
			}

			/*
				Sets the main song control statuses to playing by removing the
				'amplitude-paused' class and adding the 'amplitude-playing' class.
			*/
			if( document.querySelectorAll('[amplitude-main-play-pause="true"]').length > 0 ){
				var mainControls = document.querySelectorAll('[amplitude-main-play-pause="true"]');

				for( var i = 0; i < mainControls.length; i++ ){
					mainControls[i].classList.add('amplitude-playing');
					mainControls[i].classList.remove('amplitude-paused');
				}
			}
		}

		/*
			If the state is paused, we set all of the classes accordingly.
		*/
		if( state == 'paused' ){
			$("#amplitude-play-pause").removeClass("action-pause").addClass("action-play");

			if( document.querySelectorAll('.amplitude-play-pause[amplitude-song-index="'+config.active_index+'"]').length > 0 ){
				var currentPlayPauseControls = document.querySelectorAll('.amplitude-play-pause[amplitude-song-index="'+config.active_index+'"]');
				
				/*
					Iterates over all of the play pause controls adding the
					'amplitude-paused' classes and removing the 'amplitude-playing'
					classes.
				*/
				for( var i = 0; i < currentPlayPauseControls.length; i++ ){
					currentPlayPauseControls[i].classList.remove('amplitude-playing');
					currentPlayPauseControls[i].classList.add('amplitude-paused');
				}
			}

			/*
				Sets the main song control statuses to paused by removing the
				'amplitude-playing' class and adding the 'amplitude-paused' class.
			*/
			if( document.querySelectorAll('[amplitude-main-play-pause="true"]').length > 0 ){
				var mainControls = document.querySelectorAll('[amplitude-main-play-pause="true"]');

				for( var i = 0; i < mainControls.length; i++ ){
					mainControls[i].classList.add('amplitude-paused');
					mainControls[i].classList.remove('amplitude-playing');
				}
			}
		}
	}

	/*--------------------------------------------------------------------------
		Sets all of the song status sliders to 0, the time update event
		handler will adjust the one that represents the current song
		that is playing.
	--------------------------------------------------------------------------*/
	function privateResetSongStatusSliders(){
		var amplitude_song_sliders = document.getElementsByClassName("amplitude-song-slider");

		/*
			Iterate over all of the song sliders and sets them to
			0 essentially resetting them.
		*/
		for( var i = 0; i < amplitude_song_sliders.length; i++ ){
			amplitude_song_sliders[i].value = 0;
		}
	}

	/*--------------------------------------------------------------------------
		Sets all of the volume sliders to the active song's volume. 
	--------------------------------------------------------------------------*/
	function privateSyncVolumeSliders(){
		var amplitude_volume_sliders = document.getElementsByClassName("amplitude-volume-slider");

		/*
			Iterates over all of the volume sliders for the song, setting the value
			to the config value.
		*/
		for( var i = 0; i < amplitude_volume_sliders.length; i++ ){
			amplitude_volume_sliders[i].value = config.active_song.volume * 100;
		}
	}

	/*--------------------------------------------------------------------------
		Handles the situation if there is no audio context
		available
	--------------------------------------------------------------------------*/
	function privateSyncNoAudioContext(){
		if( !window.AudioContext ){
			privateHandleVisualizationBackup();
		}
	}

	/*--------------------------------------------------------------------------
		Syncs the current time displays so you can have multiple song time
		displays. When a song changes, we need the current minutes and seconds
		to go to 0:00
	--------------------------------------------------------------------------*/
	function privateSyncCurrentTimes(){
		var current_minute_times = document.getElementsByClassName("amplitude-current-minutes");

		for( var i = 0; i < current_minute_times.length; i++ ){
			current_minute_times[i].innerHTML = '0';
		}

		var current_second_times = document.getElementsByClassName("amplitude-current-seconds");

		for( var i = 0; i < current_second_times.length; i++ ){
			current_second_times[i].innerHTML = '00';
		}
	}

	/*--------------------------------------------------------------------------
		For visual playing containers, we find all containers that
		have a class of 'amplitude-song-container' and remove all of 
		the additional 'amplitude-active-song-container' classes.
		When a new song is activated, it will find the parameter
		'amplitude-song-index' and the class of 'amplitude-song-container'
		and give it the additional class 'amplitude-active-song-container'.
	--------------------------------------------------------------------------*/
	function privateSyncVisualPlayingContainers(){
		var visual_playing_containers = document.getElementsByClassName("amplitude-song-container");

		for( var i = 0; i < visual_playing_containers.length; i++ ){
			visual_playing_containers[i].classList.remove('amplitude-active-song-container');
		}
	}

	/*--------------------------------------------------------------------------
		Sets shuffle on for all of the shuffle buttons. Users
		can apply styles to the amplitude-shuffle-on and 
		amplitude-shuffle-off classes. They represent the state
		of the playlist.
	--------------------------------------------------------------------------*/
	function privateSyncVisualShuffle(){
		var shuffle_classes = document.getElementsByClassName("amplitude-shuffle");

		for( var i = 0; i < shuffle_classes.length; i++ ){
			if( config.shuffle_on ){
				shuffle_classes[i].classList.add('amplitude-shuffle-on');
				shuffle_classes[i].classList.remove('amplitude-shuffle-off');
			}else{
				shuffle_classes[i].classList.remove('amplitude-shuffle-on');
				shuffle_classes[i].classList.add('amplitude-shuffle-off');
			}
		}
	}

	/*--------------------------------------------------------------------------
		Sets repeat on for all of the repeat buttons. Users
		can apply styles to the amplitude-repeat-on and 
		amplitude-repeat-off classes. They represent the state
		of the player.
	--------------------------------------------------------------------------*/
	function privateSyncVisualRepeat(){
		var repeat_classes = document.getElementsByClassName("amplitude-repeat");

		for( var i = 0; i < repeat_classes.length; i++ ){
			if( config.repeat ){
				repeat_classes[i].classList.add('amplitude-repeat-on');
				repeat_classes[i].classList.remove('amplitude-repeat-off');
			}else{
				repeat_classes[i].classList.remove('amplitude-repeat-on');
				repeat_classes[i].classList.add('amplitude-repeat-off');
			}
		}
	}

	/*
	|--------------------------------------------------------------------------
	| CORE FUNCTIONAL METHODS
	|--------------------------------------------------------------------------
	| Interacts directly with native functions of the Audio element. Logic
	| leading up to these methods are handled by click handlers which call
	| helpers and visual synchronizers. These are the core functions of AmplitudeJS.
	| Every other function that leads to these prepare the information to be 
	| acted upon by these functions.
	*/

	/*--------------------------------------------------------------------------
		Plays the active song. If the current song is live, it reconnects
		the stream before playing.
	--------------------------------------------------------------------------*/
	function privatePlay(){
		privateRunCallback('before_play');

		if( config.active_metadata.live ){
			privateReconnectStream();
		}

		config.active_song.play();

		privateRunCallback('after_play');
	}

	/*--------------------------------------------------------------------------
		Pauses the active song. If it's live, it disconnects the stream.
	--------------------------------------------------------------------------*/
	function privatePause(){
		config.active_song.pause();
		
		if( config.active_metadata.live ){
			privateDisconnectStream();
		}
	}

	/*--------------------------------------------------------------------------
		Stops the active song by setting the current song time to 0.
		When the user resumes, it will be from the beginning.
		If it's a live stream it disconnects.
	*/
	function privateStop(){
		privateRunCallback('before_stop');

		config.active_song.currentTime = 0;
		config.active_song.pause();

		if( config.active_metadata.live ){
			privateDisconnectStream();
		}

		privateRunCallback('after_stop');
	}

	/*--------------------------------------------------------------------------
		Sets the song volume.

		@param int volume_level A number between 1 and 100 as a percentage of
		min to max for a volume level.
	--------------------------------------------------------------------------*/
	function privateSetVolume( volume_level ){
		config.active_song.volume = volume_level / 100;
	}

	/*--------------------------------------------------------------------------
		Sets the song percentage. If it's a live song, we ignore this because
		we can't skip ahead. This is an issue if you have a playlist with 
		a live source.

		@param int song_percentage A number between 1 and 100 as a percentage of
		song completion.
	--------------------------------------------------------------------------*/
	function privateSetSongLocation( song_percentage ){
		if( !config.active_metadata.live ){
			config.active_song.currentTime = ( config.active_song.duration ) * ( song_percentage / 100 );
		}
	}

	/*--------------------------------------------------------------------------
		Disconnects the live stream
	--------------------------------------------------------------------------*/
	function privateDisconnectStream(){
		config.active_song.src = '';
		config.active_song.load();
	}

	/*--------------------------------------------------------------------------
		Reconnects the live stream
	--------------------------------------------------------------------------*/
	function privateReconnectStream(){
		config.active_song.src = config.active_metadata.url;
		config.active_song.load();
	}

	/*
		Defines which methods and variables are public.
	*/
	return {
		init: publicInit,
		setDebug: publicSetDebug,
		getActiveSongMetadata: publicGetActiveSongMetadata,
		getSongByIndex: publicGetSongByIndex,
		setSongs: publicSetSongs,
		playNow: publicPlayNow,
		playNowById: publicPlayNowById,
		play: publicPlay,
		pause: publicPause,
		registerVisualization: publicRegisterVisualization,
		visualizationCapable: publicVisualizationCapable,
		changeVisualization: publicChangeActiveVisualization,
		addSong: publicAddSong,
		analyser: publicGetAnalyser,
		active: config.active_song
	};
})();


//_______________________________________________________________________

'use strict';
var app = angular.module('muschart', ['ngCookies', 'ngSanitize', 'pascalprecht.translate', 'ui.router']);

//_______________________________________________________________________

'use strict';
app.config(function($cookiesProvider, $locationProvider) {

	$cookiesProvider.defaults.path = '/';
	$cookiesProvider.defaults.expires = new Date(new Date().getTime() + 604800000);
	$locationProvider.html5Mode(true);

});

//_______________________________________________________________________

'use strict';
app.config(function($stateProvider, $urlRouterProvider, CONTROLLER, PATH, STATE, TITLE, URL) {

	var mainHeader = {
		controller: CONTROLLER.USER_CONTROLLER,
		controllerAs: CONTROLLER.CTRL,
		templateUrl: PATH.MAIN_HEADER
	};

	var authenticationHeader = {
		templateUrl: PATH.AUTHENTICATION_HEADER
	};

	var editHeader = {
		templateUrl: PATH.EDIT_HEADER
	};

	var footer = {
		templateUrl: PATH.FOOTER
	};

	$stateProvider
	.state(STATE.LOGIN, {
		title: TITLE.LOGIN,
		url: URL.LOGIN,
		views: {
			header: authenticationHeader,
			main_content: {
				controller: CONTROLLER.USER_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.LOGIN_FORM
			},
			footer: footer
		}
	})

	.state(STATE.REGISTER, {
		title: TITLE.REGISTER,
		url: URL.REGISTER,
		views: {
			header: authenticationHeader,
			main_content: {
				controller: CONTROLLER.USER_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.REGISTER_FORM
			},
			footer: footer
		}
	})

	.state(STATE.SETTINGS, {
		title: TITLE.SETTINGS,
		url: URL.SETTINGS,
		views: {
			header: mainHeader,
			tool: {
				controller: CONTROLLER.SETTINGS_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.SETTINGS_TOOL
			}
		}
	})

	.state(STATE.PLAYLIST, {
		title: TITLE.PLAYLIST,
		url: URL.PLAYLIST,
		views: {
			header: mainHeader,
			main_content: {
				controller: CONTROLLER.TRACK_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.TRACK_CONTENT
			},
			footer: footer
		}
	})

	.state(STATE.ARTIST_ADD, {
		title: TITLE.ARTIST_ADD,
		url: URL.ARTIST_ADD,
		views: {
			header: editHeader,
			main_content: {
				controller: CONTROLLER.ARTIST_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.ARTIST_EDIT_FORM
			},
			footer: footer
		}
	})

	.state(STATE.GENRE_ADD, {
		title: TITLE.GENRE_ADD,
		url: URL.GENRE_ADD,
		views: {
			header: editHeader,
			main_content: {
				controller: CONTROLLER.GENRE_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.GENRE_EDIT_FORM
			},
			footer: footer
		}
	})

	.state(STATE.TRACK_ADD, {
		title: TITLE.TRACK_ADD,
		url: URL.TRACK_ADD,
		views: {
			header: editHeader,
			main_content: {
				controller: CONTROLLER.TRACK_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.TRACK_EDIT_FORM
			},
			footer: footer
		}
	})

	.state(STATE.ARTISTS, {
		title: TITLE.ARTISTS,
		url: URL.ARTISTS,
		params: {
			page: '1'
		},
		views: {
			header: mainHeader,
			tool: {
				controller: CONTROLLER.PAGINATION_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.PAGINATION_TOOL
			},
			main_content: {
				controller: CONTROLLER.ARTIST_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.ARTISTS_CONTENT
			},
			footer: footer
		}
	})

	.state(STATE.GENRES, {
		title: TITLE.GENRES,
		url: URL.GENRES,
		params: {
			page: '1'
		},
		views: {
			header: mainHeader,
			tool: {
				controller: CONTROLLER.PAGINATION_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.PAGINATION_TOOL
			},
			main_content: {
				controller: CONTROLLER.GENRE_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.GENRES_CONTENT
			},
			footer: footer
		}
	})

	.state(STATE.TRACKS, {
		title: TITLE.TRACKS,
		url: URL.TRACKS,
		params: {
			page: '1'
		},
		views: {
			header: mainHeader,
			tool: {
				controller: CONTROLLER.PAGINATION_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.PAGINATION_TOOL
			},
			main_content: {
				controller: CONTROLLER.TRACK_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.TRACKS_CONTENT
			},
			footer: footer
		}
	})

	.state(STATE.ARTIST, {
		url: URL.ARTIST,
		views: {
			header: mainHeader,
			info: {
				controller: CONTROLLER.ARTIST_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.INFO_CONTENT
			},
			additional_content_1: {
				controller: CONTROLLER.GENRE_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.GENRES_CONTENT
			},
			additional_content_2: {
				controller: CONTROLLER.TRACK_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.TRACKS_CONTENT
			},
			footer: footer
		}
	})

	.state(STATE.GENRE, {
		url: URL.GENRE,
		views: {
			header: mainHeader,
			info: {
				controller: CONTROLLER.GENRE_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.INFO_CONTENT
			},
			additional_content_1: {
				controller: CONTROLLER.TRACK_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.TRACKS_CONTENT
			},
			additional_content_2: {
				controller: CONTROLLER.ARTIST_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.ARTISTS_CONTENT
			},
			footer: footer
		}
	})

	.state(STATE.TRACK, {
		url: URL.TRACK,
		views: {
			header: mainHeader,
			info: {
				controller: CONTROLLER.TRACK_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.INFO_CONTENT
			},
			additional_content_1: {
				controller: CONTROLLER.GENRE_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.GENRES_CONTENT
			},
			additional_content_2: {
				controller: CONTROLLER.ARTIST_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.ARTISTS_CONTENT
			},
			footer: footer
		}
	})

	.state(STATE.GENRE_ARTISTS, {
		url: URL.GENRE_ARTISTS,
		params: {
			page: '1'
		},
		views: {
			header: mainHeader,
			info: {
				controller: CONTROLLER.GENRE_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.INFO_CONTENT
			},
			tool: {
				controller: CONTROLLER.PAGINATION_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.PAGINATION_TOOL
			},
			main_content: {
				controller: CONTROLLER.ARTIST_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.ARTISTS_CONTENT
			},
			footer: footer
		}
	})

	.state(STATE.TRACK_ARTISTS, {
		url: URL.TRACK_ARTISTS,
		params: {
			page: '1'
		},
		views: {
			header: mainHeader,
			info: {
				controller: CONTROLLER.TRACK_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.INFO_CONTENT
			},
			tool: {
				controller: CONTROLLER.PAGINATION_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.PAGINATION_TOOL
			},
			main_content: {
				controller: CONTROLLER.ARTIST_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.ARTISTS_CONTENT
			},
			footer: footer
		}
	})

	.state(STATE.USER_ARTISTS, {
		title: TITLE.USER_ARTISTS,
		url: URL.USER_ARTISTS,
		params: {
			page: '1'
		},
		views: {
			header: mainHeader,
			tool: {
				controller: CONTROLLER.PAGINATION_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.PAGINATION_TOOL
			},
			main_content: {
				controller: CONTROLLER.ARTIST_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.ARTISTS_CONTENT
			},
			footer: footer
		}
	})

	.state(STATE.ARTIST_GENRES, {
		url: URL.ARTIST_GENRES,
		params: {
			page: '1'
		},
		views: {
			header: mainHeader,
			info: {
				controller: CONTROLLER.ARTIST_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.INFO_CONTENT
			},
			tool: {
				controller: CONTROLLER.PAGINATION_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.PAGINATION_TOOL
			},
			main_content: {
				controller: CONTROLLER.GENRE_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.GENRES_CONTENT
			},
			footer: footer
		}
	})

	.state(STATE.TRACK_GENRES, {
		url: URL.TRACK_GENRES,
		params: {
			page: '1'
		},
		views: {
			header: mainHeader,
			info: {
				controller: CONTROLLER.TRACK_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.INFO_CONTENT
			},
			tool: {
				controller: CONTROLLER.PAGINATION_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.PAGINATION_TOOL
			},
			main_content: {
				controller: CONTROLLER.GENRE_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.GENRES_CONTENT
			},
			footer: footer
		}
	})

	.state(STATE.USER_GENRES, {
		title: TITLE.USER_GENRES,
		url: URL.USER_GENRES,
		params: {
			page: '1'
		},
		views: {
			header: mainHeader,
			tool: {
				controller: CONTROLLER.PAGINATION_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.PAGINATION_TOOL
			},
			main_content: {
				controller: CONTROLLER.GENRE_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.GENRES_CONTENT
			},
			footer: footer
		}
	})

	.state(STATE.ARTIST_TRACKS, {
		url: URL.ARTIST_TRACKS,
		params: {
			page: '1'
		},
		views: {
			header: mainHeader,
			info: {
				controller: CONTROLLER.ARTIST_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.INFO_CONTENT
			},
			tool: {
				controller: CONTROLLER.PAGINATION_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.PAGINATION_TOOL
			},
			main_content: {
				controller: CONTROLLER.TRACK_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.TRACKS_CONTENT
			},
			footer: footer
		}
	})

	.state(STATE.GENRE_TRACKS, {
		url: URL.GENRE_TRACKS,
		params: {
			page: '1'
		},
		views: {
			header: mainHeader,
			info: {
				controller: CONTROLLER.GENRE_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.INFO_CONTENT
			},
			tool: {
				controller: CONTROLLER.PAGINATION_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.PAGINATION_TOOL
			},
			main_content: {
				controller: CONTROLLER.TRACK_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.TRACKS_CONTENT
			},
			footer: footer
		}
	})

	.state(STATE.USER_TRACKS, {
		title: TITLE.USER_TRACKS,
		url: URL.USER_TRACKS,
		params: {
			page: '1'
		},
		views: {
			header: mainHeader,
			tool: {
				controller: CONTROLLER.PAGINATION_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.PAGINATION_TOOL
			},
			main_content: {
				controller: CONTROLLER.TRACK_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.TRACKS_CONTENT
			},
			footer: footer
		}
	});

	$urlRouterProvider.otherwise(URL.HOME);

});

//_______________________________________________________________________

'use strict';
app.config(function($translateProvider) {

	$translateProvider.useSanitizeValueStrategy('escapeParameters');

	$translateProvider
	.translations('by', {
		ARTIST: 'Выканавец',
		GENRE: 'Жанр',
		TRACK: 'Песня',

		ARTISTS: 'Выканаўцы',
		GENRES: 'Жанры',
		TRACKS: 'Песні',

		MY_ACCOUNT: 'Мой рахунак',
		MY_ARTISTS: 'Мае выканаўцы',
		MY_GENRES: 'Мае жанры',
		MY_TRACKS: 'Мае песні',

		MORE_ARTISTS: 'Больш выканаўцаў',
		MORE_GENRES: 'Больш жанраў',
		MORE_TRACKS: 'Больш песен',

		EDIT: 'Рэдагаваць',
		LOG_IN: 'Увайсці',
		LOG_OUT: 'Выйсці',
		REGISTER: 'Рэгістрацыя',
		SAVE: 'Захаваць',
		SETTINGS: 'Налады',
		SUBMIT: 'Адправіць',

		ADD_ARTIST: 'Дадаць выканаўца',
		ADD_GENRE: 'Дадаць жанр',
		REMOVE_ARTIST: 'Выдаліць выканаўца',
		REMOVE_GENRE: 'Выдаліць жанр',

		LANGUAGE: 'Мова',
		DESIGN: 'Дызайн',
		SORTING: 'Сартаванне',

		CLASSIC: 'Класічны',
		STANDARD: 'Стандартны',

		ALPHABET: 'Алфавіт',
		PUBLISH_DATE: 'Дзень публікацыі',
		RATING: 'Рэйтынг',
		RELEASE_DATE: 'Дзень выпуску',

		ASC: 'Па ўзрастанні',
		DESC: 'Па змяншэнні',

		CONFIRM_PASSWORD: 'Пацвердзіце пароль',
		COVER: 'Вокладка',
		LOGIN: 'Лагін',
		NAME: 'Імя',
		PASSWORD: 'Пароль',
		PHOTO: 'Фота',
		REMEMBER_ME: 'Запомніць мяне',
		SONG_NAME: 'Імя песні',
		YOUTUBE_VIDEO_ID: 'Id Youtube відэа',

		CHECK_GENRE_NAME_MESSAGE: 'Праверка існавання жанру...',
		CHECK_LOGIN_MESSAGE: 'Праверка занятасці лагіну...',
		EXISTS_GENRE_NAME_MESSAGE: 'Дадзены жанр ужо ёсць',
		EXISTS_LOGIN_MESSAGE: 'Дадзены лагін ужо заняты',
		MINIMAL_COUNT_MESSAGE: 'Мінімальная колькасць сімвалаў - 3',
		PASSWORDS_DO_NOT_MATCH_MESSAGE: 'Паролі не супадаюць',
		WRONG_DATA: 'Няправільны лагін або пароль',

		SAVING_SETTINGS_SUCCESS: 'Налады былі паспяхова захаваны'
	});

});

//_______________________________________________________________________

'use strict';
app.config(function($translateProvider) {

	$translateProvider.useSanitizeValueStrategy('escapeParameters');

	$translateProvider
	.translations('en', {
		ARTIST: 'Artist',
		GENRE: 'Genre',
		TRACK: 'Track',

		ARTISTS: 'Artists',
		GENRES: 'Genres',
		TRACKS: 'Tracks',

		MY_ACCOUNT: 'My account',
		MY_ARTISTS: 'My artists',
		MY_GENRES: 'My genres',
		MY_TRACKS: 'My tracks',

		MORE_ARTISTS: 'More artists',
		MORE_GENRES: 'More genres',
		MORE_TRACKS: 'More tracks',

		EDIT: 'Edit',
		LOG_IN: 'Log in',
		LOG_OUT: 'Log out',
		REGISTER: 'Register',
		SAVE: 'Save',
		SETTINGS: 'Settings',
		SUBMIT: 'Submit',

		ADD_ARTIST: 'Add artist',
		ADD_GENRE: 'Add genre',
		REMOVE_ARTIST: 'Remove artist',
		REMOVE_GENRE: 'Remove genre',

		LANGUAGE: 'Language',
		DESIGN: 'Design',
		SORTING: 'Sorting',

		CLASSIC: 'Classic',
		STANDARD: 'Standard',

		ALPHABET: 'Alphabet',
		PUBLISH_DATE: 'Publish date',
		RATING: 'Rating',
		RELEASE_DATE: 'Release date',

		ASC: 'By ascending',
		DESC: 'By descending',

		CONFIRM_PASSWORD: 'Confirm the password',
		COVER: 'Cover',
		LOGIN: 'Login',
		NAME: 'Name',
		PASSWORD: 'Password',
		PHOTO: 'Photo',
		REMEMBER_ME: 'Remember me',
		SONG_NAME: 'Song name',
		YOUTUBE_VIDEO_ID: 'Youtube video id',

		CHECK_GENRE_NAME_MESSAGE: 'Checking if this genre exists...',
		CHECK_LOGIN_MESSAGE: 'Checking if this login is available...',
		EXISTS_GENRE_NAME_MESSAGE: 'This genre already exists',
		EXISTS_LOGIN_MESSAGE: 'This login is already taken',
		MINIMAL_COUNT_MESSAGE: 'Minimum count of symbols is 3',
		PASSWORDS_DO_NOT_MATCH_MESSAGE: 'Passwords do not match',
		WRONG_DATA: 'Login or password is wrong',

		SAVING_SETTINGS_SUCCESS: 'Settings were successfully saved'
	});

});

//_______________________________________________________________________

'use strict';
app.config(function($translateProvider) {

	$translateProvider.useSanitizeValueStrategy('escapeParameters');

	$translateProvider
	.translations('fr', {
		ARTISTS: 'Artiste',
		GENRES: 'Genre',
		TRACKS: 'Chanson',

		ARTISTS: 'Artistes',
		GENRES: 'Genres',
		TRACKS: 'Chansons',

		MY_ACCOUNT: 'Mon compte',
		MY_ARTISTS: 'Mes artistes',
		MY_GENRES: 'Mes genres',
		MY_TRACKS: 'Mes chansons',

		MORE_ARTISTS: 'Plus de artistes',
		MORE_GENRES: 'Plus de genres',
		MORE_TRACKS: 'Plus de chansons',

		EDIT: 'Éditer',
		LOG_IN: 'Connexion',
		LOG_OUT: 'Déconnexion',
		REGISTER: 'Registre',
		SAVE: 'Enregistrer',
		SETTINGS: 'Réglages',
		SUBMIT: 'Soumettre',

		ADD_ARTIST: 'Ajouter un artiste',
		ADD_GENRE: 'Ajouter un genre',
		REMOVE_ARTIST: 'Supprimer artiste',
		REMOVE_GENRE: 'Supprimer le genre',

		LANGUAGE: 'Langue',
		DESIGN: 'Désign',
		SORTING: 'Tri',

		CLASSIC: 'Classique',
		STANDARD: 'Standard',

		ALPHABET: 'Alphabet',
		PUBLISH_DATE: 'Date de publication',
		RATING: 'Audimat',
		RELEASE_DATE: 'Date de sortie',

		ASC: 'En montant',
		DESC: 'En descendant',

		CONFIRM_PASSWORD: 'Confirmer le mot de passe',
		COVER: 'Couverture',
		LOGIN: 'Login',
		NAME: 'Nom',
		PASSWORD: 'Mot de passe',
		PHOTO: 'Photo',
		REMEMBER_ME: 'Souviens-toi de moiе',
		SONG_NAME: 'Nom de chanson',
		YOUTUBE_VIDEO_ID: 'Youtube video id',

		CHECK_GENRE_NAME_MESSAGE: 'Vérifier si ce genre existe...',
		CHECK_LOGIN_MESSAGE: 'Vérification de la disponibilité de cette login...',
		EXISTS_GENRE_NAME_MESSAGE: 'Ce genre existe déjà',
		EXISTS_LOGIN_MESSAGE: 'Cette login est déjà prise',
		MINIMAL_COUNT_MESSAGE: 'Le nombre minimum de symboles est de 3',
		PASSWORDS_DO_NOT_MATCH_MESSAGE: 'Les mots de passe ne correspondent pas',
		WRONG_DATA: 'Connexion ou mot de passe incorrect',

		SAVING_SETTINGS_SUCCESS: 'Les paramètres ont été correctement enregistrés'
	});

});

//_______________________________________________________________________

'use strict';
app.config(function($translateProvider) {

	$translateProvider.useSanitizeValueStrategy('escapeParameters');

	$translateProvider
	.translations('ru', {
		ARTIST: 'Исполнитель',
		GENRE: 'Жанр',
		TRACK: 'Песня',

		ARTISTS: 'Исполнители',
		GENRES: 'Жанры',
		TRACKS: 'Песни',

		MY_ACCOUNT: 'Мой аккаунт',
		MY_ARTISTS: 'Мои исполнители',
		MY_GENRES: 'Мои жанры',
		MY_TRACKS: 'Мои песни',

		MORE_ARTISTS: 'Больше исполнителей',
		MORE_GENRES: 'Больше жанров',
		MORE_TRACKS: 'Больше песен',

		EDIT: 'Редактировать',
		LOG_IN: 'Войти',
		LOG_OUT: 'Выйти',
		REGISTER: 'Регистрация',
		SAVE: 'Сохранить',
		SETTINGS: 'Настройки',
		SUBMIT: 'Выполнить',

		ADD_ARTIST: 'Добавить артиста',
		ADD_GENRE: 'Добавить жанр',
		REMOVE_ARTIST: 'Удалить артиста',
		REMOVE_GENRE: 'Удалить жанр',

		LANGUAGE: 'Язык',
		DESIGN: 'Дизайн',
		SORTING: 'Сортировка',

		CLASSIC: 'Классический',
		STANDARD: 'Стандартный',

		ALPHABET: 'Алфавит',
		PUBLISH_DATE: 'Дата публикации',
		RATING: 'Рейтинг',
		RELEASE_DATE: 'Дата выпуска',

		ASC: 'По возрастанию',
		DESC: 'По убыванию',

		CONFIRM_PASSWORD: 'Подтвердите пароль',
		COVER: 'Обложка',
		LOGIN: 'Логин',
		NAME: 'Имя',
		PASSWORD: 'Пароль',
		PHOTO: 'Фото',
		REMEMBER_ME: 'Запомнить меня',
		SONG_NAME: 'Имя песни',
		YOUTUBE_VIDEO_ID: 'Id Youtube видео',

		CHECK_GENRE_NAME_MESSAGE: 'Проверка существования жанра...',
		CHECK_LOGIN_MESSAGE: 'Проверка занятости логина...',
		EXISTS_GENRE_NAME_MESSAGE: 'Данный жанр уже есть',
		EXISTS_LOGIN_MESSAGE: 'Данный логин уже занят',
		MINIMAL_COUNT_MESSAGE: 'Минимальное количество символов - 3',
		PASSWORDS_DO_NOT_MATCH_MESSAGE: 'Пароли не совпадают',
		WRONG_DATA: 'Неправильный логин или пароль',

		SAVING_SETTINGS_SUCCESS: 'Настройки были успешно сохранены'
	});

});

//_______________________________________________________________________

'use strict';
app.config(function($translateProvider) {

	$translateProvider.preferredLanguage('en');

});

//_______________________________________________________________________

'use strict';
app.constant('CONTROLLER', (function() {

	return {
		CTRL: 'ctrl',
		ARTIST_CONTROLLER: 'ArtistController',
		GENRE_CONTROLLER: 'GenreController',
		PAGINATION_CONTROLLER: 'PaginationController',
		SETTINGS_CONTROLLER: 'SettingsController',
		TRACK_CONTROLLER: 'TrackController',
		USER_CONTROLLER: 'UserController'
	}

})());

//_______________________________________________________________________

'use strict';
app.constant('COOKIE', (function() {

	return {
		SETTINGS: 'settings',
		USER: 'user'
	}

})());

//_______________________________________________________________________

'use strict';
app.constant('DEFAULT', (function() {

	return {
		COUNT: {
			artists: 6,
			genres: 18,
			tracks: 6
		},

		ENTITY: {
			artist: 'artist',
			genre: 'genre',
			track: 'track'
		},

		SETTINGS: {
			language: 'en',
			design: '1',
			sort: {artists: '1', genres: '0', tracks: '1'},
			order: {artists: 'false', genres: 'true', tracks: 'false'}
		}
	}

})());

//_______________________________________________________________________

'use strict';
app.constant('MESSAGE', (function() {

	var creatingError = 'Error while creating ';
	var updatingError = 'Error while updating ';
	var deletingError = 'Error while deleting ';
	var gettingError = 'Error while getting ';
	var creatingSuccess = ' has been created successfully';
	var deletingSuccess = ' has been deleted successfully';

	return {
		AUTHENTICATION_ERROR: 'Login or password is wrong',
		EXIST_GENRE_ERROR: 'This genre already exists',
		TAKEN_LOGIN_ERROR: 'This login is already taken',
		SAVING_FILE_ERROR: 'Error while saving file',
		CREATING_ARTIST_ERROR: creatingError + 'artist',
		CREATING_GENRE_ERROR: creatingError + 'genre',
		CREATING_TRACK_ERROR: creatingError + 'track',
		CREATING_USER_ERROR: creatingError + 'user',
		UPDATING_ARTIST_ERROR: updatingError + 'artist',
		UPDATING_GENRE_ERROR: updatingError + 'genre',
		UPDATING_TRACK_ERROR: updatingError + 'track',
		UPDATING_USER_ERROR: updatingError + 'user',
		DELETING_ARTIST_ERROR: deletingError + 'artist',
		DELETING_GENRE_ERROR: deletingError + 'genre',
		DELETING_TRACK_ERROR: deletingError + 'track',
		GETTING_ARTIST_ERROR: gettingError + 'artist',
		GETTING_GENRE_ERROR: gettingError + 'genre',
		GETTING_RESULTS_ERROR: gettingError + 'results',
		GETTING_TRACK_ERROR: gettingError + 'track',
		GETTING_UNIT_ERROR: gettingError + 'unit',
		GETTING_USER_ERROR: gettingError + 'user',
		CREATING_ARTIST_SUCCESS: 'Artist ' + creatingSuccess,
		CREATING_GENRE_SUCCESS: 'Genre ' + creatingSuccess,
		CREATING_TRACK_SUCCESS: 'Track ' + creatingSuccess,
		DELETING_ARTIST_SUCCESS: 'Artist ' + deletingSuccess,
		DELETING_GENRE_SUCCESS: 'Genre ' + deletingSuccess,
		DELETING_TRACK_SUCCESS: 'Track ' + deletingSuccess,
		VALIDATION_ERROR: 'All required attributes must be filled'
	}

})());

//_______________________________________________________________________

'use strict';
app.constant('PATH', (function() {

	var path = 'html';
	var contentPath = path + '/content';
	var formPath = path + '/form';
	var titlePath = path + '/title';
	var toolPath = path + '/tool';
	var htmlExt = '.html';

	return {
		INFO_CONTENT: contentPath + '/info' + htmlExt,
		ARTISTS_CONTENT: contentPath + '/artists' + htmlExt,
		GENRES_CONTENT: contentPath + '/genres' + htmlExt,
		TRACKS_CONTENT: contentPath + '/tracks' + htmlExt,
		LOGIN_FORM: formPath + '/login' + htmlExt,
		REGISTER_FORM: formPath + '/register' + htmlExt,
		ARTIST_EDIT_FORM: formPath + '/artist.edit' + htmlExt,
		GENRE_EDIT_FORM: formPath + '/genre.edit' + htmlExt,
		TRACK_EDIT_FORM: formPath + '/track.edit' + htmlExt,
		AUTHENTICATION_HEADER: titlePath + '/authentication.header' + htmlExt,
		EDIT_HEADER: titlePath + '/edit.header' + htmlExt,
		MAIN_HEADER: titlePath + '/main.header' + htmlExt,
		FOOTER: titlePath + '/footer' + htmlExt,
		PAGINATION_TOOL: toolPath + '/pagination' + htmlExt,
		SETTINGS_TOOL: toolPath + '/settings' + htmlExt
	}

})());

//_______________________________________________________________________

'use strict';
app.constant('REST', (function() {

	var service = 'http://localhost:8081/service';

	return {
		ARTISTS: service + '/artists',
		GENRES: service + '/genres',
		SEARCH: service + '/search',
		TRACKS: service + '/tracks',
		UNITS: service + '/units',
		UPLOAD: service + '/upload',
		USERS: service + '/users'
	}

})());

//_______________________________________________________________________

'use strict';
app.constant('STATE', (function() {

	var delimiter = '_';
	var artists = 'artists';
	var genres = 'genres';
	var tracks = 'tracks';
	var artist = 'artist';
	var genre = 'genre';
	var track = 'track';
	var user = 'user';
	var addOperation = delimiter + 'add';

	return {
		LOGIN: 'login',
		REGISTER: 'register',
		SETTINGS: 'settings',
		PLAYLIST: 'playlist',
		ARTIST_ADD: artist + addOperation,
		GENRE_ADD: genre + addOperation,
		TRACK_ADD: track + addOperation,
		ARTISTS: artists,
		GENRES: genres,
		TRACKS: tracks,
		ARTIST: artist,
		GENRE: genre,
		TRACK: track,
		GENRE_ARTISTS: genre + delimiter + artists,
		TRACK_ARTISTS: track + delimiter + artists,
		USER_ARTISTS: user + delimiter + artists,
		ARTIST_GENRES: artist + delimiter + genres,
		TRACK_GENRES: track + delimiter + genres,
		USER_GENRES: user + delimiter + genres,
		ARTIST_TRACKS: artist + delimiter + tracks,
		GENRE_TRACKS: genre + delimiter + tracks,
		USER_TRACKS: user + delimiter + tracks
	}

})());

//_______________________________________________________________________

'use strict';
app.constant('TEMPLATE', (function() {

	var prefix = '/html/content';
	var postfix = '.html';
	var classic = '.classic';
	var standard = '.standard';
	var artists = '/artists';
	var genres = '/genres';
	var tracks = '/tracks';

	return {
		ARTISTS: [prefix + artists + artists + classic + postfix, prefix + artists + artists + standard + postfix],
		GENRES: [prefix + genres + genres + classic + postfix, prefix + genres + genres + standard + postfix],
		TRACKS: [prefix + tracks + tracks + classic + postfix, prefix + tracks + tracks + standard + postfix]
	}

})());

//_______________________________________________________________________

'use strict';
app.constant('TITLE', (function() {

	return {
		LOGIN: 'Login',
		REGISTER: 'Register',
		SETTINGS: 'Settings',
		PLAYLIST: 'Playlist',
		ARTIST_ADD: 'Add artist',
		GENRE_ADD: 'Add genre',
		TRACK_ADD: 'Add track',
		ARTISTS: 'Artists',
		GENRES: 'Genres',
		TRACKS: 'Tracks',
		USER_ARTISTS: 'My artists',
		USER_GENRES: 'My genres',
		USER_TRACKS: 'My tracks'
	}

})());

//_______________________________________________________________________

'use strict';
app.constant('TYPE', (function() {

	return {
		COVER: 'cover',
		PHOTO: 'photo',
		SONG: 'song'
	}

})());

//_______________________________________________________________________

'use strict';
app.constant('UPLOAD', (function() {

	var imageRoot = '/image';

	return {
		ARTIST_PHOTO: imageRoot + '/artist',
		AUDIO: '/audio',
		TRACK_COVER: imageRoot + '/track'
	}

})());

//_______________________________________________________________________

'use strict';
app.constant('URL', (function() {

	var artistsUrl = '/artists';
	var genresUrl = '/genres';
	var tracksUrl = '/tracks';
	var artistUrl = '/artist';
	var genreUrl = '/genre';
	var trackUrl = '/track';
	var userUrl = '/user';
	var addOperation = '/add';
	var idKey = '{id:[0-9]{1,}}';
	var pageKey = '{page:[0-9]{1,}}';

	return {
		HOME: tracksUrl + '?page=1',
		LOGIN: '/login',
		REGISTER: '/register',
		SETTINGS: '/settings',
		PLAYLIST: '/playlist',
		ARTIST_ADD: artistUrl + addOperation,
		GENRE_ADD: genreUrl + addOperation,
		TRACK_ADD: trackUrl + addOperation,
		ARTISTS: artistsUrl + '?' + pageKey,
		GENRES: genresUrl + '?' + pageKey,
		TRACKS: tracksUrl + '?' + pageKey,
		ARTIST: artistUrl + '/' + idKey,
		GENRE: genreUrl + '/' + idKey,
		TRACK: trackUrl + '/' + idKey,
		GENRE_ARTISTS: genreUrl + '/' + idKey + artistsUrl + '?' + pageKey,
		TRACK_ARTISTS: trackUrl + '/' + idKey + artistsUrl + '?' + pageKey,
		USER_ARTISTS: userUrl + artistsUrl + '?' + pageKey,
		ARTIST_GENRES: artistUrl + '/' + idKey + genresUrl + '?' + pageKey,
		TRACK_GENRES: trackUrl + '/' + idKey + genresUrl + '?' + pageKey,
		USER_GENRES: userUrl + genresUrl + '?' + pageKey,
		ARTIST_TRACKS: artistUrl + '/' + idKey + tracksUrl + '?' + pageKey,
		GENRE_TRACKS: genreUrl + '/' + idKey + tracksUrl + '?' + pageKey,
		USER_TRACKS: userUrl + tracksUrl + '?' + pageKey
	}

})());

//_______________________________________________________________________

'use strict';
app.controller('ArtistController', function($scope, $state, DEFAULT, STATE, TEMPLATE, TYPE, UPLOAD, ArtistFactory, GenreFactory, UserFactory, ChoiceService, CookieService, FileService, FlashService, UtilityService) {

	$scope.url = '#';
	$scope.info = {};
	$scope.artists = [];
	$scope.genres = [];
	$scope.genresChoice = [];

	$scope.createArtist = function() {
		$scope.dataLoading = true;
		FileService.uploadFile($scope.photoFile, TYPE.PHOTO, function(response) {
			if (response.success) {
				var genresId = [];
				for (var i = 0; i < $scope.genresChoice.length; i++) {
					genresId.push($scope.genresChoice[i].id);
				}
				createArtist($scope.artist.name, $scope.artist.photo.replace(/^C:\\fakepath\\/i, ''), genresId);
				$scope.dataLoading = false;
			} else {
				$scope.dataLoading = false;
				FlashService.error(response.message);
			}
		});
	};

	$scope.deleteArtist = function(id) {
		ArtistFactory.deleteArtist(id, function(response) {
			if (response.success) {
				FlashService.success(response.message);
			} else {
				FlashService.error(response.message);
			}
		});
	};

	$scope.like = function(artistId) {
		UserFactory.setUserLike(DEFAULT.ENTITY.artist, artistId, function(response) {
			if (response.success) {
				UtilityService.setLike($scope.artists, artistId);
			} else {
				FlashService.error(response.message);
			}
		});
	};

	$scope.addGenreChoice = function() {
		ChoiceService.addGenreChoice($scope.genresChoice);
	};

	$scope.removeGenreChoice = function(index) {
		ChoiceService.removeGenreChoice($scope.genresChoice, index);
	};

	$scope.getTemplate = function() {
		return TEMPLATE.ARTISTS[CookieService.getSettings().design];
	};

	function init(state, sort, order, page) {
		switch (state) {
			case STATE.ARTIST_ADD:
				$scope.genresChoice.push({});
				getAllGenresIdAndName();
				break;
			case STATE.ARTISTS:
				$scope.url = '#';
				getArtists(sort, order, page);
				break;
			case STATE.ARTIST:
			case STATE.ARTIST_GENRES:
			case STATE.ARTIST_TRACKS:
				getArtistById($state.params.id);
				break;
			case STATE.GENRE:
				$scope.url = 'genre_artists({id: ' + $state.params.id + ', page: 1})';
				getEntityArtists('genre', $state.params.id, sort, order, 0);
				break;
			case STATE.TRACK:
				$scope.url = 'track_artists({id: ' + $state.params.id + ', page: 1})';
				getEntityArtists('track', $state.params.id, sort, order, 0);
				break;
			case STATE.GENRE_ARTISTS:
				$scope.url = '#';
				getEntityArtists('genre', $state.params.id, sort, order, page);
				break;
			case STATE.TRACK_ARTISTS:
				$scope.url = '#';
				getEntityArtists('track', $state.params.id, sort, order, page);
				break;
			case STATE.USER_ARTISTS:
				$scope.url = '#';
				getUserArtists(sort, order, page);
				break;
		}
	}

	function createArtist(name, photo, genresId) {
		ArtistFactory.createArtist(name, photo, genresId, function(response) {
			if (response.success) {
				FlashService.success(response.message);
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getArtistById(id) {
		ArtistFactory.getArtistById(id, function(response) {
			if (response.success) {
				$scope.info.image = UPLOAD.ARTIST_PHOTO + '/' + response.data.photo;
				$scope.info.data = response.data.name;
				$state.current.title = response.data.name;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getArtists(sort, order, page) {
		ArtistFactory.getArtists(sort, order, page, function(response) {
			if (response.success) {
				$scope.artists = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getEntityArtists(entity, entityId, sort, order, page) {
		ArtistFactory.getEntityArtists(entity, entityId, sort, order, page, function(response) {
			if (response.success) {
				$scope.artists = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getUserArtists(sort, order, page) {
		ArtistFactory.getUserArtists(sort, order, page, function(response) {
			if (response.success) {
				$scope.artists = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getAllGenresIdAndName() {
		GenreFactory.getAllGenresIdAndName(function(response) {
			if (response.success) {
				$scope.genres = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	init($state.current.name, CookieService.getSettings().sort.artists, CookieService.getSettings().order.artists, $state.params.page);

});

//_______________________________________________________________________

'use strict';
app.controller('GenreController', function($scope, $state, DEFAULT, STATE, GenreFactory, UserFactory, CookieService, FlashService, UtilityService) {

	$scope.url = '#';
	$scope.info = {};
	$scope.genres = [];

	$scope.createGenre = function() {
		$scope.dataLoading = true;
		GenreFactory.createGenre($scope.genre.name, function(response) {
			if (response.success) {
				FlashService.success(response.message);
			} else {
				FlashService.error(response.message);
			}
			$scope.dataLoading = false;
		});
	};

	$scope.deleteGenre = function(id) {
		GenreFactory.deleteGenre(id, function(response) {
			if (response.success) {
				FlashService.success(response.message);
			} else {
				FlashService.error(response.message);
			}
		});
	};

	$scope.like = function(genreId) {
		UserFactory.setUserLike(DEFAULT.ENTITY.genre, genreId, function(response) {
			if (response.success) {
				UtilityService.setLike($scope.genres, genreId);
			} else {
				FlashService.error(response.message);
			}
		});
	};

	$scope.menu = function(genreId) {
		for (var i = 0; i < $scope.genres.length; i++) {
			delete($scope.genres[i].menu);
			if ($scope.genres[i].id === genreId) {
				$scope.genres[i].menu = true;
			}
		}
	};

	$scope.hide = function() {
		for (var i = 0; i < $scope.genres.length; i++) {
			delete($scope.genres[i].menu);
		}
	};

	function init(state, sort, order, page) {
		switch (state) {
			case STATE.GENRES:
				$scope.url = '#';
				getGenres(sort, order, page);
				break;
			case STATE.GENRE:
			case STATE.GENRE_ARTISTS:
			case STATE.GENRE_TRACKS:
				getGenreById($state.params.id);
				break;
			case STATE.ARTIST:
				$scope.url = 'artist_genres({id: ' + $state.params.id + ', page: 1})';
				getEntityGenres('artist', $state.params.id, sort, order, 0);
				break;
			case STATE.TRACK:
				$scope.url = 'track_genres({id: ' + $state.params.id + ', page: 1})';
				getEntityGenres('track', $state.params.id, sort, order, 0);
				break;
			case STATE.ARTIST_GENRES:
				$scope.url = '#';
				getEntityGenres('artist', $state.params.id, sort, order, page);
				break;
			case STATE.TRACK_GENRES:
				$scope.url = '#';
				getEntityGenres('track', $state.params.id, sort, order, page);
				break;
			case STATE.USER_GENRES:
				$scope.url = '#';
				getUserGenres(sort, order, page);
				break;
		}
	}

	function getGenreById(id) {
		GenreFactory.getGenreById(id, function(response) {
			if (response.success) {
				$scope.info.data = response.data.name;
				$state.current.title = response.data.name;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getGenres(sort, order, page) {
		GenreFactory.getGenres(sort, order, page, function(response) {
			if (response.success) {
				$scope.genres = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getEntityGenres(entity, entityId, sort, order, page) {
		GenreFactory.getEntityGenres(entity, entityId, sort, order, page, function(response) {
			if (response.success) {
				$scope.genres = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getUserGenres(sort, order, page) {
		GenreFactory.getUserGenres(sort, order, page, function(response) {
			if (response.success) {
				$scope.genres = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	init($state.current.name, CookieService.getSettings().sort.genres, CookieService.getSettings().order.genres, $state.params.page);

});

//_______________________________________________________________________

'use strict';
app.controller('PaginationController', function($scope, $state, STATE, ArtistFactory, GenreFactory, TrackFactory, FlashService) {

	$scope.pages = [];

	function init(state, page) {
		var callback = function(response) {
			if (response.success) {
				setPages(state, page, response.data);
			} else {
				FlashService.error(response.message);
			}
		};

		switch (state) {
			case STATE.ARTISTS:
				setPagesCount(ArtistFactory, callback);
				break;
			case STATE.GENRES:
				setPagesCount(GenreFactory, callback);
				break;
			case STATE.TRACKS:
				setPagesCount(TrackFactory, callback);
				break;
			case STATE.GENRE_ARTISTS:
				setEntityPagesCount(ArtistFactory, 'genre', $state.params.id, callback);
				break;
			case STATE.TRACK_ARTISTS:
				setEntityPagesCount(ArtistFactory, 'track', $state.params.id, callback);
				break;
			case STATE.USER_ARTISTS:
				setUserPagesCount(ArtistFactory, callback);
				break;
			case STATE.ARTIST_GENRES:
				setEntityPagesCount(GenreFactory, 'artist', $state.params.id, callback);
				break;
			case STATE.TRACK_GENRES:
				setEntityPagesCount(GenreFactory, 'track', $state.params.id, callback);
				break;
			case STATE.USER_GENRES:
				setUserPagesCount(GenreFactory, callback);
				break;
			case STATE.ARTIST_TRACKS:
				setEntityPagesCount(TrackFactory, 'artist', $state.params.id, callback);
				break;
			case STATE.GENRE_TRACKS:
				setEntityPagesCount(TrackFactory, 'genre', $state.params.id, callback);
				break;
			case STATE.USER_TRACKS:
				setUserPagesCount(TrackFactory, callback);
				break;
		}
	}

	function setPagesCount(factory, callback) {
		factory.getPagesCount(callback);
	}

	function setEntityPagesCount(factory, entity, entityId, callback) {
		factory.getEntityPagesCount(entity, entityId, callback);
	}

	function setUserPagesCount(factory, callback) {
		factory.getUserPagesCount(callback);
	}

	function setPages(state, page, count) {
		if (count === 0) {
			return;
		}

		var currentPage = parseInt(page);
		var from = (currentPage <= 3) ? 1 : (currentPage - 2);
		var to = (currentPage <= 3) ? 5 : (currentPage + 2);
		from = (count <= 5) ? 1 : from;
		to = (to <= count) ? to : count;

		for (var i = from; i <= to; i++) {
			var type = 'default';
			if (currentPage == i) {
				type = 'active';
			}
			$scope.pages.push({
				number: i,
				link: state + '({page:' + i + '})',
				type: type
			});
		}
	}

	init($state.current.name, $state.params.page);

});

//_______________________________________________________________________

'use strict';
app.controller('SearchController', function($scope, SearchFactory, FlashService) {

	$scope.search = function(keyCode) {
		if ((keyCode === -1) || (keyCode === 8) || (keyCode === 46) || ((keyCode >= 48) && (keyCode <= 57)) || ((keyCode >= 65) && (keyCode <= 90))) {
			if (!angular.isUndefined($scope.query) && $scope.query !== '') {
				getSuggestions($scope.query);
			} else {
				$scope.suggestions = [];
			}
		}
	};

	$scope.show = function(show) {
		if (!show && $scope.hold) {
			return;
		}
		if (show) {
			if ($scope.suggestions !== undefined) {
				var divsToShow = document.getElementsByClassName('suggestion');
				for (var i = 0; i < divsToShow.length; i++) {
					divsToShow[i].style.display = 'block';
				}
			} else {
				$scope.search(-1);
			}
		} else {
			var divsToHide = document.getElementsByClassName('suggestion');
			for (var i = 0; i < divsToHide.length; i++) {
				divsToHide[i].style.display = 'none';
			}
		}
	};

	$scope.holdSuggestions = function(hold) {
		$scope.hold = hold;
	};

	function getSuggestions(query) {
		SearchFactory.getSuggestions(query, function(response) {
			if (response.success) {
				$scope.suggestions = response.data;
				for (var i = 0; i < $scope.suggestions.length; i++) {
					$scope.suggestions[i].url = $scope.suggestions[i].entity + '({id: ' + $scope.suggestions[i].id + '})';
				}
			} else {
				FlashService.error(response.message);
			}
		});
	}

});

//_______________________________________________________________________

'use strict';
app.controller('SettingsController', function($scope, $translate, CookieService, FlashService) {

	$scope.settings = CookieService.getSettings();
	$scope.languages = [{name: 'Беларуская', iso: 'by'}, {name: 'English', iso: 'en'}, {name: 'Français', iso: 'fr'}, {name: 'Русский', iso: 'ru'}];
	$scope.designs = [{name: '', value: 0}, {name: '', value: 1}];
	$scope.sorts = {
		artists: [{name: '', value: 0}, {name: '', value: 1}, {name: '', value: 2}],
		genres: [{name: '', value: 0}, {name: '', value: 1}, {name: '', value: 2}],
		tracks: [{name: '', value: 0}, {name: '', value: 1}, {name: '', value: 2}, {name: '', value: 3}]
	};

	$scope.saveSettings = function() {
		CookieService.setSettings($scope.settings);
		$translate.use($scope.settings.language);
		init();
		FlashService.success($translate.instant('SAVING_SETTINGS_SUCCESS'));
	};

	function init() {
		$translate(['CLASSIC', 'STANDARD', 'ALPHABET', 'PUBLISH_DATE', 'RATING', 'RELEASE_DATE'])
		.then(function(translations) {
			$scope.designs[0].name = translations.CLASSIC;
			$scope.designs[1].name = translations.STANDARD;

			$scope.sorts.artists[0].name = translations.ALPHABET;
			$scope.sorts.genres[0].name = translations.ALPHABET;
			$scope.sorts.tracks[0].name = translations.ALPHABET;

			$scope.sorts.artists[1].name = translations.PUBLISH_DATE;
			$scope.sorts.genres[1].name = translations.PUBLISH_DATE;
			$scope.sorts.tracks[1].name = translations.PUBLISH_DATE;

			$scope.sorts.artists[2].name = translations.RATING;
			$scope.sorts.genres[2].name = translations.RATING;
			$scope.sorts.tracks[2].name = translations.RATING;

			$scope.sorts.tracks[3].name = translations.RELEASE_DATE;
		});
	}

	init();

});

//_______________________________________________________________________

'use strict';
app.controller('TrackController', function($scope, $state, $window, DEFAULT, STATE, TEMPLATE, TYPE, UPLOAD, ArtistFactory, GenreFactory, TrackFactory, UnitFactory, UserFactory, AmplitudeService, ChoiceService, CookieService, FileService, FlashService, UtilityService) {

	$scope.url = '#';
	$scope.info = {};
	$scope.artists = [];
	$scope.genres = [];
	$scope.tracks = [];
	$scope.units = [];
	$scope.artistsChoice = [];
	$scope.genresChoice = [];
	$scope.unitsChoice = [];

	var renew = false;

	$scope.createTrack = function() {
		$scope.dataLoading = true;
		FileService.uploadFile($scope.songFile, TYPE.SONG, function(response) {
			if (response.success) {
				FileService.uploadFile($scope.coverFile, TYPE.COVER, function(response) {
					if (response.success) {
						var artistsId = [];
						var genresId = [];
						var unitsId = [];
						for (var i = 0; i < $scope.artistsChoice.length; i++) {
							artistsId.push($scope.artistsChoice[i].id);
						}
						for (var i = 0; i < $scope.genresChoice.length; i++) {
							genresId.push($scope.genresChoice[i].id);
						}
						for (var i = 0; i < $scope.unitsChoice.length; i++) {
							unitsId.push($scope.unitsChoice[i].id);
						}
						createTrack($scope.track.name, $scope.track.song.replace(/^C:\\fakepath\\/i, ''), $scope.track.cover.replace(/^C:\\fakepath\\/i, ''), $scope.track.video, $scope.track.release, artistsId, unitsId, genresId);
						$scope.dataLoading = false;
					} else {
						$scope.dataLoading = false;
						FlashService.error(response.message);
					}
				});
			} else {
				$scope.dataLoading = false;
				FlashService.error(response.message);
			}
		});
	};

	$scope.deleteTrack = function(id) {
		TrackFactory.deleteTrack(id, function(response) {
			if (response.success) {
				FlashService.success(response.message);
			} else {
				FlashService.error(response.message);
			}
		});
	};

	$scope.like = function(trackId) {
		UserFactory.setUserLike(DEFAULT.ENTITY.track, trackId, function(response) {
			if (response.success) {
				UtilityService.setLike($scope.tracks, trackId);
			} else {
				FlashService.error(response.message);
			}
		});
	};

	$scope.addArtistChoice = function() {
		ChoiceService.addArtistChoice($scope.artistsChoice, $scope.unitsChoice);
	};

	$scope.removeArtistChoice = function(index) {
		ChoiceService.removeArtistChoice($scope.artistsChoice, $scope.unitsChoice, index);
	};

	$scope.addGenreChoice = function() {
		ChoiceService.addGenreChoice($scope.genresChoice);
	};

	$scope.removeGenreChoice = function(index) {
		ChoiceService.removeGenreChoice($scope.genresChoice, index);
	};

	$scope.getTemplate = function() {
		return TEMPLATE.TRACKS[CookieService.getSettings().design];
	};

	$scope.playSong = function(trackId) {
		if (renew) {
			renew = false;
			Amplitude.setSongs(AmplitudeService.parseSongs($scope.tracks));
		}
		Amplitude.playNowById(trackId);
	};

	$scope.openVideo = function(videoId) {
		$window.open('https://www.youtube.com/watch?v=' + videoId);
	};

	function init(state, sort, order, page) {
		switch (state) {
			case STATE.PLAYLIST:
				$scope.url = '#';
				$scope.tracks = AmplitudeService.playlist;
				break;
			case STATE.TRACK_ADD:
				$scope.artistsChoice.push({});
				$scope.genresChoice.push({});
				getAllArtistsIdAndName();
				getAllUnitsIdAndName();
				getAllGenresIdAndName();
				break;
			case STATE.TRACKS:
				$scope.url = '#';
				getTracks(sort, order, page);
				break;
			case STATE.TRACK:
			case STATE.TRACK_ARTISTS:
			case STATE.TRACK_GENRES:
				getTrackById($state.params.id);
				break;
			case STATE.ARTIST:
				$scope.url = 'artist_tracks({id: ' + $state.params.id + ', page: 1})';
				getEntityTracks('artist', $state.params.id, sort, order, 0);
				break;
			case STATE.GENRE:
				$scope.url = 'genre_tracks({id: ' + $state.params.id + ', page: 1})';
				getEntityTracks('genre', $state.params.id, sort, order, 0);
				break;
			case STATE.ARTIST_TRACKS:
				$scope.url = '#';
				getEntityTracks('artist', $state.params.id, sort, order, page);
				break;
			case STATE.GENRE_TRACKS:
				$scope.url = '#';
				getEntityTracks('genre', $state.params.id, sort, order, page);
				break;
			case STATE.USER_TRACKS:
				$scope.url = '#';
				getUserTracks(sort, order, page);
				break;
		}
	}

	function createTrack(name, song, cover, video, release, artistsId, unitsId, genresId) {
		TrackFactory.createTrack(name, song, cover, video, release, artistsId, unitsId, genresId, function(response) {
			if (response.success) {
				FlashService.success(response.message);
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getTrackById(id) {
		TrackFactory.getTrackById(id, function(response) {
			if (response.success) {
				$scope.info.image = UPLOAD.TRACK_COVER + '/' + response.data.cover;
				$scope.info.data = response.data.name;
				$state.current.title = response.data.name;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getTracks(sort, order, page) {
		TrackFactory.getTracks(sort, order, page, function(response) {
			if (response.success) {
				$scope.tracks = response.data;
				Amplitude.init({songs: AmplitudeService.parseSongs($scope.tracks)});
				renew = true;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getEntityTracks(entity, entityId, sort, order, page) {
		TrackFactory.getEntityTracks(entity, entityId, sort, order, page, function(response) {
			if (response.success) {
				$scope.tracks = response.data;
				Amplitude.init({songs: AmplitudeService.parseSongs($scope.tracks)});
				renew = true;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getUserTracks(sort, order, page) {
		TrackFactory.getUserTracks(sort, order, page, function(response) {
			if (response.success) {
				$scope.tracks = response.data;
				Amplitude.init({songs: AmplitudeService.parseSongs($scope.tracks)});
				renew = true;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getAllArtistsIdAndName() {
		ArtistFactory.getAllArtistsIdAndName(function(response) {
			if (response.success) {
				$scope.artists = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getAllUnitsIdAndName() {
		UnitFactory.getAllUnitsIdAndName(function(response) {
			if (response.success) {
				$scope.units = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getAllGenresIdAndName() {
		GenreFactory.getAllGenresIdAndName(function(response) {
			if (response.success) {
				$scope.genres = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	init($state.current.name, CookieService.getSettings().sort.tracks, CookieService.getSettings().order.tracks, $state.params.page);

});

//_______________________________________________________________________

'use strict';
app.controller('UserController', function($rootScope, $scope, $state, STATE, UserFactory, CookieService, FlashService) {

	$scope.login = function() {
		$scope.dataLoading = true;
		UserFactory.authentication($scope.user.login, $scope.user.password, function(response) {
			if (response.success) {
				$rootScope.user = response.data;
				if ($scope.user.rememberMe) {
					CookieService.setUser($rootScope.user);
				}
				$state.go(STATE.TRACKS, {page: 1});
			} else {
				FlashService.error(response.message);
			}
			$scope.dataLoading = false;
		});
	};

	$scope.logout = function() {
		$rootScope.user = null;
		CookieService.clearUser();
		UserFactory.logout();
		switch ($state.current.name) {
			case STATE.USER_ARTISTS:
				$state.go(STATE.ARTISTS, {page: 1});
				break;
			case STATE.USER_GENRES:
				$state.go(STATE.GENRES, {page: 1});
				break;
			case STATE.USER_TRACKS:
				$state.go(STATE.TRACKS, {page: 1});
				break;
		}
	};

	$scope.register = function() {
		$scope.dataLoading = true;
		UserFactory.createUser($scope.user.login, $scope.user.password, $scope.user.confirmPassword, function(response) {
			if (response.success) {
				$scope.login();
			} else {
				FlashService.error(response.message);
			}
			$scope.dataLoading = false;
		});
	};

});

//_______________________________________________________________________

'use strict';
app.factory('ArtistFactory', function($http, MESSAGE, REST, UtilityService) {

	function createArtist(name, photo, genresId, callback) {
		if (!UtilityService.allNotEmpty(callback, name, photo, genresId)) {
			return;
		}

		var artist = {
			name: name,
			photo: photo,
			genresId: genresId
		};

		$http.post(REST.ARTISTS, artist)
		.success(function(response) {
			var data = {success: true, data: response, message: MESSAGE.CREATING_ARTIST_SUCCESS};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.CREATING_ARTIST_ERROR};
			callback(response);
		});
	}

	function deleteArtist(id, callback) {
		if (!UtilityService.allNotEmpty(callback, id)) {
			return;
		}

		$http.delete(REST.ARTISTS + '/' + id)
		.success(function(response) {
			response = {success: true, message: MESSAGE.GELETING_ARTIST_SUCCESS};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GELETING_ARTIST_ERROR};
			callback(response);
		});
	}

	function getArtistById(id, callback) {
		if (!UtilityService.allNotEmpty(callback, id)) {
			return;
		}

		$http.get(REST.ARTISTS + '/' + id)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_ARTIST_ERROR};
			callback(response);
		});
	}

	function getArtists(sort, order, page, callback) {
		if (!UtilityService.allNotEmpty(callback, sort, order, page)) {
			return;
		}

		$http.get(REST.ARTISTS + '/' + sort + '/' + order + '/' + page)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_ARTIST_ERROR};
			callback(response);
		});
	}

	function getEntityArtists(entity, entityId, sort, order, page, callback) {
		if (!UtilityService.allNotEmpty(callback, entity, entityId, sort, order, page)) {
			return;
		}

		$http.get(REST.ARTISTS + '/' + entity + '/' + entityId + '/' + sort + '/' + order + '/' + page)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_ARTIST_ERROR};
			callback(response);
		});
	}

	function getUserArtists(sort, order, page, callback) {
		if (!UtilityService.allNotEmpty(callback, sort, order, page)) {
			return;
		}

		$http.get(REST.ARTISTS + '/user/' + sort + '/' + order + '/' + page)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_ARTIST_ERROR};
			callback(response);
		});
	}

	function getAllArtistsIdAndName(callback) {
		$http.get(REST.ARTISTS + '/all/id_name')
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_ARTIST_ERROR};
			callback(response);
		});
	}

	function getPagesCount(callback) {
		$http.get(REST.ARTISTS + '/pages_count')
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_ARTIST_ERROR};
			callback(response);
		});
	}

	function getEntityPagesCount(entity, entityId, callback) {
		if (!UtilityService.allNotEmpty(callback, entity, entityId)) {
			return;
		}

		$http.get(REST.ARTISTS + '/' + entity + '/' + entityId + '/pages_count')
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_ARTIST_ERROR};
			callback(response);
		});
	}

	function getUserPagesCount(callback) {
		$http.get(REST.ARTISTS + '/user/pages_count')
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_ARTIST_ERROR};
			callback(response);
		});
	}

	return {
		createArtist: createArtist,
		deleteArtist: deleteArtist,
		getArtistById: getArtistById,
		getArtists: getArtists,
		getEntityArtists: getEntityArtists,
		getUserArtists: getUserArtists,
		getAllArtistsIdAndName: getAllArtistsIdAndName,
		getPagesCount: getPagesCount,
		getEntityPagesCount: getEntityPagesCount,
		getUserPagesCount: getUserPagesCount
	};

});

//_______________________________________________________________________

'use strict';
app.factory('GenreFactory', function($http, MESSAGE, REST, UtilityService) {

	function createGenre(name, callback) {
		if (!UtilityService.allNotEmpty(callback, name)) {
			return;
		}

		var genre = {
			name: name
		};

		$http.post(REST.GENRES, genre)
		.success(function(response) {
			var data = {success: true, data: response, message: MESSAGE.CREATING_GENRE_SUCCESS};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.CREATING_GENRE_ERROR};
			callback(response);
		});
	}

	function deleteGenre(id, callback) {
		if (!UtilityService.allNotEmpty(callback, id)) {
			return;
		}

		$http.delete(REST.GENRES + '/' + id)
		.success(function(response) {
			response = {success: true, message: MESSAGE.DELETING_GENRE_SUCCESS};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.DELETING_GENRE_ERROR};
			callback(response);
		});
	}

	function getGenreById(id, callback) {
		if (!UtilityService.allNotEmpty(callback, id)) {
			return;
		}

		$http.get(REST.GENRES + '/' + id)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_GENRE_ERROR};
			callback(response);
		});
	}

	function getGenres(sort, order, page, callback) {
		if (!UtilityService.allNotEmpty(callback, sort, order, page)) {
			return;
		}

		$http.get(REST.GENRES + '/' + sort + '/' + order + '/' + page)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_GENRE_ERROR};
			callback(response);
		});
	}

	function getEntityGenres(entity, entityId, sort, order, page, callback) {
		if (!UtilityService.allNotEmpty(callback, entity, entityId, sort, order, page)) {
			return;
		}

		$http.get(REST.GENRES + '/' + entity + '/' + entityId + '/' + sort + '/' + order + '/' + page)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_GENRE_ERROR};
			callback(response);
		});
	}

	function getUserGenres(sort, order, page, callback) {
		if (!UtilityService.allNotEmpty(callback, sort, order, page)) {
			return;
		}

		$http.get(REST.GENRES + '/user/' + sort + '/' + order + '/' + page)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_GENRE_ERROR};
			callback(response);
		});
	}

	function getAllGenresIdAndName(callback) {
		$http.get(REST.GENRES + '/all/id_name')
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_GENRE_ERROR};
			callback(response);
		});
	}

	function getPagesCount(callback) {
		$http.get(REST.GENRES + '/pages_count')
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_ARTIST_ERROR};
			callback(response);
		});
	}

	function getEntityPagesCount(entity, entityId, callback) {
		if (!UtilityService.allNotEmpty(callback, entity, entityId)) {
			return;
		}

		$http.get(REST.GENRES + '/' + entity + '/' + entityId + '/pages_count')
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_GENRE_ERROR};
			callback(response);
		});
	}

	function getUserPagesCount(callback) {
		$http.get(REST.GENRES + '/user/pages_count')
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_GENRE_ERROR};
			callback(response);
		});
	}

	function checkGenreName(name, callback) {
		$http.post(REST.GENRES + '/check/genre_name', name)
		.success(function(response) {
			if (response) {
				response = {success: true, message: MESSAGE.EXIST_GENRE_ERROR};
			} else {
				response = {success: false};
			}
			callback(response);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_GENRE_ERROR};
			callback(response);
		});
	}

	return {
		createGenre: createGenre,
		deleteGenre: deleteGenre,
		getGenreById: getGenreById,
		getGenres: getGenres,
		getEntityGenres: getEntityGenres,
		getUserGenres: getUserGenres,
		getAllGenresIdAndName: getAllGenresIdAndName,
		getPagesCount: getPagesCount,
		getEntityPagesCount: getEntityPagesCount,
		getUserPagesCount: getUserPagesCount,
		checkGenreName: checkGenreName
	};

});

//_______________________________________________________________________

'use strict';
app.factory('SearchFactory', function($http, MESSAGE, REST, UtilityService) {

	function getSuggestions(query, callback) {
		if (!UtilityService.allNotEmpty(callback, query)) {
			return;
		}

		$http.post(REST.SEARCH + '/suggest', query)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_RESULTS_ERROR};
			callback(response);
		});
	}

	return {
		getSuggestions: getSuggestions
	};

});

//_______________________________________________________________________

'use strict';
app.factory('TrackFactory', function($http, MESSAGE, REST, UtilityService) {

	function createTrack(name, song, cover, video, release, artistsId, unitsId, genresId, callback) {
		if (!UtilityService.allNotEmpty(callback, name, song, cover, video, release, artistsId, unitsId, genresId)) {
			return;
		}

		var track = {
			name: name,
			song: song,
			cover: cover,
			video: video,
			release: release,
			artistsId: artistsId,
			unitsId: unitsId,
			genresId: genresId
		};

		$http.post(REST.TRACKS, track)
		.success(function(response) {
			var data = {success: true, data: response, message: MESSAGE.CREATING_TRACK_SUCCESS};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.CREATING_TRACK_ERROR};
			callback(response);
		});
	}

	function deleteTrack(id, callback) {
		if (!UtilityService.allNotEmpty(callback, id)) {
			return;
		}

		$http.delete(REST.TRACKS + '/' + id)
		.success(function(response) {
			response = {success: true, message: MESSAGE.GELETING_TRACK_SUCCESS};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GELETING_TRACK_ERROR};
			callback(response);
		});
	}

	function getTrackById(id, callback) {
		if (!UtilityService.allNotEmpty(callback, id)) {
			return;
		}

		$http.get(REST.TRACKS + '/' + id)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_TRACK_ERROR};
			callback(response);
		});
	}

	function getTracks(sort, order, page, callback) {
		if (!UtilityService.allNotEmpty(callback, sort, order, page)) {
			return;
		}

		$http.get(REST.TRACKS + '/' + sort + '/' + order + '/' + page)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_TRACK_ERROR};
			callback(response);
		});
	}

	function getEntityTracks(entity, entityId, sort, order, page, callback) {
		if (!UtilityService.allNotEmpty(callback, entity, entityId, sort, order, page)) {
			return;
		}

		$http.get(REST.TRACKS + '/' + entity + '/' + entityId + '/' + sort + '/' + order + '/' + page)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_TRACK_ERROR};
			callback(response);
		});
	}

	function getUserTracks(sort, order, page, callback) {
		if (!UtilityService.allNotEmpty(callback, sort, order, page)) {
			return;
		}

		$http.get(REST.TRACKS + '/user/' + sort + '/' + order + '/' + page)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_TRACK_ERROR};
			callback(response);
		});
	}

	function getAllTracksIdAndName(callback) {
		$http.get(REST.TRACKS + '/all/id_name')
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_TRACK_ERROR};
			callback(response);
		});
	}

	function getPagesCount(callback) {
		$http.get(REST.TRACKS + '/pages_count')
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_TRACK_ERROR};
			callback(response);
		});
	}

	function getEntityPagesCount(entity, entityId, callback) {
		if (!UtilityService.allNotEmpty(callback, entity, entityId)) {
			return;
		}

		$http.get(REST.TRACKS + '/' + entity + '/' + entityId + '/pages_count')
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_TRACK_ERROR};
			callback(response);
		});
	}

	function getUserPagesCount(callback) {
		$http.get(REST.TRACKS + '/user/pages_count')
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_TRACK_ERROR};
			callback(response);
		});
	}

	return {
		createTrack: createTrack,
		deleteTrack: deleteTrack,
		getTrackById: getTrackById,
		getTracks: getTracks,
		getEntityTracks: getEntityTracks,
		getUserTracks: getUserTracks,
		getAllTracksIdAndName: getAllTracksIdAndName,
		getPagesCount: getPagesCount,
		getEntityPagesCount: getEntityPagesCount,
		getUserPagesCount: getUserPagesCount
	};

});

//_______________________________________________________________________

'use strict';
app.factory('UnitFactory', function($http, MESSAGE, REST) {

	function getAllUnitsIdAndName(callback) {
		$http.get(REST.UNITS + '/all/id_name')
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_UNIT_ERROR};
			callback(response);
		});
	}

	return {
		getAllUnitsIdAndName: getAllUnitsIdAndName
	};

});

//_______________________________________________________________________

'use strict';
app.factory('UserFactory', function($http, $translate, MESSAGE, REST, UtilityService) {

	function authentication(login, password, callback) {
		if (!UtilityService.allNotEmpty(callback, login, password)) {
			return;
		}

		var headers = {authorization: "Basic " + btoa(login + ":" + password)};
		$http.get(REST.USERS + '/auth', {'headers' : headers})
		.success(function(response) {
			if (response) {
				var data = {success: true, data: response};
				callback(data);
			} else {
				response = {success: false, message: MESSAGE.AUTHENTICATION_ERROR};
				callback(response);
			}
		})
		.error(function(response) {
			$translate(['WRONG_DATA'])
			.then(function(translations) {
				response = {success: false, message: translations.WRONG_DATA};
				callback(response);
			});
		});
	}

	function logout() {
		$http.post(REST.USERS + '/logout', {});
	}

	function createUser(login, password, confirmPassword, callback) {
		if (!UtilityService.allNotEmpty(callback, login, password, confirmPassword)) {
			return;
		}

		var user = {
			login: btoa(login),
			password: btoa(password),
			confirmPassword: btoa(confirmPassword)
		};

		$http.post(REST.USERS, user)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			var data = {success: false, message: response.message};
			callback(data);
		});
	}

	function getUser(callback) {
		$http.get(REST.USERS + '/auth', {})
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_USER_ERROR};
			callback(response);
		});
	}

	function setUserLike(entity, entityId, callback) {
		if (!UtilityService.allNotEmpty(callback, entity, entityId)) {
			return;
		}

		var entity = {
			entity: entity,
			entityId: entityId
		};

		$http.post(REST.USERS + '/like', entity)
		.success(function(response) {
			response = {success: true};
			callback(response);
		})
		.error(function(response) {
			response = {success: false, message: response.message};
			callback(response);
		});
	}

	function checkLogin(login, callback) {
		if (!UtilityService.allNotEmpty(callback, login)) {
			return;
		}

		$http.post(REST.USERS + '/check/login', login)
		.success(function(response) {
			if (response) {
				response = {success: true};
			} else {
				response = {success: false, message: MESSAGE.TAKEN_LOGIN_ERROR};
			}
			callback(response);
		})
		.error(function(response) {
			response = {success: false, message: response.message};
			callback(response);
		});
	}

	return {
		authentication: authentication,
		logout: logout,
		createUser: createUser,
		getUser: getUser,
		setUserLike: setUserLike,
		checkLogin: checkLogin
	};

});

//_______________________________________________________________________

'use strict';
var fileRequired = 'fileRequired';
var fileModel = 'fileModel';
var ngMatch = 'ngMatch';
var ngModel = 'ngModel';
var ngCheckGenreName = 'ngCheckGenreName';
var ngCheckLogin = 'ngCheckLogin';

app.directive(ngCheckGenreName, function($timeout, $q, GenreFactory) {
	var timer;
	return {
		require: ngModel,
		link: function(scope, element, attributes, controller) {
			controller.$asyncValidators.ngCheckGenreName = function(modelValue, viewValue) {
				var def = $q.defer();
				$timeout.cancel(timer);
				timer = $timeout(function() {
					GenreFactory.checkGenreName(modelValue, function(response) {
						if (!response.success) {
							def.resolve();
						} else {
							def.reject();
						}
					});
				}, 1000);
				return def.promise;
			};
		}
	};
});

app.directive(ngCheckLogin, function($timeout, $q, UserFactory) {
	var timer;
	return {
		require: ngModel,
		link: function(scope, element, attributes, controller) {
			controller.$asyncValidators.ngCheckLogin = function(modelValue, viewValue) {
				var def = $q.defer();
				$timeout.cancel(timer);
				timer = $timeout(function() {
					UserFactory.checkLogin(modelValue, function(response) {
						if (!response.success) {
							def.resolve();
						} else {
							def.reject();
						}
					});
				}, 1000);
				return def.promise;
			};
		}
	};
});

app.directive(ngMatch, function() {
	return {
		require: ngModel,
		scope: {
			otherModelValue: "=" + ngMatch
		},
		link: function(scope, element, attributes, controller) {
			controller.$validators.ngMatch = function(modelValue) {
				return modelValue == scope.otherModelValue;
			};
			scope.$watch("otherModelValue", function() {
				controller.$validate();
			});
		}
	};
});

app.directive(fileRequired, function() {
	return {
		require: ngModel,
		link: function(scope, element, attributes, controller) {
			element.bind('change', function() {
				scope.$apply(function() {
					controller.$setViewValue(element.val());
					controller.$render();
				});
			});
		}
	}
});

app.directive(fileModel, function($parse) {
	return {
		restrict: 'A',
		link: function(scope, element, attributes) {
			var model = $parse(attributes.fileModel);
			var modelSetter = model.assign;
			element.bind('change', function() {
				scope.$apply(function() {
					modelSetter(scope, element[0].files[0]);
				});
			});
		}
	};
});

//_______________________________________________________________________

'use strict';
app.run(function($rootScope, $state, $translate, DEFAULT, STATE, UserFactory, CookieService, FlashService) {

	$rootScope.$state = $state;
	$rootScope.count = DEFAULT.COUNT;

	$rootScope.$on('$stateChangeStart', function() {
		FlashService.clearFlashMessage(0);
	});

	var settings = CookieService.getSettings();
	if (settings === undefined) {
		CookieService.setSettings(DEFAULT.SETTINGS);
	} else {
		$translate.use(settings.language);
	}

	var user = CookieService.getUser();
	if (user === undefined) {
		UserFactory.getUser(function(response) {
			if (response.success) {
				if (response.data !== null) {
					$rootScope.user = response.data;
					CookieService.setUser($rootScope.user);
				} else {
					$state.go(STATE.LOGIN);
				}
			}
		});
	} else {
		$rootScope.user = user;
	}

});

//_______________________________________________________________________

'use strict';
app.service('AmplitudeService', function(UPLOAD) {

	var playlist = [];

	function parseSong(track) {
		var artist = track.artists[0].name;
		for (var i = 0; i < track.units.length; i++) {
			artist += track.units[i].name + track.artists[i + 1].name;
		}
		var song = {
			id: track.id,
			album: '',
			artist: artist,
			cover_art_url: UPLOAD.TRACK_COVER + '/' + track.cover,
			name: '<a ui-sref="track({id: {{tracks[i].id}}})">' + track.name + '</a>',
			url: UPLOAD.AUDIO + '/' + track.song
		};
		return song;
	}

	function parseSongs(tracks) {
		var songs = [];
		playlist.length = 0;
		for (var i = 0; i < tracks.length; i++) {
			playlist.push(tracks[i]);
			var artist = tracks[i].artists[0].name;
			for (var j = 0; j < tracks[i].units.length; j++) {
				artist += tracks[i].units[j].name + tracks[i].artists[j + 1].name;
			}
			songs.push({
				id: tracks[i].id,
				album: '',
				artist: artist,
				cover_art_url: UPLOAD.TRACK_COVER + '/' + tracks[i].cover,
				name: '<a ui-sref="track({id: {{tracks[i].id}}})">' + tracks[i].name + '</a>',
				url: UPLOAD.AUDIO + '/' + tracks[i].song
			});
		};
		return songs;
	}

	return {
		parseSong: parseSong,
		parseSongs: parseSongs,
		playlist: playlist
	};

});

//_______________________________________________________________________

'use strict';
app.service('ChoiceService', function() {

	function addArtistChoice(artistsChoice, unitsChoice) {
		artistsChoice.push({});
		if (artistsChoice.length > 1) {
			unitsChoice.push({});
		}
	}

	function removeArtistChoice(artistsChoice, unitsChoice, index) {
		if (artistsChoice.length > 1) {
			unitsChoice.splice(index - 1, 1);
		}
		artistsChoice.splice(index, 1);
	}

	function addGenreChoice(genresChoice) {
		genresChoice.push({});
	}

	function removeGenreChoice(genresChoice, index) {
		genresChoice.splice(index, 1);
	}

	return {
		addArtistChoice: addArtistChoice,
		removeArtistChoice: removeArtistChoice,
		addGenreChoice: addGenreChoice,
		removeGenreChoice: removeGenreChoice
	};

});

//_______________________________________________________________________

'use strict';
app.service('CookieService', function($cookies, COOKIE) {

	function getUser() {
		return $cookies.getObject(COOKIE.USER);
	}

	function setUser(user) {
		$cookies.putObject(COOKIE.USER, user);
	}

	function clearUser() {
		$cookies.remove(COOKIE.USER);
	}

	function getSettings() {
		return $cookies.getObject(COOKIE.SETTINGS);
	}

	function setSettings(settings) {
		$cookies.putObject(COOKIE.SETTINGS, settings);
	}

	function clearSettings() {
		$cookies.remove(COOKIE.SETTINGS);
	}

	return {
		getUser: getUser,
		setUser: setUser,
		clearUser: clearUser,
		getSettings: getSettings,
		setSettings: setSettings,
		clearSettings: clearSettings
	};

});

//_______________________________________________________________________

'use strict';
app.service('FileService', function($http, MESSAGE, REST) {

	function uploadFile(file, type, callback) {
		var formData = new FormData();
		formData.append('file', file);

		$http.post(REST.UPLOAD + '/' + type, formData, {
			transformRequest: angular.identity,
			headers: {'Content-Type': undefined}
		})
		.success(function(response) {
			response = {success: true};
			callback(response);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.SAVING_FILE_ERROR};
			callback(response);
		});
	}

	return {
		uploadFile: uploadFile
	};

});

//_______________________________________________________________________

'use strict';
app.service('FlashService', function($rootScope) {

	function clearFlashMessage(delay) {
		var flash = $rootScope.flash;
		if (flash) {
			if (!flash.keepAfterLocationChange) {
				setTimeout(function() {
					$rootScope.$apply(function() {
						delete $rootScope.flash;
					});
				}, delay);
			} else {
				flash.keepAfterLocationChange = false;
			}
		}
	}

	function success(message) {
		$rootScope.flash = {
			message: message,
			type: 'success',
			keepAfterLocationChange: false
		};
		this.clearFlashMessage(3000);
	}

	function error(message) {
		$rootScope.flash = {
			message: message,
			type: 'error',
			keepAfterLocationChange: false
		};
		this.clearFlashMessage(3000);
	}

	return {
		clearFlashMessage: clearFlashMessage,
		success: success,
		error: error
	};

});

//_______________________________________________________________________

'use strict';
app.service('UtilityService', function(MESSAGE) {

	function allNotEmpty() {
		for (var i = 1; i < arguments.length; i++) {
			if (arguments[i] === null) {
				var response = {success: false, message: MESSAGE.VALIDATION_ERROR};
				arguments[0](response);
				return false;
			}
		}
		return true;
	}

	function setLike(entities, id) {
		for (var i = 0; i < entities.length; i++) {
			if (entities[i].id === id) {
				if (entities[i].liked) {
					--entities[i].rating;
				} else {
					++entities[i].rating;
				}
				entities[i].liked = !entities[i].liked;
				return;
			}
		}
	}

	return {
		allNotEmpty: allNotEmpty,
		setLike: setLike
	};

});