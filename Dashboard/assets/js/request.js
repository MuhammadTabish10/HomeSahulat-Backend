// Usama Works

var token = localStorage.getItem("token");
var url = "https://api.homesahulat.stepwaysoftwares.com/api";

function getAllServiceProvidersByVerify(verified) {
    fetch(`${url}/service-provider/verified/${verified}`, {
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
                        <h4><a href="" class="badge badge-primary badge-pill px-3 py-2" onclick="">Approve</a></h4>
                        <h4><a href="" class="badge badge-primary badge-pill px-3 py-2" onclick="">Reject</a></h4>
                    </div>
                    <div class="pt-1 bg-img m-auto"><img src="${employee?.user?.profilePictureUrl}" class="img-fluid h-100" alt="employees-img"></div>
                    <div class="mt-3 employees-contant-inner">
                        <h4 class="mb-1">${employee?.user?.name}</h4>
                        <h5 class="mb-0 text-muted">${employee?.services?.name}</h5>
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
        debugger
        col.appendChild(card);
        cardContainer.appendChild(col);
    });

}

getAllServiceProvidersByVerify("false");

function downloadImg(id) {
    debugger
    // fetch(`${url}/file/ServiceProvider/ServiceProvider_${id}/Cnic_2023_12_30_15_55_17.png`, {
    //     method: "GET",
    //     headers: {
    //         "Content-Type": "application/json",
    //         "Authorization": `Bearer ${token}`,
    //     },
    // })
    //     .then((res) => res.json())
    //     .then((data) => {
    //         debugger
    //     })
    //     .catch((err) => {
    //         console.log(err);
    //     });
    const link = document.createElement('a');
    link.href = `http://localhost:8080/api/file/ServiceProvider/ServiceProvider_${10}/Cnic_2023_12_30_15_55_17.png`;
    link.click();
}