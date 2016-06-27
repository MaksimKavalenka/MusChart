function setScroll(i) {
	var div = document.getElementById("scroll"+i);
	var height = div.offsetHeight;
	var content = div.innerHTML;

	if (height > 40) {
		div.innerHTML = "<marquee direction=\"left\">" + content + "</marquee>";
	}

	function redirect() {
		refresh=window.setTimeout(
			function() {
				location.href=""
			}
		, 0);
	}
}