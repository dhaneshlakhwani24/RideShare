document.getElementById('userRegistrationForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    try {
        const result = await api.post(URLS.auth.registerUser, serializeForm(e.target));
        showMessage('messageBox', `${result.message}. <a href="/login">Login now</a>`, 'success');
        e.target.reset();
    } catch (err) {
        showMessage('messageBox', err.message, 'error');
    }
});
