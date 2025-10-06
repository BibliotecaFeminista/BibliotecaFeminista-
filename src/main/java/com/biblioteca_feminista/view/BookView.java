package com.biblioteca_feminista.view;

import java.util.Scanner;
import com.biblioteca_feminista.controller.BookController;

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
}
