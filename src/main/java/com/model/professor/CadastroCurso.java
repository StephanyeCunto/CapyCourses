package com.model.professor;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;

import com.UserSession;

public class CadastroCurso {
    public CadastroCurso(String title, String description, String category, String level, String tags) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("CapyCourses\\src\\main\\resources\\com\\bd\\bd_curso.csv", true))) {
            writer.write(UserSession.getInstance().getUserEmail() + "," + title + "," + description + "," + category + "," + level + "," + tags );
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
