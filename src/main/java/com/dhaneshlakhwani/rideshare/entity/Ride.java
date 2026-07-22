package com.dhaneshlakhwani.rideshare.entity;

import com.dhaneshlakhwani.rideshare.enums.RideStatus;
import com.dhaneshlakhwani.rideshare.enums.RideType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rides")
public class Ride extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private DriverProfile driver;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;
    @Enumerated(EnumType.STRING)
    @Column(name = "ride_type", nullable = false, length = 20)
    private RideType rideType;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RideStatus status;
    @Column(name = "origin_name", nullable = false, length = 120)
    private String originName;
    @Column(name = "origin_lat", nullable = false, precision = 10, scale = 7)
    private BigDecimal originLat;
    @Column(name = "origin_lng", nullable = false, precision = 10, scale = 7)
    private BigDecimal originLng;
    @Column(name = "destination_name", nullable = false, length = 120)
    private String destinationName;
    @Column(name = "destination_lat", nullable = false, precision = 10, scale = 7)
    private BigDecimal destinationLat;
    @Column(name = "destination_lng", nullable = false, precision = 10, scale = 7)
    private BigDecimal destinationLng;
    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;
    @Column(name = "base_fare", precision = 10, scale = 2)
    private BigDecimal baseFare;
    @Column(name = "available_seats", nullable = false)
    private Integer availableSeats;
}
