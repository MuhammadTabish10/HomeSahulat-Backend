// Usama Works

var token = localStorage.getItem("token");
var url = "http://localhost:8080/api";

function getBookings() {
  fetch(url + "/booking", {
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

getBookings();

function addRowToTable(rowData) {
  const table = document.getElementById("bookingTable");

  for (var i = 0; i < rowData.length; i++) {
    const newRow = document.createElement("tr");

    const createdAt = rowData[i].createdAt || "";
    const appointmentTime = rowData[i].appointmentTime || "";
    const appointmentDate = rowData[i].appointmentDate || "";
    const bookingStatus = rowData[i].bookingStatus || "";
    const userName = rowData[i].user?.name || "";
    const serviceProviderId = rowData[i].serviceProvider?.id || "";

    const cellData = [createdAt, appointmentTime, appointmentDate, bookingStatus, serviceProviderId, userName];

    for (var j = 0; j < cellData.length; j++) {
      const cell = document.createElement("td");
      cell.innerHTML = cellData[j];
      newRow.appendChild(cell);
    }

    // Create and append buttons directly to the row
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
  return `<button class="btn" onclick="deleteBooking(${id})">
  <i class="fa fa-trash"
  data-toggle="tooltip" data-placement="top" title=""
  data-original-title="Delete"></i>
  </button>`;
}

function createEditButton(id) {
  return `<button class="mr-2 btn" class="btn" onclick="editBooking(${id})">
  <i class="fa fa-pencil"
  data-toggle="tooltip" data-placement="top" title="" 
  data-original-title="Edit"></i>
  </button>`;
}

function deleteBooking(id) {
  fetch(`${url}/booking/${id}`, {
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
function editBookings(id) { }


function getServiceCounts(service) {
  fetch(`${url}/booking/by-service/${service}`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token}`,
    },
  })
    .then((res) => res.json())
    .then((data) => {
      const elementId = `${service.toLowerCase()}Count`;
      const serviceCountElement = document.getElementById(elementId);
      if (serviceCountElement) {
        serviceCountElement.innerHTML = data;
      }
    })
    .catch((err) => {
      console.log(err);
    });
}

getServiceCounts("Electrician");
getServiceCounts("Plumber");
getServiceCounts("Carpenter");

function getTotalBookings() {
  fetch(`${url}/booking/total`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token}`,
    },
  })
    .then((res) => res.json())
    .then((data) => {
      const totalBookingCounts = document.getElementById("totalBookingCounts");
      totalBookingCounts.innerHTML = data;
    })
    .catch((err) => {
      console.log(err);
    });
}
getTotalBookings();

function getNewBookingCount() {
  fetch(`${url}/booking/new`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token}`,
    },
  })
    .then((res) => res.json())
    .then((data) => {
      const getNewBookingCount = document.getElementById("newBooking");
      getNewBookingCount.innerHTML = data;
    })
    .catch((err) => {
      console.log(err);
    });
}
getNewBookingCount();

function getCountOfBookingByStatus(status) {
  debugger
  fetch(`${url}/booking/by-status/${status}`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token}`,
    },
  })
    .then((res) => res.json())
    .then((data) => {
      const elementId = `${status.toLowerCase()}BookingCount`;
      const serviceCountElement = document.getElementById(elementId);
      if (serviceCountElement) {
        serviceCountElement.innerHTML = data;
      }
    })
    .catch((err) => {
      console.log(err);
    });
}
getCountOfBookingByStatus("Pending");
getCountOfBookingByStatus("Completed");
getCountOfBookingByStatus("Rejected");