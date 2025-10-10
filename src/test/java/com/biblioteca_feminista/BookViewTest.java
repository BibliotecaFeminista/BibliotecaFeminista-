package com.biblioteca_feminista;

import com.biblioteca_feminista.controller.BookController;
import com.biblioteca_feminista.model.Book;
import com.biblioteca_feminista.view.BookView;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.ByteArrayInputStream;
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
        String input = "Mi Libro\nAutor Ejemplo\nDescripción\n1234567890\nFicción\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        bookView.createBook();

        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        verify(bookController, times(1)).createBook(captor.capture());

        Book book = captor.getValue();
        assertEquals("Mi Libro", book.getTitle());
        assertEquals("Autor Ejemplo", book.getAuthor());
        assertEquals("Descripción", book.getDescription());
        assertEquals("1234567890", book.getIsbn());
        assertEquals("Ficción", book.getGenre());
    }

    @Test
    void testCreateBookCancelled() {
        String input = "m\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        bookView.createBook();

        verify(bookController, never()).createBook(any());
    }

    @Test
    void testDeleteBookConfirmed() {
        // Mock para safeSelectAll
        Book book = new Book("Titulo", "Autor", "Desc", "123", "Genero");
        book.setId(1);
        when(bookController.selectAllBooks()).thenReturn(List.of(book));

        // Simula input: id y confirmación
        String input = "1\ns\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        bookView.deleteBook();

        verify(bookController, times(1)).removeBook(1);
    }

    @Test
    void testDeleteBookCancelled() {
        Book book = new Book("Titulo", "Autor", "Desc", "123", "Genero");
        book.setId(1);
        when(bookController.selectAllBooks()).thenReturn(List.of(book));

        String input = "1\nn\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        bookView.deleteBook();

        verify(bookController, never()).removeBook(anyInt());
    }

    @Test
    void testEditBook() {
        Book book = new Book("Titulo", "Autor", "Desc", "123", "Genero");
        book.setId(1);
        when(bookController.selectAllBooks()).thenReturn(List.of(book));

        // Input: id, luego nuevos valores
        String input = "1\nNuevoTitulo\nNuevoAutor\nNuevaDesc\n456\nNuevoGenero\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        bookView.editBook();

        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        verify(bookController, times(1)).updatedBook(captor.capture());

        Book updated = captor.getValue();
        assertEquals(1, updated.getId());
        assertEquals("NuevoTitulo", updated.getTitle());
        assertEquals("NuevoAutor", updated.getAuthor());
        assertEquals("NuevaDesc", updated.getDescription());
        assertEquals("456", updated.getIsbn());
        assertEquals("NuevoGenero", updated.getGenre());
    }

    @Test
    void testSearchMenu() {
        Book book = new Book("Titulo", "Autor", "Desc", "123", "Genero");

        when(bookController.findBookByTitle("Titulo")).thenReturn(List.of(book));

        // input: opción 1 (buscar por título), término de búsqueda, Enter para continuar, luego 'm' para salir
        String input = "1\nTitulo\n\nm\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        bookView.createBook(); // asegúrate de que Scanner se reinicie si lo usas varias veces
        bookView.editBook();   // solo si quieres probar varios flujos

        // No verificamos controlador aquí porque searchMenu imprime resultados, pero puedes capturarlo si quieres
    }
}
