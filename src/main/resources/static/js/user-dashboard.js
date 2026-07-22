async function loadUserDashboard() {
    try {
        const [statsResult, bookingsResult] = await Promise.all([api.get(URLS.user.dashboard), api.get(URLS.user.myBookings)]);
        const s = statsResult.data;
        document.getElementById('userStats').innerHTML = `
            <div class="stat-card"><h3>Total Bookings</h3><p>${s.totalBookings}</p></div>
            <div class="stat-card"><h3>Pending</h3><p>${s.pendingBookings}</p></div>
            <div class="stat-card"><h3>Accepted</h3><p>${s.acceptedBookings}</p></div>
        `;
        renderBookings(bookingsResult.data);
    } catch (err) { alert(err.message); window.location.href = '/login'; }
}
function renderBookings(items) {
    document.getElementById('bookingTable').innerHTML = items.map(x => `
        <tr><td>${x.rideType}</td><td>${x.pickupName}</td><td>${x.dropName}</td><td>${x.requestedSeats}</td><td>${badge(x.status)}</td></tr>
    `).join('') || '<tr><td colspan="5">No bookings yet</td></tr>';
}
document.getElementById('nearbyVehiclesForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    const result = await api.get(`${URLS.user.nearbyVehicles}?${new URLSearchParams(serializeForm(e.target)).toString()}`);
    document.getElementById('nearbyVehicleTable').innerHTML = result.data.map(v => `
        <tr>
            <td>${v.driverName}</td>
            <td>${v.vehicleType} (${v.registrationNumber})</td>
            <td>${v.bookableSeats}</td>
            <td>${v.distanceKm.toFixed(2)} KM</td>
            <td><button class="btn btn-primary" onclick="bookStandardRide(${v.vehicleId}, ${v.driverId}, ${Number(v.pricePerKm || 0)})">Book Ride</button></td>
        </tr>
    `).join('') || '<tr><td colspan="5">No nearby vehicles found</td></tr>';
});
async function bookStandardRide(vehicleId, driverId, pricePerKm) {
    const route = serializeForm(document.getElementById('sharedRideSearchForm'));
    await api.post(URLS.user.rideRequests, {
        vehicleId, driverId, pickupName: route.pickupName, pickupLat: Number(route.originLat), pickupLng: Number(route.originLng),
        dropName: route.dropName, dropLat: Number(route.destinationLat), dropLng: Number(route.destinationLng),
        requestedSeats: 1, estimatedFare: Number(pricePerKm || 0) * 5, requestMode: 'STANDARD'
    });
    alert('Ride request sent to driver');
    loadUserDashboard();
}
document.getElementById('sharedRideSearchForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    const payload = serializeForm(e.target);
    const query = new URLSearchParams({
        originLat: payload.originLat, originLng: payload.originLng, destinationLat: payload.destinationLat, destinationLng: payload.destinationLng, radiusKm: payload.radiusKm
    }).toString();
    const result = await api.get(`${URLS.user.sharedRides}?${query}`);
    document.getElementById('sharedRideTable').innerHTML = result.data.map(r => `
        <tr>
            <td>${r.driverName}</td>
            <td>${r.originName} → ${r.destinationName}</td>
            <td>${r.baseFare ?? 0}</td>
            <td>${r.availableSeats}</td>
            <td>${String(r.departureTime).replace('T', ' ')}</td>
            <td><button class="btn btn-primary" onclick="bookSharedRide(${r.rideId})">Join Shared</button></td>
        </tr>
    `).join('') || '<tr><td colspan="6">No shared rides found on this route</td></tr>';
});
async function bookSharedRide(rideId) {
    const p = serializeForm(document.getElementById('sharedRideSearchForm'));
    await api.post(URLS.user.rideRequests, {
        rideId, pickupName: p.pickupName, pickupLat: Number(p.originLat), pickupLng: Number(p.originLng),
        dropName: p.dropName, dropLat: Number(p.destinationLat), dropLng: Number(p.destinationLng), requestedSeats: 1, requestMode: 'SHARED'
    });
    alert('Shared ride request sent');
    loadUserDashboard();
}
loadUserDashboard();
