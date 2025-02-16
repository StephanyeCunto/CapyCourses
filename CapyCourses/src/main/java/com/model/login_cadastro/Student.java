package com.model.login_cadastro;

import com.model.student.StudentCourse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "students")
@Data
public class Student {
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

  @OneToMany(mappedBy = "student")
  private List<StudentCourse> courses = new ArrayList<>();
}
