(function() {
    window.addEventListener('load', function() {
        const performance = window.performance;
        const navigationEntry = performance.getEntriesByType('navigation')[0];

        if (navigationEntry) {
            const loadTime = navigationEntry.domContentLoadedEventEnd - navigationEntry.startTime;
            const roundedLoadTime = (loadTime / 1000).toFixed(3);
            const loadTimeElement = document.getElementById('loadTime');
            if (loadTimeElement) {
                loadTimeElement.textContent = `Время загрузки страницы: ${roundedLoadTime} секунд`;
            }
        }
    });
})();