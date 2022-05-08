package com.ebook.backend.serviceimpl;

import com.ebook.backend.dao.UserDao;
import com.ebook.backend.entity.UserAuthority;
import com.ebook.backend.service.UserService;
import com.ebook.backend.utils.messagegutils.Message;
import com.ebook.backend.utils.sessionutils.SessionUtil;
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

    @Override
    public Message register(String username,String password,String email){
        Message msg =userDao.checkUserDup(username);
        if(msg.getStatus()<0)
            return msg;
        System.out.println(email);
        return userDao.register(username,password,email);
    }
    @Override
    public Message add2Cart(Integer bookId, Integer purchaseNumber){
        Integer custom = SessionUtil.getUserId();
        return userDao.add2Cart(custom,bookId,purchaseNumber);
    }
}
