package com.model.professor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import com.UserSession;

public class CadastroCurso {

    public CadastroCurso(String title, String description, String category, String level, String tags,
            List<Map<String, Object>> modulesData, LocalDate dateStart, LocalDate dateEnd, String durationTotal,
            boolean isDateEnd, boolean isCertificate,
            boolean isGradeMiniun, Object ComboBoxVisibily) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("CapyCourses\\src\\main\\resources\\com\\bd\\bd_curso.csv", true))) {
            writer.write(UserSession.getInstance().getUserEmail() + "," + title + "," + description + "," + category
                    + "," + level + "," + tags);
            writer.newLine();

            registerModules(modulesData, title);
            registerSettings(title, dateStart, dateEnd, durationTotal, isDateEnd, isCertificate,
                    isGradeMiniun, ComboBoxVisibily);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registerModules(List<Map<String, Object>> modulesData, String title) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("CapyCourses\\src\\main\\resources\\com\\bd\\bd_modulos.csv", true))) {
            for (Map<String, Object> moduleData : modulesData) {
                String moduleTitle = (String) moduleData.get("moduleTitle");
                Integer moduleNumber = (Integer) moduleData.get("moduleNumber");
                String moduleDuration = (String) moduleData.get("moduleDuration");
                String moduleDescription = (String) moduleData.get("moduleDescription");

                writer.write(title + "," + moduleNumber + "," + moduleTitle + "," + moduleDuration + ","
                        + moduleDescription);
                writer.newLine();
                registerClasses((List<Map<String, Object>>) moduleData.get("content"), title,moduleTitle,moduleNumber);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registerClasses(List<Map<String, Object>> lessons, String title, String moduleTitle, Integer moduleNumber) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("CapyCourses\\src\\main\\resources\\com\\bd\\bd_aulas.csv", true))) {
                
            Map<String, Object> lessonData = lessons.get(0); 
            
            int numberOfLessons = 0;
            while (lessonData.containsKey("lessonTitle" + numberOfLessons)) {
                numberOfLessons++;
            }
            
            for(int j = 0; j < numberOfLessons; j++) {
                Integer lessonNumber = (Integer) lessonData.get("lessonNumber" + j);
                String lessonTitle = (String) lessonData.get("lessonTitle" + j);
                String lessonVideoLink = (String) lessonData.get("lessonVideoLink" + j);
                String lessonDetails = (String) lessonData.get("lessonDetails" + j);
                String lessonMaterials = (String) lessonData.get("lessonMaterials" + j);
                String lessonDuration = (String) lessonData.get("lessonDuration" + j);
                
                writer.write(title + "," + moduleTitle + "," + moduleNumber + "," + 
                            lessonNumber + "," + lessonTitle + "," +
                            lessonVideoLink + "," + lessonDetails + "," + 
                            lessonMaterials + "," + lessonDuration);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registerSettings(String title, LocalDate dateStart, LocalDate dateEnd, String durationTotal,
            boolean isDateEnd,
            boolean isCertificate,
            boolean isGradeMiniun, Object ComboBoxVisibily) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("CapyCourses\\src\\main\\resources\\com\\bd\\bd_settingsCurso.csv", true))) {
            writer.write(title + "," + dateStart + "," + dateEnd + "," + durationTotal + "," + isDateEnd + ","
                    + isCertificate
                    + ","
                    + isGradeMiniun + "," + ComboBoxVisibily);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
