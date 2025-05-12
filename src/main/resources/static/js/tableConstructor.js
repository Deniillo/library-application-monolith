document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('book-form');
    const resultDiv = document.getElementById('result');

    loadParamsFromLocalStorage();

    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const days = parseInt(this.days.value);
        const genre = this.genre.value;

        generateTable(days, genre);

        saveParamsToLocalStorage(days, genre);
    });
});

function saveParamsToLocalStorage(days, genre) {
    localStorage.setItem('days', days);
    localStorage.setItem('genre', genre);
}

function loadParamsFromLocalStorage() {
    const savedDays = localStorage.getItem('days');
    const savedGenre = localStorage.getItem('genre');

    if (savedDays && savedGenre) {
        document.getElementById('days').value = savedDays;
        document.getElementById('genre').value = savedGenre;

        generateTable(savedDays, savedGenre);
    }
}

function generateTable(days) {
    const resultDiv = document.getElementById('result');

    let tableHTML = `<table border="1"><tr><th>День</th><th>Книга</th><th>Автор</th></tr>`;

    const weekDays = ['Понедельник', 'Вторник', 'Среда', 'Четверг', 'Пятница', 'Суббота', 'Воскресенье'];

    const startDate = new Date();

    for (let day = 1; day <= days; day++) {
        const currentDate = new Date(startDate.getFullYear(), startDate.getMonth(), startDate.getDate() + day - 2);
        const dayOfWeek = weekDays[currentDate.getDay()];

        tableHTML += `<tr><td>${day} день - ${dayOfWeek}</td><td>Название книги</td><td>Имя автора</td></tr>`;
    }

    tableHTML += `</table>`;

    resultDiv.innerHTML = tableHTML;
}
