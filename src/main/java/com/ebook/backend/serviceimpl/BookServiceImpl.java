package com.ebook.backend.serviceimpl;

import com.ebook.backend.dao.BookDao;
import com.ebook.backend.entity.Book;
import com.ebook.backend.service.BookService;
import com.ebook.backend.utils.messagegutils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Override
    public Book getBookById(Integer id){
        return bookDao.getBookById(id);
    }

    @Override
    public List<Book> getBooks() {
        return bookDao.getBooks();
    }

    @Override
    public Message addBook(String isbn, String name, String type, String author, String description, Integer num, String image, Boolean state, Integer price) {
        return bookDao.addBook(isbn, name, type, author, description, image, num, state, price);

    }

    @Override
    public Message modifyBook(Map<String, String> params) {
        return bookDao.modifyBook(params);
    }

}
