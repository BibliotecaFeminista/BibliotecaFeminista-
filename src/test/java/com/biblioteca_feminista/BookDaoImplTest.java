package com.biblioteca_feminista;

import com.biblioteca_feminista.controller.BookController;
import com.biblioteca_feminista.model.Book;
import com.biblioteca_feminista.model.BookDaoInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BookControllerTest {

    private BookDaoInterface bookDao;
    private BookController controller;
    private Book book;

    @BeforeEach
    void setUp() {
        bookDao = mock(BookDaoInterface.class);
        controller = new BookController(bookDao);
        book = new Book("Título", "Autora", "Descripción corta", "123456", "Ficción");
    }

    @Test
    void testCreateBook_Success() {
        assertDoesNotThrow(() -> controller.createBook(book));
        verify(bookDao).createBook(any(Book.class));
    }

    @Test
    void testCreateBook_InvalidData() {
        Book invalid = new Book("", "", "", "", "");
        Exception ex = assertThrows(IllegalArgumentException.class, () -> controller.createBook(invalid));
        assertTrue(ex.getMessage().contains("Errores de validación"));
        verify(bookDao, never()).createBook(any());
    }

    @Test
    void testUpdateBook_Success() {
        assertDoesNotThrow(() -> controller.updateBook(book));
        verify(bookDao).updateBook(any(Book.class));
    }

    @Test
    void testUpdateBook_InvalidData() {
        Book invalid = new Book("", "", "", "", "");
        assertThrows(IllegalArgumentException.class, () -> controller.updateBook(invalid));
        verify(bookDao, never()).updateBook(any());
    }

    @Test
    void testRemoveBook_CallsDaoDelete() {
        controller.removeBook(5);
        verify(bookDao).deleteBook(5);
    }

    @Test
    void testSelectAllBooks_CallsFindAll() {
        controller.selectAllBooks();
        verify(bookDao).findAll();
    }

    @Test
    void testFindBookByTitle_CallsDaoWhenNotBlank() {
        controller.findBookByTitle("algo");
        verify(bookDao).findByTitle("algo");
    }

    @Test
    void testFindBookByTitle_DoesNotCallDaoWhenBlank() {
        assertThrows(IllegalArgumentException.class, () -> controller.findBookByTitle("  "));
        verify(bookDao, never()).findByTitle(anyString());
    }

    @Test
    void testFindBookByAuthor_CallsDaoWhenNotBlank() {
        controller.findBookByAuthor("nombre");
        verify(bookDao).findByAuthor("nombre");
    }

    @Test
    void testFindBookByGenre_CallsDaoWhenNotBlank() {
        controller.findBookByGenre("ficción");
        verify(bookDao).findByGenre("ficción");
    }
}
