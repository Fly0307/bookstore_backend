package com.ebook.backend.service;

import com.ebook.backend.entity.UserOrder;
import com.ebook.backend.utils.messagegutils.Message;
import net.sf.json.JSONArray;

import java.util.Date;
import java.util.List;


public interface UserOrderService {

    List<UserOrder> getAllOrders() ;

    List<UserOrder> manageOrders();

    Message recordUserOrder(String receiver, String tel, String address, JSONArray books, Integer userId);

    Message deleteUserOrders(Integer userId,Integer orderId);
    Message getOrderState(Integer userId,String receivertel);

    Message modifyOrders(Integer orderId, Integer orderState);

    List<UserOrder> getAllOrderByKeyword(Date start, Date end, String keyword);
}
