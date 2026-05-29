let editingStudentId = null;
let selectedStudentId = null;
let assignCoursesModal = null;

loadDepartments();
loadStudents();

document.addEventListener("DOMContentLoaded", function() {

    assignCoursesModal =
        new bootstrap.Modal(
            document.getElementById("assignCoursesModal")
        );

});

function loadDepartments() {

    fetch(`${BASE_URL}/api/departments`)

        .then(response => response.json())

        .then(data => {

            const departments = data.data;

            const departmentDropdown =
                document.getElementById("departmentId");

            departments.forEach(department => {

                departmentDropdown.innerHTML += `

                    <option value="${department.id}">

                        ${department.name}

                    </option>

                `;
            });

        })

        .catch(error => {

            console.log("Error loading departments:", error);

        });
}

function renderStudents(students) {

    const tableBody =
        document.getElementById("studentTableBody");

    tableBody.innerHTML = "";

    students.forEach(student => {

        tableBody.innerHTML += `

        <tr>

            <td>${student.id}</td>

            <td>${student.name}</td>

            <td>${student.usn}</td>

            <td>${student.email}</td>

            <td>${student.phoneNumber}</td>

            <td>${student.department.name}</td>
			
			<td>

			    ${
			        student.courses && student.courses.length > 0

			        ? student.courses
			            .map(course => course.courseName)
			            .join(", ")

			        : "No Courses Assigned"
			    }

			</td>

			<td>

			    <button class="btn btn-info btn-sm me-2"
			            onclick="assignCourses(${student.id})">

			        Assign Courses

			    </button>

			    <button class="btn btn-warning btn-sm me-2"
			            onclick="editStudent(${student.id})">

			        Edit

			    </button>

			    <button class="btn btn-danger btn-sm"
			            onclick="deleteStudent(${student.id})">

			        Delete

			    </button>

			</td>

        </tr>

        `;
    });
}

function loadStudents() {

    fetch(`${BASE_URL}/api/students`)

        .then(response => response.json())

        .then(data => {

            renderStudents(data.data);

        })

        .catch(error => {

            console.log("Error:", error);

        });
}

function deleteStudent(studentId) {

    const confirmDelete =
        confirm("Are you sure you want to delete this student?");

    if (!confirmDelete) {
        return;
    }

    fetch(`${BASE_URL}/api/students/${studentId}`, {

        method: "DELETE"

    })

    .then(response => response.json())

    .then(data => {

        alert("Student deleted successfully");

        loadStudents();

    })

    .catch(error => {

        console.log("Error:", error);

    });
}

function editStudent(studentId) {

    fetch(`${BASE_URL}/api/students/${studentId}`)

        .then(response => response.json())

        .then(data => {

            const student = data.data;

            editingStudentId = student.id;

            document.getElementById("name").value =
                student.name;

            document.getElementById("usn").value =
                student.usn;

            document.getElementById("email").value =
                student.email;

            document.getElementById("phoneNumber").value =
                student.phoneNumber;

            document.getElementById("dateOfBirth").value =
                student.dateOfBirth;

            document.getElementById("departmentId").value =
                student.department.id;

            document.getElementById("submitButton").innerText =
                "Update Student";

            window.scrollTo({
                top: 0,
                behavior: "smooth"
            });

        })

        .catch(error => {

            console.log("Error:", error);

        });
}

function assignCourses(studentId) {

    selectedStudentId = studentId;

    fetch(`${BASE_URL}/api/courses`)

        .then(response => response.json())

        .then(data => {

            const courses = data.data;

            const container =
                document.getElementById(
                    "coursesCheckboxContainer"
                );

            container.innerHTML = "";

            courses.forEach(course => {

                container.innerHTML += `

                <div class="form-check mb-3">

                    <input class="form-check-input"
                           type="checkbox"
                           value="${course.id}"
                           id="course${course.id}">

                    <label class="form-check-label"
                           for="course${course.id}">

                        ${course.courseName}

                    </label>

                </div>

                `;
            });

            assignCoursesModal.show();

        })

        .catch(error => {

            console.log("Error:", error);

        });
}

function submitAssignedCourses() {

    const checkedCourses =

        document.querySelectorAll(
            "#coursesCheckboxContainer input:checked"
        );

    const courseIds =

        Array.from(checkedCourses)

            .map(checkbox => Number(checkbox.value));

    if (courseIds.length === 0) {

        alert("Please select at least one course");

        return;
    }

    const requestBody = {

        studentId: selectedStudentId,

        courseIds: courseIds
    };

    fetch(`${BASE_URL}/api/students/assign-courses`, {

        method: "PUT",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(requestBody)

    })

    .then(async response => {

        const data = await response.json();

        if (!response.ok) {

            throw new Error(data.message);
        }

        return data;
    })

    .then(data => {

        alert("Courses assigned successfully");

        assignCoursesModal.hide();

        loadStudents();

    })

    .catch(error => {

        alert(error.message);

        console.log("Error:", error);

    });
}

document.getElementById("searchName")

    .addEventListener("keyup", function() {

        const name = this.value;

        if (name.trim() === "") {

            loadStudents();
            return;
        }

        fetch(`${BASE_URL}/api/students/searchByName?name=${name}`)

            .then(response => response.json())

            .then(data => {

                renderStudents(data.data);

            })

            .catch(error => {

                console.log("Error:", error);

            });

    });
	
	document.getElementById("searchUsn")

	    .addEventListener("keyup", function() {

	        const usn = this.value;

	        if (usn.trim() === "") {

	            loadStudents();
	            return;
	        }

	        fetch(`${BASE_URL}/api/students/searchByUsn?usn=${usn}`)

	            .then(response => response.json())

	            .then(data => {

	                renderStudents(data.data);

	            })

	            .catch(error => {

	                console.log("Error:", error);

	            });

	    });


document.getElementById("studentForm")

    .addEventListener("submit", function(event) {

        event.preventDefault();

        const student = {

            name:
                document.getElementById("name").value,

            usn:
                document.getElementById("usn").value,

            email:
                document.getElementById("email").value,

            phoneNumber:
                document.getElementById("phoneNumber").value,

            dateOfBirth:
                document.getElementById("dateOfBirth").value,

            departmentId:
                document.getElementById("departmentId").value
        };

		const url =
		    editingStudentId == null
		        ? `${BASE_URL}/api/students`
		        : `${BASE_URL}/api/students/${editingStudentId}`;

		const method =
		    editingStudentId == null
		        ? "POST"
		        : "PUT";

		fetch(url, {

		    method: method,

		    headers: {
		        "Content-Type": "application/json"
		    },

		    body: JSON.stringify(student)

		})

		.then(response => response.json())

		.then(data => {

		    console.log(data);

		    alert(
		        editingStudentId == null
		            ? "Student Added Successfully"
		            : "Student Updated Successfully"
		    );

		    document.getElementById("studentForm").reset();

		    editingStudentId = null;

		    document.getElementById("submitButton").innerText =
		        "Add Student";

		    loadStudents();

		})

		.catch(error => {

		    console.log("Error:", error);

		});

    });