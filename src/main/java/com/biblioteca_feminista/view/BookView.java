package com.biblioteca_feminista.view;

import java.util.Scanner;
import com.biblioteca_feminista.controller.BookController;
import com.biblioteca_feminista.model.Book;

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
            System.out.println("\n════════════════ MENÚ ════════════════");
            System.out.println("\t1. Añadir libro\n\t2. Ediar libro\n\t3. Eliminar libro\n"
                    + "\t4. Buscar libro\n\t5. Ver listado de libros");
            System.out.print("Elige una opción: ");

            int answer = scanner.nextInt();
            scanner.nextLine();

            switch (answer) {
                case 1:
                    System.out.println("\nHas elegido la opción 1: Añadir libro");
                    createBook();
                    break;
                case 2:
                    System.out.println("\nHas elegido la opción 2: Editar libro");
                    editBook();
                    break;
                case 3:
                    System.out.println("\nHas elegido la opción 3: Eliminar libro");
                    deleteBook();
                    break;
                case 4:
                    System.out.println("\nHas elegido la opción 4: Buscar libro");
                    System.out.println("Quieres buscar por: \n\t1.Título \n\t2. Autor \n\t3.Género");
                    int searchAnswer = scanner.nextInt();
                    scanner.nextLine();
                    searchChoice(searchAnswer);
                    break;
                case 5:
                    System.out.println("\nHas elegido la opción 5: Ver listado de libros");
                    selectAllBooks();
                    break;
                case 0:
                    if (confirm("¿Seguro que deseas salir? (s/n): ")) {
                        running = false;
                    }
                    break;
                default:
                    System.out.println("Error: Opción no válida");
            }
        }
        System.out.println("¡Hasta luego!");

    }

    public void selectAllBooks() {
        bookController.selectAllBooks();

    }

    public void createBook() {
        System.out.println("Ingrese el título del libro: ");
        String title = scanner.nextLine();
        System.out.println("Ingrese el autor del libro: ");
        String author = scanner.nextLine();
        System.out.println("Ingrese la descripción del libro: ");
        String description = scanner.nextLine();
        System.out.println("Ingrese ISBL del libro: ");
        String isbn = scanner.nextLine();
        System.out.println("Ingrese el género del libro: ");
        String genre = scanner.nextLine();

        Book book = new Book(title, author, description, isbn, genre);
        bookController.createBook(book);
    }

    public void searchChoice(int searchAnswer) {
        System.out.println("Escribe la búsqueda: ");
        String query = scanner.nextLine();

        switch (searchAnswer) {
            case 1:
                bookController.findBookByTitle(query);
                break;
            case 2:
                bookController.findBookByAuthor(query);
                break;
            case 3:
                bookController.findBookByGenre(query);
                break;
            default:
                System.out.println("Error: Opción no válida");
                break;
        }
    }

    public void editBook() {
        System.out.println("Lista actual de libros: ");
        bookController.selectAllBooks();

        System.out.println("Ingresa el Id del libro que quieres modificar: ");
        int idAnswer = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Nuevo título: ");
        String title = scanner.nextLine();
        System.out.println("Nuevo autor: ");
        String author = scanner.nextLine();
        System.out.println("Nueva descripción: ");
        String description = scanner.nextLine();
        System.out.println("Nuevo ISBN: ");
        String isbn = scanner.nextLine();
        System.out.println("Nuevo género: ");
        String genre = scanner.nextLine();

        Book updatedBook = new Book(title, author, description, isbn, genre);
        updatedBook.setId(idAnswer);
        bookController.updatedBook(updatedBook);
    }

    public void deleteBook() {
        System.out.println("Lista actual de libros: ");
        bookController.selectAllBooks();

        System.out.println("Ingressa Id del libro que quieres eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (confirm("¿Confirmas la eliminación del libro? (s/n): ")) {
            bookController.removeBook(id);
        } else {
            System.out.println("Operación cancelada.");
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
            System.out.println("Responde con 's' o 'n'.");

        }
    }
}