loadMarks();

let editingMarks = false;

let selectedStudentId = null;

function renderMarks(marksList) {

    const tableBody =
        document.getElementById("marksTableBody");

    tableBody.innerHTML = "";

    marksList.forEach(student => {

        student.marks.forEach(mark => {

            const grade =
                mark.marksObtained >= 90 ? "A+" :
                mark.marksObtained >= 80 ? "A" :
                mark.marksObtained >= 70 ? "B+" :
                mark.marksObtained >= 60 ? "B" :
                mark.marksObtained >= 50 ? "C+" :
                mark.marksObtained >= 35 ? "C" :
                "F";

            const result =
                mark.marksObtained >= 35
                    ? "PASS"
                    : "FAIL";

            tableBody.innerHTML += `

            <tr>

                <td>
                    ${student.studentUsn}
                </td>

                <td>
                    ${student.studentName}
                </td>

                <td>
                    ${mark.courseName}
                </td>

                <td>
                    ${mark.marksObtained}
                </td>

                <td>
                    ${grade}
                </td>

                <td>
                    ${result}
                </td>

                <td>

                    <button class="btn btn-warning btn-sm me-2"

                        onclick="editMarks(
                            ${student.studentId},
                            '${student.studentUsn}',
                            ${mark.courseId},
                            ${mark.marksObtained}
                        )">

                        Edit

                    </button>

                    <button class="btn btn-danger btn-sm"

                        onclick="deleteMarks(
                            ${student.studentId},
                            ${mark.courseId}
                        )">

                        Delete

                    </button>

                </td>

            </tr>

            `;
        });

    });
}

function loadMarks() {

    fetch(`${BASE_URL}/api/marks`)

        .then(response => response.json())

        .then(data => {

            renderMarks(data.data);

        })

        .catch(error => {

            console.log(error);

        });
}

document.getElementById("studentUsn")

    .addEventListener("keyup", function() {

        const usn = this.value.trim();

        const suggestionsBox =
            document.getElementById("usnSuggestions");

        suggestionsBox.innerHTML = "";

        if (usn === "") {
            return;
        }

        fetch(`${BASE_URL}/api/students/searchByUsn?usn=${usn}`)

            .then(response => response.json())

            .then(data => {

                const students = data.data;

                students.forEach(student => {

                    suggestionsBox.innerHTML += `

                    <button type="button"
                            class="list-group-item list-group-item-action"

                            onclick="selectStudent(
                                ${student.id},
                                '${student.usn}',
                                '${student.name}'
                            )">

                        ${student.usn} - ${student.name}

                    </button>

                    `;
                });

            })

            .catch(error => {

                console.log(error);

            });

    });
	
	function selectStudent(studentId, usn, name) {

	    selectedStudentId = studentId;

	    document.getElementById("studentUsn").value =
	        `${usn}`;

	    document.getElementById("usnSuggestions").innerHTML =
	        "";

	    fetch(`${BASE_URL}/api/students/${studentId}`)

	        .then(response => response.json())

	        .then(data => {

	            const student = data.data;

	            const courseDropdown =
	                document.getElementById("courseId");

					courseDropdown.innerHTML = "";

					if (student.courses.length === 0) {

					    courseDropdown.innerHTML = `

					    <option value="">

					        No Courses Assigned

					    </option>

					    `;

					    return;
					}

					courseDropdown.innerHTML = `

					<option value="">

					    Select Course

					</option>

					`;

					student.courses.forEach(course => {

					    courseDropdown.innerHTML += `

					    <option value="${course.id}">

					        ${course.courseName}

					    </option>

					    `;
					});
	        })

	        .catch(error => {

	            console.log(error);

	        });
	}

document.getElementById("marksForm")

    .addEventListener("submit", function(event) {

        event.preventDefault();

        const marksData = {

            studentId: selectedStudentId,

            courseId:
                document.getElementById("courseId").value,

            marksObtained:
                document.getElementById("marksObtained").value
        };

        const method =
            editingMarks ? "PUT" : "POST";

        fetch(`${BASE_URL}/api/marks`, {

            method: method,

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(marksData)

        })

        .then(async response => {

            const data = await response.json();

            if (!response.ok) {

                throw new Error(data.message);
            }

            return data;
        })

        .then(data => {

            alert(
                editingMarks
                    ? "Marks updated successfully"
                    : "Marks added successfully"
            );

            document.getElementById("marksForm").reset();

            document.getElementById("courseId").innerHTML = `

            <option value="">

                Select Course

            </option>

            `;

            editingMarks = false;

            selectedStudentId = null;

            document.getElementById("submitButton")
                .innerText = "Add Marks";

            loadMarks();

        })

        .catch(error => {

            alert(error.message);

            console.log(error);

        });

    });

function editMarks(
    studentId,
    studentUsn,
    courseId,
    marksObtained
) {

    selectedStudentId = studentId;

    document.getElementById("studentUsn").value =
        studentUsn;

    fetch(`${BASE_URL}/api/students/usn/${studentUsn}`)

        .then(response => response.json())

        .then(data => {

            const student = data.data;

            const courseDropdown =
                document.getElementById("courseId");

            courseDropdown.innerHTML = `

            <option value="">

                Select Course

            </option>

            `;

            student.courses.forEach(course => {

                courseDropdown.innerHTML += `

                <option value="${course.id}">

                    ${course.courseName}

                </option>

                `;
            });

            document.getElementById("courseId").value =
                courseId;

        });

    document.getElementById("marksObtained").value =
        marksObtained;

    editingMarks = true;

    document.getElementById("submitButton")
        .innerText = "Update Marks";

    window.scrollTo({
        top: 0,
        behavior: "smooth"
    });
}

function deleteMarks(studentId, courseId) {

    const confirmDelete =
        confirm("Are you sure you want to delete marks?");

    if (!confirmDelete) {
        return;
    }

    fetch(
        `${BASE_URL}/api/marks/student/${studentId}/course/${courseId}`,
        {
            method: "DELETE"
        }
    )

    .then(async response => {

        const data = await response.json();

        if (!response.ok) {

            throw new Error(data.message);
        }

        return data;
    })

    .then(data => {

        alert("Marks deleted successfully");

        loadMarks();

    })

    .catch(error => {

        alert(error.message);

        console.log(error);

    });
}