function validate() {
    resetErrors();

    let username = document.getElementById("username");
    let password = document.getElementById("password");
    let valid = true;

    if (password.value.length === 0) {
        valid = false;
        password.classList.add("error");
    }

    if (username.value.length === 0) {
        valid = false;
        username.classList.add("error");
    }

    if (valid === false) {
        document.getElementById("error-box").style.display = "block";
    }

    return valid;
}

function resetErrors() {
    document.getElementById("username").classList.remove("error");
    document.getElementById("password").classList.remove("error");
    document.getElementById("error-box").style.display = "none";
}