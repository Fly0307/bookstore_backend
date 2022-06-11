package com.ebook.backend.serviceimpl;

import com.ebook.backend.dao.UserDao;
import com.ebook.backend.entity.Cart;
import com.ebook.backend.entity.UserAuthority;
import com.ebook.backend.service.UserService;
import com.ebook.backend.utils.messagegutils.Message;
import com.ebook.backend.utils.sessionutils.SessionUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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
    public Message addToCart(Integer bookId, Integer purchaseNumber){
        Integer custom = SessionUtil.getUserId();
        System.out.println("加入数量:");
        return userDao.addToCart(custom,bookId,purchaseNumber);
    }
    @Override
    public List<Cart> getCart(Integer userid) {
        Integer custom = SessionUtil.getUserId();
        return userDao.getCart(custom);
    }

    @Override
    public JSONArray getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public Message banUser(int userId) {
        return userDao.banUser(userId);
    }

    @Override
    public Message liftUser(int userId) {
        return userDao.liftUser(userId);
    }
}
