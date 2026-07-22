document.getElementById('driverRegistrationForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    try {
        const payload = serializeForm(e.target);
        payload.seatCapacity = Number(payload.seatCapacity);
        payload.pricePerKm = payload.pricePerKm ? Number(payload.pricePerKm) : null;
        const result = await api.post(URLS.auth.registerDriver, payload);
        showMessage('messageBox', `${result.message}. Superadmin approval is required before going online.`, 'success');
        e.target.reset();
    } catch (err) {
        showMessage('messageBox', err.message, 'error');
    }
});
