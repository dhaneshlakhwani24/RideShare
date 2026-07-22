DROP TABLE IF EXISTS notifications;
DROP TABLE IF EXISTS ride_requests;
DROP TABLE IF EXISTS rides;
DROP TABLE IF EXISTS driver_approval_requests;
DROP TABLE IF EXISTS vehicles;
DROP TABLE IF EXISTS driver_profiles;
DROP TABLE IF EXISTS user_profiles;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    mobile_number VARCHAR(15) NOT NULL UNIQUE,
    password VARCHAR(120) NOT NULL,
    role VARCHAR(20) NOT NULL,
    active BIT NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

CREATE TABLE user_profiles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL UNIQUE,
    full_name VARCHAR(120),
    email VARCHAR(120),
    gender VARCHAR(20),
    emergency_contact VARCHAR(15),
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    CONSTRAINT fk_user_profiles_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE driver_profiles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL UNIQUE,
    license_number VARCHAR(40) NOT NULL,
    license_expiry VARCHAR(20),
    aadhaar_or_national_id VARCHAR(50),
    approval_status VARCHAR(20) NOT NULL,
    approved_by BIGINT NULL,
    approval_remarks VARCHAR(300),
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    CONSTRAINT fk_driver_profiles_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE vehicles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    driver_id BIGINT NOT NULL,
    registration_number VARCHAR(30) NOT NULL UNIQUE,
    vehicle_type VARCHAR(40) NOT NULL,
    brand VARCHAR(60),
    model VARCHAR(60),
    color VARCHAR(30),
    seat_capacity INT NOT NULL,
    bookable_seats INT NOT NULL,
    price_per_km DECIMAL(10,2),
    online BIT NOT NULL,
    current_latitude DECIMAL(10,7),
    current_longitude DECIMAL(10,7),
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    CONSTRAINT fk_vehicles_driver FOREIGN KEY (driver_id) REFERENCES driver_profiles(id)
);

CREATE TABLE driver_approval_requests (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    driver_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    remarks VARCHAR(300),
    reviewed_by BIGINT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    CONSTRAINT fk_driver_approval_driver FOREIGN KEY (driver_id) REFERENCES driver_profiles(id)
);

CREATE TABLE rides (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    driver_id BIGINT NOT NULL,
    vehicle_id BIGINT NOT NULL,
    ride_type VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    origin_name VARCHAR(120) NOT NULL,
    origin_lat DECIMAL(10,7) NOT NULL,
    origin_lng DECIMAL(10,7) NOT NULL,
    destination_name VARCHAR(120) NOT NULL,
    destination_lat DECIMAL(10,7) NOT NULL,
    destination_lng DECIMAL(10,7) NOT NULL,
    departure_time DATETIME NOT NULL,
    base_fare DECIMAL(10,2),
    available_seats INT NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    CONSTRAINT fk_rides_driver FOREIGN KEY (driver_id) REFERENCES driver_profiles(id),
    CONSTRAINT fk_rides_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicles(id)
);

CREATE TABLE ride_requests (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    driver_id BIGINT NULL,
    vehicle_id BIGINT NULL,
    ride_id BIGINT NULL,
    ride_type VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    pickup_name VARCHAR(120) NOT NULL,
    pickup_lat DECIMAL(10,7) NOT NULL,
    pickup_lng DECIMAL(10,7) NOT NULL,
    drop_name VARCHAR(120) NOT NULL,
    drop_lat DECIMAL(10,7) NOT NULL,
    drop_lng DECIMAL(10,7) NOT NULL,
    requested_seats INT NOT NULL,
    estimated_fare DECIMAL(10,2),
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    CONSTRAINT fk_ride_requests_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_ride_requests_driver FOREIGN KEY (driver_id) REFERENCES driver_profiles(id),
    CONSTRAINT fk_ride_requests_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicles(id),
    CONSTRAINT fk_ride_requests_ride FOREIGN KEY (ride_id) REFERENCES rides(id)
);

CREATE TABLE notifications (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NULL,
    title VARCHAR(120) NOT NULL,
    message VARCHAR(300) NOT NULL,
    read_flag BIT NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    CONSTRAINT fk_notifications_user FOREIGN KEY (user_id) REFERENCES users(id)
);
