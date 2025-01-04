package com.controller.elements;

import java.util.List;

import com.dto.ForumDTO;
import com.model.student.LoadForum;

public class LoadForumController {
    public List<ForumDTO> LoadForum() {
        LoadForum loadForum = new LoadForum();
        List<ForumDTO> forum = loadForum.LoadForum();
        return forum;
    }

    public List<ForumDTO> LoadMyForum() {
        LoadForum loadForum = new LoadForum();
        List<ForumDTO> myForum = loadForum.LoadMyForum();
        return myForum;
    }
}
