package com.model.student;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.dto.ForumDTO;

public class LoadForum {
    public List<ForumDTO> LoadForum() {
        List<ForumDTO> forum = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new FileReader("CapyCourses\\src\\main\\resources\\com\\bd\\bd_forum.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");
                ForumDTO forumDTO = new ForumDTO();
                forumDTO.setAuthor(elements[0]);
                forumDTO.setTitle(elements[1]);
                forumDTO.setDescription(elements[2]);
                forumDTO.setCategory(elements[3]);
                forumDTO.setDateTime(elements[4]);
                forumDTO.setTopics(Integer.parseInt(elements[5]));
                forum.add(forumDTO);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return forum;
    }
}
