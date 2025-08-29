

    document.addEventListener("DOMContentLoaded", function () {

      window.proceedToPayment = function () {

        const checkbox = document.getElementById("terms");

        if (!checkbox.checked) {

          alert("Please agree to the terms and conditions before proceeding.");

          return;

        }



        const firstName = document.getElementById("first-name").value.trim();

        const lastName = document.getElementById("last-name").value.trim();

        const title = document.getElementById("title").value;

        const dob = document.getElementById("dob").value.trim();

        if (!firstName || !lastName || !dob) {

          alert("Please fill in all required fields.");

          return;

        }

        document.getElementById("reviewFirstName").textContent = firstName;

        document.getElementById("reviewLastName").textContent = lastName;

        document.getElementById("reviewGender").textContent = title === "mr" ? "Male" : "Female";

        document.getElementById("reviewDOB").textContent = dob;

        document.getElementById("reviewModal").style.display = "block";

      };

      window.closeModal = function () {

        document.getElementById("reviewModal").style.display = "none";
        document.getElementById("first-name").scrollIntoView({ behavior: "smooth" });



      };

      window.confirmBooking = function () {

        closeModal();
        document.getElementById("bookingForm").submit();

        window.location.href = "/content/mysite/us/payform.html";

      };

    });

    function toggleAdditionalRequests(event) {

      event.preventDefault();

      const section = document.getElementById("additional-requests");

      section.style.display = section.style.display === "none" ? "block" : "none";

    }


