// Usama Works

var token = localStorage.getItem("token");
var url = window.myConfig.baseUrl;

function getAllServiceProvidersByVerify() {
    fetch(`${url}/service-provider/verified/null`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`,
        },
    })
        .then((res) => res.json())
        .then((data) => {
            
            generateCard(data);
        })
        .catch((err) => {
            console.log(err);
        });
}

function generateCard(employeeData) {
    const cardContainer = document.getElementById('cardContainer');

    employeeData.forEach((employee) => {
        const col = document.createElement('div');
        col.className = 'col-xl-4 col-sm-6';

        const card = document.createElement('div');
        card.className = 'card card-statistics employees-contant px-2';
        card.innerHTML = `
            <div class="card-body pb-5 pt-4">
                <div class="text-center">
                    <div class="text-right">
                        <h4><a class="badge badge-primary badge-pill px-3 py-2" onclick="changeVerified(true, ${employee.id})">Approve</a></h4>
                        <h4><a class="badge badge-primary badge-pill px-3 py-2" onclick="changeVerified(false,${employee.id})">Reject</a></h4>
                    </div>
                    <div class="pt-1 bg-img m-auto"><img src="${employee?.user?.profilePictureUrl}" class="img-fluid h-100" alt="employees-img"></div>
                    <div class="mt-3 employees-contant-inner">
                        <h4 class="mb-1">${employee?.user?.name}</h4>
                        <h5 class="my-3 text-muted">${employee?.services?.name}</h5>
                        <h5 class="my-3 text-muted d-inline">Hourly Price: ${employee?.hourlyPrice}</h5>
                        <h5 class="my-3 text-muted d-inline px-1">Experience: ${employee?.totalExperience}</h5>
                        <h5 class="my-3 text-muted d-inline px-1">Rating: ${employee?.totalRating}</h5>
                        <div class="mt-3">
                            <a class="badge badge-pill badge-info-inverse px-3 py-2" onclick="downloadImg(${employee.id})">CNIC</a>
                            <a class="badge badge-pill badge-info-inverse px-3 py-2">Shop No</a>
                            <a class="badge badge-pill badge-info-inverse px-3 py-2">Address</a>
                            <a class="badge badge-pill badge-info-inverse px-3 py-2">Experiance</a>
                        </div>
                        <div class="mt-3">
                        <small class="py-2 mx-1">${employee?.cnicNo}</small>
                        <small class="py-2 mx-1">${employee?.cnicNo}</small>
                        <small class="py-2 mx-2">${employee?.cnicNo}</small>
                        <small class="py-2 mx-1">${employee?.cnicNo}</small>
                        </div>
                    </div>
                </div>
            </div>
        `;
        
        col.appendChild(card);
        cardContainer.appendChild(col);
    });

}

getAllServiceProvidersByVerify();

function downloadImg(id) {
    const link = document.createElement('a');
    link.href = `http://localhost:8080/api/file/ServiceProvider/ServiceProvider_${10}/Cnic_2023_12_30_15_55_17.png`;
    link.click();
}

function changeVerified(status, id) {
    const requestData = { id, verify: status };

    fetch(`${url}/service-provider/${id}/verify/${status}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`,
        },
        body: JSON.stringify(requestData),
    })
        .then((res) => {
            if (res.ok) {
                window.location.reload();
            } else {
                throw new Error(`HTTP error! Status: ${res.status}`);
            }
        })
        .catch((err) => {
            console.error(err);
        });
}


