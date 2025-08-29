$(document).ready(function () {
    $("#submitBtn").click(function () {
        var inputVal = $("#userInput").val();

        var formData = { userInput: inputVal };

        $.ajax({
            type: "POST",
            url: "/bin/ThanujaServlet", // your servlet path
            data: {
                load: JSON.stringify(formData)
            },
            cache: false,
            success: function (data) {
                alert("Success: " + data.message);
                $("#responseDiv").text("Server says: " + data.message);
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            }
        });
    });
});