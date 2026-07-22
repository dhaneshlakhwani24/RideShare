INSERT INTO users (id, mobile_number, password, role, active, created_at, updated_at) VALUES
(1, '9999999999', 'Pass@123', 'SUPERADMIN', b'1', NOW(), NOW()),
(2, '8888888888', 'Pass@123', 'DRIVER', b'1', NOW(), NOW()),
(3, '7777777777', 'Pass@123', 'USER', b'1', NOW(), NOW());

INSERT INTO user_profiles (id, user_id, full_name, email, gender, emergency_contact, created_at, updated_at) VALUES
(1, 1, 'System Admin', 'admin@ridenest.com', NULL, NULL, NOW(), NOW()),
(2, 2, 'Arjun Driver', 'driver@ridenest.com', NULL, NULL, NOW(), NOW()),
(3, 3, 'Riya User', 'user@ridenest.com', NULL, NULL, NOW(), NOW());

INSERT INTO driver_profiles (id, user_id, license_number, license_expiry, aadhaar_or_national_id, approval_status, approved_by, approval_remarks, created_at, updated_at) VALUES
(1, 2, 'LIC-2026-XY1', '2028-12-31', 'ID123456789', 'APPROVED', 1, 'Verified successfully', NOW(), NOW());

INSERT INTO vehicles (id, driver_id, registration_number, vehicle_type, brand, model, color, seat_capacity, bookable_seats, price_per_km, online, current_latitude, current_longitude, created_at, updated_at) VALUES
(1, 1, 'DL01AB1234', 'Sedan', 'Honda', 'City', 'White', 5, 4, 18.50, b'1', 28.6200000, 77.2200000, NOW(), NOW());

INSERT INTO rides (id, driver_id, vehicle_id, ride_type, status, origin_name, origin_lat, origin_lng, destination_name, destination_lat, destination_lng, departure_time, base_fare, available_seats, created_at, updated_at) VALUES
(1, 1, 1, 'SHARED', 'SCHEDULED', 'Connaught Place', 28.6315000, 77.2167000, 'India Gate', 28.6129000, 77.2295000, DATE_ADD(NOW(), INTERVAL 2 HOUR), 120.00, 4, NOW(), NOW());

INSERT INTO ride_requests (id, user_id, driver_id, vehicle_id, ride_id, ride_type, status, pickup_name, pickup_lat, pickup_lng, drop_name, drop_lat, drop_lng, requested_seats, estimated_fare, created_at, updated_at) VALUES
(1, 3, 1, 1, 1, 'SHARED', 'PENDING', 'Connaught Place', 28.6315000, 77.2167000, 'India Gate', 28.6129000, 77.2295000, 1, 120.00, NOW(), NOW());

INSERT INTO notifications (id, user_id, title, message, read_flag, created_at, updated_at) VALUES
(1, 2, 'Ride request received', 'A passenger is requesting one seat on your shared route.', b'0', NOW(), NOW());
