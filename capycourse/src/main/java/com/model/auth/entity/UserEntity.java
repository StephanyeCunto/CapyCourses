package com.model.auth.entity;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String type;
    
    @Column(nullable = false)
    private String date;
    
    @Column(nullable = false)
    private String cpf;
    
    @Column(nullable = false)
    private String phone;
     
    @Column(nullable = false)
    private String education;
}
