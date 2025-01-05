package com.model.elements;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.dto.ForumComentarioDTO;
import com.dto.ForumDTO;
import com.singleton.UserSession;

public class LoadForum {
    public List<ForumDTO> LoadForum() {
        List<ForumDTO> forum = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new FileReader("capycourses/src/main/resources/com/bd/bd_forum.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");
                ForumDTO forumDTO = new ForumDTO();
                forumDTO.setAuthor(elements[0]);
                forumDTO.setTitle(elements[1]);
                forumDTO.setDescription(elements[2]);
                forumDTO.setCategory(elements[3]);
                forumDTO.setDateTime(elements[4]);
                forumDTO.setView(Integer.parseInt(elements[5]));
                forumDTO.setLike(curtidadas(elements[1]));
                forumDTO.setComments(comentarios(elements[1]));
                forumDTO.setQuestion(elements[8]);
                forum.add(forumDTO);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return forum;
    }

    public List<ForumDTO> LoadMyForum() {
        List<ForumDTO> myForum = new ArrayList<>();
        List<ForumDTO> forum = LoadForum();

        for (ForumDTO forumDTO : forum) {
            if (forumDTO.getAuthor().equals(UserSession.getInstance().getUserName())) {
                myForum.add(forumDTO);
            }
        }

        return myForum;
    }

    public void addView(ForumDTO forumData) {
        List<ForumDTO> forums = LoadForum();
        for (ForumDTO forum : forums) {
            if (forum.getTitle().equals(forumData.getTitle())) {
                forum.setView(forum.getView() + 1);
                break;
            }
        }

        try (java.io.FileWriter fw = new java.io.FileWriter("capycourses/src/main/resources/com/bd/bd_forum.csv")) {
            for (ForumDTO forum : forums) {
                fw.write(forum.getAuthor() + "," +
                        forum.getTitle() + "," +
                        forum.getDescription() + "," +
                        forum.getCategory() + "," +
                        forum.getDateTime() + "," +
                        forum.getView() + "," +
                        forum.getLike() + "," +
                        forum.getComments() + "," +
                        forum.getQuestion() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<ForumComentarioDTO> LoadComentario(String title) {
        List<ForumComentarioDTO> comentarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new FileReader("capycourses/src/main/resources/com/bd/bd_forum_comentarios.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");
                if (title.equals(elements[0])) {
                    ForumComentarioDTO comentario = new ForumComentarioDTO();
                    comentario.setUsuario(elements[1]);
                    comentario.setData(elements[2]);
                    comentario.setComentario(elements[3]);
                    comentarios.add(comentario);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return comentarios;
    }

    public boolean curtiu(String title, String user) {
        try (BufferedReader br = new BufferedReader(
                new FileReader("capycourses/src/main/resources/com/bd/bd_forum_likes.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");
                if (title.equals(elements[0]) && user.equals(elements[1])) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void curtir(String title, String user) {
        try (java.io.FileWriter fw = new java.io.FileWriter("capycourses/src/main/resources/com/bd/bd_forum_likes.csv",
                true)) {
            fw.write(title + "," + user + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void desCurtir(String title, String user) {
        List<String> likes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new FileReader("capycourses/src/main/resources/com/bd/bd_forum_likes.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");
                if (!title.equals(elements[0]) || !user.equals(elements[1])) {
                    likes.add(elements[0] + "," + elements[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (java.io.FileWriter fw = new java.io.FileWriter(
                "capycourses/src/main/resources/com/bd/bd_forum_likes.csv")) {
            for (String like : likes) {
                fw.write(like + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int curtidadas(String title){
        int curtidas = 0;
        try (BufferedReader br = new BufferedReader(
                new FileReader("capycourses/src/main/resources/com/bd/bd_forum_likes.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");
                if (title.equals(elements[0])) {
                    curtidas++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return curtidas;
    }

    private int comentarios(String title){
        int comentarios = 0;
        try (BufferedReader br = new BufferedReader(
                new FileReader("capycourses/src/main/resources/com/bd/bd_forum_comentarios.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");
                if (title.equals(elements[0])) {
                    comentarios++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return comentarios;
    }

    public void addComentario(String title, String comentario) {
        try (java.io.FileWriter fw = new java.io.FileWriter("capycourses/src/main/resources/com/bd/bd_forum_comentarios.csv",
                true)) {
            fw.write(title + "," + UserSession.getInstance().getUserName() +","  + comentario+ "," + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addForum(String title, String description, String category, String question) {
        try (java.io.FileWriter fw = new java.io.FileWriter("capycourses/src/main/resources/com/bd/bd_forum.csv",
                true)) {
            fw.write(UserSession.getInstance().getUserName() + "," + title + "," + description + "," + category + "," + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "," + 0 + "," + 0 + "," + 0 + "," + question + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
