package com.view.elements.Forum;

import java.io.File;
import java.io.IOException;

import com.controller.elements.LoadForumJson;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CreateJsonForum {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String JSON_PATH = "capycourses/src/main/resources/com/json/forum.json";

    public static void saveForum(String author, String title, String description, String category, 
            String dateTime, int view, int like, int comments, String question, String filePath) {
        try {
            File file = new File(filePath);
            LoadForumJson forumData = new LoadForumJson(author, title, description, category, 
                    dateTime, view, like, comments, question);
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, forumData);
        } catch (IOException e) {
            System.err.println("Erro ao salvar fórum: " + e.getMessage());
        }
    }

    private static LoadForumJson loadForumData(String filePath) {
        try {
            return mapper.readValue(new File(filePath), LoadForumJson.class);
        } catch (IOException e) {
            System.err.println("Erro ao carregar fórum: " + e.getMessage());
            return null;
        }
    }

    public static String getSavedAuthor(String filePath) {
        LoadForumJson forum = loadForumData(filePath);
        return forum != null ? forum.getAuthor() : null;
    }

    public static String getSavedTitle(String filePath) {
        LoadForumJson forum = loadForumData(filePath);
        return forum != null ? forum.getTitle() : null;
    }

    public static String getSavedDescription(String filePath) {
        LoadForumJson forum = loadForumData(filePath);
        return forum != null ? forum.getDescription() : null;
    }

    public static String getSavedCategory(String filePath) {
        LoadForumJson forum = loadForumData(filePath);
        return forum != null ? forum.getCategory() : null;
    }

    public static String getSavedDateTime(String filePath) {
        LoadForumJson forum = loadForumData(filePath);
        return forum != null ? forum.getDateTime() : null;
    }

    public static int getSavedView(String filePath) {
        LoadForumJson forum = loadForumData(filePath);
        return forum != null ? forum.getView() : -1;
    }

    public static int getSavedLike(String filePath) {
        LoadForumJson forum = loadForumData(filePath);
        return forum != null ? forum.getLike() : -1;
    }

    public static int getSavedComments(String filePath) {
        LoadForumJson forum = loadForumData(filePath);
        return forum != null ? forum.getComments() : -1;
    }

    public static String getSavedQuestion(String filePath) {
        LoadForumJson forum = loadForumData(filePath);
        return forum != null ? forum.getQuestion() : null;
    }
}
