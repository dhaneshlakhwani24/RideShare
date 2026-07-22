async function loadDriverDashboard() {
    try {
        const [statsResult, requestsResult, ridesResult] = await Promise.all([
            api.get(URLS.driver.dashboard), api.get(URLS.driver.pendingRequests), api.get(URLS.driver.myRides)
        ]);
        const s = statsResult.data?.data || {};
        document.getElementById('driverStats').innerHTML = `
            <div class="stat-card"><h3>Approval Status</h3><p>${badge(s.approvalStatus)}</p></div>
            <div class="stat-card"><h3>Total Vehicles</h3><p>${s.totalVehicles}</p></div>
            <div class="stat-card"><h3>Active Shared Rides</h3><p>${s.activeSharedRides}</p></div>
            <div class="stat-card"><h3>Pending Requests</h3><p>${s.pendingRideRequests}</p></div>
        `;
        document.getElementById('pendingRequestTable').innerHTML = requestsResult.data.map(x => `
            <tr>
                <td>User #${x.userId}</td><td>${x.rideType}</td><td>${x.pickupName} → ${x.dropName}</td><td>${x.requestedSeats}</td><td>${badge(x.status)}</td>
                <td><div class="action-row"><button class="btn btn-success" onclick="driverDecision(${x.id}, 'ACCEPTED')">Accept</button><button class="btn btn-danger" onclick="driverDecision(${x.id}, 'REJECTED')">Reject</button></div></td>
            </tr>
        `).join('') || '<tr><td colspan="6">No pending requests</td></tr>';
        document.getElementById('driverRideTable').innerHTML = ridesResult.data.map(x => `
            <tr><td>${x.originName} → ${x.destinationName}</td><td>${String(x.departureTime).replace('T', ' ')}</td><td>${x.availableSeats}</td><td>${badge(x.status)}</td></tr>
        `).join('') || '<tr><td colspan="4">No rides created yet</td></tr>';
    } catch (err) { alert(err.message); window.location.href = '/login'; }
}
document.getElementById('locationForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    const p = serializeForm(e.target);
    p.latitude = Number(p.latitude); p.longitude = Number(p.longitude); p.online = p.online === 'true';
    await api.put(URLS.driver.location, p);
    alert('Location updated');
    loadDriverDashboard();
});
document.getElementById('sharedRideForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    const p = serializeForm(e.target);
    p.vehicleId = Number(p.vehicleId); p.originLat = Number(p.originLat); p.originLng = Number(p.originLng);
    p.destinationLat = Number(p.destinationLat); p.destinationLng = Number(p.destinationLng);
    p.baseFare = Number(p.baseFare); p.availableSeats = Number(p.availableSeats);
    p.departureTime = new Date(p.departureTime).toISOString().slice(0, 19);
    await api.post(URLS.driver.offerSharedRide, p);
    alert('Shared ride published');
    loadDriverDashboard();
});
async function driverDecision(id, action) { await api.post(URLS.driver.respondRequest(id), { action }); loadDriverDashboard(); }
loadDriverDashboard();
