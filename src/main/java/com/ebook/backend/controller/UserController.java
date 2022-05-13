package com.ebook.backend.controller;

import com.ebook.backend.constant.Constant;
import com.ebook.backend.entity.Cart;
import com.ebook.backend.entity.UserAuthority;
import com.ebook.backend.service.CartService;
import com.ebook.backend.service.UserService;
import com.ebook.backend.utils.messagegutils.Message;
import com.ebook.backend.utils.sessionutils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

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
    Message addToCart(@RequestBody Map<String, Integer> params) {
        Integer bookId =params.get("bookId");
        Integer purchaseNumber =params.get("purchaseNum");
        Integer userId = SessionUtil.getUserId();
        System.out.println("加入数量:");
        System.out.println(purchaseNumber);
        return cartService.addNewCart(userId,bookId,purchaseNumber);
        //return userService.addToCart(bookId,purchaseNumber);
    }
    @RequestMapping("/deleteCartItem")
    void deleteCartItem(@RequestBody Map<String, Integer> params) {
        Integer bookId =params.get("bookId");
        cartService.deleteBook(bookId);
    }
    @RequestMapping("/modifyCart")
    void modifyCart(@RequestBody Map<String, Integer> params) {
        Integer bookId = params.get("bookId");
        //System.out.println(bookId);
        Integer newPurchaseNum = params.get("newPurchaseNum");
        cartService.modifyCart(bookId, newPurchaseNum);
    }

    @RequestMapping("/getCart")
    public List<Cart> getCart(@RequestBody Map<String, Integer> params) {
        Integer userid= params.get(Constant.USER_ID);

        return userService.getCart(userid);
    }

}
