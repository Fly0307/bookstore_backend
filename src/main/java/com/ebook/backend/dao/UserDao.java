package com.ebook.backend.dao;

import com.ebook.backend.entity.UserAuthority;

public interface UserDao {

    UserAuthority checkUser(String username, String password);
}
