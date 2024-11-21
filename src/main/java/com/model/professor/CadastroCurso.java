package com.model.professor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

                registerquestionaire((List<Map<String, Object>>) moduleData.get("contentQuestionaire"), title,
                        moduleTitle,
                        moduleNumber);
                registerClasses((List<Map<String, Object>>) moduleData.get("contentLesson"), title, moduleTitle,
                        moduleNumber);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int g = 0;

    private void registerquestionaire(List<Map<String, Object>> questionaire, String title, String moduleTitle,
            Integer moduleNumber) {
        for (int h = 0; h < questionaire.size(); h++) {
            Map<String, Object> questionaireData = questionaire.get(h);

            String questionaireNumber = (String) questionaireData.get("questionaireNumber" + g);
            String questionaireTitle = (String) questionaireData.get("questionaireTitle" + g);
            String questionaireScore = (String) questionaireData.get("questionaireScore" + g);
            String questionaireArea = (String) questionaireData.get("questionaireDescription" + g);
            Integer cont = (Integer) questionaireData.get("cont" + g);

            Thread writerThread = new Thread(() -> {
                try (BufferedWriter writer = new BufferedWriter(
                        new FileWriter("CapyCourses\\src\\main\\resources\\com\\bd\\bd_questionaire.csv",
                                true))) {
                    writer.write(title + "," + moduleTitle + "," + moduleNumber + "," +
                            questionaireNumber + "," + questionaireTitle + "," +
                            questionaireArea + "," + questionaireScore + "," + cont);
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            System.out.println(questionaireData.get("questions" + h));

            Map<String, Object> questions = (Map<String, Object>) questionaireData.get("questions" + h);

            registerQuestions(questions, moduleTitle, questionaireNumber, questionaireTitle);
            writerThread.start();
            g++;
        }
    }

    private void registerQuestions(Map<String, Object> questionData, String moduleTitle, String questionaireNumber,
            String questionaireTitle) {
        for (int i = 0; i < questionData.size(); i++) {
            System.out.println("questionData "+questionData.get("type" + i));
            String questionNumber = (String) questionData.get("questionNumber" + i);
            String questionScore = (String) questionData.get("questionScore" + i);
            String questionText = (String) questionData.get("questionText" + i);
            String questionType = (String) questionData.get("type" + i);
            if (questionType.matches("SINGLE_CHOICE")) {
                List<Map<String, Object>> response = (List<Map<String, Object>>) questionData.get("responseField");
                if (response != null) {
                    for (int j = 0; j < response.size(); j++) {
                        String typeQuestion = (String) questionData.get("type");
                        if (typeQuestion.matches("SINGLE_CHOICE")) {
                        }
                    }
                }
            }
            String questionAnswer = (String) questionData.get("questionAnswer" + i);
            String questionArea = (String) questionData.get("questionArea" + i);
            Integer cont = (Integer) questionData.get("cont" + i);

            Thread writerThread = new Thread(() -> {
                try (BufferedWriter writer = new BufferedWriter(
                        new FileWriter("CapyCourses\\src\\main\\resources\\com\\bd\\bd_questions.csv",
                                true))) {
                    writer.write(moduleTitle + "," + questionaireNumber + "," + questionaireTitle + "," +
                            questionNumber + "," + questionText + "," +
                            questionType + "," + questionAnswer + "," + questionScore + "," + questionArea + ","
                            + cont);
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            writerThread.start();
        }
    }

    private int j = 0;

    private void registerClasses(List<Map<String, Object>> lessons, String title, String moduleTitle,
            Integer moduleNumber) {
        try {
            for (int k = 0; k < lessons.size(); k++) {
                Map<String, Object> lessonData = lessons.get(k);

                Integer lessonNumber = (Integer) lessonData.get("lessonNumber" + j);
                String lessonTitle = (String) lessonData.get("lessonTitle" + j);
                String lessonVideoLink = (String) lessonData.get("lessonVideoLink" + j);
                String lessonDetails = (String) lessonData.get("lessonDetails" + j);
                String lessonMaterials = (String) lessonData.get("lessonMaterials" + j);
                String lessonDuration = (String) lessonData.get("lessonDuration" + j);
                Integer cont = (Integer) lessonData.get("cont" + j);

                Thread writerThread = new Thread(() -> {
                    try (BufferedWriter writer = new BufferedWriter(
                            new FileWriter("CapyCourses\\src\\main\\resources\\com\\bd\\bd_aulas.csv", true))) {
                        writer.write(title + "," + moduleTitle + "," + moduleNumber + "," +
                                lessonNumber + "," + lessonTitle + "," +
                                lessonVideoLink + "," + lessonDetails + "," +
                                lessonMaterials + "," + lessonDuration + "," + cont);
                        writer.newLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                writerThread.start();
                j++;
            }
        } catch (Exception e) {
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
