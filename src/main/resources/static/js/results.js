loadResults();

function loadResults() {

    fetch(`${BASE_URL}/api/marks/results`)

        .then(response => response.json())

        .then(data => {

            renderResults(data.data);

        })

        .catch(error => {

            console.log(error);

        });
}

function renderResults(resultsList) {

    const tableBody =
        document.getElementById("resultsTableBody");

    tableBody.innerHTML = "";

    resultsList.forEach(result => {

        let subjectsHtml = "";

        result.subjects.forEach(subject => {

            subjectsHtml += `

            <div class="mb-2 p-2 border rounded">

                <strong>
                    ${subject.courseName}
                </strong>

                (${subject.courseCode})

                <br>

                Marks:
                ${subject.marksObtained}

                |

                Grade:
                ${subject.grade}

                |

                <span class="${
                    subject.result === 'Pass'
                        ? 'text-success'
                        : 'text-danger'
                } fw-bold">

                    ${subject.result}

                </span>

            </div>

            `;
        });

        tableBody.innerHTML += `

        <tr>

            <td>

                ${result.studentUsn}

            </td>

            <td>

                ${result.studentName}

            </td>

            <td>

                ${subjectsHtml}

            </td>

            <td>

                ${result.totalMarks}

            </td>

            <td>

                ${result.percentage.toFixed(2)}%

            </td>

            <td>

                <span class="badge fs-6 px-3 py-2
                    ${
                        result.finalResult === 'PASS'
                            ? 'bg-success'
                            : 'bg-danger'
                    }">

                    ${result.finalResult}

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

            loadResults();

            return;
        }

        fetch(
            `${BASE_URL}/api/marks/results/searchByUsn?usn=${usn}`
        )

        .then(async response => {

            const data = await response.json();

            if (!response.ok) {

                throw new Error(data.message);
            }

            return data;
        })

        .then(data => {

            renderResults(data.data);

        })

        .catch(error => {

            console.log(error);

        });

    });