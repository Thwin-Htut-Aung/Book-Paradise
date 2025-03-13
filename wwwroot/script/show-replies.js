function showReplies(n){
	
	var replyBox = document.getElementById(n)
	if(replyBox.style.display==="none"){
		replyBox.style.display="block"
		replyBox.style.maxHeight = replyBox.scrollHeight+"px"
	}
	else{
		replyBox.style.maxHeight = 0;
		replyBox.style.display="none"
	} 
	
}


function confirmDelete(commentId, type, target, contentName) {
	
	if (confirm("Are you sure you want to delete this comment?")) {
				window.location.path = '/delete-comment?commentId='+commentId+'&type='+type+'&target='+target+'&contentName='+contentName;
			}
			
		}