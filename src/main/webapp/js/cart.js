function removeItem(id, qnt) {
    let ele = document.getElementById(id);
    let itemPrice = parseFloat(document.getElementById(id + "-total").innerText);
    let subtotal = document.getElementById("subtotal");
    let total = document.getElementById("order-total");
    ele.remove();
    removeFromCart(id, qnt)
    let subPrice = parseFloat(subtotal.innerText) - itemPrice;
    let totalPrice = parseFloat(total.innerText) - itemPrice;
    if (subPrice > 0) {
        subtotal.innerText = subPrice.toString();
        total.innerText = totalPrice.toFixed();
    } else {
        let deleteDiv = document.getElementById("cart-main-div");
        let newDiv = document.createElement("div");
        newDiv.innerHTML = "<h3 class=\"text-center mt-4\">Your shopping cart is empty</h3>";
        deleteDiv.remove();
        document.getElementById("main-container").appendChild(newDiv);
    }
// <div><h3 class="text-center mt-4">Your shopping cart is empty</h3></div>
}

function removeFromCart(goodsParamID, quantity) {
    let reqBody = "command=ajax&do=remove&goodsId=" + goodsParamID + "&quantity=" + quantity;
    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            let resText = this.responseText;
            if (resText === "1") {
                let inCartCount = document.querySelector("#countInCart");
                inCartCount.textContent = (parseInt(inCartCount.textContent) - quantity).toString();
            }
            if (resText === "0") {
                alert("No goods in the stock");
            }
        }
    }
    xhr.open('POST', "../controller");
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send(reqBody);
}
