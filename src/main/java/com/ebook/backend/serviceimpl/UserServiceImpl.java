package com.ebook.backend.serviceimpl;

import com.ebook.backend.dao.UserDao;
import com.ebook.backend.service.UserService;
import com.ebook.backend.entity.UserAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserAuthority checkUser(String username, String password){
        return userDao.checkUser(username,password);


    }
}
