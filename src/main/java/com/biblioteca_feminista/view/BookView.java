package com.biblioteca_feminista.view;

import java.util.Scanner;
import com.biblioteca_feminista.controller.BookController;
import com.biblioteca_feminista.model.Book;

import static com.biblioteca_feminista.view.WelcomeLibrary.*;

public class BookView {
    private BookController bookController;
    private Scanner scanner;

    public BookView(BookController bookController) {
        this.bookController = bookController;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n" + ANSI_YELLOW + "════════════════ MENÚ ════════════════" + ANSI_RESET);
            System.out.println(ANSI_CYAN +
                    "\t1. Añadir libro\n\t2. Editar libro\n\t3. Eliminar libro\n" +
                    "\t4. Buscar libro\n\t5. Ver listado de libros" + ANSI_RESET);
            System.out.print(ANSI_MAGENTA + "Elige una opción: " + ANSI_RESET);

            int answer = scanner.nextInt();
            scanner.nextLine();

            switch (answer) {
                case 1 -> {
                    System.out.println(ANSI_GREEN + "\nHas elegido la opción 1: Añadir libro" + ANSI_RESET);
                    createBook();
                }
                case 2 -> {
                    System.out.println(ANSI_GREEN + "\nHas elegido la opción 2: Editar libro" + ANSI_RESET);
                    editBook();
                }
                case 3 -> {
                    System.out.println(ANSI_GREEN + "\nHas elegido la opción 3: Eliminar libro" + ANSI_RESET);
                    deleteBook();
                }
                case 4 -> {
                    System.out.println(ANSI_GREEN + "\nHas elegido la opción 4: Buscar libro" + ANSI_RESET);
                    System.out.println(
                            ANSI_CYAN + "¿Quieres buscar por: \n\t1. Título \n\t2. Autor \n\t3. Género" + ANSI_RESET);
                    int searchAnswer = scanner.nextInt();
                    scanner.nextLine();
                    searchChoice(searchAnswer);
                }
                case 5 -> {
                    System.out.println(ANSI_GREEN + "\nHas elegido la opción 5: Ver listado de libros" + ANSI_RESET);
                    selectAllBooks();
                }
                case 0 -> {
                    if (confirm(ANSI_YELLOW + "¿Seguro que deseas salir? (s/n): " + ANSI_RESET)) {
                        running = false;
                    }
                }
                default -> System.out.println(ANSI_RED + "Error: Opción no válida" + ANSI_RESET);
            }
        }
        System.out.println(ANSI_MAGENTA + "¡Hasta luego!" + ANSI_RESET);
    }

    public void selectAllBooks() {
        bookController.selectAllBooks();
    }

    public void createBook() {
        System.out.print(ANSI_MAGENTA + "Ingresa el título del libro: " + ANSI_RESET);
        String title = scanner.nextLine();
        System.out.print(ANSI_MAGENTA + "Ingresa el autor del libro: " + ANSI_RESET);
        String author = scanner.nextLine();
        System.out.print(ANSI_MAGENTA + "Ingresa la descripción del libro: " + ANSI_RESET);
        String description = scanner.nextLine();
        System.out.print(ANSI_MAGENTA + "Ingresa el ISBN del libro: " + ANSI_RESET);
        String isbn = scanner.nextLine();
        System.out.print(ANSI_MAGENTA + "Ingresa el género del libro: " + ANSI_RESET);
        String genre = scanner.nextLine();

        Book book = new Book(title, author, description, isbn, genre);
        bookController.createBook(book);
        System.out.println(ANSI_GREEN + "✔ Libro añadido con éxito." + ANSI_RESET);
    }

    public void searchChoice(int searchAnswer) {
        System.out.print(ANSI_MAGENTA + "Escribe la búsqueda: " + ANSI_RESET);
        String query = scanner.nextLine();

        switch (searchAnswer) {
            case 1 -> bookController.findBookByTitle(query);
            case 2 -> bookController.findBookByAuthor(query);
            case 3 -> bookController.findBookByGenre(query);
            default -> System.out.println(ANSI_RED + "Error: Opción no válida" + ANSI_RESET);
        }
    }

    public void editBook() {
        System.out.println(ANSI_YELLOW + "Lista actual de libros: " + ANSI_RESET);
        bookController.selectAllBooks();

        System.out.print(ANSI_MAGENTA + "Ingresa el ID del libro que quieres modificar: " + ANSI_RESET);
        int idAnswer = scanner.nextInt();
        scanner.nextLine();

        System.out.print(ANSI_MAGENTA + "Nuevo título: " + ANSI_RESET);
        String title = scanner.nextLine();
        System.out.print(ANSI_MAGENTA + "Nuevo autor: " + ANSI_RESET);
        String author = scanner.nextLine();
        System.out.print(ANSI_MAGENTA + "Nueva descripción: " + ANSI_RESET);
        String description = scanner.nextLine();
        System.out.print(ANSI_MAGENTA + "Nuevo ISBN: " + ANSI_RESET);
        String isbn = scanner.nextLine();
        System.out.print(ANSI_MAGENTA + "Nuevo género: " + ANSI_RESET);
        String genre = scanner.nextLine();

        Book updatedBook = new Book(title, author, description, isbn, genre);
        updatedBook.setId(idAnswer);
        bookController.updatedBook(updatedBook);

        System.out.println(ANSI_GREEN + "✔ Libro actualizado." + ANSI_RESET);
    }

    public void deleteBook() {
        System.out.println(ANSI_YELLOW + "Lista actual de libros: " + ANSI_RESET);
        bookController.selectAllBooks();

        System.out.print(ANSI_MAGENTA + "Ingresa el ID del libro que quieres eliminar: " + ANSI_RESET);
        int id = scanner.nextInt();
        scanner.nextLine();

        if (confirm(ANSI_YELLOW + "¿Confirmas la eliminación del libro? (s/n): " + ANSI_RESET)) {
            bookController.removeBook(id);
            System.out.println(ANSI_GREEN + "✔ Libro eliminado." + ANSI_RESET);
        } else {
            System.out.println(ANSI_CYAN + "Operación cancelada." + ANSI_RESET);
        }
    }

    private boolean confirm(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            if (line == null)
                continue;
            String v = line.trim().toLowerCase();
            if (v.equals("s") || v.equals("si") || v.equals("sí"))
                return true;
            if (v.equals("n") || v.equals("no"))
                return false;
            System.out.println(ANSI_YELLOW + "Responde con 's' o 'n'." + ANSI_RESET);
        }
    }
}
