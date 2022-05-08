package com.ebook.backend.service;

import com.ebook.backend.entity.Book;

import java.util.List;


public interface BookService {

    Book findBookById(Integer id);

    List<Book> getBooks();
}
