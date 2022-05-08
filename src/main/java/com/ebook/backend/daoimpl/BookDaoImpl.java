package com.ebook.backend.daoimpl;

import com.ebook.backend.dao.BookDao;
import com.ebook.backend.entity.Book;
import com.ebook.backend.repository.BookRepository;
import com.ebook.backend.utils.messagegutils.Message;
import com.ebook.backend.utils.messagegutils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book getBookById(Integer id){
        return bookRepository.getOne(id);
    }

    @Override
    public Message changeSale(Integer bookId, Integer purchaseNum) {

        Book book = bookRepository.getBookById(bookId);
        if(book==null){
            System.out.format("没有 id 为 %d 的书",bookId);
            return MessageUtil.makeMsg(-1, "没有此书");
        }else {

            Integer num = book.getNum();
            Integer newNum = num - purchaseNum;
            if (newNum >= 0) {
                book.setNum(num - purchaseNum);
                bookRepository.save(book);
                return MessageUtil.makeMsg(5, "记录成功");
            }
            return MessageUtil.makeMsg(-5, "数量不够");
        }
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.getBooks();
    }

    @Override
    public Message addBook(String isbn, String name, String type, String author, String description, String image, Integer num, Boolean state, BigDecimal price){
        Book book = new Book();
        book.setAuthor(author);
        book.setImage(image);
        book.setName(name);
        book.setPrice(price);
        book.setType(type);
        book.setNum(num);
        book.setIsbn(isbn);
        book.setState(state);
        book.setDescription(description);
        bookRepository.save(book);
        return MessageUtil.makeMsg(200, "加入成功");
    }




}
