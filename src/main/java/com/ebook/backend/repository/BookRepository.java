package com.ebook.backend.repository;

import com.ebook.backend.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface BookRepository extends JpaRepository<Book,Integer> {


    @Query("select b from Book b")
    List<Book> getBooks();

    @Query("from Book where bookId=?1")
    Book getBookById(Integer bookId);

    @Query("from Book  where isbn=?1")
    Book getBookByIsbn(String isbn);

}
