var mainZone = document.getElementById("mainZone");
var itemDetailContainer = document.getElementById("itemDetailContainer");
$(".menubar").on("click", function () {

    if(!mainZone){
    }else{
        if (mainZone.style.display === "block") {
            mainZone.style.display = "none";
        } else {
            mainZone.style.display = "block";
        }
    }

    if(!itemDetailContainer){
    }else{
        if (itemDetailContainer.style.display === "block") {
            itemDetailContainer.style.display = "none";
        } else {
            itemDetailContainer.style.display = "block";
        }
    }

});

window.addEventListener("resize", function () {
    var width = window.innerWidth;

    if (width > 880) {
        if(!mainZone){
        }else{
                mainZone.style.display = "block";
        }

        if(!itemDetailContainer){
        }else{
                itemDetailContainer.style.display = "block";
        }
    }

});