package com.ebook.backend.controller;

import com.ebook.backend.entity.UserOrder;
import com.ebook.backend.service.BookService;
import com.ebook.backend.service.UserOrderService;
import com.ebook.backend.service.UserService;
import com.ebook.backend.utils.messagegutils.Message;
import com.ebook.backend.utils.messagegutils.MessageUtil;
import com.ebook.backend.utils.sessionutils.SessionUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ManagerController {
    @Autowired
    UserService userService;

    @Autowired
    UserOrderService userOrderService;

    @Autowired
    BookService bookService;



    @RequestMapping("/modifyBook")
    Message modifyBook(@RequestBody Map<String, String> params) {
        if(SessionUtil.getUserType()<=0) return MessageUtil.makeMsg(-1,"无权访问") ;

        return bookService.modifyBook(params);
    }
    @RequestMapping("/manage/getAllUsers")
    JSONArray getAllUsers(){
        if(SessionUtil.getUserType()<=0) return null;
        return userService.getAllUsers();
    }

    @RequestMapping("/setUserPermission/ban")
    Message banUser(@RequestParam("userId") int userId){
        return userService.banUser(userId);
    }
    @RequestMapping("/setUserPermission/lift")
    Message liftUser(@RequestParam("userId") int userId){
        return userService.liftUser(userId);
    }

    @RequestMapping("/manage/getAllOrders")
    List<UserOrder> getAllOrders(){
        Integer usertype=SessionUtil.getUserType();
        if(usertype<=0) return null;
        return userOrderService.manageOrders();
    }
}
