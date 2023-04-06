var footer = document.getElementById("footer");

window.addEventListener("resize", function () {
    var width = window.innerWidth;

    if (width < 880) {
        footer.style.display = "none";
    }else{
        footer.style.display = "flex";
    }

});

function checkWidth() {
    var width = window.innerWidth;

    if (width < 880) {
        footer.style.display = "none";
    } else {
        footer.style.display = "flex";
    }
}

// 브라우저가 시작될 때 한번 실행
checkWidth();