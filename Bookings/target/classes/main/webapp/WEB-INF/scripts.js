document.addEventListener("DOMContentLoaded", function () {
    loadBookings();
});

// ✅ Function to Fetch and Display All Bookings
function loadBookings() {
    fetch("http://localhost:8080/viewBookings")
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById("bookingTableBody");
            tableBody.innerHTML = "";

            data.forEach(booking => {
                let row = `<tr>
                    <td>${booking.id}</td>
                    <td>${booking.customerName}</td>
                    <td>${booking.flightNumber}</td>
                    <td>${booking.hotelName}</td>
                    <td>${booking.bookingDate}</td>
                    <td>
                        <button class="action-btn edit-btn" onclick="editBooking(${booking.id})">Edit</button>
                        <button class="action-btn delete-btn" onclick="deleteBooking(${booking.id})">Delete</button>
                    </td>
                </tr>`;
                tableBody.innerHTML += row;
            });
        })
        .catch(error => console.error("Error fetching bookings:", error));
}

// ✅ Function to Add New Booking
document.getElementById("bookingForm").addEventListener("submit", function (event) {
    event.preventDefault();

    let newBooking = {
        customerName: document.getElementById("customerName").value,
        flightNumber: document.getElementById("flightNumber").value,
        hotelName: document.getElementById("hotelName").value,
        bookingDate: document.getElementById("bookingDate").value
    };

    fetch("http://localhost:8080/addBooking", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(newBooking)
    })
    .then(response => response.text())
    .then(() => {
        alert("Booking added successfully!");
        loadBookings(); // Refresh table
        document.getElementById("bookingForm").reset();
    })
    .catch(error => console.error("Error adding booking:", error));
});

// ✅ Function to Edit Booking (Prompt for new details)
function editBooking(id) {
    let newCustomerName = prompt("Enter new customer name:");
    let newFlightNumber = prompt("Enter new flight number:");
    let newHotelName = prompt("Enter new hotel name:");
    let newBookingDate = prompt("Enter new booking date (YYYY-MM-DD):");

    let updatedBooking = {
        customerName: newCustomerName,
        flightNumber: newFlightNumber,
        hotelName: newHotelName,
        bookingDate: newBookingDate
    };

    fetch(`http://localhost:8080/updateBooking?id=${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(updatedBooking)
    })
    .then(response => response.text())
    .then(() => {
        alert("Booking updated successfully!");
        loadBookings();
    })
    .catch(error => console.error("Error updating booking:", error));
}

// ✅ Function to Delete Booking
function deleteBooking(id) {
    if (confirm("Are you sure you want to delete this booking?")) {
        fetch(`http://localhost:8080/deleteBooking?id=${id}`, {
            method: "DELETE"
        })
        .then(response => response.text())
        .then(() => {
            alert("Booking deleted successfully!");
            loadBookings();
        })
        .catch(error => console.error("Error deleting booking:", error));
    }
}
