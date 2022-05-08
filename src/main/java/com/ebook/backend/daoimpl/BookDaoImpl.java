package com.ebook.backend.daoimpl;

import com.ebook.backend.dao.BookDao;
import com.ebook.backend.entity.Book;
import com.ebook.backend.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName BookDaoImpl
 * @Description TODO
 * @Author thunderBoy
 * @Date 2019/11/5 20:20
 */
@Repository
public class BookDaoImpl implements BookDao {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book findOne(Integer id){
        return bookRepository.getOne(id);
    }


    @Override
    public List<Book> getBooks() {
        return bookRepository.getBooks();
    }




}
