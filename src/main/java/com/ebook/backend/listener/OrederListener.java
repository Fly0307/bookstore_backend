package com.ebook.backend.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ebook.backend.WebSocket.WebSocketServer;
import com.ebook.backend.service.UserOrderService;
import com.ebook.backend.utils.messagegutils.Message;
import com.ebook.backend.utils.sessionutils.SessionUtil;
import net.sf.json.JSONArray;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.ebook.backend.utils.messagegutils.MessageUtil.PURCHASE_SUCCESS;

@Component
public class OrederListener {
    @Autowired
    private UserOrderService userOrderService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private WebSocketServer ws;

    //监听接收未处理的订单消息
    @KafkaListener(topics = "topic1", groupId = "group_topic_bookstore")
    public void topic1Listener(ConsumerRecord<String, String> record) {
        System.out.println("topic1Listener:Topic1监听到消息");
        JSONObject orderdata_json= JSON.parseObject(record.value());
        System.out.println("order_topic1 get data="+orderdata_json);
        Integer userId=orderdata_json.getInteger("userId");
        String tel = orderdata_json.getString("tel");
        String address =orderdata_json.getString("address");
        String receiver =orderdata_json.getString("receiver");
        JSONArray cartItems = JSONArray.fromObject(orderdata_json.getJSONArray("books"));
        int status=-1;
        System.out.println("topic1Listener:userid="+userId);
        System.out.println("topic1Listener "+SessionUtil.getUserId());
        Message message= userOrderService.recordUserOrder(receiver,tel,address,cartItems,userId);
        status= message.getStatus();
        ws.onMessage("websocket连接成功");
        if(status==PURCHASE_SUCCESS)ws.sendMessageToUser(String.valueOf(userId),String.valueOf(PURCHASE_SUCCESS));
        System.out.println("topic1Listener:Topic1处理后，转发订单处理结果给topic2");
        kafkaTemplate.send("topic2",  "OrderStatus", message.getMsg());
    }

    //接受下订单的处理结果
    @KafkaListener(topics = "topic2", groupId = "group_topic_bookstore")
    public void topic2Listener(ConsumerRecord<String, String> record) {
        System.out.println("topic1Listener:Topic2监听到消息");
        String Status = record.value();
        System.out.println("topic2Listener:下订单状态为"+Status);
    }
}
