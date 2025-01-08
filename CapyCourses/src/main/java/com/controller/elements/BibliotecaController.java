package com.controller.elements;

import java.util.*;
import com.dto.BibliotecaDTO;
import com.model.elements.LoadBiblioteca;

public class BibliotecaController {
    public List<BibliotecaDTO> loadBiblioteca() {
        LoadBiblioteca loadBiblioteca = new LoadBiblioteca();
        return loadBiblioteca.loadBiblioteca();
    }

    public void addFavorite(String title) {
        LoadBiblioteca loadBiblioteca = new LoadBiblioteca();
        loadBiblioteca.addFavorite(title);
    }

    public void removeFavorite(String title) {
        LoadBiblioteca loadBiblioteca = new LoadBiblioteca();
        loadBiblioteca.removeFavorite(title);
    }
}
