package com.dhaneshlakhwani.rideshare.entity;

import com.dhaneshlakhwani.rideshare.enums.ApprovalStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "driver_approval_requests")
public class DriverApprovalRequest extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private DriverProfile driver;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ApprovalStatus status;
    @Column(length = 300)
    private String remarks;
    @Column(name = "reviewed_by")
    private Long reviewedBy;
}
