fetch("http://localhost:9090/records")
    .then((response) => response.json())
    .then((data) => {
        const table = document.querySelector(".add-row-table");

        data.forEach((row) => {
            const tr = document.createElement("tr");

            tr.innerHTML = `
                <td>${row.user.username}</td>
                <td>${row.record.score}</td>
                <td>${row.record.pointWeight}</td>
                <td>${new Date(row.record.date).toLocaleString()}</td>
                <td><a href="/maps.html?id=${row.record.id}" onclick="openCabinet('${row.record.cabinet}')">Inspect maps</a></td>
            `;

            table.appendChild(tr);
        });

        console.log(data);
    }
);

function openCabinet(cabinet) {
    fetch(`http://localhost:9090/cabinets/${cabinet}`)
        .then((response) => response.json())
        .then((data) => {
            const modal = document.querySelector(".modal");
            const modalContent = document.querySelector(".modal-content");

            modal.style.display = "block";

            modalContent.innerHTML = `
                <span class="close" onclick="closeModal()">&times;</span>
                <h2>Cabinet ${cabinet}</h2>
                <table>
                    <tr>
                        <th>Map</th>
                        <th>Score</th>
                    </tr>
            `;

            data.forEach((map) => {
                const tr = document.createElement("tr");

                tr.innerHTML = `
                    <td>${map.map}</td>
                    <td>${map.score}</td>
                `;

                modalContent.appendChild(tr);
            });
        });
}