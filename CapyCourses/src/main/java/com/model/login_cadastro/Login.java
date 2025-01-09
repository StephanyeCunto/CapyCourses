package com.model.login_cadastro;

<<<<<<< HEAD
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

=======
import com.dao.UserDAO;
import com.dao.StudentDAO;
import com.dao.TeacherDAO;
>>>>>>> 4a68bd8 (Sprint 00 - Resolvido Bug Cadastro Incompleto)
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Login {
    private String user;
    private String password;
<<<<<<< HEAD

    public String isCheck() {
        try (BufferedReader br = new BufferedReader(
                new FileReader("capycourses/src/main/resources/com/bd/bd_user.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");
                if (user.equals(elements[1])) {
                    if (password.equals(elements[2])) {    
                        if (completeRegistration(elements[4],elements[1])) {
                            return "true";
                        }
                        return "incomplete " + elements[4];
                    }
                }
            }
            return "false";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "false";
    }

    private boolean completeRegistration(String type,String email) {
        if (type.trim().equals("student")) {
            return isCheckStudent(email);
        }else if(type.trim().equals("teacher")){
            return isCheckTeacher(email);
        }

        return false;
    }

    private boolean isCheckStudent(String email) {
        try (BufferedReader br = new BufferedReader(
                new FileReader("capycourses/src/main/resources/com/bd/bd_student.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (email.equals(values[0])) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
=======
    private final UserDAO userDAO = new UserDAO();
    private final StudentDAO studentDAO = new StudentDAO();
    private final TeacherDAO teacherDAO = new TeacherDAO();

    public String isCheck() {
        try {
            User userFound = userDAO.buscarPorEmail(user);
            
            if (userFound != null && userFound.getPassword().equals(password)) {
                if (completeRegistration(userFound)) {
                    return "true";
                }
                return "incomplete " + userFound.getTypeUser().toLowerCase();
            }
            return "false";
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }

    private boolean completeRegistration(User user) {
        if (user.getTypeUser().equalsIgnoreCase("STUDENT")) {
            return isCheckStudent(user.getId());
        } else if (user.getTypeUser().equalsIgnoreCase("TEACHER")) {
            return isCheckTeacher(user.getId());
>>>>>>> 4a68bd8 (Sprint 00 - Resolvido Bug Cadastro Incompleto)
        }
        return false;
    }

<<<<<<< HEAD
    private boolean isCheckTeacher(String email) {
        try (BufferedReader br = new BufferedReader(
                new FileReader("capycourses/src/main/resources/com/bd/bd_teacher.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (email.equals(values[0])) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
=======
    private boolean isCheckStudent(Integer userId) {
        try {
            return studentDAO.buscarPorUserId(userId) != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isCheckTeacher(Integer userId) {
        try {
            return teacherDAO.buscarPorUserId(userId) != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
>>>>>>> 4a68bd8 (Sprint 00 - Resolvido Bug Cadastro Incompleto)
    }
}