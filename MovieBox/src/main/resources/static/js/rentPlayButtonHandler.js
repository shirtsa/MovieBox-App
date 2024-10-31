document.addEventListener('DOMContentLoaded', function () {
    const rentPlayButton = document.getElementById('rentPlayButton');
    const rentPlayText = document.getElementById('rentPlayText');
    const rentIcon = document.getElementById('rentIcon');
    const playIcon = document.getElementById('playIcon');
    const currencySelect = document.getElementById('currency');

    // Initialize the button state
    let isRent = true;

    // Function to update rent price
    async function updateRentPrice() {
        const currency = currencySelect.value;
        const rentPriceInBGN = document.getElementById('rentPriceInBGN').value;

        try {
            const response = await fetch(`/api/convert?from=BGN&to=${currency}&amount=${rentPriceInBGN}`);
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            const data = await response.json();
            const newRentPrice = data.result.toFixed(2);

            // Update the rent price text
            rentPlayText.textContent = 'Rent ' + newRentPrice + '';

            // Ensure the correct icon is displayed
            rentIcon.style.display = 'inline';
            playIcon.style.display = 'none';

            // Ensure the button is in rent state
            isRent = true;
            currencySelect.style.display = 'inline'; // Show dropdown in Rent state
        } catch (error) {
            console.error('There was a problem with the fetch operation:', error);
        }
    }

    // Add event listener to update rent price when currency changes
    currencySelect.addEventListener('change', () => {
        updateRentPrice().catch(console.error);
    });

    // Add event listener for button click
    rentPlayButton.addEventListener('click', function (event) {
        if (isRent) {
            event.preventDefault();  // Prevent default link behavior
            rentPlayText.textContent = 'Play';
            rentIcon.style.display = 'none';
            playIcon.style.display = 'inline';
            currencySelect.style.display = 'none'; // Hide dropdown in Play state
            isRent = false;
        } else {
            // const movieId = rentPlayButton.getAttribute('data-movie-id');  // Ensure this attribute is set
            // window.location.href = `/productions/video/${movieId}`;

            // Redirect to video URL
            window.location.href = rentPlayButton.getAttribute('href');
        }
    });

    updateRentPrice().catch(console.error);
});

