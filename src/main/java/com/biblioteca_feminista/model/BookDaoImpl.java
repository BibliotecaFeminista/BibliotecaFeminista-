package com.biblioteca_feminista.model;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.biblioteca_feminista.config.DBManager;

public class BookDaoImpl implements BookDaoInterface {
    private Connection connection;
    private PreparedStatement stm ;

    @Override
    public void createBook (Book book){
        try {
            connection = DBManager.init();
            String sql = "INSERT INTO books (title, author, description, isbn, genre) VALUES (?,?,?,?,?)";
            stm = connection.prepareStatement(sql);
            stm.setString(1, book.getTitle());
            stm.setString(2, book.getAuthor());
            stm.setString(3, book.getDescription());
            stm.setString(4, book.getIsbn());
            stm.setString(5, book.getGenre());
            System.out.println(book.getTitle() + "\nha sido creado con éxito");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("No se ha podido crear");
        } finally  {
            DBManager.close();
        }
    }
     

    
}
