const api = {
    get: (url) => fetch(url, { credentials: 'include' }).then(handleResponse),
    post: (url, payload) => fetch(url, {
        method: 'POST', credentials: 'include', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(payload || {})
    }).then(handleResponse),
    put: (url, payload) => fetch(url, {
        method: 'PUT', credentials: 'include', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(payload || {})
    }).then(handleResponse),
    delete: (url) => fetch(url, { method: 'DELETE', credentials: 'include' }).then(handleResponse)
};

function handleResponse(response) {
    return response.json().then(result => {
        if (!response.ok || result.success === false) throw new Error(result.message || 'Something went wrong');
        return result;
    });
}
function serializeForm(form) { return Object.fromEntries(new FormData(form).entries()); }
function showMessage(targetId, message, type = 'success') {
    const el = document.getElementById(targetId);
    if (!el) return;
    el.className = `message-box ${type}`;
    el.innerHTML = message;
}
function badge(status) { return `<span class="badge ${String(status).toLowerCase()}">${status}</span>`; }
function logoutHandler() { api.post(URLS.auth.logout).then(() => window.location.href = '/login').catch(err => alert(err.message)); }
document.addEventListener('click', (e) => { if (e.target && e.target.id === 'logoutBtn') logoutHandler(); });
