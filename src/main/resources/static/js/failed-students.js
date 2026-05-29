loadFailedStudents();

function loadFailedStudents() {

    fetch(`${BASE_URL}/api/marks/failed-students`)

        .then(response => response.json())

        .then(data => {

            renderFailedStudents(data.data);

        })

        .catch(error => {

            console.log(error);

        });
}

function renderFailedStudents(students) {

    const tableBody =
        document.getElementById(
            "failedStudentsTableBody"
        );

    tableBody.innerHTML = "";

    students.forEach(student => {

        let failedSubjectsHtml = "";

        student.failedSubjects.forEach(subject => {

            failedSubjectsHtml += `

            <span class="badge bg-danger me-2 mb-2 p-2">

                ${subject}

            </span>

            `;
        });

        tableBody.innerHTML += `

        <tr>

            <td>

                ${student.studentUsn}

            </td>

            <td>

                ${student.studentName}

            </td>

            <td>

                ${failedSubjectsHtml}

            </td>

            <td>

                ${student.percentage.toFixed(2)}%

            </td>

            <td>

                <span class="badge bg-danger">

                    ${student.finalResult}

                </span>

            </td>

        </tr>

        `;
    });
}

document.getElementById("searchUsn")

    .addEventListener("keyup", function() {

        const usn = this.value.trim();

        if (usn === "") {

            loadFailedStudents();

            return;
        }

        fetch(
            `${BASE_URL}/api/marks/failed-students/searchByUsn?usn=${usn}`
        )

        .then(response => response.json())

        .then(data => {

            renderFailedStudents(data.data);

        })

        .catch(error => {

            console.log(error);

        });

    });