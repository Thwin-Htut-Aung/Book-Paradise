function showDeleteButtons(){
	var buttons = document.getElementsByClassName("deleting")
	for(var i=0; i<buttons.length; i++){
		buttons[i].style.display="block"
	}
	
	document.getElementById("delete-button").style.display="none"
	document.getElementById("cancel-button").style.display="inline"
}

function hideDeleteButtons(){
	var buttons = document.getElementsByClassName("deleting")
	for(var i=0; i<buttons.length; i++){
		buttons[i].style.display="none"
	}
	
	document.getElementById("delete-button").style.display="inline"
	document.getElementById("cancel-button").style.display="none"
}

function showBookmarkForm(){
	var checkboxes = document.getElementsByClassName("bookmarking")
	for(var i=0; i<checkboxes.length; i++){
		checkboxes[i].style.display="block"
	}
	
	document.getElementById("show-form-button").style.display="none"
	var buttons = document.getElementsByClassName("bookmark-button");
	buttons[0].style.display="inline"
	buttons[1].style.display="inline"
	
}

function hideBookmarkForm(){
	var checkboxes = document.getElementsByClassName("bookmarking")
	for(var i=0; i<checkboxes.length; i++){
		checkboxes[i].style.display="none"
	}
	
	document.getElementById("show-form-button").style.display="inline"
	var buttons = document.getElementsByClassName("bookmark-button")
	buttons[0].style.display="none"
	buttons[1].style.display="none"
	
}