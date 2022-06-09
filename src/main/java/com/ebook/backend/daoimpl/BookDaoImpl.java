package com.ebook.backend.daoimpl;

import com.ebook.backend.dao.BookDao;
import com.ebook.backend.entity.Book;
import com.ebook.backend.repository.BookRepository;
import com.ebook.backend.utils.messagegutils.Message;
import com.ebook.backend.utils.messagegutils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

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
    public Message addBook(String isbn, String name, String type, String author, String description, String image, Integer num, Boolean state, Integer price){
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

    @Override
    @Transactional
    //修改先改 cash 再改database
    public Message modifyBook(Map<String, String> params){
        Integer bookId = Integer.parseInt(params.get("bookId"));
//        String bookKey = "bookId:" + bookId;
        Book book=bookRepository.getBookById(bookId);
//        Book book =(Book) redisUtil.get(bookKey);
        if(book!=null){
            System.out.format("有 id 为 %d 的书",bookId);
        }
        else {
            book = bookRepository.getBookById(bookId);
            System.out.format("有 id 为 %d 的书",bookId);
        }
        book.setDescription(params.get("description"));
        book.setIsbn(params.get("isbn"));
        book.setNum(Integer.parseInt(params.get("num")));
        book.setType(params.get("type"));
        book.setName(params.get("name"));
        book.setImage(params.get("image"));
        book.setPrice(Integer.valueOf(params.get("price")));
        book.setAuthor(params.get("author"));
        book.setState(params.get("state").equals("1"));
//        redisUtil.set(bookKey,book,300);
        bookRepository.save(book);
        return MessageUtil.makeMsg(4,"修改成功");
    }



}
