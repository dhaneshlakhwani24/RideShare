package com.dhaneshlakhwani.rideshare.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.dhaneshlakhwani.rideshare.enums.ApprovalStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "driver_profiles")
public class DriverProfile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    @Column(name = "license_number", nullable = false, length = 40)
    private String licenseNumber;
    @Column(name = "license_expiry", length = 20)
    private String licenseExpiry;
    @Column(name = "aadhaar_or_national_id", length = 50)
    private String aadhaarOrNationalId;
    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status", nullable = false, length = 20)
    private ApprovalStatus approvalStatus;
    @Column(name = "approved_by")
    private Long approvedBy;
    @Column(name = "approval_remarks", length = 300)
    private String approvalRemarks;
}
