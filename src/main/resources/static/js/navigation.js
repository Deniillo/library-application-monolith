document.addEventListener('DOMContentLoaded', function () {
    const menuItems = document.querySelectorAll('.menu-item');
    const currentLocation = window.location.href;

    menuItems.forEach(item => {
        if (item.href === currentLocation) {
            item.classList.add('bg-gray-300');
        } else {
            item.classList.remove('bg-gray-300');
        }
    });
});