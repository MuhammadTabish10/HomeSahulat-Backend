// Usama Works

var token = localStorage.getItem("token");
var url = window.myConfig.baseUrl;

function login() {

    localStorage.clear();
    var username = document.getElementById("number").value;
    var password = document.getElementById("password").value;
    var rowData = {
        phone: username,
        password: password
    };
    fetch(url + "/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(rowData),
    })
        .then((res) => res.json())
        .then((data) => {
            if (data.jwt) {
                localStorage.setItem("token", data.jwt)
                location.href = "index.html";
            } else {
                console.error("Authentication failed");
            }
        })
        .catch((err) => {
            console.log(err);
        });
}
