package com.dhaneshlakhwani.rideshare.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "notifications")
public class Notification extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id")
    private User user;
    @Column(nullable = false, length = 120)
    private String title;
    @Column(nullable = false, length = 300)
    private String message;
    @Column(name = "read_flag", nullable = false)
    private Boolean readFlag;
}
