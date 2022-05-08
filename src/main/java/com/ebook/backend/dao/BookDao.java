package com.ebook.backend.dao;

import com.ebook.backend.entity.Book;

import java.util.List;

public interface BookDao {
    Book findOne(Integer id);

    List<Book> getBooks();
}
