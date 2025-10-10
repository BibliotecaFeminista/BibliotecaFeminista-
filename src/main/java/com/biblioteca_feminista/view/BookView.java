package com.biblioteca_feminista.view;

import java.util.List;
import java.util.Scanner;

import com.biblioteca_feminista.controller.BookController;
import com.biblioteca_feminista.model.Book;

import static com.biblioteca_feminista.view.WelcomeLibrary.*;

public class BookView {
    private final BookController bookController;
    private final Scanner scanner;

    public BookView(BookController bookController) {
        this.bookController = bookController;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n" + ANSI_YELLOW + "════════════ MENÚ DE LIBROS ════════════" + ANSI_RESET);
            System.out.println(ANSI_CYAN +
                    "\t1. Añadir libro\n\t2. Editar libro\n\t3. Eliminar libro\n" +
                    "\t4. Buscar libro\n\t5. Ver listado de libros" + ANSI_RESET);

            System.out.println();
            System.out.print(ANSI_MAGENTA + "Elige una opción: " + ANSI_RESET);

            Integer answer = readInt();
            switch (answer) {
                case 1 -> createBook();
                case 2 -> editBook();
                case 3 -> deleteBook();
                case 4 -> searchMenu();
                case 5 -> renderBooks(safeSelectAll());
                default -> System.out.println(ANSI_RED + "Opción no válida." + ANSI_RESET);
            }
        }
    }

    private Integer readInt() {
        while (!scanner.hasNextInt()) {
            System.out.println(ANSI_YELLOW + "Introduce un número válido." + ANSI_RESET);
            scanner.nextLine();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    private Integer readIntOrMenu(String label) {
        while (true) {
            if (label != null)
                System.out.print(ANSI_MAGENTA + label + " " + ANSI_RESET);
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } else {
                String token = scanner.nextLine().trim();
                if (token.equalsIgnoreCase("m")) {
                    return null;
                }
                System.out.println(ANSI_YELLOW + "Introduce un número válido." + ANSI_RESET);
            }
        }
    }

    private String readLineOrMenu(String label) {
        System.out.print(ANSI_MAGENTA + label + " " + ANSI_RESET);
        String line = scanner.nextLine();
        if (line != null && line.trim().equalsIgnoreCase("m")) {
            return null;
        }
        return line;
    }

    public List<Book> safeSelectAll() {
        try {
            return bookController.selectAllBooks();
        } catch (RuntimeException ex) {
            System.out.println(ANSI_RED + ex.getMessage() + ANSI_RESET);
            return List.of();
        }
    }

    public void renderBooks(List<Book> books) {
        if (books == null || books.isEmpty()) {
            System.out.println(ANSI_YELLOW + "No hay libros para mostrar." + ANSI_RESET);
            return;
        }
        System.out.println("\n" + ANSI_CYAN + "Listado de libros:" + ANSI_RESET);
        for (Book b : books) {
            System.out.printf(
                    "ID: %d | Título: %s | Autor: %s | ISBN: %s | Género: %s%n",
                    b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn(), b.getGenre());
        }
    }

    public void createBook() {
        String title = readLineOrMenu("Título:");
        if (title == null)
            return;
        String author = readLineOrMenu("Autor:");
        if (author == null)
            return;
        String description = readLineOrMenu("Descripción:");
        if (description == null)
            return;
        String isbn = readLineOrMenu("ISBN:");
        if (isbn == null)
            return;
        String genre = readLineOrMenu("Género:");
        if (genre == null)
            return;

        Book book = new Book(title, author, description, isbn, genre);
        try {
            bookController.createBook(book);
            System.out.println(ANSI_GREEN + "✔ Libro añadido con éxito." + ANSI_RESET);
        } catch (IllegalArgumentException ex) {
            printMultilineError(ex.getMessage());
        } catch (RuntimeException ex) {
            System.out.println(ANSI_RED + ex.getMessage() + ANSI_RESET);
        }
    }

    private void searchMenu() {
        while (true) {
            System.out.println(ANSI_CYAN + "Buscar por:\n\t1. Título\n\t2. Autor\n\t3. Género" + ANSI_RESET);
            Integer searchAnswer = readIntOrMenu("Elige una opción de búsqueda:");
            if (searchAnswer == null)
                return;

            String query = readLineOrMenu("Escribe el término de búsqueda:");
            if (query == null)
                return;

            try {
                List<Book> books = switch (searchAnswer) {
                    case 1 -> bookController.findBookByTitle(query);
                    case 2 -> bookController.findBookByAuthor(query);
                    case 3 -> bookController.findBookByGenre(query);
                    default -> {
                        System.out.println(ANSI_RED + "Opción no válida." + ANSI_RESET);
                        yield List.of();
                    }
                };
                if (books.isEmpty()) {
                    System.out.println(ANSI_YELLOW + "No se encontraron resultados." + ANSI_RESET);
                } else {
                    System.out.println("\n" + ANSI_CYAN + "Resultados:" + ANSI_RESET);
                    for (Book b : books) {
                        System.out.printf(
                                "ID: %d | Título: %s | Autor: %s | ISBN: %s | Género: %s%n",
                                b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn(), b.getGenre());
                    }
                }
                String again = readLineOrMenu("¿Otra búsqueda? Presiona Enter para continuar:");
                if (again == null)
                    return;
            } catch (IllegalArgumentException ex) {
                printMultilineError(ex.getMessage());
            } catch (RuntimeException ex) {
                System.out.println(ANSI_RED + ex.getMessage() + ANSI_RESET);
            }
        }
    }

    public void editBook() {
        System.out.println(ANSI_YELLOW + "Lista actual de libros:" + ANSI_RESET);
        List<Book> all = safeSelectAll();
        renderBooks(all);

        Integer idAnswer = readIntOrMenu("ID del libro a modificar:");
        if (idAnswer == null)
            return;

        Book current = findById(all, idAnswer);
        if (current == null) {
            System.out.println(ANSI_RED + "No se encontró un libro con ese ID." + ANSI_RESET);
            return;
        }

        String title = readOptional("Nuevo título:", current.getTitle());
        if (title == null)
            return;
        String author = readOptional("Nuevo autor:", current.getAuthor());
        if (author == null)
            return;
        String description = readOptional("Nueva descripción:", current.getDescription());
        if (description == null)
            return;
        String isbn = readOptional("Nuevo ISBN:", current.getIsbn());
        if (isbn == null)
            return;
        String genre = readOptional("Nuevo género:", current.getGenre());
        if (genre == null)
            return;

        Book updatedBook = new Book(title, author, description, isbn, genre);
        updatedBook.setId(idAnswer);

        try {
            bookController.updatedBook(updatedBook);
            System.out.println(ANSI_GREEN + "Libro actualizado con éxito." + ANSI_RESET);
        } catch (IllegalArgumentException ex) {
            printMultilineError(ex.getMessage());
        } catch (RuntimeException ex) {
            System.out.println(ANSI_RED + ex.getMessage() + ANSI_RESET);
        }
    }

    public void deleteBook() {
        System.out.println(ANSI_YELLOW + "Lista actual de libros:" + ANSI_RESET);
        renderBooks(safeSelectAll());

        Integer id = readIntOrMenu("ID del libro a eliminar:");
        if (id == null)
            return;

        String confirm = readLineOrMenu("Confirmar eliminación (s/n):");
        if (confirm == null)
            return;
        confirm = confirm.trim().toLowerCase();

        if (confirm.equals("s") || confirm.equals("si") || confirm.equals("sí")) {
            try {
                bookController.removeBook(id);
                System.out.println(ANSI_GREEN + "Libro eliminado con éxito." + ANSI_RESET);
            } catch (RuntimeException ex) {
                System.out.println(ANSI_RED + ex.getMessage() + ANSI_RESET);
            }
        } else {
            System.out.println(ANSI_CYAN + "Operación cancelada." + ANSI_RESET);
        }
    }

    private String readOptional(String label, String currentValue) {
        System.out.print(ANSI_MAGENTA + label + " " + ANSI_RESET);
        String input = scanner.nextLine();
        if (input != null && input.trim().equalsIgnoreCase("m"))
            return null;
        if (input == null || input.trim().isEmpty())
            return currentValue;
        return input.trim();
    }

    private Book findById(List<Book> books, int id) {
        if (books == null)
            return null;
        for (Book b : books) {
            if (b.getId() == id)
                return b;
        }
        return null;
    }

    private void printMultilineError(String msg) {
        System.out.println(ANSI_RED + msg + ANSI_RESET);
    }
}
