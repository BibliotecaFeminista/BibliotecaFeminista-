package com.biblioteca_feminista.model;

import java.util.List;

public interface BookDaoInterface {
    void createBook(Book book);

    void updateBook(Book book);

    void deleteBook(int id);

    List<Book> findAll();

    List<Book> findByTitle(String title);

    List<Book> findByAuthor(String author);

    List<Book> findByGenre(String genre);
}
