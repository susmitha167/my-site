function toggleReturnDate() {
    const tripType = document.querySelector('input[name="tripType"]:checked').value;
    const returnDate = document.getElementById("returnDate");
    if (tripType === "oneway") {
      returnDate.disabled = true;
      returnDate.value = "";
    } else {
      returnDate.disabled = false;
    }
  }

  function searchFlights() {
    const from = document.getElementById("fromCity").value;
    const to = document.getElementById("toCity").value;
    const departDate = document.getElementById("departDate").value;
    const returnDate = document.getElementById("returnDate").value;
    const travellers = document.getElementById("travellers").value;
    const tripType = document.querySelector('input[name="tripType"]:checked').value;

    if (!from || !to || !departDate || !travellers || (tripType === "round" && !returnDate)) {
      alert("Please fill all the fields");
      return;
    }

    if (from === "Bangalore" && to === "Hyderabad") {
      window.location.href = "/content/mysite/us/landing-page/btoh.html";
    } else if (from === "Hyderabad" && to === "Chennai") {
      window.location.href = "/content/mysite/us/landing-page/htoc.html";
    } else if (from === "Bangalore" && to === "Chennai") {
      window.location.href = "/content/mysite/us/landing-page/btoc.html";
    } else if (from === "Chennai" && to === "Hyderabad") {
      window.location.href = "/content/mysite/us/landing-page/ctoh.html";
    } else if (from === "Chennai" && to === "Bangalore") {
      window.location.href = "/content/mysite/us/landing-page/ctob.html";
    } else if (from === "Hyderabad" && to === "Bangalore") {
      window.location.href = "/content/mysite/us/landing-page/htob.html";
    } else {
      alert("Flights for this route not available or invalid selection.");
    }
  }

const today = new Date();
  const tomorrow = new Date(today);
  tomorrow.setDate(today.getDate() + 1);
  const formattedTomorrow = tomorrow.toISOString().split('T')[0];

  document.getElementById("departDate").setAttribute("min", formattedTomorrow);
  document.getElementById("returnDate").setAttribute("min", formattedTomorrow);


  window.onload = toggleReturnDate;