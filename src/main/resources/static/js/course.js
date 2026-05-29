loadCourses();

let editingCourseId = null;

function renderCourses(courses) {

    const tableBody =
        document.getElementById("courseTableBody");

    tableBody.innerHTML = "";

    courses.forEach(course => {

        tableBody.innerHTML += `

        <tr>

            <td>${course.id}</td>

            <td>${course.courseName}</td>

            <td>${course.courseCode}</td>

            <td>${course.credits}</td>

            <td>

                <button class="btn btn-warning btn-sm me-2"
                        onclick="editCourse(${course.id})">

                    Edit

                </button>

                <button class="btn btn-danger btn-sm"
                        onclick="deleteCourse('${course.courseCode}')">

                    Delete

                </button>

            </td>

        </tr>

        `;
    });
}

function loadCourses() {

    fetch(`${BASE_URL}/api/courses`)

        .then(response => response.json())

        .then(data => {

            renderCourses(data.data);

        })

        .catch(error => {

            console.log("Error:", error);

        });
}

document.getElementById("courseForm")

    .addEventListener("submit", function(event) {

        event.preventDefault();

        const course = {

            courseName:
                document.getElementById("courseName").value,

            courseCode:
                document.getElementById("courseCode").value,

            credits:
                document.getElementById("credits").value
        };

        const url =
            editingCourseId == null
                ? `${BASE_URL}/api/courses`
                : `${BASE_URL}/api/courses/${editingCourseId}`;

        const method =
            editingCourseId == null
                ? "POST"
                : "PUT";

				fetch(url, {

				    method: method,

				    headers: {
				        "Content-Type": "application/json"
				    },

				    body: JSON.stringify(course)

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
				        editingCourseId == null
				            ? "Course Added Successfully"
				            : "Course Updated Successfully"
				    );

				    document.getElementById("courseForm").reset();

				    editingCourseId = null;

				    document.getElementById("submitButton").innerText =
				        "Add Course";

				    loadCourses();

				})

				.catch(error => {

				    alert(error.message);

				    console.log("Error:", error);

				});

    });

function deleteCourse(courseCode) {

    const confirmDelete =
        confirm("Are you sure you want to delete this course?");

    if (!confirmDelete) {
        return;
    }

    fetch(`${BASE_URL}/api/courses/delete/${courseCode}`, {

        method: "DELETE"

    })

    .then(response => response.json())

    .then(data => {

        alert("Course deleted successfully");

        loadCourses();

    })

    .catch(error => {

        console.log("Error:", error);

    });
}

function editCourse(courseId) {

    fetch(`${BASE_URL}/api/courses`)

        .then(response => response.json())

        .then(data => {

            const course =
                data.data.find(c => c.id == courseId);

            editingCourseId = course.id;

            document.getElementById("courseName").value =
                course.courseName;

            document.getElementById("courseCode").value =
                course.courseCode;

            document.getElementById("credits").value =
                course.credits;

            document.getElementById("submitButton").innerText =
                "Update Course";

            window.scrollTo({
                top: 0,
                behavior: "smooth"
            });

        })

        .catch(error => {

            console.log("Error:", error);

        });
}