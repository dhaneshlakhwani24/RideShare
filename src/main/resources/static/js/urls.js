const URLS = {
    auth: {
        login: '/api/auth/login',
        logout: '/api/auth/logout',
        registerUser: '/api/auth/register-user',
        registerDriver: '/api/auth/register-driver',
        me: '/api/auth/me'
    },
    user: {
        dashboard: '/api/user/dashboard',
        nearbyVehicles: '/api/user/nearby-vehicles',
        sharedRides: '/api/user/shared-rides',
        rideRequests: '/api/user/ride-requests',
        myBookings: '/api/user/my-bookings'
    },
    driver: {
        dashboard: '/api/driver/dashboard',
        location: '/api/driver/location',
        offerSharedRide: '/api/driver/rides/offer-shared',
        pendingRequests: '/api/driver/pending-requests',
        myRides: '/api/driver/my-rides',
        respondRequest: (id) => `/api/driver/ride-requests/${id}/respond`
    },
    admin: {
        dashboard: '/api/admin/dashboard',
        pendingApprovals: '/api/admin/pending-approvals',
        driverDecision: (driverId) => `/api/admin/driver-approvals/${driverId}/decision`
    }
};
