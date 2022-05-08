package com.ebook.backend.service;

import com.ebook.backend.entity.UserAuthority;


public interface UserService {

    UserAuthority checkUser(String username, String password);
}
