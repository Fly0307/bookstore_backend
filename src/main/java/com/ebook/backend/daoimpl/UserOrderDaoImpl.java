package com.ebook.backend.daoimpl;

import com.ebook.backend.dao.UserOrderDao;
import com.ebook.backend.entity.OrderItem;
import com.ebook.backend.entity.UserOrder;
import com.ebook.backend.repository.BookRepository;
import com.ebook.backend.repository.OrderItemRepository;
import com.ebook.backend.repository.UserOrderRepository;
import com.ebook.backend.utils.messagegutils.Message;
import com.ebook.backend.utils.messagegutils.MessageUtil;
import com.ebook.backend.utils.sessionutils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class UserOrderDaoImpl implements UserOrderDao {

    @Autowired
    UserOrderRepository userOrderRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    OrderItemRepository orderItemRepository;


    @Autowired
    void setUserOrderRepository(UserOrderRepository userOrderRepository) {
        this.userOrderRepository = userOrderRepository;
    }

    @Override
    public List<OrderItem> getOrderItemByOrderId(Integer orderId) {
        return orderItemRepository.getOrderItemByOrderId(orderId);
    }

    @Override
    public List<UserOrder> getAllOrders() {
        Integer userId = SessionUtil.getUserId();
        if (userId != null)
            return userOrderRepository.getAllOrders(userId);
        return null;
    }

    @Override
    public List<UserOrder> manageOrders() {

        return userOrderRepository.findAll();
    }

    @Override
    public Message deleteOrder(Integer userId, Integer OrderId) {
        orderItemRepository.deleteOrderById(OrderId);
        return MessageUtil.makeMsg(1,"删除订单内容成功");
    }
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer addUserOrder(String receiver, String tel, String address, Integer totalPrice, Integer userId) {
        System.out.println("addUserOrder:"+userId+receiver+" "+tel+" "+address);
        if (userId != null) {
//            使用Listener，丢失Session
//            int permission = SessionUtil.getUserType();
//            if (permission < 0)
//                return null;
            UserOrder userOrder = new UserOrder();
            userOrder.setUserId(userId);
            userOrder.setOrderReceiver(receiver);
            userOrder.setOrderAddress(address);
            userOrder.setOrderTel(tel);
            userOrder.setTotalPrice(totalPrice);
            userOrder.setOrderTime(Timestamp.valueOf(LocalDateTime.now()));
            userOrder.setState(1);
//            int result=10/0;
            return userOrderRepository.saveAndFlush(userOrder).getOrderId();//返回生成的OrderId
        }
        return null;
    }

    @Override
    public UserOrder getUserOrder(Integer orderId) {
        return userOrderRepository.getOne(orderId);
    }


//    @Override
//    public void addOrderItem(Integer orderId, Integer bookId, Integer purchaseNumber) {
//        OrderItem orderItem = new OrderItem();
//        Book book=bookRepository.getBookById(bookId);
//        orderItem.setOrderId(orderId);
//        orderItem.setPurchaseNumber(purchaseNumber);
//        orderItem.setBookId(bookId);
//        orderItem.setPrice(book.getPrice());
//        orderItemRepository.save(orderItem);
//    }

    @Override
    public List<UserOrder> getOrderByDate(Date start, Date end) {

        return userOrderRepository.getOrderByDate(start, end);
    }

    @Override
    public List<UserOrder> getOnesOrder(Date start, Date end, Integer userId) {
        return userOrderRepository.getOrdersInRange(userId, start, end);
    }

    @Override
    public Message updateOrder(Integer orderId, Integer orderState) {
//        UserOrder userOrder = new UserOrder();
//        userOrder.setOrderId(orderId);
//        userOrder.setState(orderState);
//        userOrderRepository.save(userOrder);
        userOrderRepository.updateState(orderId,orderState);
        return MessageUtil.makeMsg(1,"修改订单成功");
    }

    @Override
    public List<UserOrder> getAllOrderByKeyword(Date start, Date end, String keyword) {
        Integer usertype=SessionUtil.getUserType();
        Integer userId=SessionUtil.getUserId();
        if(usertype==0){
            List<UserOrder> allOrdersByDate = userOrderRepository.getOrderByDate(start,end);
            List<UserOrder> resultOrders = new ArrayList<>();
            for (UserOrder userOrder : allOrdersByDate) {
                if (Objects.equals(userId, userOrder.getUserId())){
                    Set<OrderItem> items = userOrder.getOrders();
                    for (OrderItem item : items) {
                        if(item.getBook().getName().contains(keyword)){
                            resultOrders.add(userOrder);
                            break;
                        }
                    }
                }
            }
            return resultOrders ;
        }else {
            List<UserOrder> allOrdersByDate = userOrderRepository.getOrderByDate(start,end);
            List<UserOrder> resultOrders = new ArrayList<>();
            for (UserOrder userOrder : allOrdersByDate) {
                Set<OrderItem> items = userOrder.getOrders();
                for (OrderItem item : items) {
                    if(item.getBook().getName().contains(keyword)){
                        resultOrders.add(userOrder);
                        break;
                    }
                }
            }
            return resultOrders ;
        }

    }

}