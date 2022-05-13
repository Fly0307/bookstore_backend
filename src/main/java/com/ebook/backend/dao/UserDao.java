package com.ebook.backend.dao;

import com.ebook.backend.entity.Cart;
import com.ebook.backend.entity.User;
import com.ebook.backend.entity.UserAuthority;
import com.ebook.backend.utils.messagegutils.Message;

import java.util.List;

public interface UserDao {

    User getUser();

    List<Cart> getCart(Integer userid);
    UserAuthority checkUser(String username, String password);

    Message addToCart(Integer buyer, Integer bookid, Integer purchaseNum);

    Message register(String userName, String password, String email);

    Message checkUserDup(String userName);

}
