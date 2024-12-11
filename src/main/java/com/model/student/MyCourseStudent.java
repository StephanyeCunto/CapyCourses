package com.model.student;

import com.UserSession;
import com.model.Course.Course;

import lombok.*;
import java.io.*;

@Getter
@Setter
@AllArgsConstructor
public class MyCourseStudent {

    public void addCourse(Course course) throws FileNotFoundException, IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("CapyCourses\\src\\main\\resources\\com\\bd\\bd_myCourse.csv", true))) {
            writer.write(UserSession.getInstance().getUserEmail() + "," + course.getTitle() + "," + "started");
            writer.newLine();
        }
    }

    public boolean searhCourse(String title) {
        try (BufferedReader br = new BufferedReader(
                new FileReader("CapyCourses\\src\\main\\resources\\com\\bd\\bd_myCourse.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");
                if (UserSession.getInstance().getUserEmail().equals(elements[0]) && title.equals(elements[1])) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean searhCourseFilter(String title, String status) {
        try (BufferedReader br = new BufferedReader(
                new FileReader("CapyCourses\\src\\main\\resources\\com\\bd\\bd_myCourse.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");

                if (UserSession.getInstance().getUserEmail().equals(elements[0]) && title.equals(elements[1])
                        && status.equals(elements[2])) {
                    return true;
                }

                if (status.equals("todos")) {
                    if (UserSession.getInstance().getUserEmail().equals(elements[0]) && title.equals(elements[1])
                            && (elements[2].equals("started") || elements[2].equals("completed"))) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
