let cartEmptyText;

function setCartEmpty(text) {
    cartEmptyText = text;
}

function removeItem(id, qnt) {
    let ele = document.getElementById(id);
    let itemPrice = parseFloat(document.getElementById(id + "-total").innerText);
    let subtotal = document.getElementById("subtotal");
    let total = document.getElementById("order-total");
    let idForm = document.getElementById(("id-form-" + id));
    let quantityForm = document.getElementById(("quantity-form-" + id));
    if (idForm !== null && quantityForm !== null) {
        idForm.remove();
        quantityForm.remove();
    }
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
        newDiv.innerHTML = "<h3 class=\"text-center mt-4\">"+cartEmptyText+"</h3>";
        deleteDiv.remove();
        document.getElementById("main-container").appendChild(newDiv);
    }
}

function makeZero() {
    let inCartCount = document.querySelector("#countInCart");
    inCartCount.textContent = '0'.toString();
}

function removeFromCart(goodsParamID, quantity) {
    let reqBody = "command=ajax&do=remove&goodsId=" + goodsParamID + "&quantity=" + quantity;
    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (this.readyState === 4) {
            if (this.status === 200) {
                let resText = this.responseText;
                if (resText === "1" || resText === "0") {
                    if (resText === "1") {
                        let inCartCount = document.querySelector("#countInCart");
                        inCartCount.textContent = (parseInt(inCartCount.textContent) - quantity).toString();
                    }
                    if (resText === "0") {
                        alert("No goods in the stock");
                    }
                } else {
                    location.assign("../error-page.jsp");
                }
            }
            if (this.status >= 400 && this.status <= 500) {
                location.assign("../error-page.jsp")
            }
        }
    }
    xhr.open('POST', "../controller");
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send(reqBody);
}
