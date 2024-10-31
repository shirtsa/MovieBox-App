/*If isShowingMore is true, it hides the additional items and changes the button text to "See More".
If isShowingMore is false, it shows the additional items and changes the button text to "See Less".*/

document.addEventListener('DOMContentLoaded', function () {
    const seeMoreButton = document.getElementById('see-more-button');
    let isShowingMore = false;

    seeMoreButton.addEventListener('click', function () {
        const hiddenItems = document.querySelectorAll('.image_movies_main:nth-child(n+6)');

        if (isShowingMore) {
            hiddenItems.forEach(item => {
                item.style.display = 'none';
            });
            this.textContent = 'See More';
        } else {
            hiddenItems.forEach(item => {
                item.style.display = 'block';
            });
            this.textContent = 'See Less';
        }

        isShowingMore = !isShowingMore;
    });
});