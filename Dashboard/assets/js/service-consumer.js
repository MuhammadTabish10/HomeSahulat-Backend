// Usama Work

var token = localStorage.getItem("token");
var url = window.myConfig.baseUrl;

function getServiceConsumers() {
  fetch(url + "/user", {
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
getServiceConsumers()

function addRowToTable(rowData) {
  
  const table = document.getElementById("serviceConsumerTable");

  for (var i = 0; i < rowData.length; i++) {
    const newRow = document.createElement("tr");

    const createdAt = rowData[i].createdAt || "";
    const email = rowData[i].email || "";
    const name = rowData[i].name || "";
    const otpFlag = rowData[i].otpFlag ? "Verified" : "Not Verified";
    const phone = rowData[i].phone || "";

    const cellData = [createdAt, email, name, otpFlag, phone];

    for (var j = 0; j < cellData.length; j++) {
      const cell = document.createElement("td");

      // Add a conditional check for background color based on otpFlag
      if (j === 3) {
        cell.innerHTML = rowData[i].otpFlag ? "Verified" : "Not Verified";
        cell.className = rowData[i].otpFlag ? "text-success fw-bolder" : "text-danger fw-bolder";
      } else {
        cell.innerHTML = cellData[j];
      }

      // Add a conditional check for chip (tick or cross)
      if (j === 3) {
        const chip = document.createElement("span");
        chip.className = rowData[i].otpFlag ? "fas fa-check-circle ml-1 text-success" : "fas fa-times-circle text-danger ml-1";
        cell.appendChild(chip);
      }

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
  return `<button class="btn" onclick="deleteConsumer(${id})">
    <i class="fa fa-trash"
    data-toggle="tooltip" data-placement="top" title=""
    data-original-title="Delete"></i>
    </button>`;
}

function createEditButton(id) {
  return `<button class="mr-2 btn" class="btn" onclick="editConsumer(${id})">
  <i class="fa fa-pencil" 
  data-toggle="tooltip" data-placement="top" title="" 
  data-original-title="Edit"></i></button>`;
}

function deleteConsumer(id) {
  fetch(`${url}/user/${id}`, {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token}`,
    },
  })
    .then((res) => {
      if (!res.ok) {
        throw new Error(`Failed to delete Consumer. Status: ${res.status}`);
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
function editConsumer(id) { }