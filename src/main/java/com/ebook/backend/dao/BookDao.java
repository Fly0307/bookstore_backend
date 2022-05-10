package com.ebook.backend.dao;

import com.ebook.backend.entity.Book;
import com.ebook.backend.utils.messagegutils.Message;

import java.util.List;

public interface BookDao {
   // Book findOne(Integer id);

    List<Book> getBooks();

    Message addBook(String isbn, String name, String type, String author, String description,
                    String image, Integer num, Boolean state, Integer price);
    Book getBookById(Integer bookId);

    Message changeSale(Integer bookId, Integer purchaseNum);
}
