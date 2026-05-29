loadDashboard();

async function loadDashboard() {

    try {

        const studentsResponse =
            await fetch(`${BASE_URL}/api/students`);

        const departmentsResponse =
            await fetch(`${BASE_URL}/api/departments`);

        const coursesResponse =
            await fetch(`${BASE_URL}/api/courses`);

        const failedStudentsResponse =
            await fetch(`${BASE_URL}/api/marks/failed-students`);

        const rankListResponse =
            await fetch(`${BASE_URL}/api/marks/rank-list`);

        const studentsData =
            await studentsResponse.json();

        const departmentsData =
            await departmentsResponse.json();

        const coursesData =
            await coursesResponse.json();

        const failedStudentsData =
            await failedStudentsResponse.json();

        const rankListData =
            await rankListResponse.json();

        // Total Counts

        document.getElementById("totalStudents")

            .innerText =
                studentsData.data.length;

        document.getElementById("totalDepartments")

            .innerText =
                departmentsData.data.length;

        document.getElementById("totalCourses")

            .innerText =
                coursesData.data.length;

        document.getElementById("failedStudents")

            .innerText =
                failedStudentsData.data.length;

        // Pass Fail Chart

        const totalStudents =
            studentsData.data.length;

        const failedStudents =
            failedStudentsData.data.length;

        const passedStudents =
            totalStudents - failedStudents;

        new Chart(

            document.getElementById(
                "passFailChart"
            ),

            {

                type: "pie",

                data: {

                    labels: [

                        "Pass",
                        "Fail"
                    ],

                    datasets: [{

                        data: [

                            passedStudents,
                            failedStudents
                        ],

                        backgroundColor: [

                            "#198754",
                            "#dc3545"
                        ]
                    }]
                }
            }
        );

        // Top Rankers

        const topRankersDiv =
            document.getElementById(
                "topRankers"
            );

        const top3 =
            rankListData.data.slice(0, 3);

        top3.forEach(student => {

            let medal = "";

            if (student.rank === 1) {

                medal = "🥇";
            }

            else if (student.rank === 2) {

                medal = "🥈";
            }

            else if (student.rank === 3) {

                medal = "🥉";
            }

            topRankersDiv.innerHTML += `

            <div class="border rounded p-3 mb-3 shadow-sm">

                <h5>

                    ${medal}
                    ${student.studentName}

                </h5>

                <p class="mb-1">

                    USN:
                    ${student.studentUsn}

                </p>

                <p class="mb-1">

                    Percentage:
                    ${student.percentage.toFixed(2)}%

                </p>

                <span class="badge bg-success">

                    ${student.finalResult}

                </span>

            </div>

            `;
        });

    }

    catch(error) {

        console.log(error);
    }
}