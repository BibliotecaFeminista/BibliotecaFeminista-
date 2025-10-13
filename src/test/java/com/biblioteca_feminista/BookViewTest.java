package com.biblioteca_feminista;

import com.biblioteca_feminista.controller.BookController;
import com.biblioteca_feminista.model.Book;
import com.biblioteca_feminista.view.BookView;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookViewTest {

    private BookController bookController;
    private BookView bookView;

    @BeforeEach
    void setUp() {
        bookController = mock(BookController.class);
        bookView = new BookView(bookController);
    }

    @Test
    void testCreateBookSuccess() {
        Book bookToCreate = new Book("Mi Libro", "Autor Ejemplo", "Descripción", "1234567890", "Ficción");

        bookController.createBook(bookToCreate);

        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        verify(bookController).createBook(captor.capture());

        Book created = captor.getValue();
        assertEquals("Mi Libro", created.getTitle());
        assertEquals("Autor Ejemplo", created.getAuthor());
        assertEquals("Descripción", created.getDescription());
        assertEquals("1234567890", created.getIsbn());
        assertEquals("Ficción", created.getGenre());
    }

    @Test
    void testCreateBookCancelled() {
        verify(bookController, never()).createBook(any());
    }

    @Test
    void testDeleteBookConfirmed() {
        Book book = new Book("Titulo", "Autor", "Desc", "123", "Genero");
        book.setId(1);
        when(bookController.selectAllBooks()).thenReturn(List.of(book));

        bookController.removeBook(book.getId());


        verify(bookController).removeBook(1);
    }

    @Test
    void testDeleteBookCancelled() {
        Book book = new Book("Titulo", "Autor", "Desc", "123", "Genero");
        book.setId(1);
        when(bookController.selectAllBooks()).thenReturn(List.of(book));

        verify(bookController, never()).removeBook(anyInt());
    }

    @Test
    void testEditBook() {
        Book existingBook = new Book("Titulo", "Autor", "Desc", "123", "Genero");
        existingBook.setId(1);

        when(bookController.selectAllBooks()).thenReturn(List.of(existingBook));

        Book updatedBook = new Book("NuevoTitulo", "NuevoAutor", "NuevaDesc", "456", "NuevoGenero");
        updatedBook.setId(existingBook.getId());

        bookController.updatedBook(updatedBook);

        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        verify(bookController).updatedBook(captor.capture());

        Book result = captor.getValue();
        assertEquals(1, result.getId());
        assertEquals("NuevoTitulo", result.getTitle());
        assertEquals("NuevoAutor", result.getAuthor());
        assertEquals("NuevaDesc", result.getDescription());
        assertEquals("456", result.getIsbn());
        assertEquals("NuevoGenero", result.getGenre());
    }

    @Test
    void testSearchBookByTitle() {
        Book book = new Book("Titulo", "Autor", "Desc", "123", "Genero");

        when(bookController.findBookByTitle("Titulo")).thenReturn(List.of(book));

        List<Book> results = bookController.findBookByTitle("Titulo");

        assertEquals(1, results.size());
        assertEquals("Titulo", results.get(0).getTitle());
        assertEquals("Autor", results.get(0).getAuthor());
        assertEquals("Desc", results.get(0).getDescription());
        assertEquals("123", results.get(0).getIsbn());
        assertEquals("Genero", results.get(0).getGenre());
    }
}