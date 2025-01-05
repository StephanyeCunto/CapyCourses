package com.view.elements.Forum;

import java.io.File;
import java.io.IOException;

import com.controller.elements.LoadForumJson;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CreateJsonForum {

    public static void saveForum(String author, String title, String description, String category, String dateTime,
    int view, int like, int comments,String question, String filePath) {
    {
    ObjectMapper mapper = new ObjectMapper();
    File file = new File(filePath);

    // Verifica se o arquivo existe e deleta
    if (file.exists()) {
        file.delete();
    }

    LoadForumJson loginJsonController = new LoadForumJson(author, title, description, category, dateTime, view,
        like, comments, question);
    try {
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, loginJsonController);
    } catch (IOException e) {
        System.out.println(e);
    }
    }
}


    public static String getSavedAuthor(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            LoadForumJson savedForum = mapper.readValue(new File(filePath), LoadForumJson.class);
            return savedForum.getAuthor();
        } catch (IOException e) {
            System.out.println("erro" + e);
            return null;
        }
    }

    public static String getSavedTitle(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            LoadForumJson savedForum = mapper.readValue(new File(filePath), LoadForumJson.class);
            return savedForum.getTitle();
        } catch (IOException e) {
            System.out.println("erro" + e);
            return null;
        }
    }

    public static String getSavedDescription(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            LoadForumJson savedForum = mapper.readValue(new File(filePath), LoadForumJson.class);
            return savedForum.getDescription();
        } catch (IOException e) {
            System.out.println("erro" + e);
            return null;
        }
    }

    public static String getSavedCategory(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            LoadForumJson savedForum = mapper.readValue(new File(filePath), LoadForumJson.class);
            return savedForum.getCategory();
        } catch (IOException e) {
            System.out.println("erro" + e);
            return null;
        }
    }

    public static String getSavedDateTime(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            LoadForumJson savedForum = mapper.readValue(new File(filePath), LoadForumJson.class);
            return savedForum.getDateTime();
        } catch (IOException e) {
            System.out.println("erro" + e);
            return null;
        }
    }

    public static int getSavedView(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            LoadForumJson savedForum = mapper.readValue(new File(filePath), LoadForumJson.class);
            return savedForum.getView();
        } catch (IOException e) {
            System.out.println("erro" + e);
            return -1;
        }
    }

    public static int getSavedLike(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            LoadForumJson savedForum = mapper.readValue(new File(filePath), LoadForumJson.class);
            return savedForum.getLike();
        } catch (IOException e) {
            System.out.println("erro" + e);
            return -1;
        }
    }

    public static int getSavedComments(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            LoadForumJson savedForum = mapper.readValue(new File(filePath), LoadForumJson.class);
            return savedForum.getComments();
        } catch (IOException e) {
            System.out.println("erro" + e);
            return -1;
        }
    }
public static String getSavedQuestion(String filePath) {
    ObjectMapper mapper = new ObjectMapper();
    try {
        LoadForumJson savedForum = mapper.readValue(new File(filePath), LoadForumJson.class);
        return savedForum.getQuestion();
    } catch (IOException e) {
        System.out.println("erro" + e);
        return null;
    }
}

}
