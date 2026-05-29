loadDepartments();

function loadDepartments() {

    fetch(`${BASE_URL}/api/departments`)

        .then(response => response.json())

        .then(data => {

            renderDepartments(data.data);

        })

        .catch(error => {

            console.log(error);

        });
}

function renderDepartments(departments) {

    const tableBody =
        document.getElementById(
            "departmentTableBody"
        );

    tableBody.innerHTML = "";

    departments.forEach(department => {

        tableBody.innerHTML += `

        <tr>

            <td>

                ${department.id}

            </td>

            <td>

                ${department.name}

            </td>

            <td>

                ${department.code}

            </td>

            <td>

                <button class="btn btn-warning me-2"

                        onclick="editDepartment(
                            ${department.id},
                            '${department.name}',
                            '${department.code}'
                        )">

                    Edit

                </button>

                <button class="btn btn-danger"

                        onclick="deleteDepartment(
                            ${department.id}
                        )">

                    Delete

                </button>

            </td>

        </tr>

        `;
    });
}

function addDepartment() {

    const name =
        document.getElementById(
            "departmentName"
        ).value;

    const code =
        document.getElementById(
            "departmentCode"
        ).value;

    fetch(`${BASE_URL}/api/departments`, {

        method: "POST",

        headers: {

            "Content-Type": "application/json"
        },

        body: JSON.stringify({

            name: name,
            code: code
        })
    })

    .then(response => response.json())

    .then(data => {

        alert(data.message);

        loadDepartments();

        document.getElementById(
            "departmentName"
        ).value = "";

        document.getElementById(
            "departmentCode"
        ).value = "";

    })

    .catch(error => {

        console.log(error);

    });
}

function editDepartment(id, oldName, oldCode) {

    const newName =
        prompt(
            "Enter Department Name",
            oldName
        );

    if (!newName) return;

    const newCode =
        prompt(
            "Enter Department Code",
            oldCode
        );

    if (!newCode) return;

    fetch(`${BASE_URL}/api/departments/${id}`, {

        method: "PUT",

        headers: {

            "Content-Type": "application/json"
        },

        body: JSON.stringify({

            name: newName,
            code: newCode
        })
    })

    .then(response => response.json())

    .then(data => {

        alert(data.message);

        loadDepartments();

    })

    .catch(error => {

        console.log(error);

    });
}

function deleteDepartment(id) {

    const confirmDelete =
        confirm(
            "Are you sure you want to delete this department?"
        );

    if (!confirmDelete) return;

    fetch(`${BASE_URL}/api/departments/${id}`, {

        method: "DELETE"
    })

    .then(response => response.json())

    .then(data => {

        alert(data.message);

        loadDepartments();

    })

    .catch(error => {

        console.log(error);

    });
}