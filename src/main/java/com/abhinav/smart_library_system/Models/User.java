package com.abhinav.smart_library_system.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role; // ROLE_STUDENT, ROLE_ADMIN

    @Enumerated(EnumType.STRING)
    private Tier membershipTier; // BRONZE, SILVER, GOLD

    private int creditScore = 700; // Starting reputation
}
