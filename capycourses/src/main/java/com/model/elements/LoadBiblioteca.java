package com.model.elements;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import com.dto.BibliotecaDTO;
import com.singleton.UserSession;

public class LoadBiblioteca {
    public List<BibliotecaDTO> loadBiblioteca() {
        List<BibliotecaDTO> biblioteca = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new FileReader("capycourses/src/main/resources/com/bd/bd_biblioteca.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");
                BibliotecaDTO bibliotecaDTO = new BibliotecaDTO();
                bibliotecaDTO.setAuthor(elements[0]);
                bibliotecaDTO.setTitle(elements[1]);
                bibliotecaDTO.setDescription(elements[3]);
                bibliotecaDTO.setCategory(elements[2]);
                biblioteca.add(bibliotecaDTO);
                bibliotecaDTO.setFavorite(favorite(elements[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return biblioteca;
    }

    private boolean favorite(String title) {
        try (BufferedReader br = new BufferedReader(
                new FileReader("capycourses/src/main/resources/com/bd/bd_conteudoFavorite.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");
                if (elements[0].equals(UserSession.getInstance().getUserEmail()) && elements[1].equals(title)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addFavorite(String title) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("capycourses/src/main/resources/com/bd/bd_conteudoFavorite.csv", true))) {
            writer.write(UserSession.getInstance().getUserEmail() + "," + title);
            writer.newLine();
        } catch (Exception e) {
        }
    }

    public void removeFavorite(String title) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new FileReader("capycourses/src/main/resources/com/bd/bd_conteudoFavorite.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");
                if (!(elements[0].equals(UserSession.getInstance().getUserEmail()) && elements[1].equals(title))) {
                    lines.add(line);
                }
            }
            
            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter("capycourses/src/main/resources/com/bd/bd_conteudoFavorite.csv"))) {
                for (String l : lines) {
                    writer.write(l);
                    writer.newLine();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
