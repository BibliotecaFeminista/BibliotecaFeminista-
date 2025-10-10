package com.biblioteca_feminista.controller;

import com.biblioteca_feminista.model.Book;
import com.biblioteca_feminista.model.BookDaoInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class BookController {

    private final BookDaoInterface bookDao;

    public BookController(BookDaoInterface bookDao) {
        this.bookDao = bookDao;
    }

    public boolean createBook(Book book) {
        List<String> errors = validateForCreateOrUpdate(book);
        if (!errors.isEmpty())
            throw new IllegalArgumentException(joinErrors(errors));
        try {
            bookDao.createBook(normalize(book));
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el libro: " + e.getMessage(), e);
        }
        return false;
    }

    public void updateBook(Book book) {
        List<String> errors = validateForCreateOrUpdate(book);
        if (!errors.isEmpty())
            throw new IllegalArgumentException(joinErrors(errors));
        try {
            bookDao.updateBook(normalize(book));
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el libro: " + e.getMessage(), e);
        }
    }

    public void updatedBook(Book book) {
        updateBook(book);
    }

    public void removeBook(int id) {
        try {
            bookDao.deleteBook(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el libro: " + e.getMessage(), e);
        }
    }

    public List<Book> selectAllBooks() {
        try {
            return bookDao.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar los libros: " + e.getMessage(), e);
        }
    }

    public List<Book> findBookByTitle(String term) {
        if (isBlank(term))
            throw new IllegalArgumentException("El título de búsqueda no puede estar vacío.");
        try {
            return bookDao.findByTitle(term);
        } catch (Exception e) {
            throw new RuntimeException("Error en la búsqueda por título: " + e.getMessage(), e);
        }
    }

    public List<Book> findBookByAuthor(String term) {
        if (isBlank(term))
            throw new IllegalArgumentException("El nombre del autor no puede estar vacío.");
        try {
            return bookDao.findByAuthor(term);
        } catch (Exception e) {
            throw new RuntimeException("Error en la búsqueda por autor: " + e.getMessage(), e);
        }
    }

    public List<Book> findBookByGenre(String term) {
        if (isBlank(term))
            throw new IllegalArgumentException("El género no puede estar vacío.");
        try {
            return bookDao.findByGenre(term);
        } catch (Exception e) {
            throw new RuntimeException("Error en la búsqueda por género: " + e.getMessage(), e);
        }
    }

    private List<String> validateForCreateOrUpdate(Book b) {
        List<String> errors = new ArrayList<>();
        if (b == null) {
            errors.add("El libro no puede ser nulo.");
            return errors;
        }
        if (isBlank(b.getTitle()))
            errors.add("El título es obligatorio.");
        if (isBlank(b.getAuthor()))
            errors.add("El autor es obligatorio.");
        if (isBlank(b.getIsbn()))
            errors.add("El ISBN es obligatorio.");
        if (isBlank(b.getGenre()))
            errors.add("El género es obligatorio.");
        if (b.getDescription() != null && trim(b.getDescription()).length() > 200)
            errors.add("La descripción debe tener como máximo 200 caracteres.");
        return errors;
    }

    private String joinErrors(List<String> errors) {
        StringJoiner sj = new StringJoiner("\n");
        sj.add("Errores de validación:");
        for (String e : errors)
            sj.add(" - " + e);
        return sj.toString();
    }

    private Book normalize(Book b) {
        Book n = new Book(
                trim(b.getTitle()),
                trim(b.getAuthor()),
                b.getDescription() == null ? null : trim(b.getDescription()),
                trim(b.getIsbn()),
                trim(b.getGenre()));
        n.setId(b.getId());
        return n;
    }

    private static String trim(String s) {
        return s == null ? null : s.trim();
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
