function validateInputs(){
	
	var bookName = document.forms["myForm"]["bookName"].value;
	var genre = document.forms["myForm"]["genre"].value;
	var authors = document.forms["myForm"]["author"].value;
	var translators = document.forms["myForm"]["translators"].value;
	var tags = document.forms["myForm"]["tags"].value;
	var chap1 = document.forms["myForm"]["firstChapter"].value;
	var chap2 = document.forms["myForm"]["secondChapter"].value;
	var chap3 = document.forms["myForm"]["thirdChapter"].value;
	var cover = document.forms["myForm"]["coverImage"].value;
	
	
	if(bookName===""||genre===""||authors===""||translators===""||tags===""){
		document.getElementById("e1").style.display="block";
		document.getElementById("e2").style.display="none";
		document.getElementById("e3").style.display="none";
		return false;
	}
	else if((chap1===null||chap1.length===0)&&(chap2===null||chap2.length===0)&&(chap3===null||chap3.length===0)){
		document.getElementById("e2").style.display="block";
		document.getElementById("e1").style.display="none";
		document.getElementById("e3").style.display="none";
		return false;
	}
	else if(cover===null||cover.length===0){
		document.getElementById("e3").style.display="block";
		document.getElementById("e1").style.display="none";
		document.getElementById("e2").style.display="none";
		return false;
	}
	else{
		return true;
	}
}

function validateChapter(){
	var chapter = document.forms["chapterForm"]["newChapter"].value;
	if(chapter===null||chapter.length===0){
		document.getElementById("err").style.display="block";
		return false;
	}
	else{
		return true;
	}
}