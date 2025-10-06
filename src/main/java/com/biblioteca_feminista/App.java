package com.biblioteca_feminista;

import com.biblioteca_feminista.model.Book;
import com.biblioteca_feminista.view.BookView;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        Book book = new Book(null, null, null, null, null);
        System.out.println(book.getTitle());

        BookView bookView = new BookView(bookController);
        bookView.showMenu();
    }
}
