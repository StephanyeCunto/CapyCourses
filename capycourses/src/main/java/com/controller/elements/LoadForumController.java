package com.controller.elements;

import java.util.List;

import com.dto.ForumComentarioDTO;
import com.dto.ForumDTO;
import com.model.elements.LoadForum;

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

    public void addView(ForumDTO forumDTO) {
        LoadForum loadForum = new LoadForum();
        loadForum.addView(forumDTO);
    }

    public List<ForumComentarioDTO> loadComentario(String title){
        LoadForum loadForum = new LoadForum();
        return loadForum.LoadComentario(title);
    }

    public boolean curtiu(String title,String user){
        LoadForum loadForum = new LoadForum();
        return loadForum.curtiu(title, user);
    }

    public void curtir(String title, String user){
        LoadForum loadForum = new LoadForum();
        loadForum.curtir(title, user);
    }

    public void desCurtir(String title, String user){
        LoadForum loadForum = new LoadForum();
        loadForum.desCurtir(title, user);
    }

    public void addComentario(String title, String comentario){
        LoadForum loadForum = new LoadForum();
        loadForum.addComentario(title, comentario);
    }

    public void addForum(String title, String description, String category, String question){
        LoadForum loadForum = new LoadForum();
        loadForum.addForum(title, description, category, question);
    }
}
