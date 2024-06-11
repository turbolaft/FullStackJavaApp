document.addEventListener('DOMContentLoaded', function () {
    const params = new URLSearchParams(window.location.search);
    const id = params.get('id');

    // document.getElementById('recordId').textContent = id;
    // document.getElementById('recordName').textContent = name;
    // document.getElementById('recordScore').textContent = score;

    fetch(`https://turbolaft.com/records/${id}`)
        .then((response) => response.json())
        .then((data) => {
            const row = document.querySelector(".row");

            for (let i = 0; i < data.maps.length; i++) {
                const map = data.maps[i];
                
                row.innerHTML += `
                    <div class="col-md-4">
                        <div class="card">
                            <img src="https://turbolaft.com/maps/${map.id}/before" class="card-img-top" alt="Map before 1">
                            <div class="card-body">
                                <h5 class="card-title">Map level ${map.mapLevel}</h5>
                            </div>
                        </div>
                    </div>
                `;
            }

            console.log(data);
        }
    );
});