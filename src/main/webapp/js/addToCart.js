function addToCart(i) {
    let gId = this.document.forms['add_to_cart' + i].item_id;
    document.forms['add_to_cart' + i].onsubmit = function (e) {
        e.preventDefault()
        const xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                console.log(this.responseText);
                if (this.responseText === '1') {
                    alert('Item added to the cart: ' + gId.value);
                } else {
                    alert('Item dont added to the cart');
                }
            }
        }

        xhr.open('POST', "../controller");
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.send('command=cart&cart=add&goodsId=' + gId.value);
    }
}