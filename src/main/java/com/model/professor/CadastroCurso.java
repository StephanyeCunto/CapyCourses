package com.model.professor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.UserSession;

public class CadastroCurso {

    public CadastroCurso(String title, String description, String category, String level, String tags,
                         List<Map<String, Object>> modulesData) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("CapyCourses\\src\\main\\resources\\com\\bd\\bd_curso.csv", true))) {
            writer.write(UserSession.getInstance().getUserEmail() + "," + title + "," + description + "," + category
                    + "," + level + "," + tags);
            writer.newLine();

            registerModules(modulesData, title);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registerModules(List<Map<String, Object>> modulesData, String title) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("CapyCourses\\src\\main\\resources\\com\\bd\\bd_modulos.csv", true))) {
            for (Map<String, Object> moduleData : modulesData) {
                String moduleTitle = (String) moduleData.get("moduleTitle");
                String moduleDuration = (String) moduleData.get("moduleDuration");
                String moduleDescription = (String) moduleData.get("moduleDescription");

                writer.write(title + "," + moduleTitle + "," + moduleDuration + "," + moduleDescription);
                writer.newLine();

                // Verifique se "lessons" está no tipo correto antes de passá-lo
                Object lessons = moduleData.get("lessons");
                if (lessons instanceof List) {
                    List<Map<String, String>> lessonsData = (List<Map<String, String>>) lessons;
                    registerClasses(lessonsData, title, moduleTitle);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registerClasses(List<Map<String, String>> lessonsData, String title, String moduleTitle) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("CapyCourses\\src\\main\\resources\\com\\bd\\bd_aulas.csv", true))) {
            for (Map<String, String> lessonData : lessonsData) {
                String lessonTitle = lessonData.get("lessonTitle");
                String lessonVideoLink = lessonData.get("lessonVideoLink");
                String lessonDetails = lessonData.get("lessonDetails");
                String lessonMaterials = lessonData.get("lessonMaterials");
                String lessonDuration = lessonData.get("lessonDuration");

                writer.write(title + "," + moduleTitle + "," + lessonTitle + "," + lessonVideoLink + "," + lessonDetails + ","
                        + lessonMaterials + "," + lessonDuration);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
