package itmo.deniill.service.services.interfaces;

import itmo.deniill.dao.model.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book getBookById(Integer id);

    Book createBook(Book book);

    Book updateBook(Integer id, Book bookDetails);

    void deleteBook(Integer id);
}
