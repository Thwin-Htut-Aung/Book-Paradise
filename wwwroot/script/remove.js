function showRemoveForm(formName) {
	var forms = document.getElementsByClassName("removing-" + formName)
	for(var i=0; i<forms.length; i++){
		forms[i].style.display="block"
	}
	document.getElementById("remove-button-" + formName).style.display = "none"
}


function hideRemoveForm(formName) {
	var forms = document.getElementsByClassName("removing-" + formName)
	for(var i=0; i<forms.length; i++){
		forms[i].style.display="none"
	}
	document.getElementById("remove-button-" + formName).style.display = "inline"
}
