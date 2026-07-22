package com.dhaneshlakhwani.rideshare.entity;

import com.dhaneshlakhwani.rideshare.enums.Role;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(name = "uk_users_mobile", columnNames = "mobile_number"))
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "mobile_number", nullable = false, length = 15)
    private String mobileNumber;
    @JsonIgnore
    @Column(name = "password", nullable = false, length = 120)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;
    @Column(nullable = false)
    private Boolean active;
}
