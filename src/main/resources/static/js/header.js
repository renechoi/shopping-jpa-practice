function showMenu() {
    var menu = document.getElementById("topHoverMenu");
    menu.style.display = "block";
}

function hideMenu() {
    var menu = document.getElementById("topHoverMenu");
    menu.style.display = "none";
}

function menuToggle() {
    var midiMenu = document.getElementById("midiMenu")
    var displayAttr = midiMenu.style.display;
    var topDog = document.getElementById("topDog");


    if (displayAttr == "none") {
        midiMenu.style.display = "block";
        topDog.style.display = "none";
    } else {
        midiMenu.style.display = "none";
        topDog.style.display = "block";
    }

}

var midiMenu = document.getElementById("midiMenu");
window.addEventListener("resize", function () {
    var width = window.innerWidth;

    if (width > 880) {
        midiMenu.style.display = "none";
    }

});


const head = document.querySelector(".head");
const headerHeight = head.getBoundingClientRect().height;

const main = document.querySelector(".main");
const main2 = document.querySelector(".main2");
const NS = document.querySelector(".NoticeSearchZone");

window.addEventListener("scroll", () => {
    var width = window.innerWidth;

    if (window.scrollY > headerHeight && width < 880) {
        main.classList.add('fixed');
        main2.classList.add('fixed');
        NS.classList.add("jumped2")
        NS.classList.remove("jumped1");
    } else if (window.scrollY > headerHeight && width > 880) {
        main.classList.add('fixed');
        main2.classList.add('fixed');
        NS.classList.remove("jumped2")
        NS.classList.add("jumped1");
    } else {

        main.classList.remove('fixed');
        NS.classList.remove("jumped1")
        main2.classList.remove('fixed');
        NS.classList.remove("jumped2")
    }
});


function movingTopDog() {
    $(".topDog").attr("src", "/images/topDog.gif")
}

function stopTopDog() {
    $(".topDog").attr("src", "/images/topDog(1).png")
}



