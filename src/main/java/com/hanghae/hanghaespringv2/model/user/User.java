package com.hanghae.hanghaespringv2.model.user;

import com.hanghae.hanghaespringv2.model.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String username;

    @Column(nullable = false, length = 200)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String provider;

    private String providerId;
}
