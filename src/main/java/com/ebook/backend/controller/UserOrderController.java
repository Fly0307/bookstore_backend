package com.ebook.backend.controller;

import com.ebook.backend.entity.UserOrder;
import com.ebook.backend.service.UserOrderService;
import com.ebook.backend.utils.messagegutils.Message;
import com.ebook.backend.utils.messagegutils.MessageUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class UserOrderController {

    @Autowired
    UserOrderService userOrderService;

    MessageUtil messageUtil;

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
        int status=-1;
        try {
            status= userOrderService.recordUserOrder(receiver,tel,address,cartItems).getStatus();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(status>=0){
            return MessageUtil.makeMsg(1,MessageUtil.PURCHASE_SUCCESS_MSG) ;
        }
        else {
            return MessageUtil.makeMsg(-1,MessageUtil.PURCHASE_ERROR_MSG);
        }
    }
    /*关键词获取订单信息*/
    @RequestMapping("/getAllOrderByKeyword")
    List<UserOrder> getAllOrderByKeyword(@RequestBody JSONObject infos) throws ParseException {
        String word = infos.getString("key");
        String startT = infos.getString("start");
        String endT =infos.getString("end");
        if(!startT.equals("")&&!endT.equals("")){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = sdf.parse(startT);
            SimpleDateFormat sbf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date end = sbf.parse(endT);
            return userOrderService.getAllOrderByKeyword(start,end,word);
        }
        return userOrderService.getAllOrderByKeyword(new Date(0),new Date(),word);

    }

}
