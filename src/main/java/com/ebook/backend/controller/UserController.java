package com.ebook.backend.controller;

import com.ebook.backend.entity.UserAuthority;
import com.ebook.backend.service.UserService;
import com.ebook.backend.utils.messagegutils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/checkUser")
    public UserAuthority checkUser(@RequestParam("username") String username, @RequestParam("password") String password){
        return userService.checkUser(username, password);
    }

    @RequestMapping("/register")
    Message register(@RequestBody Map<String, String> params){
        String username = params.get("username");
        String password =params.get("password");
        String email =params.get("email");
        return userService.register(username,password,email);
    }

    @RequestMapping("/addToCart")
    Message add2Cart(@RequestBody Map<String, Integer> params) {
        Integer bookId =params.get("bookId");
        Integer purchaseNumber =params.get("purchaseNum");
        System.out.println("加入数量:");
        System.out.println(purchaseNumber);
        return userService.add2Cart(bookId,purchaseNumber);
    }

}
