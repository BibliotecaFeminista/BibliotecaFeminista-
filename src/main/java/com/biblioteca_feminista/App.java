package com.biblioteca_feminista;

import com.biblioteca_feminista.model.Book;
import com.biblioteca_feminista.model.BookDaoImpl;
import com.biblioteca_feminista.model.BookDaoInterface;
import com.biblioteca_feminista.view.BookView;
import com.biblioteca_feminista.view.WelcomeLibrary;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        WelcomeLibrary.printWelcomeMessage();
        Book book = new Book(null, null, null, null, null);
        System.out.println(book.getTitle());
        BookDaoInterface model = new BookDaoImpl();
        model.createBook(book);
        BookView bookView = new BookView(bookController);
        bookView.showMenu();
    }
}
