var price = document.getElementById("price");
var count = document.getElementById("orderCount");
var totalPrice = document.getElementById("totalPrice");

function minusCount() {
    var num = parseInt(count.value);
    var cost = parseInt(price.value);
    if(num > 1) {
        num = num - 1;
        count.value = num;
        totalPrice.innerHTML = cost*num;
    }
}
function plusCount() {
    var num = parseInt(count.value);
    var cost = parseInt(price.value);
    num = num + 1;
    count.value = num;
    totalPrice.innerHTML = cost * num;
}

