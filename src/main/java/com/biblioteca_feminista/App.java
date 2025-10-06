package com.biblioteca_feminista;

import com.biblioteca_feminista.controller.BookController;
import com.biblioteca_feminista.model.BookDaoImpl;
import com.biblioteca_feminista.model.BookDaoInterface;
import com.biblioteca_feminista.view.BookView;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        Book book = new Book(null, null, null, null, null);
        System.out.println(book.getTitle());
        
        BookDaoInterface dao = new BookDaoImpl();
        BookController controller = new BookController(dao);
        BookView bookView = new BookView(bookController);
        bookView.showMenu();
    }
}
