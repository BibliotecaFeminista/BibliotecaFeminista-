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

    public void selectAllBooks() {
        try {
            List<Book> books = nvl(bookDao.findAll());
            if (books.isEmpty()) {
                System.out.println("No hay libros en el catálogo");
                return;
            }
            printBooksNoDescription(books);
        } catch (Exception e) {
            System.out.println("Error al listar libros: " + e.getMessage());
        }
    }

    // validaciones básicas
    public void createBook(Book book) {
        List<String> errors = validateForCreate(book);
        if (!errors.isEmpty()) {
            printValidationErrors(errors);
            System.out.println("El libro NO se creó.");
            return;
        }
        try {
            Book created = bookDao.create(normalize(book));
            System.out.println("Libro creado correctamente: ");
            printOneFull(created);
        } catch (Exception e) {
            System.out.println("Error al crear el libro: " + e.getMessage());
        }
    }

    public void findBookByTitle(String term) {
        if (isBlank(term)) {
            System.out.println("El título de búsqueda no puede estar vacío.");
            return;
        }
        try {
            List<Book> books = nvl(bookDao.findByTitle(term));
            if (books.isEmpty()) {
                System.out.println("Sin resultados para el título: " + term);
                return;
            }
            // Si est'a bien mostrar todos los campos: printBooksFull(books);
            printBooksFull(books);
        } catch (Exception e) {
            System.out.println("Error al buscar por título: " + e.getMessage());
        }
    }

    public void findBookByAuthor(String term) {
        if (isBlank(term)) {
            System.out.println("El nombre de la autora no puede estar vacío.");
            return;
        }
        try {
            List<Book> books = nvl(bookDao.findByAuthor(term));
            if (books.isEmpty()) {
                System.out.println("Sin resultados para la autora: " + term);
                return;
            }
            // Si está bien  mostrar todos los campos,  printBooksFull(books);
            printBooksFull(books);
        } catch (Exception e) {
            System.out.println("Error al buscar por autora: " + e.getMessage());
        }
    }

    public void findBookByGenre(String term) {
        if (isBlank(term)) {
            System.out.println("El género no puede estar vacío.");
            return;
        }
        try {
            List<Book> books = nvl(bookDao.findByGenre(term));
            if (books.isEmpty()) {
                System.out.println("Sin resultados para el género: " + term);
                return;
            }
            printBooksNoDescription(books);
        } catch (Exception e) {
            System.out.println("Error al buscar por género: " + e.getMessage());
        }
    }

    // para validar y normalizar
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

    // impresión provisional (hasta que la View lo haga)
    private void printBooksNoDescription(List<Book> books) {
        System.out.println("\nID | TÍTULO | AUTORA | ISBN | GÉNERO");
        for (Book b : books) {
            System.out.printf("%d | %s | %s | %s | %s%n",
                    b.getId(), safe(b.getTitle()), safe(b.getAuthor()), safe(b.getIsbn()), safe(b.getGenre()));
        }
    }

    private void printBooksFull(List<Book> books) {
        System.out.println("\nID | TÍTULO | AUTORA | DESCRIPCIÓN | ISBN | GÉNERO");
        for (Book b : books) {
            System.out.printf("%d | %s | %s | %s | %s | %s%n",
                    b.getId(), safe(b.getTitle()), safe(b.getAuthor()), safe(b.getDescription()), safe(b.getIsbn()), safe(b.getGenre()));
        }
    }

    private void printOneFull(Book b) {
        System.out.printf("ID=%d, Título=%s, Autora=%s, Descripción=%s, ISBN=%s, Género=%s%n",
                b.getId(), safe(b.getTitle()), safe(b.getAuthor()), safe(b.getDescription()), safe(b.getIsbn()), safe(b.getGenre()));
    }

    private void printValidationErrors(List<String> errors) {
        System.out.println("Errores de validación:");
        for (String e : errors) System.out.println(" - " + e);
    }

    // utilidades básicas
    private static String trim(String s) { return s == null ? null : s.trim(); }
    private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
    private static <T> List<T> nvl(List<T> list) { return list == null ? new ArrayList<>() : list; }
    private static String safe(String s) { return s == null ? "" : s; }
}
