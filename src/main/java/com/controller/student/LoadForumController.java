package com.controller.student;

import java.util.List;

import com.dto.ForumDTO;
import com.model.student.LoadForum;

public class LoadForumController {
    public List<ForumDTO> LoadForum() {
        LoadForum loadForum = new LoadForum();
        List<ForumDTO> forum = loadForum.LoadForum();
        return forum;
    }
}
