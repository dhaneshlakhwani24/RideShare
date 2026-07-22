package com.dhaneshlakhwani.rideshare.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "vehicles", uniqueConstraints = @UniqueConstraint(name = "uk_vehicles_registration", columnNames = "registration_number"))
public class Vehicle extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private DriverProfile driver;
    @Column(name = "registration_number", nullable = false, length = 30)
    private String registrationNumber;
    @Column(name = "vehicle_type", nullable = false, length = 40)
    private String vehicleType;
    @Column(length = 60)
    private String brand;
    @Column(length = 60)
    private String model;
    @Column(length = 30)
    private String color;
    @Column(name = "seat_capacity", nullable = false)
    private Integer seatCapacity;
    @Column(name = "bookable_seats", nullable = false)
    private Integer bookableSeats;
    @Column(name = "price_per_km", precision = 10, scale = 2)
    private BigDecimal pricePerKm;
    @Column(nullable = false)
    private Boolean online;
    @Column(name = "current_latitude", precision = 10, scale = 7)
    private BigDecimal currentLatitude;
    @Column(name = "current_longitude", precision = 10, scale = 7)
    private BigDecimal currentLongitude;
}
