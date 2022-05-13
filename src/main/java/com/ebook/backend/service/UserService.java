package com.ebook.backend.service;

import com.ebook.backend.entity.Cart;
import com.ebook.backend.entity.UserAuthority;
import com.ebook.backend.utils.messagegutils.Message;

import java.util.List;

public interface UserService {
    UserAuthority checkUser(String username, String password);

    Message register(String username,String password,String email);

    Message addToCart(Integer bookId, Integer purchaseNumber);

    List<Cart> getCart(Integer userid);

}
