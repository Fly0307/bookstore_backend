package com.ebook.backend.service;

import com.ebook.backend.entity.Book;
import com.ebook.backend.utils.messagegutils.Message;

import java.util.List;
import java.util.Map;


public interface BookService {

    Book getBookById(Integer id);

    List<Book> getBooks();

    Message addBook(String isbn , String name, String type , String author, String description, Integer num,
                    String image, Boolean state, Integer price);

    Message modifyBook(Map<String, String> params);

    Message deleteBook(Integer bookId);

    List<Book> searchBookByKeyword(String keyWord);
}
