package com.dao;

import java.util.List;

public interface IDao<T> {
    void salvar(T obj);
    void editar(T obj);
    boolean deletar(T obj);
    T buscar(int id);
    List<T> buscarTodos();
} 