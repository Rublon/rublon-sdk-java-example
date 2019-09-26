function validate() {
    resetErrors();

    let email = document.getElementById("email");
    let password = document.getElementById("password");
    let emailPattern = /^[a-zA-Z0-9._\+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    let valid = true;

    if (password.value.length === 0) {
        valid = false;
        password.classList.add("error");
    }

    if (emailPattern.test(email.value) === false) {
        valid = false;
        email.classList.add("error");
    }

    if (valid === false) {
        document.getElementById("error-box").style.display = "block";
    }

    return valid;
}

function resetErrors() {
    document.getElementById("email").classList.remove("error");
    document.getElementById("password").classList.remove("error");
    document.getElementById("error-box").style.display = "none";
}