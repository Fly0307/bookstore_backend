package com.ebook.backend.dao;

import com.ebook.backend.entity.UserOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface UserOrderDao {

    ArrayList<UserOrder> getAllOrders();

    List<UserOrder> manageOrders();

    //返回生成的OrderId
    Integer addUserOrder(String receiver, String tel, String address);

    UserOrder getUserOrder(Integer orderId);

    //在这个操作前要先用addUserOrder增加一个订单号
    void addOrderItem(Integer orderId, Integer bookId, Integer purchaseNumber);

    List<UserOrder>  getOrderByDate(Date start, Date end);

    List<UserOrder> getOnesOrder(Date start, Date end,Integer userId);

/*    List<UserOrder>  getAllOrderByKeyword(Date start, Date end,String keyword);*/
}
