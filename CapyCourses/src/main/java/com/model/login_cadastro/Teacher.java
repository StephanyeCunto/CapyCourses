package com.model.login_cadastro;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "teachers")
@Data
public class Teacher {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @OneToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column private Date dateOfBirth;

  @Column private String cpf;

  @Column private String telephone;

  @Column private String education;

  @Column private String areaOfInterest;
}
