package com.model.student;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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
                forumDTO.setLike(Integer.parseInt(elements[6]));
                forumDTO.setComments(Integer.parseInt(elements[7]));
                forum.add(forumDTO);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return forum;
    }

    public List<ForumDTO> LoadMyForum(){
        List<ForumDTO> myForum = new ArrayList<>();
        List<ForumDTO> forum = LoadForum();

        for (ForumDTO forumDTO : forum) {
            if (forumDTO.getAuthor().equals(UserSession.getInstance().getUserName())) {
                myForum.add(forumDTO);
            }
        }

        return myForum;
    }
}
