package com.ebook.backend.serviceimpl;

import com.ebook.backend.dao.BookDao;
import com.ebook.backend.entity.Book;
import com.ebook.backend.entity.BookImage;
import com.ebook.backend.repository.BookImageRepository;
import com.ebook.backend.searching.searchfile;
import com.ebook.backend.service.BookService;
import com.ebook.backend.utils.Imageutils.ImageInsert;
import com.ebook.backend.utils.messagegutils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private BookImageRepository bookImageRepository;

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

    @Override
    public Message deleteBook(Integer bookId) {
        return bookDao.deleteBook(bookId);
    }
    @Override
    public List<Book> searchBookByKeyword(String keyWord) {
        List<Integer> BookIds = searchfile.searchByKeyword(keyWord);
        List<Book> books = new ArrayList<>();
        for (Integer bookId : BookIds) {
            Book book = bookDao.getBookById(bookId);
            books.add(book);
        }
        return books;
    }

    @Override
    public Boolean UpdateBookImage() {
        String imagePath="E:\\互联网应用开发技术\\bookstorebackend\\bookstore_backend\\src\\main\\resources\\bookimage\\";
        List<Book> Books=getBooks();

        for (Book book:Books) {
            String bookName=book.getName();
            String bookPath=imagePath+bookName;
            bookPath+=".jpg";
            String imageBase=ImageInsert.GetImageStr(bookPath);
            System.out.println("book "+bookName+" base64="+imageBase);
            BookImage bookImage=new BookImage(book.getBookId(),bookName,imageBase);
            //            Optional<BookImage> image = bookImageRepository;
            bookImageRepository.save(bookImage);
        }
        System.out.println("存储图片成功");
        return true;
    }


}
