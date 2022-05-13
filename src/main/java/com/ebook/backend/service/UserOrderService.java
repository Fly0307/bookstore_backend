package com.ebook.backend.service;

import com.ebook.backend.entity.UserOrder;
import com.ebook.backend.utils.messagegutils.Message;
import net.sf.json.JSONArray;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface UserOrderService {

    List<UserOrder> getAllOrders() ;

    List<UserOrder> manageOrders();

    Message recordUserOrder(String receiver,String tel,String address,JSONArray books);

    Message deleteUserOrders(Integer userId,Integer orderId);


    Message modifyOrders(Integer orderId, Integer orderState);

    /*List<UserOrder> getOrderByKeyword(Date start, Date end,String keyword);*/

    /*List<UserOrder> getAllOrderByKeyword(Date start, Date end, String keyword);*/
}
