package com.model.login_cadastro;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import lombok.Data;
import java.time.LocalDateTime;
import at.favre.lib.crypto.bcrypt.BCrypt;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private LocalDateTime dateRegister;
    
    @Column(nullable = false)
    private String typeUser;

    public void setPassword(String password) {
        this.password = BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public boolean checkPassword(String plainPassword) {
        return BCrypt.verifyer().verify(plainPassword.toCharArray(), this.password).verified;
    }
} 