function changePassword() {

    const email =
        localStorage.getItem(
            "adminEmail"
        );

    const oldPassword =
        document.getElementById(
            "oldPassword"
        ).value;

    const newPassword =
        document.getElementById(
            "newPassword"
        ).value;

    fetch(`${BASE_URL}/api/admin/change-password`, {

        method: "PUT",

        headers: {

            "Content-Type":
                "application/json"
        },

        body: JSON.stringify({

            email,
            oldPassword,
            newPassword
        })
    })

    .then(response => response.json())

    .then(data => {

        alert(data.message);

        window.location.href =
            "index.html";
    })

    .catch(error => {

        console.log(error);
    });
}