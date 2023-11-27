document.addEventListener("DOMContentLoaded", function() {
    const newsListContainer = document.getElementById('newsList');
    const createNewsForm = document.getElementById('createNewsForm');
    const editNewsForm = document.getElementById('editNewsForm');
    const deleteButton = document.getElementById('deleteButton');

    // Función para obtener la lista de noticias
    function fetchNews() {
        fetch('/news/listOfNews')
            .then(response => {
                const contentType = response.headers.get('content-type');
                if (response.status === 200 && contentType && contentType.includes('application/json')) {
                    return response.json();
                } else if (response.status === 204) {
                    // No Content: no hay noticias para mostrar
                    return [];
                } else {
                    throw new Error('Invalid response or empty content.');
                }
            })
            .then(newsList => {
                displayNews(newsList);
            })
            .catch(error => console.error('Error fetching news:', error));
    }

    // Función para mostrar las noticias en la vista
    function displayNews(newsList) {
        newsListContainer.innerHTML = '';
        newsList.forEach(news => {
            const card = document.createElement('div');
            card.classList.add('card');
            card.textContent = news.title;
            card.title = news.title;
            card.addEventListener('click', () => {
                fillEditForm(news);
            });
            newsListContainer.appendChild(card);
        });
    }

    // Función para rellenar el formulario de edición con los datos de la noticia seleccionada
    function fillEditForm(news) {
        document.getElementById('editId').value = news.id_news;
        document.getElementById('editTitle').value = news.title;
        document.getElementById('editBody').value = news.body;
    }

    // Manejar el envío del formulario para crear una noticia
    createNewsForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const title = document.getElementById('createTitle').value.trim();
        const body = document.getElementById('createBody').value.trim();

        if (title === '' || body === '') {
            alert('El título y el cuerpo de la noticia son requeridos.');
            return; // Detener el envío del formulario si los campos no cumplen la validación
        }

        createNews({ title, body });
    });

    // Función para crear una noticia
    function createNews(newsData) {
        fetch('/news/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newsData)
        })
            .then(response => response.json())
            .then(newNews => {
                fetchNews();
                createNewsForm.reset();
            })
            .catch(error => console.error('Error creating news:', error));
    }

    // Manejar el envío del formulario para editar una noticia
    function updateNews(id, newsData) {
        fetch(`/news/update/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newsData)
        })
            .then(response => response.json())
            .then(updatedNews => {
                fetchNews();
                editNewsForm.reset();
            })
            .catch(error => console.error('Error updating news:', error));
    }

// Manejar el envío del formulario para actualizar una noticia
    editNewsForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const id = document.getElementById('editId').value;
        const title = document.getElementById('editTitle').value.trim();
        const body = document.getElementById('editBody').value.trim();

        if (title === '' || body === '') {
            alert('You must complete both fields.');
            return; // Detener el envío del formulario si los campos no cumplen la validación
        }

        updateNews(id, { title, body });
    });


    // Manejar el clic en el botón de eliminación
    deleteButton.addEventListener('click', function() {
        const id = document.getElementById('editId').value;
        deleteNews(id);
    });

    // Función para "eliminar" una noticia (dar de baja)
    function deleteNews(id) {
        fetch(`/news/deactivate/${id}`, {
            method: 'PUT'
        })
            .then(response => {
                if (response.status === 204) {
                    fetchNews(); // Vuelve a cargar la lista de noticias después de eliminar una noticia
                } else if (response.ok) {
                    return response.json();
                } else {
                    throw new Error('Network response was not ok.');
                }
            })
            .then(deletedNews => {
                if (deletedNews) {
                    editNewsForm.reset();
                    fetchNews();
                }
            })
            .catch(error => console.error('Error deleting news:', error));
    }

    // Cargar las noticias al cargar la página
    fetchNews();
});