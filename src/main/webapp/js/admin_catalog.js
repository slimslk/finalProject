let myRow = document.getElementById("mainRow");
let t = document.querySelector('#tempCard');
let ageMap = new Map();
ageMap.set("baby", 0);
ageMap.set("toddler", 1);
ageMap.set("kid", 2);

function parseJSON(jsonText) {
    return JSON.parse(jsonText);
}

function filterParam(req) {
    let command = "command=ajax&do=filter" + req;
    let sort = sortBy();
    let price = setPrice();
    command = command + "&sort=" + sort[0] + "&direction=" + sort[1] + price + "&start=-1";
    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            let stringArray=this.responseText.split("|");
            createGoods(parseJSON(stringArray[1]));
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
            let clone = document.importNode(t.content, true);
            clone.querySelector("#itemCatalogId").value = json[i]["id"]
            clone.querySelector("#goodsParamId").value = json[i]["goodsParam"]["id"]
            clone.querySelector("#goodsId").value = json[i]["goods"]["id"]
            clone.querySelector("#goods-price").value = json[i]["price"];
            clone.querySelector("#file-name").value = json[i]["goods"]["img"];
            clone.querySelector("#goods-quantity").value = json[i]["quantity"];
            clone.querySelector("#goods-name").value = json[i]["goods"]["name"];
            clone.querySelector("#goods-age").id = "goods-age" + i;
            clone.querySelector("#goods-category").id = "goods-category" + i;
            clone.querySelector("#goods-gender").id = "goods-gender" + i;
            clone.querySelector("#goods-size").id = "goods-size" + i;
            clone.querySelector("#goods-style").id = "goods-style" + i;
            myRow.appendChild(clone);
        }
        for (let i = 0; i < json.length; i++) {
            let ageName = json[i]["goodsParam"]["ageName"].charAt(0).toUpperCase() + json[i]["goodsParam"]["ageName"].slice(1);
            let categoryName = json[i]["goodsParam"]["categoryName"].charAt(0).toUpperCase() + json[i]["goodsParam"]["categoryName"].slice(1);
            let genderName = json[i]["goodsParam"]["genderName"].charAt(0).toUpperCase() + json[i]["goodsParam"]["genderName"].slice(1);
            let sizeName = json[i]["goodsParam"]["sizeName"].toUpperCase();
            let styleName = json[i]["goodsParam"]["styleName"].charAt(0).toUpperCase() + json[i]["goodsParam"]["styleName"].slice(1);
            let select = document.querySelector('#goods-age' + i).getElementsByTagName('option');
            for (let j = 0; j < select.length; j++) {
                if (select[j].textContent === ageName) select[j].selected = true;
            }
            select = document.querySelector('#goods-category' + i).getElementsByTagName('option');
            for (let j = 0; j < select.length; j++) {
                if (select[j].textContent === categoryName) select[j].selected = true;
            }
            select = document.querySelector('#goods-gender' + i).getElementsByTagName('option');
            for (let j = 0; j < select.length; j++) {
                if (select[j].textContent === genderName) select[j].selected = true;
            }
            select = document.querySelector('#goods-size' + i).getElementsByTagName('option');
            for (let j = 0; j < select.length; j++) {
                if (select[j].textContent === sizeName) select[j].selected = true;
            }
            select = document.querySelector('#goods-style' + i).getElementsByTagName('option');
            for (let j = 0; j < select.length; j++) {
                if (select[j].textContent === styleName) select[j].selected = true;
            }
        }
    } else {
        myRow.innerHTML = '<div class="text-center pt-4">\n' +
            '    <h3>Sorry, we have not goods in this category</h3>\n' +
            '</div>';
    }
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