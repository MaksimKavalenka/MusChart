var currentTrack = 0;

function GetPlayer(id, name, url) {
	name = name.replace(new RegExp("&",'g'),"and");

	if ($("#track"+id+"Icon").length > 0) {
		if (currentTrack > 0) {
			swfobject.removeSWF("track"+currentTrack+"Player");
			document.getElementById("track"+currentTrack+"Icon").style.display="block";
			$("#track").prepend("<div class='footer' id='trackPlayer'></div>");
		}

		var flashvars = {"m":"audio", "st":"http://localhost:8080/muschart/plugins/audio232-179.txt", "file":url, "uid":"track"+id+"Player", "comment":name};
		var params = {allowFullScreen:"true", allowScriptAccess:"always", bgcolor:"#ffffff"};
		var att = {id:"track"+id+"Player"};
		new swfobject.embedSWF("http://localhost:8080/muschart/plugins/uppod.swf", "trackPlayer", "97%", "60", "10.10.10", false, flashvars, params, att);

		currentTrack = id;
	}
}

function NextPlayer() {
	$("#track"+(currentTrack+1)+"Icon").length>0?$("#track"+(currentTrack+1)+"Icon").click():$("#track1Icon").click();
}

function uppodEvent(playerID, event) {
	switch(event) {
		case 'end':
			setTimeout(NextPlayer, 1000);
			break;
	}
}

function uppodSend(playerID, com, callback) {
	document.getElementById(playerID).sendToUppod(com);
}

function uppodGet(playerID, com, callback) {
	return document.getElementById(playerID).getUppod(com);
}