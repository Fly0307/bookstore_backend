package com.ebook.backend.service;

import com.ebook.backend.entity.Cart;
import com.ebook.backend.entity.UserAuthority;
import com.ebook.backend.utils.messagegutils.Message;
import net.sf.json.JSONArray;

import java.util.List;

public interface UserService {
    UserAuthority checkUser(String username, String password);

    Message register(String username,String nickname,String tel,String password,String email);

    Message addToCart(Integer bookId, Integer purchaseNumber);

    List<Cart> getCart(Integer userid);

    JSONArray getAllUsers();

    Message banUser(int userId);

    Message liftUser(int userId);

    Message isexist(String username);
}
