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
            case 4:
                System.out.println("\nHas elegido la opción 3: Buscar libro");
                System.out.println("Quieres buscar por: \n\t1.Título \n\t2. Autor \n\t3.Género");
                int searchAnswer = scanner.nextInt();
                scanner.nextLine();
                searchChoice(searchAnswer);
                break;
            default:
                System.out.println("Error: Opción no válida");
            case 5:
                System.out.println("\nHas elegido la opción 5: Ver listado de libros");
                selectAllBooks();
                break;
            default:
                System.out.println("Error: Opción no válida");
        }

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
}
