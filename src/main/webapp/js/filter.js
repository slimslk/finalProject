const mapAge = new Map;
const categoryMap = new Map;
const genderMap = new Map;
const szMap = new Map;
const styleMap = new Map;
const paramsCount = new Map;
let parameter = "";
let req = "";

let all = document.getElementsByClassName('form-check-input');
paramsCount.set("age", 0);
paramsCount.set("category", 0);
paramsCount.set("gender", 0);
paramsCount.set("size", 0);
paramsCount.set("style", 0);
mapAge.set("age", "");
categoryMap.set("category", "");
genderMap.set("gender", "");
szMap.set("size", "");
styleMap.set("style", "");

for (let i = 0; i < all.length; i++) {
    all[i].addEventListener('click', function () {
        const elementId = this.parentNode.parentNode.parentNode.id
        switch (elementId) {
            case "age-collapse":
                if (this.checked) {
                    paramsCount.set("age", paramsCount.get("age") + 1);
                    mapAge.set(this.value, this.value);
                } else {
                    paramsCount.set("age", paramsCount.get("age") - 1);
                    mapAge.set(this.value, "");
                }
                if (paramsCount.get("age") > 0) {
                    mapAge.set("age", "&param=age");
                } else {
                    mapAge.set("age", "");
                }
                break;
            case "category-collapse":
                if (this.checked) {
                    paramsCount.set("category", paramsCount.get("category") + 1);
                    categoryMap.set(this.value, this.value);
                } else {
                    paramsCount.set("category", paramsCount.get("category") - 1);
                    categoryMap.set(this.value, "");
                }
                if (paramsCount.get("category") > 0) {
                    categoryMap.set("category", "&param=category");
                } else {
                    categoryMap.set("category", "");
                }
                break;
            case "gender-collapse":
                if (this.checked) {
                    paramsCount.set("gender", paramsCount.get("gender") + 1);
                    genderMap.set(this.value, this.value);
                } else {
                    paramsCount.set("gender", paramsCount.get("gender") - 1);
                    genderMap.set(this.value, "");
                }
                if (paramsCount.get("gender") > 0) {
                    genderMap.set("gender", "&param=gender");
                } else {
                    genderMap.set("gender", "");
                }
                break;
            case "size-collapse":
                if (this.checked) {
                    paramsCount.set("size", paramsCount.get("size") + 1);
                    szMap.set(this.value, this.value);
                } else {
                    paramsCount.set("size", paramsCount.get("size") - 1);
                    szMap.set(this.value, "");
                }
                if (paramsCount.get("size") > 0) {
                    szMap.set("size", "&param=size");
                } else {
                    szMap.set("size", "");
                }
                break;
            case "style-collapse":
                if (this.checked) {
                    paramsCount.set("style", paramsCount.get("style") + 1);
                    styleMap.set(this.value, this.value);
                } else {
                    paramsCount.set("style", paramsCount.get("style") - 1);
                    styleMap.set(this.value, "");
                }
                if (paramsCount.get("style") > 0) {
                    styleMap.set("style", "&param=style");
                } else {
                    styleMap.set("style", "");
                }
                break;
        }
        doIt();
    });
}

function doIt() {
    parameter = "";
    parameter = parameter + mapAge.get("age");
    for (let key of mapAge.keys()) {
        if (key !== "age") {
            parameter = parameter + mapAge.get(key);
        }
    }
    parameter = parameter + categoryMap.get("category");
    for (let key of categoryMap.keys()) {
        if (key !== "category") {
            parameter = parameter + categoryMap.get(key);
        }
    }
    parameter = parameter + genderMap.get("gender");
    for (let key of genderMap.keys()) {
        if (key !== "gender") {
            parameter = parameter + genderMap.get(key);
        }
    }
    parameter = parameter + szMap.get("size");
    for (let key of szMap.keys()) {
        if (key !== "size") {
            parameter = parameter + szMap.get(key);
        }
    }
    parameter = parameter + styleMap.get("style");
    for (let key of styleMap.keys()) {
        if (key !== "style") {
            parameter = parameter + styleMap.get(key);
        }
    }
    filterParam(parameter);
}