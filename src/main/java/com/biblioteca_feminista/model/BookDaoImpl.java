package com.biblioteca_feminista.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.biblioteca_feminista.config.DBManager;

public class BookDaoImpl implements BookDaoInterface {

    @Override
    public void createBook(Book book) {
        String sql = "INSERT INTO books (title, author, description, isbn, genre) VALUES (?,?,?,?,?)";
        try (Connection conn = DBManager.init();
                PreparedStatement stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stm.setString(1, book.getTitle());
            stm.setString(2, book.getAuthor());
            stm.setString(3, book.getDescription());
            stm.setString(4, book.getIsbn());
            stm.setString(5, book.getGenre());

            int affected = stm.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = stm.getGeneratedKeys()) {
                    if (rs.next()) {
                        book.setId(rs.getInt(1));
                    }
                }
            }
            System.out.println(book.getTitle() + " ha sido creado con éxito");

        } catch (Exception e) {
            System.out.println("No se ha podido crear: " + e.getMessage());
        } finally {
            DBManager.close();
        }
    }

    @Override
    public void updateBook(Book book) {
        String sql = "UPDATE books SET title=?, author=?, description=?, isbn=?, genre=? WHERE id=?";
        try (Connection conn = DBManager.init();
                PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setString(1, book.getTitle());
            stm.setString(2, book.getAuthor());
            stm.setString(3, book.getDescription());
            stm.setString(4, book.getIsbn());
            stm.setString(5, book.getGenre());
            stm.setInt(6, book.getId());

            int affected = stm.executeUpdate();
            if (affected == 0) {
                System.out.println("No se encontró libro con id " + book.getId());
            }
        } catch (Exception e) {
            System.out.println("No se ha podido actualizar: " + e.getMessage());
        } finally {
            DBManager.close();
        }
    }

    @Override
    public void deleteBook(int id) {
        String sql = "DELETE FROM books WHERE id=?";
        try (Connection conn = DBManager.init();
                PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setInt(1, id);
            int affected = stm.executeUpdate();
            if (affected == 0) {
                System.out.println("No se encontró libro con id " + id);
            }
        } catch (Exception e) {
            System.out.println("No se ha podido eliminar: " + e.getMessage());
        } finally {
            DBManager.close();
        }
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT id, title, author, description, isbn, genre FROM books ORDER BY id";
        List<Book> list = new ArrayList<>();
        try (Connection conn = DBManager.init();
                PreparedStatement stm = conn.prepareStatement(sql);
                ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (Exception e) {
            System.out.println("Error al listar: " + e.getMessage());
        } finally {
            DBManager.close();
        }
        return list;
    }

    @Override
    public List<Book> findByTitle(String title) {
        String sql = "SELECT id, title, author, description, isbn, genre " +
                "FROM books WHERE LOWER(title) LIKE LOWER(?) ORDER BY title";
        return queryList(sql, "%" + title + "%");
    }

    @Override
    public List<Book> findByAuthor(String author) {
        String sql = "SELECT id, title, author, description, isbn, genre " +
                "FROM books WHERE LOWER(author) LIKE LOWER(?) ORDER BY author";
        return queryList(sql, "%" + author + "%");
    }

    @Override
    public List<Book> findByGenre(String genre) {
        String sql = "SELECT id, title, author, description, isbn, genre " +
                "FROM books WHERE LOWER(genre) LIKE LOWER(?) ORDER BY genre";
        return queryList(sql, "%" + genre + "%");
    }

    private List<Book> queryList(String sql, String param) {
        List<Book> list = new ArrayList<>();
        try (Connection conn = DBManager.init();
                PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setString(1, param);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (Exception e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            DBManager.close();
        }
        return list;
    }

    private Book mapRow(ResultSet rs) throws SQLException {
        Book b = new Book(
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("description"),
                rs.getString("isbn"),
                rs.getString("genre"));
        b.setId(rs.getInt("id"));
        return b;
    }
}