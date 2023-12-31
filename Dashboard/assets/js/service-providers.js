// Usama Work

var token = localStorage.getItem("token");
var url = "http://localhost:8080/api";

function getServiceProviders() {
    fetch(url + "/service-provider", {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`,
        },
    })
        .then((res) => {

            return res.json();
        })
        .then((data) => {
            addRowToTable(data);
        })
        .catch((err) => {
            console.log(err);
        });
}
getServiceProviders()

function addRowToTable(rowData) {
    debugger
    const table = document.getElementById("serviceProviderTable");

    for (var i = 0; i < rowData.length; i++) {
        const newRow = document.createElement("tr");

        const createdAt = rowData[i].createdAt || "";
        const desc = rowData[i].description || "";
        const haveShop = rowData[i].haveShop ? "Yes" : "No";
        const hourlyPrice = rowData[i].hourlyPrice || "";
        const totalExperience = rowData[i].totalExperience || "";
        const totalRating = rowData[i].totalRating || "";
        const serviceName = rowData[i].services?.name || "";
        const cnic = rowData[i].cnicNo || "";

        const cellData = [createdAt, desc, haveShop, hourlyPrice, totalExperience, totalRating, serviceName, cnic];

        for (var j = 0; j < cellData.length; j++) {
            const cell = document.createElement("td");
            cell.innerHTML = cellData[j];
            newRow.appendChild(cell);
        }

        newRow.appendChild(createButtonCell(createDeleteButton(rowData[i].id)));
        newRow.appendChild(createButtonCell(createEditButton(rowData[i].id)));

        table.appendChild(newRow);
    }
}

function createButtonCell(buttonHTML) {
    const cell = document.createElement("td");
    cell.innerHTML = buttonHTML;
    return cell;
}

function createDeleteButton(id) {
    return `<button class="btn" onclick="deleteProvider(${id})">
    <i class="fa fa-trash"
    data-toggle="tooltip" data-placement="top" title=""
    data-original-title="Delete"></i>
    </button>`;
}

function createEditButton(id) {
    return `<button class="mr-2 btn" class="btn" onclick="editProvider(${id})">
    <i class="fa fa-pencil" 
    data-toggle="tooltip" data-placement="top" title="" 
    data-original-title="Edit"></i></button>`;
}

function deleteProvider(id) {
    fetch(`${url}/service-provider/${id}`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`,
        },
    })
        .then((res) => {
            if (!res.ok) {

                throw new Error(`Failed to delete booking. Status: ${res.status}`);
            }
            return res.json();
        })
        .then((data) => {

            getBookings();
        })
        .catch((err) => {
            console.error(err);
        });
}

function editProvider(id) { }
