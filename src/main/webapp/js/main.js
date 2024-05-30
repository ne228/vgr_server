let loginForm = document.getElementById('loginForm');
if (loginForm != null)
    loginForm.elements['returnUrl'].value = window.location.pathname;