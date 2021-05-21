function addToCart(i) {
    let gId = document.forms['add_to_cart'+i];
    let formVal=gId.elements.item_id;
    alert(formVal.value)
    const xhr = new XMLHttpRequest();
    xhr.open('POST', "controller?command=cart");
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send('goodsId='+formVal.value);
}