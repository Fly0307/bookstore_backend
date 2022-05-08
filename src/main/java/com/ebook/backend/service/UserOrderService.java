package com.ebook.backend.service;

import com.ebook.backend.entity.UserOrder;
import com.ebook.backend.utils.messagegutils.Message;
import net.sf.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public interface UserOrderService {

    ArrayList<UserOrder> getAllOrders() ;

    List<UserOrder> manageOrders();

    Message recordUserOrder();

    Message recordUserOrder(JSONArray books,String tel,String receiver,String address);

    List<UserOrder> getOrderByDate(Date start, Date end);

    /*List<UserOrder> getOrderByKeyword(Date start, Date end,String keyword);*/

    /*List<UserOrder> getAllOrderByKeyword(Date start, Date end, String keyword);*/
}
