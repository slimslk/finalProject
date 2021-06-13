let myRow = document.getElementById("mainRow");
let t = document.querySelector('#tempCard');
let position = 0;

function parseJSON(jsonText) {
    return JSON.parse(jsonText);
}

function createPaginationNumbers(num) {
    let ul = document.querySelector("#paginationUl");
    ul.innerHTML = "";
    let count = num / 3
    for (let i = 0; i < count; i++) {
        let li = document.createElement('li');
        li.innerHTML = "<button class='page-link' onclick='setStart(" + i + ")'>" + (i + 1) + "</button>";
        li.setAttribute("class", "page-item")
        ul.appendChild(li);
    }
}

function setStart(pos) {
    position = pos * 3;
    doIt(1);
    // return position;
}

function filterParam(req, pagination) {
    let command = "command=ajax&do=filter" + req;
    let sort = sortBy();
    let price = setPrice();
    if (pagination === 0) {
        position = 0
    }
    command = command + "&sort=" + sort[0] + "&direction=" + sort[1] + price + "&start=" + position;
    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (this.readyState === 4) {
            if (this.status === 200) {
                let jsonToParse = this.responseText;
                let stringArray = jsonToParse.split("|");
                if (stringArray[1] !== "") {
                    let parseJson = parseJSON(stringArray[1]);
                    createGoods(parseJson);
                    createPaginationNumbers(parseInt(stringArray[0]));
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
    xhr.send(command);
}

function createGoods(json) {
    myRow.innerHTML = '';
    if (json.length !== 0) {
        for (let i = 0; i < json.length; i++) {
            let quantity = json[i]["quantity"];
            let clone = document.importNode(t.content, true);
            let imgAttr = clone.querySelector("#goods-img");
            let btnAttr = clone.querySelector("#goods-addToCart");
            imgAttr.setAttribute("src", imgAttr.getAttribute("src") + "/img/" + json[i]["goods"]["img"])
            clone.querySelector("#goods-img").setAttribute("alt", json[i]["goods"]["name"]);
            clone.querySelector("#goods-price").innerText = "$" + json[i]["price"];
            clone.querySelector("#goods-name").innerText = json[i]["goods"]["name"];
            btnAttr.setAttribute('value', json[i]["goodsParamId"]);
            if (quantity === 0) {
                btnAttr.setAttribute('class', btnAttr.getAttribute("class") + " disabled");
            }
            myRow.appendChild(clone);
        }
    } else {
        myRow.innerHTML = '<div class="text-center pt-4">\n' +
            '    <h3>Sorry, we have not goods in this category</h3>\n' +
            '</div>';
    }
}

function addToCart(clickedValue) {
    let reqBody = "command=ajax&do=add&goodsId=" + clickedValue + "&quantity=1";
    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            let resText = this.responseText;
            if (resText === "1") {
                let inCartCount = document.querySelector("#countInCart");
                inCartCount.textContent = (parseInt(inCartCount.textContent) + 1).toString();
                doIt(0);
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

function setPrice() {
    let maxPrice = document.querySelector("#max-price")
    let minPrice = document.querySelector("#min-price")
    return "&param=price:" + minPrice.value + ":" + maxPrice.value;
}

function sortBy() {
    let sort = document.querySelector("#sort-by");
    let sortArray = [];
    sortArray[0] = "adddate";
    sortArray[1] = "desc";
    if (sort.value === "name_asc") {
        sortArray[0] = "name";
        sortArray[1] = "asc";
    }
    if (sort.value === "name_desc") {
        sortArray[0] = "name";
        sortArray[1] = "desc";
    }
    if (sort.value === "price_ltoh") {
        sortArray[0] = "price";
        sortArray[1] = "asc";
    }
    if (sort.value === "price_htol") {
        sortArray[0] = "price";
        sortArray[1] = "desc";
    }
    return sortArray;
}