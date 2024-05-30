// Получение токена из localStorage или любого другого места, где он хранится
const token = localStorage.getItem('token'); // Предполагается, что токен хранится в localStorage

// Функция для отправки запроса с заголовком Authorization
function fetchData(url) {
    fetch(url, {
        method: 'GET', // Может быть любым методом запроса (GET, POST, и т. д.)
        headers: {
            'Authorization': `Bearer ${token}` // Добавление заголовка Authorization
        }
    })
        .then(response => {
            if (response.status === 401) {
                // Перенаправление на страницу входа в случае ошибки 401
                window.location.href = 'auth/login'; // Замените '/login' на ваш путь к странице входа
            }
            return response.json();
        })
        .then(data => {
            // Обработка полученных данных
            console.log(data);
        })
        .catch(error => {
            // Обработка ошибок
            console.error('Error:', error);
        });
}

// Вызов функции для получения данных
fetchData('http://localhost:8080/auth/login'); // Замените URL на ваш реальный URL
