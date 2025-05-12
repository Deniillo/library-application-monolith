function createElement(tagName, options = {}) {
    const el = document.createElement(tagName);
    for (const key in options) {
        if (key === 'class') {
            el.className = options[key];
        } else if (key === 'text') {
            el.textContent = options[key];
        } else {
            el.setAttribute(key, options[key]);
        }
    }
    return el;
}

async function renderAuthorCard(author) {
    const card = createElement('div', { class: 'author-card p-4 border border-gray-200 bg-white rounded-lg shadow-md' });
    const name = createElement('p', { class: 'author-name text-lg font-medium mb-2', text: author.name });
    card.appendChild(name);

    const container = createElement('div', { class: 'comment-container mt-4 p-4 border border-gray-200 max-h-96 overflow-y-auto' });
    card.appendChild(container);

    try {
        const response = await fetch(`https://jsonplaceholder.typicode.com/posts?userId=${author.id}`);
        const comments = await response.json();
        
        comments.forEach(comment => {
            const commentEl = createElement('div', { class: 'comment mb-2 p-2 bg-gray-200 rounded', text: comment.body });
            container.appendChild(commentEl);
        });
    } catch (error) {
        console.error('Ошибка при получении комментариев:', error);
        const message = createElement('p', { class: 'error-message text-red-500 italic mt-2', text: '⚠ Что-то пошло не так' });
        container.appendChild(message);
    }

    return card;
}

async function renderAuthors(authors) {
    const list = document.querySelector('.author-list');
    
    for (let author of authors) {
        const card = await renderAuthorCard(author);
        list.appendChild(card);
    }
}

function filterAuthors(authors, condition) {
    return authors.filter(condition);
}

async function getData() {
    const preloader = document.querySelector('.preloader');
    preloader.classList.remove('hidden');

    try {
        const response = await fetch('https://jsonplaceholder.typicode.com/users');
        const users = await response.json();

        preloader.classList.add('hidden');

        let randomNumber = Math.floor(Math.random() * 5 + 1);
        let filteredUsers = filterAuthors(users, user => user.id >= randomNumber);

        await renderAuthors(filteredUsers);
    } catch (error) {
        console.error('Ошибка при получении данных:', error);
        preloader.classList.add('hidden');
        const message = createElement('p', { class: 'error-message text-red-500 italic mt-2', text: '⚠ Что-то пошло не так' });
        document.body.appendChild(message);
    }
}

window.onload = () => {
    getData();
};