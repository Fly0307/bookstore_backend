package com.ebook.backend.serviceimpl;

import com.ebook.backend.dao.*;
import com.ebook.backend.entity.Book;
import com.ebook.backend.entity.OrderItem;
import com.ebook.backend.entity.UserOrder;
import com.ebook.backend.repository.BookRepository;
import com.ebook.backend.repository.UserOrderRepository;
import com.ebook.backend.service.UserOrderService;
import com.ebook.backend.utils.messagegutils.Message;
import com.ebook.backend.utils.messagegutils.MessageUtil;
import com.ebook.backend.utils.sessionutils.SessionUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.ebook.backend.utils.messagegutils.MessageUtil.PURCHASE_SUCCESS;

@Service
public class UserOrderServiceimpl implements UserOrderService {
    @Autowired

    private UserOrderDao userOrderDao;
    @Autowired

    private OrderItemDao orderItemDao;

    private BookDao bookDao;

    private CartDao cartDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserOrderRepository userOrderRepository;


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


    //采取默认情况
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public Message recordUserOrder(String receiver, String tel, String address, JSONArray books, Integer userId) {
        Integer totalPrice=0;
        for (Object book : books) {
            JSONObject jobj = JSONObject.fromObject(book);
            Integer bookId = jobj.getInt("bookId");
            Book tmpBook =bookDao.getBookById(bookId);
            totalPrice=totalPrice+tmpBook.getPrice()*jobj.getInt("purchaseNum");
            if(jobj.getInt("purchaseNum")>bookDao.getBookById(bookId).getNum()){
                System.out.println("库存不足");
                return MessageUtil.makeMsg(-5,tmpBook.getName()+"库存不够");
            }
        }
        System.out.println("更新购物车，处理每个订单项目，books="+books);
        System.out.println(userId);
        Integer orderId= userOrderDao.addUserOrder(receiver,tel,address,totalPrice,userId);

        /*使用orderItemImpl更新购物车*/
        for (Object book : books) {
            JSONObject jobj = JSONObject.fromObject(book);
            Integer bookId = jobj.getInt("bookId");
            Book tmpBook =bookDao.getBookById(bookId);
            Integer purchaseNum =jobj.getInt("purchaseNum");
            if(bookDao.changeSale(bookId,purchaseNum).getStatus()>0){
                //通过orderItemDao来处理订单的每个内容
                orderItemDao.addOrderItem(orderId,bookId,purchaseNum);
                cartDao.deleteBook(bookId, userId);
            }
            else{
                return MessageUtil.makeMsg(-5,tmpBook.getName()+"库存不够");
            }
        }

        JSONObject retData = new JSONObject();
        retData.put("orders",books);
        return MessageUtil.makeMsg(PURCHASE_SUCCESS, "消费成功",retData);
    }

    @Override
    public Message deleteUserOrders(Integer userId,Integer orderId) {
        List<OrderItem> orderItems = userOrderDao.getOrderItemByOrderId(orderId);
        /*for (OrderItem order : orderItems){
            Integer tmpId=order.getOrderId();
            userOrderDao.deleteOrder(userId,tmpId);
            System.out.println("删除订单内容");
        }*/
//        userOrderRepository.deleteOrderById(orderId);
        userOrderRepository.deleteById(orderId);
        return MessageUtil.makeMsg(1,"删除成功");

    }

    @Override
    public Message getOrderState(Integer userId, String receivertel) {
        UserOrder userOrder=userOrderRepository.getUserOrderByUserIdAndOrderTel(userId,receivertel);
        System.out.println("getOrderState：订单状态为"+userOrder.getState());
        if (userOrder.getState()!=null) return MessageUtil.makeMsg(1,"下单成功");
        else return MessageUtil.makeMsg(0,"下单失败");
    }

    @Transactional
    @Override
    public Message modifyOrders(Integer orderId, Integer orderState) {
        Integer userId = SessionUtil.getUserId();
        if(orderState==0){
            /*如果state==0,则删除订单*/
            deleteUserOrders(userId,orderId);
        }
        if(orderState!= 0){
            userOrderDao.updateOrder(orderId,orderState);
        }
        return MessageUtil.makeMsg(1,"修改订单成功");
    }

    @Override
    public List<UserOrder> getAllOrderByKeyword(Date start, Date end, String keyword) {
        return userOrderDao.getAllOrderByKeyword(start,end,keyword);
    }


}
