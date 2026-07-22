async function loadAdminDashboard() {
    try {
        const [statsResult, approvalsResult] = await Promise.all([api.get(URLS.admin.dashboard), api.get(URLS.admin.pendingApprovals)]);
        const s = statsResult.data;
        document.getElementById('adminStats').innerHTML = `
            <div class="stat-card"><h3>Total Users</h3><p>${s.totalUsers}</p></div>
            <div class="stat-card"><h3>Total Drivers</h3><p>${s.totalDrivers}</p></div>
            <div class="stat-card"><h3>Pending Approvals</h3><p>${s.pendingApprovals}</p></div>
            <div class="stat-card"><h3>Active Vehicles</h3><p>${s.activeVehicles}</p></div>
        `;
        document.getElementById('approvalTable').innerHTML = approvalsResult.data.map(r => `
            <tr>
                <td>${r.driver.id}</td><td>${r.id}</td><td>${badge(r.status)}</td><td>${r.remarks ?? ''}</td>
                <td><div class="action-row"><button class="btn btn-success" onclick="approveDriver(${r.driver.id}, 'APPROVED')">Approve</button><button class="btn btn-danger" onclick="approveDriver(${r.driver.id}, 'REJECTED')">Reject</button></div></td>
            </tr>
        `).join('') || '<tr><td colspan="5">No pending approvals</td></tr>';
    } catch (err) { alert(err.message); window.location.href = '/login'; }
}
async function approveDriver(driverId, action) {
    const remarks = prompt(`Enter remarks for ${action}:`) || '';
    await api.post(URLS.admin.driverDecision(driverId), { action, remarks });
    loadAdminDashboard();
}
loadAdminDashboard();
