if(
    localStorage.getItem(
        "isLoggedIn"
    ) !== "true"
) {

    window.location.href =
        "login.html";
}

function logout() {

    localStorage.clear();

    window.location.href =
        "login.html";
}