package com.biblioteca_feminista;

import com.biblioteca_feminista.controller.BookController;
import com.biblioteca_feminista.model.BookDaoImpl;
import com.biblioteca_feminista.model.BookDaoInterface;
import com.biblioteca_feminista.view.BookView;
import com.biblioteca_feminista.view.WelcomeLibrary;

public class App {
    public static void main(String[] args) {
        WelcomeLibrary.printWelcomeMessage();

        BookDaoInterface dao = new BookDaoImpl();
        BookController bookController = new BookController(dao);

        BookView bookView = new BookView(bookController);
        bookView.showMenu();
    }
}
