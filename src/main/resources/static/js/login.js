function login() {

    const email =
        document.getElementById(
            "email"
        ).value;

    const password =
        document.getElementById(
            "password"
        ).value;

    fetch(`${BASE_URL}/api/admin/login`, {

        method: "POST",

        headers: {

            "Content-Type":
                "application/json"
        },

        body: JSON.stringify({

            email,
            password
        })
    })

    .then(response => response.json())

    .then(data => {

        if(data.success) {

            alert(data.message);

            localStorage.setItem(
                "isLoggedIn",
                "true"
            );

            localStorage.setItem(
                "adminEmail",
                email
            );

            window.location.href =
                "index.html";
        }

        else {

            alert(data.message);
        }
    })

    .catch(error => {

        console.log(error);

        alert(
            "Login Failed"
        );
    });
}