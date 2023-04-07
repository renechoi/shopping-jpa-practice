var divs = document.querySelectorAll('.mainContainer div');

function showMoreBtn(){
    if(divs.length >= 12) {
        var moreBtn = document.getElementById("moreBtn");
        moreBtn.style.display = "inline";
    }else{

    }
}
showMoreBtn();


