package com.ebook.backend.serviceimpl;

import com.ebook.backend.dao.BookDao;
import com.ebook.backend.dao.CartDao;
import com.ebook.backend.dao.UserDao;
import com.ebook.backend.dao.UserOrderDao;
import com.ebook.backend.entity.Book;
import com.ebook.backend.entity.OrderItem;
import com.ebook.backend.entity.UserOrder;
import com.ebook.backend.repository.BookRepository;
import com.ebook.backend.repository.OrderItemRepository;
import com.ebook.backend.repository.UserOrderRepository;
import com.ebook.backend.service.UserOrderService;
import com.ebook.backend.utils.messagegutils.Message;
import com.ebook.backend.utils.messagegutils.MessageUtil;
import com.ebook.backend.utils.sessionutils.SessionUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserOrderServiceimpl implements UserOrderService {

    private UserOrderDao userOrderDao;

    private BookDao bookDao;

    private CartDao cartDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserOrderRepository userOrderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

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
    public List<UserOrder> getAllOrders() {
        return userOrderDao.getAllOrders();
    }

    @Override
    public List<UserOrder> manageOrders() {
        return userOrderDao.manageOrders();
    }


    @Override
    public Message recordUserOrder(String receiver,String tel,String address,JSONArray books) {
        Integer totalPrice=0;
        for (Object book : books) {
            JSONObject jobj = JSONObject.fromObject(book);
            Integer bookId = jobj.getInt("bookId");
            Book tmpBook =bookDao.getBookById(bookId);
            totalPrice+=tmpBook.getPrice();
            if(jobj.getInt("purchaseNum")>bookDao.getBookById(bookId).getNum())
            {return MessageUtil.makeMsg(-5,tmpBook.getName()+"库存不够");}
        }

        Integer orderId= userOrderDao.addUserOrder(receiver,tel,address,totalPrice);

        /*更新购物车*/
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
    public Message deleteUserOrders(Integer userId,Integer orderId) {
        List<OrderItem> orderItems = userOrderDao.getOrderItemByOrderId(orderId);
        for (OrderItem order : orderItems){
            Integer tmpId=order.getOrderId();
            userOrderDao.deleteOrder(userId,tmpId);
            System.out.println("删除订单结果");
        }
        userOrderRepository.deleteOrderById(orderId);

        return MessageUtil.makeMsg(1,"删除成功");

    }

    @Transactional
    @Override
    public Message modifyOrders(Integer orderId, Integer orderState) {
        Integer userId = SessionUtil.getUserId();
        if(orderState==0){
            deleteUserOrders(userId,orderId);
        }
        if(orderState!= 0){
            userOrderDao.updateOrder(orderId,orderState);
        }
        return MessageUtil.makeMsg(1,"修改订单成功");
    }


}
