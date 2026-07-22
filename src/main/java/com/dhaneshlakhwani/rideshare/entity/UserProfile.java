package com.dhaneshlakhwani.rideshare.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_profiles")
public class UserProfile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    @Column(name = "full_name", length = 120)
    private String fullName;
    @Column(length = 120)
    private String email;
    @Column(length = 20)
    private String gender;
    @Column(name = "emergency_contact", length = 15)
    private String emergencyContact;
}
