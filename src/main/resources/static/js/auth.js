document.getElementById('loginForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    try {
        const result = await api.post(URLS.auth.login, serializeForm(e.target));
        showMessage('messageBox', result.message, 'success');
        window.location.href = result.data.redirectUrl;
    } catch (err) {
        showMessage('messageBox', err.message, 'error');
    }
});
