package com.biblioteca_feminista;

import com.biblioteca_feminista.model.Book;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Book book = new Book(null, null, null, null, null);
        System.out.println(book.getTitle());
    }
}
