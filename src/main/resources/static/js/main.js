var divs = document.querySelectorAll('.mainContainer div');
var moreBtn = document.getElementById("moreBtn");
console.log(divs.length)

function showMoreBtn(){
    if(divs.length >= 12) {
        moreBtn.style.display = "inline";
    }else{
        moreBtn.style.display = "none";

    }
}
showMoreBtn();