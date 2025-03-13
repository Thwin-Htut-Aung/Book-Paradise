let slideIndex = [1,1,1,1];
let slideId = ["mySlides-Hot-Topics", "mySlides-Most-Viewed", "mySlides-New-Releases", "mySlides-Latest-Updates"]

function showAllSlides(){
	showSlides(1, 0, "next");
	showSlides(1, 1, "next");
	showSlides(1, 2, "next");
	showSlides(1, 3, "next");
	showSlides(1, 4, "next");

}

function moveSlides(n, index, direction) {
  showSlides(slideIndex[index] += n, index, direction);
}

function showSlides(n, index, direction) {
  let i;
  let currentSlides = document.getElementsByClassName(slideId[index]);
  if (n > currentSlides.length) {slideIndex[index] = 1}    
  if (n < 1) {slideIndex[index] = currentSlides.length}
  for (i = 0; i < currentSlides.length; i++) {
     currentSlides[i].style.display = "none";  
  }
  if(direction==="prev"){
	  currentSlides[slideIndex[index]-1].classList.remove("forwardani");
	  currentSlides[slideIndex[index]-1].classList.add("backwardani");
  }
  else if(direction==="next"){
	  currentSlides[slideIndex[index]-1].classList.remove("backwardani");
	  currentSlides[slideIndex[index]-1].classList.add("forwardani");
  }
  currentSlides[slideIndex[index]-1].style.display = "block";  
}