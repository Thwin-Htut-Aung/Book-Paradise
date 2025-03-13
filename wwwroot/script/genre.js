function changeGenre(){
    var genreSelect = document.getElementById("genreSelect");
    var genre = genreSelect.value;
    const url = new URL("http://localhost:8080/view-all-books");

    if(genre==="All Genres"){
        location.replace(url);
    }

    else{
    
    url.searchParams.append('genre', genre);

    location.replace(url);
    }
    
}

function changeBookmarkGenre(){
    var genreSelect = document.getElementById("genreSelect");
    var genre = genreSelect.value;
    const url = new URL("http://localhost:8080/bookmarks");

    if(genre==="All Genres"){
        location.replace(url);
    }

    else{
    
    url.searchParams.append('genre', genre);

    location.replace(url);
    }
    
}