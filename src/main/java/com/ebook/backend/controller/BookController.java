package com.ebook.backend.controller;

import com.ebook.backend.entity.Book;
import com.ebook.backend.service.BookService;
import com.ebook.backend.utils.messagegutils.Message;
import com.ebook.backend.utils.messagegutils.MessageUtil;
import com.ebook.backend.utils.sessionutils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @RequestMapping("/getBooks")
    public List<Book> getBooks(@RequestBody Map<String, String> params) {
        return bookService.getBooks();
    }

    @RequestMapping("/getBook")
    public Book getBook(@RequestParam("id") Integer id){
        return bookService.getBookById(id);
    }

    @RequestMapping(value = "/addBook")
    Message addBook(@RequestBody Map<String, String> params){
        String bookName = params.get("name");
        String isbn = params.get("isbn");
        String  type = params.get("type");
        String author =params.get("author");
        Integer price = Integer.valueOf(params.get("price"));
        Integer num= Integer.valueOf(params.get("num"));
        String description =params.get("description");
        String tmp = params.get("state");
        Boolean state;
        if(tmp.equals("true")) state =true;
        else state =false;
        String image =params.get("image");
        return bookService.addBook(isbn,bookName,type,author,description,num,image,state, price);
    }
    @RequestMapping(value = "/deleteBook")
    Message deleteBook(@RequestBody Map<String, Integer> params) {
        if(SessionUtil.getUserType()<=0) return MessageUtil.makeMsg(-1,"无权访问") ;
        Integer bookId = params.get("bookId");
        return bookService.deleteBook(bookId);
    }
}
