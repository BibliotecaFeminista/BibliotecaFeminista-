package com.biblioteca_feminista.controller;

import com.biblioteca_feminista.model.Book;
import com.biblioteca_feminista.model.BookDaoInterface;

import java.util.ArrayList;
import java.util.List;

public class BookController {

    private final BookDaoInterface bookDao;

    public BookController(BookDaoInterface bookDao) {
        this.bookDao = bookDao;
    }

    public boolean createBook(Book book) {
        List<String> errors = validateForCreate(book);
        if (!errors.isEmpty()) {
            printValidationErrors(errors);
            System.out.println("El libro NO se creó.");
            return false;
        }
        try {
            bookDao.createBook(normalize(book));
            System.out.println("Libro creado correctamente.");
            return true;
        } catch (Exception e) {
            System.out.println("Error al crear el libro: " + e.getMessage());
            return false;
        }
    }

    public void updateBook(Book book) {
        List<String> errors = validateForCreate(book);
        if (!errors.isEmpty()) {
            printValidationErrors(errors);
            System.out.println("El libro NO se actualizó.");
            return;
        }
        try {
            bookDao.updateBook(normalize(book));
            System.out.println("Libro actualizado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al actualizar el libro: " + e.getMessage());
        }
    }

    public void updatedBook(Book book) {
        updateBook(book);
    }

    public void removeBook(int id) {
        try {
            bookDao.deleteBook(id);
            System.out.println("Libro eliminado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al eliminar el libro: " + e.getMessage());
        }
    }

    public void selectAllBooks() {
        try {
            List<Book> books = bookDao.findAll();
            if (books == null || books.isEmpty()) {
                System.out.println("No hay libros para mostrar.");
                return;
            }

            System.out.println("\nListado de libros:");
            for (Book b : books) {
                System.out.printf(
                        "ID: %d | Título: %s | Autora: %s | ISBN: %s | Género: %s%n",
                        b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn(), b.getGenre());
            }
        } catch (Exception e) {
            System.out.println("Error al listar libros: " + e.getMessage());
        }
    }

    public void findBookByTitle(String term) {
        if (isBlank(term)) {
            System.out.println("El título de búsqueda no puede estar vacío.");
            return;
        }
        try {
            List<Book> result = bookDao.findByTitle(term);
            printBooks(result);
        } catch (Exception e) {
            System.out.println("Error en la búsqueda por título: " + e.getMessage());
        }
    }

    public void findBookByAuthor(String term) {
        if (isBlank(term)) {
            System.out.println("El nombre de la autora no puede estar vacío.");
            return;
        }
        try {
            List<Book> result = bookDao.findByAuthor(term);
            printBooks(result);
        } catch (Exception e) {
            System.out.println("Error en la búsqueda por autora: " + e.getMessage());
        }
    }

    public void findBookByGenre(String term) {
        if (isBlank(term)) {
            System.out.println("El género no puede estar vacío.");
            return;
        }
        try {
            List<Book> result = bookDao.findByGenre(term);
            printBooks(result);
        } catch (Exception e) {
            System.out.println("Error en la búsqueda por género: " + e.getMessage());
        }
    }

    private List<String> validateForCreate(Book b) {
        List<String> errors = new ArrayList<>();
        if (b == null) {
            errors.add("El libro no puede ser null.");
            return errors;
        }
        if (isBlank(b.getTitle()))
            errors.add("El título es obligatorio.");
        if (isBlank(b.getAuthor()))
            errors.add("La autora es obligatoria.");
        if (isBlank(b.getIsbn()))
            errors.add("El ISBN es obligatorio.");
        if (isBlank(b.getGenre()))
            errors.add("El género es obligatorio.");
        if (b.getDescription() != null && trim(b.getDescription()).length() > 200) {
            errors.add("La descripción debe tener ≤ 200 caracteres.");
        }
        return errors;
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

    private void printValidationErrors(List<String> errors) {
        System.out.println("Errores de validación:");
        for (String e : errors)
            System.out.println(" - " + e);
    }

    private void printBooks(List<Book> books) {
        if (books == null || books.isEmpty()) {
            System.out.println("No se encontraron resultados.");
            return;
        }
        System.out.println("\nResultados:");
        for (Book b : books) {
            System.out.printf(
                    "ID: %d | Título: %s | Autora: %s | ISBN: %s | Género: %s%n",
                    b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn(), b.getGenre());
        }
    }

    private static String trim(String s) {
        return s == null ? null : s.trim();
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
