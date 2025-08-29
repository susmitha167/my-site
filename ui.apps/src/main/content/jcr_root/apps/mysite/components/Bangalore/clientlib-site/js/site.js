
  function showPopup(price) {
    var discount = price * 0.05;
    var discounted = price - discount;

    document.getElementById('originalPrice').innerText = 'Original Price: ₹' + price.toFixed(0);
    document.getElementById('discountValue').innerText = 'Discount: ₹' + discount.toFixed(0);
    document.getElementById('finalPrice').innerText = 'Final Price: ₹' + discounted.toFixed(0);

    document.getElementById('discountModal').style.display = 'block';
  }

  function goToSeatSelection() {
    document.getElementById('discountModal').style.display = 'none';
    window.location.href = '/content/mysite/us/landing-page/seats.html';
  }
  function goBackToSearch() {
    window.location.href = '/content/mysite/us/landing-page.html'; // Change this URL as needed
  }
