loadRankList();

function loadRankList() {

    fetch(`${BASE_URL}/api/marks/rank-list`)

        .then(response => response.json())

        .then(data => {

            renderRankList(data.data);

        })

        .catch(error => {

            console.log(error);

        });
}

function renderRankList(rankList) {

    const tableBody =
        document.getElementById("rankTableBody");

    tableBody.innerHTML = "";

    rankList.forEach(student => {

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

        let rowClass = "";

        if (student.rank === 1) {
            rowClass = "table-warning";
        }

        tableBody.innerHTML += `

        <tr class="${rowClass}">

            <td>

                ${medal} ${student.rank}

            </td>

			<td>

			    ${student.studentUsn}

			</td>

			<td>

			    ${student.studentName}

			</td>

            <td>

                ${student.percentage.toFixed(2)}%

            </td>

            <td>

                <span class="badge
                    ${
                        student.finalResult === "PASS"
                            ? "bg-success"
                            : "bg-danger"
                    }">

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

            loadRankList();

            return;
        }

        fetch(

            `${BASE_URL}/api/marks/rank-list/searchByUsn?usn=${usn}`

        )

        .then(response => response.json())

        .then(data => {

            renderRankList(data.data);

        })

        .catch(error => {

            console.log(error);

        });

    });