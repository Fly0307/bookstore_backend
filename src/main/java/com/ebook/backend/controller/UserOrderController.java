package com.ebook.backend.controller;

import com.ebook.backend.entity.UserOrder;
import com.ebook.backend.service.UserOrderService;
import com.ebook.backend.utils.messagegutils.Message;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

public class UserOrderController {

    @Autowired
    UserOrderService userOrderService;

    @RequestMapping("/getUserOrders")
    ArrayList<UserOrder> getAllOrders() {
        return userOrderService.getAllOrders();
    }

    @RequestMapping("/newOrder")
    Message newJmsUserOrder(@RequestBody JSONObject order) {
        String tel = order.getString("tel");
        String address =order.getString("address");
        String receiver =order.getString("receiver");
        JSONArray cartItems = order.getJSONArray("books");
        return userOrderService.recordUserOrder(cartItems,tel,receiver,address);
    }

}
