package com.biblioteca_feminista;

import com.biblioteca_feminista.model.Book;
import com.biblioteca_feminista.model.BookDaoImpl;
import com.biblioteca_feminista.model.BookDaoInterface;


/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        Book book = new Book(null, null, null, null, null);
        System.out.println(book.getTitle());
        BookDaoInterface model = new BookDaoImpl();
        model.createBook(book);
        /** BookView bookView = new BookView(bookController);
        bookView.showMenu(); **/
    }
}
