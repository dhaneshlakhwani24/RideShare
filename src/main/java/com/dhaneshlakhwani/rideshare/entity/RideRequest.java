package com.dhaneshlakhwani.rideshare.entity;

import com.dhaneshlakhwani.rideshare.enums.RequestStatus;
import com.dhaneshlakhwani.rideshare.enums.RideType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ride_requests")
public class RideRequest extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "driver_id")
    private DriverProfile driver;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "ride_id")
    private Ride ride;
    @Enumerated(EnumType.STRING) @Column(name = "ride_type", nullable = false, length = 20)
    private RideType rideType;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private RequestStatus status;
    @Column(name = "pickup_name", nullable = false, length = 120)
    private String pickupName;
    @Column(name = "pickup_lat", nullable = false, precision = 10, scale = 7)
    private BigDecimal pickupLat;
    @Column(name = "pickup_lng", nullable = false, precision = 10, scale = 7)
    private BigDecimal pickupLng;
    @Column(name = "drop_name", nullable = false, length = 120)
    private String dropName;
    @Column(name = "drop_lat", nullable = false, precision = 10, scale = 7)
    private BigDecimal dropLat;
    @Column(name = "drop_lng", nullable = false, precision = 10, scale = 7)
    private BigDecimal dropLng;
    @Column(name = "requested_seats", nullable = false)
    private Integer requestedSeats;
    @Column(name = "estimated_fare", precision = 10, scale = 2)
    private BigDecimal estimatedFare;
}
