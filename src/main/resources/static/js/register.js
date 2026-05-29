function register() {

    const name =
        document.getElementById(
            "name"
        ).value;

    const email =
        document.getElementById(
            "email"
        ).value;

    const password =
        document.getElementById(
            "password"
        ).value;

    fetch(`${BASE_URL}/api/admin/register`, {

        method: "POST",

        headers: {

            "Content-Type":
                "application/json"
        },

        body: JSON.stringify({

            name,
            email,
            password
        })
    })

    .then(response => response.json())

    .then(data => {

        alert(data.message);

        if(data.success) {

            window.location.href =
                "login.html";
        }
    })

    .catch(error => {

        console.log(error);

        alert(
            "Registration Failed"
        );
    });
}