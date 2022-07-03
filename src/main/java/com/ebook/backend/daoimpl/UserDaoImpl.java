package com.ebook.backend.daoimpl;

import com.ebook.backend.dao.UserDao;
import com.ebook.backend.entity.Cart;
import com.ebook.backend.entity.User;
import com.ebook.backend.entity.UserAuthority;
import com.ebook.backend.repository.CartRepository;
import com.ebook.backend.repository.UserAuthRepository;
import com.ebook.backend.repository.UserRepository;
import com.ebook.backend.utils.messagegutils.Message;
import com.ebook.backend.utils.messagegutils.MessageUtil;
import com.ebook.backend.utils.sessionutils.SessionUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    UserAuthRepository userAuthRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;
    @Override
    public User getUser() {
        Integer userId = SessionUtil.getUserId();

        if (userId != null)
            return userRepository.getUserById(userId);
        return null;
    }

    @Override
    public List<Cart> getCart(Integer userid){
        return cartRepository.getCart(userid);
    }

    @Override
    public UserAuthority checkUser(String username, String password){

        return userAuthRepository.checkUser(username,password);
    }
    @Override
    public Message addToCart(Integer buyer, Integer bookid, Integer purchaseNum) {
        System.out.println("try to insert:" + bookid);
        Cart cart=new Cart();
        cart.setUserId(buyer);
        cart.setCartId(bookid);
        cart.setPurchaseNum(purchaseNum);

        /*还没有save*/
        /*cartRepository.add2repository(bookid, buyer, purchaseNum);*/
        System.out.println("success to insert");
        return MessageUtil.makeMsg(0, "加入成功");
    }

    @Override
    public Message register(String username,String nickname,String tel,String password,String email){
        User user =new User();
        user.setUsername(username);
        user.setNickname(nickname);
        user.setUserTel(tel);
        user.setUserEmail(email);

        Integer userId = userRepository.save(user).getUserId();
        //System.out.println(userId);
        UserAuthority userAuthority = new UserAuthority();
        userAuthority.setUserId(userId);
        userAuthority.setUsername(username);
        userAuthority.setPassword(password);
        userAuthority.setUserType(0);
        userAuthority.setUserState(1);
        userAuthRepository.saveAndFlush(userAuthority);
        return MessageUtil.makeMsg(1,"注册成功,ID为"+userId.toString());
    }

    @Override
    public Message checkUserDup(String userName) {
        User check = userRepository.checkUserDup(userName);
        if(check!=null)
            return MessageUtil.makeMsg(-1,"用户名重复");
        else return MessageUtil.makeMsg(1,"用户名不重复");
    }

    @Override
    public JSONArray getAllUsers() {
        List<UserAuthority> allUserAuthority=userAuthRepository.findAll();
        JSONArray ret = new JSONArray();
        for (UserAuthority authority : allUserAuthority) {
            User tmpUser = userRepository.getUserById(authority.getUserId());
            JSONObject tmpObject =new JSONObject();
            tmpObject.put("userInfo",tmpUser);
            tmpObject.put("userType",authority.getUserType());
            tmpObject.put("userState",authority.getUserState());
            ret.add(tmpObject);
        }
        return ret;
    }

    @Override
    public Message banUser(int userId) {
        if (!userAuthRepository.existsById(userId)) {
            return MessageUtil.makeMsg(MessageUtil.BAN_ERROR_CODE, MessageUtil.BAN_ERROR_MSG);
        }
        UserAuthority userAuthority = userAuthRepository.getOne(userId);
        if (userAuthority.getUserType() > 0)//管理员账号,无法禁用
            return MessageUtil.makeMsg(MessageUtil.BAN_ERROR_CODE, MessageUtil.BAN_ADMIN_MSG);
        userAuthority.setUserType(-1);
        userAuthRepository.save(userAuthority);
        return MessageUtil.makeMsg(5, MessageUtil.BAN_SUCCESS_MSG);
    }

    @Override
    public Message liftUser(int userId) {
        if (!userAuthRepository.existsById(userId)) {
            return MessageUtil.makeMsg(MessageUtil.BAN_ERROR_CODE, MessageUtil.BAN_ERROR_MSG);
        }
        UserAuthority userAuthority = userAuthRepository.getOne(userId);
        if (userAuthority.getUserType() > 0)
            return MessageUtil.makeMsg(MessageUtil.BAN_ERROR_CODE, MessageUtil.BAN_ADMIN_MSG);
        userAuthority.setUserType(0);
        userAuthRepository.save(userAuthority);
        return MessageUtil.makeMsg(5, MessageUtil.LIFT_SUCCESS_MSG);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }



}
