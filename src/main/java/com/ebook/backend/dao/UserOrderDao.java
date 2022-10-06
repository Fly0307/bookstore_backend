package com.ebook.backend.dao;

import com.ebook.backend.entity.OrderItem;
import com.ebook.backend.entity.UserOrder;
import com.ebook.backend.utils.messagegutils.Message;

import java.util.Date;
import java.util.List;

public interface UserOrderDao{

    List<OrderItem> getOrderItemByOrderId(Integer orderId);

    List<UserOrder> getAllOrders();

    List<UserOrder> manageOrders();
    Message deleteOrder(Integer userId,Integer OrderId);

    //返回生成的OrderId
    Integer addUserOrder(String receiver, String tel, String address, Integer totalPrice, Integer userId);

    UserOrder getUserOrder(Integer orderId);

    //在这个操作前要先用addUserOrder增加一个订单号
//    void addOrderItem(Integer orderId, Integer bookId, Integer purchaseNumber);

    List<UserOrder>  getOrderByDate(Date start, Date end);

    List<UserOrder> getOnesOrder(Date start, Date end,Integer userId);

    Message updateOrder(Integer orderId, Integer orderState);

    List<UserOrder> getAllOrderByKeyword(Date start, Date end, String keyword);
}
