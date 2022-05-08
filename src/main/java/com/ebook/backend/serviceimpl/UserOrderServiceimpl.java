package com.ebook.backend.serviceimpl;

import com.ebook.backend.dao.BookDao;
import com.ebook.backend.dao.CartDao;
import com.ebook.backend.dao.UserDao;
import com.ebook.backend.dao.UserOrderDao;
import com.ebook.backend.entity.Book;
import com.ebook.backend.entity.Cart;
import com.ebook.backend.entity.User;
import com.ebook.backend.entity.UserOrder;
import com.ebook.backend.repository.BookRepository;
import com.ebook.backend.service.UserOrderService;
import com.ebook.backend.utils.messagegutils.Message;
import com.ebook.backend.utils.messagegutils.MessageUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserOrderServiceimpl implements UserOrderService {

    private UserOrderDao userOrderDao;

    private BookDao bookDao;

    private CartDao cartDao;

    private UserDao userDao;

    private BookRepository bookRepository;

    @Autowired
    void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    void setUserOrderDao(UserOrderDao userOrderDao) {
        this.userOrderDao = userOrderDao;
    }

    @Autowired
    void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Autowired
    void setBookDao(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    @Override
    public ArrayList<UserOrder> getAllOrders() {
        return userOrderDao.getAllOrders();
    }

    @Override
    public List<UserOrder> manageOrders() {
        return userOrderDao.manageOrders();
    }

    @Override
    public Message recordUserOrder() {
        User user = userDao.getUser();
        List<Cart> cartItems = userDao.getCart(user.getUserId());
        for (Cart cartItem : cartItems){
            Book book = bookRepository.getBookById(cartItem.getBookId());
            if(cartItem.getPurchaseNum()>book.getNum())
                return MessageUtil.makeMsg(-5,book.getName()+"库存不够");
        }
        Integer orderId= userOrderDao.addUserOrder(user.getUsername(),user.getUserTel(),user.getUserAddress());
        for (Cart cartItem : cartItems) {
            Book book = bookRepository.getBookById(cartItem.getBookId());
            if(bookDao.changeSale(cartItem.getBookId(),cartItem.getPurchaseNum()).getStatus()>0){
                userOrderDao.addOrderItem(orderId,cartItem.getBookId(),cartItem.getPurchaseNum());
                cartDao.deleteBook(cartItem.getBookId());
            }
            else{
                return MessageUtil.makeMsg(-5,book.getName()+"库存不够");
            }
        }
        return MessageUtil.makeMsg(2, "消费成功");
    }

    @Override
    public Message recordUserOrder(JSONArray books, String tel, String receiver, String address) {
        for (Object book : books) {
            JSONObject jobj = JSONObject.fromObject(book);
            Integer bookId = jobj.getInt("bookId");
            Book tmpBook =bookDao.getBookById(bookId);
            if(jobj.getInt("purchaseNum")>bookDao.getBookById(bookId).getNum())
            {return MessageUtil.makeMsg(-5,tmpBook.getName()+"库存不够");}
        }
        Integer orderId= userOrderDao.addUserOrder(receiver,tel,address);
        for (Object book : books) {
            JSONObject jobj = JSONObject.fromObject(book);
            Integer bookId = jobj.getInt("bookId");
            Book tmpBook =bookDao.getBookById(bookId);
            Integer purchaseNum =jobj.getInt("purchaseNum");
            if(bookDao.changeSale(bookId,purchaseNum).getStatus()>0){
                userOrderDao.addOrderItem(orderId,bookId,purchaseNum);
                cartDao.deleteBook(bookId);
            }
            else{
                return MessageUtil.makeMsg(-5,tmpBook.getName()+"库存不够");
            }
        }
        JSONObject retData = new JSONObject();
        retData.put("orders",books);
        return MessageUtil.makeMsg(2, "消费成功",retData);
    }


    @Override
    public List<UserOrder> getOrderByDate(Date start, Date end) {
        return userOrderDao.getOrderByDate(start,end);
    }

   /* @Override
    public List<UserOrder> getOrderByKeyword(Date start, Date end, String keyword) {
        return userOrderDao.getOrderByKeyword(start,end,keyword);
    }*/

/*    @Override
    public List<UserOrder> getAllOrderByKeyword(Date start, Date end, String keyword) {
        return userOrderDao.getAllOrderByKeyword(start,end,keyword);
    }*/


}
