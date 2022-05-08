package com.ebook.backend.serviceimpl;

import com.ebook.backend.dao.BookDao;
import com.ebook.backend.entity.Book;
import com.ebook.backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Override
    public Book findBookById(Integer id){
        return bookDao.findOne(id);
    }

    @Override
    public List<Book> getBooks() {
        return bookDao.getBooks();
    }
}
