package com.ebook.backend.dao;

import com.ebook.backend.entity.Cart;
import com.ebook.backend.entity.User;
import com.ebook.backend.entity.UserAuthority;
import com.ebook.backend.utils.messagegutils.Message;
import net.sf.json.JSONArray;

import java.util.List;

public interface UserDao {

    User getUser();

    List<Cart> getCart(Integer userid);
    UserAuthority checkUser(String username, String password);

    Message addToCart(Integer buyer, Integer bookid, Integer purchaseNum);

    Message register(String username,String nickname,String tel,String password,String email);

    Message checkUserDup(String userName);

    JSONArray getAllUsers();

    Message banUser(int userId);

    Message liftUser(int userId);

    List<User> getAll();
}
