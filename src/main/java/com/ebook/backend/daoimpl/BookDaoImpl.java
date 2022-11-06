package com.ebook.backend.daoimpl;

import com.ebook.backend.dao.BookDao;
import com.ebook.backend.entity.Book;
import com.ebook.backend.repository.BookRepository;
import com.ebook.backend.utils.messagegutils.Message;
import com.ebook.backend.utils.messagegutils.MessageUtil;
import com.ebook.backend.utils.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoImpl implements BookDao {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public Book getBookById(Integer id){
        String bookKey = "bookId:" + id;
        Book book =(Book) redisUtil.get(bookKey);
        if(book!=null){
            System.out.format("redis 中有 id 为 %d 的书\n",id);
        }
        else {
            book = bookRepository.getBookById(id);
            System.out.format("redis 中没有 id 为 %d 的书\n",id);
            //新增一本书
            redisUtil.set(bookKey,book,300);
        }
        return book;
    }

    @Override
    public Message changeSale(Integer bookId, Integer purchaseNum) {
        String bookKey = "bookId:" + bookId;
        Book book =(Book) redisUtil.get(bookKey);
//        Book book = bookRepository.getBookById(bookId);
        if(book!=null){
            System.out.format("redis有 id 为 %d 的书\n",bookId);
//            return MessageUtil.makeMsg(-1, "没有此书");
        }else {
            book = bookRepository.getBookById(bookId);
            System.out.format("redis 中没有 id 为 %d 的书\n", bookId);
        }
            Integer num = book.getNum();
            Integer newNum = num - purchaseNum;
            if (newNum >= 0) {
                book.setNum(num - purchaseNum);
                //redis更新
                redisUtil.set(bookKey,book,300);
                bookRepository.save(book);
                return MessageUtil.makeMsg(5, "记录成功");
            }
//        redisUtil.set(bookKey,book,300);
        return MessageUtil.makeMsg(-5, "数量不够");
    }

    @Override
    public List<Book> getBooks() {
//        System.out.println("从Redis读取books");
//        Object p = redisUtil.get("");
        return bookRepository.getBooks();
    }
    @Transactional
    @Override
    public Message addBook(String isbn, String name, String type, String author, String description, String image, Integer num, Boolean state, Integer price){
        System.out.format("新增isbn 为 %s 的书\n",isbn);
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
        Integer bookId=bookRepository.getBookByIsbn(isbn).getBookId();
        String bookKey="bookid"+bookId;
        System.out.format("redis 中新增 id 为 %d 的书\n",bookId);
        redisUtil.set(bookKey,book);
        return MessageUtil.makeMsg(200, "加入成功");
    }

    @Override
    @Transactional
    //修改先改 cash 再改database
    public Message modifyBook(Map<String, String> params){
        Integer bookId = Integer.parseInt(params.get("bookId"));
        String bookKey = "bookId:" + bookId;
//        Book book=bookRepository.getBookById(bookId);
        Book book =(Book) redisUtil.get(bookKey);
        if(book!=null){
            System.out.format("redis 中有 id 为 %d 的书\n",bookId);
        }
        else {
            book = bookRepository.getBookById(bookId);
            System.out.format("redis 中没有 id 为 %d 的书\n",bookId);
        }
        book.setDescription(params.get("description"));
        book.setIsbn(params.get("isbn"));
        book.setNum(Integer.parseInt(params.get("num")));
        book.setType(params.get("type"));
        book.setName(params.get("name"));
        book.setImage(params.get("image"));
        book.setPrice(Integer.valueOf(params.get("price")));
        book.setAuthor(params.get("author"));
        book.setState(Boolean.valueOf(params.get("state")));
        redisUtil.set(bookKey,book,300);
        System.out.format("修改redis中bookid=%d的书\n",bookId);
        bookRepository.save(book);
        System.out.format("修改数据库中bookid=%d的书\n",bookId);
        return MessageUtil.makeMsg(4,"修改成功");
    }

    @Override
    public List<Book> manageBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Message deleteBook(Integer bookId) {
        String bookKey = "bookId:" + bookId;
        Book book =(Book) redisUtil.get(bookKey);
//        Book book=bookRepository.getBookById(bookId);
        if (book!=null){
            System.out.format("删除redis 中 id 为 %d 的书\n",bookId);
            redisUtil.del(bookKey);


        }else {
            book = bookRepository.getBookById(bookId);

        }
        bookRepository.delete(book);
        System.out.format("删除数据库中bookid=%d的书\n",bookId);
        return MessageUtil.makeMsg(4,"删除成功");

    }


}
