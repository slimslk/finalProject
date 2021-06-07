function changeStatus(par, command) {
    let num = document.getElementById(par);
    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            location.reload();
        }
    }
    xhr.open('POST', "../controller");
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send("command=" + command + "&action=update&param=" + par + "&status=" + num.value);
}