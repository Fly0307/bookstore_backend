package com.ebook.backend.controller;

import com.ebook.backend.entity.UserOrder;
import com.ebook.backend.service.UserOrderService;
import com.ebook.backend.utils.messagegutils.Message;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class UserOrderController {

    @Autowired
    UserOrderService userOrderService;

    @RequestMapping("/getUserOrders")
    List<UserOrder> getAllOrders(@RequestBody Map<String, String> params) {
        return userOrderService.getAllOrders();
    }

    /*如果state为0则删除订单，否则修改订单状态 1未支付，2已支付，3已完成*/
    @RequestMapping("/modifyOrders")
    Message modifyOrders(@RequestBody JSONObject object) {
        Integer orderId= Integer.valueOf(object.getString("orderId"));
        Integer orderState= Integer.valueOf(object.getString("state"));

        return userOrderService.modifyOrders(orderId,orderState);
    }

    @RequestMapping("/newOrder")
    Message newJmsUserOrder(@RequestBody JSONObject order) {
        String tel = order.getString("tel");
        String address =order.getString("address");
        String receiver =order.getString("receiver");
        JSONArray cartItems = order.getJSONArray("books");
        return userOrderService.recordUserOrder(receiver,tel,address,cartItems);
    }

}
