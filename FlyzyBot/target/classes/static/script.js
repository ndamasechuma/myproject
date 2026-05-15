const baseURL = "/booking";

document.getElementById("bookingForm").addEventListener("submit", function(e) {
  e.preventDefault();

  const booking = {
    id: document.getElementById("id").value,
    type: document.getElementById("type").value,
    customerName: document.getElementById("customerName").value,
    details: document.getElementById("details").value,
    status: document.getElementById("status").value
  };

  fetch(`${baseURL}/addBooking`, {
    method: "POST",
    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
    body: new URLSearchParams(booking)
  })
  .then(() => {
    alert("Booking Added!");
    loadBookings();
    document.getElementById("bookingForm").reset();
  });
});

function loadBookings() {
  fetch(`${baseURL}/viewBookings`)
    .then(response => response.text())
    .then(data => {
      const table = document.getElementById("bookingsTable");
      table.innerHTML = "";

      const rows = data.trim().split("\n");
      rows.forEach(row => {
        const cols = row.split(", ");
        const tr = document.createElement("tr");
        cols.forEach(col => {
          const td = document.createElement("td");
          td.innerText = col.split(": ")[1];
          tr.appendChild(td);
        });

        // Action buttons
        const actionsTd = document.createElement("td");

        const updateBtn = document.createElement("button");
        updateBtn.innerText = "Confirm";
        updateBtn.className = "action-btn";
        updateBtn.onclick = () => updateBooking(cols[0].split(": ")[1]);

        const deleteBtn = document.createElement("button");
        deleteBtn.innerText = "Delete";
        deleteBtn.className = "action-btn";
        deleteBtn.onclick = () => deleteBooking(cols[0].split(": ")[1]);

        actionsTd.appendChild(updateBtn);
        actionsTd.appendChild(deleteBtn);
        tr.appendChild(actionsTd);

        table.appendChild(tr);
      });
    });
}

function updateBooking(id) {
  fetch(`${baseURL}/updateBooking?id=${id}`, {method: "PUT"})
    .then(() => {
      alert("Booking Confirmed!");
      loadBookings();
    });
}

function deleteBooking(id) {
  fetch(`${baseURL}/deleteBooking?id=${id}`, {method: "DELETE"})
    .then(() => {
      alert("Booking Deleted!");
      loadBookings();
    });
}

// Load on page load
loadBookings();
