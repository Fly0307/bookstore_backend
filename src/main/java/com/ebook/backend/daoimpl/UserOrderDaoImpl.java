package com.ebook.backend.daoimpl;

import com.ebook.backend.dao.UserOrderDao;
import com.ebook.backend.entity.OrderItem;
import com.ebook.backend.entity.UserOrder;
import com.ebook.backend.repository.BookRepository;
import com.ebook.backend.repository.OrderItemRepository;
import com.ebook.backend.repository.UserOrderRepository;
import com.ebook.backend.utils.sessionutils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public ArrayList<UserOrder> getAllOrders() {
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
    public Integer addUserOrder(String receiver, String tel, String address) {
        Integer userId = SessionUtil.getUserId();

        if (userId != null) {
            int permission = SessionUtil.getUserType();
            if (permission < 0)
                return null;
            UserOrder userOrder = new UserOrder();
            userOrder.setUserId(userId);
            userOrder.setOrderReceiver(receiver);
            userOrder.setOrderAddress(address);
            userOrder.setOrderTel(tel);
            userOrder.setOrderTime(Timestamp.valueOf(LocalDateTime.now()));
            return userOrderRepository.saveAndFlush(userOrder).getOrderId();//返回生成的OrderId
        }
        return null;
    }

    @Override
    public UserOrder getUserOrder(Integer orderId) {
        return userOrderRepository.getOne(orderId);
    }


    @Override
    public void addOrderItem(Integer orderId, Integer bookId, Integer purchaseNumber) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(orderId);
        orderItem.setPurchaseNumber(purchaseNumber);
        orderItem.setBookId(bookId);
        orderItemRepository.save(orderItem);
    }

    @Override
    public List<UserOrder> getOrderByDate(Date start, Date end) {

        return userOrderRepository.getOrderByDate(start, end);
    }

    @Override
    public List<UserOrder> getOnesOrder(Date start, Date end, Integer userId) {
        return userOrderRepository.getOrdersInRange(userId, start, end);
    }

}