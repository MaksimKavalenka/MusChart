'use strict';
app.service('AmplitudeService', ['UPLOAD', function(UPLOAD) {

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
		return {songs: songs};
	}

	return {
		parseSong: parseSong,
		parseSongs: parseSongs,
		playlist: playlist
	};

}]);