  var seatMap = document.getElementById('seatMap');
  var rows = Array.apply(null, { length: 10 }).map(function(_, i) { return i + 1; });
  var seatsPerRow = ['A', 'B', 'C', 'D', 'E', 'F'];

  var occupiedSeats = ['A2', 'B2', 'C2', 'D2', 'F2', 'A10', 'B10', 'D10'];
  var greenSeats = ['B8','C8','B9','C9','E8','F8','E9','F9','E10','F10'];

  var previouslySelected = JSON.parse(localStorage.getItem('selectedSeats') || '[]');
  var allOccupied = occupiedSeats.concat(previouslySelected);

  rows.forEach(function(row) {
    var label = document.createElement('div');
    label.className = 'row-label';
    label.innerText = row;
    seatMap.appendChild(label);

    seatsPerRow.forEach(function(col, index) {
      if (index === 3) seatMap.appendChild(document.createElement('div'));

      var seatCode = col + row;
      var seat = document.createElement('div');
      seat.className = 'seat';
      seat.innerText = seatCode;
      seat.setAttribute('data-code', seatCode);

      if (allOccupied.indexOf(seatCode) !== -1) {
        seat.classList.add('grey');
      } else if (greenSeats.indexOf(seatCode) !== -1) {
        seat.classList.add('green');
        seat.addEventListener('click', function() {
          seat.classList.toggle('selected');
        });
      } else {
        seat.addEventListener('click', function() {
          seat.classList.toggle('selected');
        });
      }

      seatMap.appendChild(seat);
    });
  });

  function continuePayment() {
    var selected = Array.prototype.slice.call(document.querySelectorAll('.seat.selected'))
            .map(function(seat) {
              return seat.getAttribute('data-code');
            });

    if (selected.length === 0) {
      alert('Please select at least one seat.');
      return;
    }

    var updated = previouslySelected.concat(selected.filter(function(code) {
      return previouslySelected.indexOf(code) === -1;
    }));
    localStorage.setItem('selectedSeats', JSON.stringify(updated));

    document.getElementById('bookedSeatsText').innerText =
            'Your seat' + (selected.length > 1 ? 's are' : ' is') + ' booked: ' + selected.join(', ');
    document.getElementById('confirmationBox').style.display = 'block';

    selected.forEach(function(code) {
      var seat = document.querySelector('.seat[data-code="' + code + '"]');
      if (seat) {
        seat.classList.remove('selected', 'green');
        seat.classList.add('grey');
        seat.replaceWith(seat.cloneNode(true)); // Remove event listeners
      }
    });
  }

  function closeConfirmation() {
    window.location.href = '/content/mysite/us/information.html';
  }
