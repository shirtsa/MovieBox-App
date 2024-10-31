document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('productionDetailsForm');
    const input = document.getElementById('imageUrl');
    const productionDetailsPage = document.querySelector('.production_details_page');

    form.addEventListener('submit', function(event) {
        event.preventDefault();
        const currentProductionImageUrl = input.value;
        productionDetailsPage.style.backgroundImage = `url(${currentProductionImageUrl})`;
    });
});