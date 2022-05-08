package com.ebook.backend.service;

import com.ebook.backend.entity.UserAuthority;
import com.ebook.backend.utils.messagegutils.Message;


public interface UserService {

    UserAuthority checkUser(String username, String password);

    Message register(String username,String password,String email);

    Message add2Cart(Integer bookId, Integer purchaseNumber);
}
