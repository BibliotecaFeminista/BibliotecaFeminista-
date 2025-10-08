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

    // ========= LISTAR =========
    // Aún no hay método en el DAO. Deja mensaje temporal para que compile.
    public void selectAllBooks() {
        System.out.println("Listado de libros: pendiente de implementación en DAO (findAll).");
    }

    // ========= CREAR =========
    public void createBook(Book book) {
        List<String> errors = validateForCreate(book);
        if (!errors.isEmpty()) {
            printValidationErrors(errors);
            System.out.println("El libro NO se creó.");
            return;
        }
        try {
            // CONTRATO ACTUAL DEL DAO:
            // BookDaoInterface.createBook(Book)
            bookDao.createBook(normalize(book));
            System.out.println("Libro creado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al crear el libro: " + e.getMessage());
        }
    }

    // ========= BÚSQUEDAS =========
    // Aún no existen en el DAO. Deja mensajes temporales para que compile.
    public void findBookByTitle(String term) {
        if (isBlank(term)) {
            System.out.println("El título de búsqueda no puede estar vacío.");
            return;
        }
        System.out.println("Búsqueda por título: pendiente de implementación en DAO (findByTitle).");
    }

    public void findBookByAuthor(String term) {
        if (isBlank(term)) {
            System.out.println("El nombre de la autora no puede estar vacío.");
            return;
        }
        System.out.println("Búsqueda por autora: pendiente de implementación en DAO (findByAuthor).");
    }

    public void findBookByGenre(String term) {
        if (isBlank(term)) {
            System.out.println("El género no puede estar vacío.");
            return;
        }
        System.out.println("Búsqueda por género: pendiente de implementación en DAO (findByGenre).");
    }

    // ========= Validación / Normalización =========
    private List<String> validateForCreate(Book b) {
        List<String> errors = new ArrayList<>();
        if (b == null) {
            errors.add("El libro no puede ser null.");
            return errors;
        }
        if (isBlank(b.getTitle())) errors.add("El título es obligatorio.");
        if (isBlank(b.getAuthor())) errors.add("La autora es obligatoria.");
        if (isBlank(b.getIsbn())) errors.add("El ISBN es obligatorio.");
        if (isBlank(b.getGenre())) errors.add("El género es obligatorio.");
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
                trim(b.getGenre())
        );
        n.setId(b.getId());
        return n;
    }

    private void printValidationErrors(List<String> errors) {
        System.out.println("Errores de validación:");
        for (String e : errors) System.out.println(" - " + e);
    }

    // ========= Utils =========
    private static String trim(String s) { return s == null ? null : s.trim(); }
    private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }

    // (Impresiones de listado/búsqueda quedan pendientes hasta que el DAO exponga métodos)
}
