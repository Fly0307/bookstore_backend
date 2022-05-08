package com.ebook.backend.daoimpl;

import com.ebook.backend.repository.UserAuthRepository;
import com.ebook.backend.dao.UserDao;
import com.ebook.backend.entity.UserAuthority;
import com.ebook.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @ClassName UserDaoImpl
 * @Description the implement of user dao
 * @Author thunderBoy
 * @Date 2019/11/7 13:19
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    UserAuthRepository userAuthRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public UserAuthority checkUser(String username, String password){

        return userAuthRepository.checkUser(username,password);
    }


}
