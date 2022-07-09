package com.ebook.backend.controller;

import com.ebook.backend.constant.Constant;
import com.ebook.backend.entity.UserAuthority;
import com.ebook.backend.service.UserService;
import com.ebook.backend.utils.messagegutils.Message;
import com.ebook.backend.utils.messagegutils.MessageCode;
import com.ebook.backend.utils.messagegutils.MessageUtil;
import com.ebook.backend.utils.sessionutils.SessionUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
//@CrossOrigin
@RestController
public class LoginController {


    @Autowired
    private UserService userService;
    //@ResponseBody
    @RequestMapping("/login")
    public Message login(@RequestBody Map<String, String> params){
        String username = params.get(Constant.USERNAME);
        String password = params.get(Constant.PASSWORD);
        UserAuthority auth = userService.checkUser(username, password);

        if(auth != null&&auth.getUserType()>=0){

            JSONObject obj = new JSONObject();
            obj.put(Constant.USER_ID, auth.getUserId());
            obj.put(Constant.USERNAME, auth.getUsername());
            obj.put(Constant.USER_TYPE, auth.getUserType());
            SessionUtil.setSession(obj);

            JSONObject data = JSONObject.fromObject(auth);
            data.remove(Constant.PASSWORD);
            return MessageUtil.makeMsg(MessageCode.SUCCESS, MessageUtil.LOGIN_SUCCESS_MSG+","+username, data);

//            return MessageUtil.makeMsg(MessageCode.SUCCESS, MessageUtil.LOGIN_SUCCESS_MSG, data);
        }
        else{
//            assert auth != null;
            if (auth==null){
                return MessageUtil.makeMsg(MessageCode.LOGIN_USER_ERROR);
            }
            else if(auth.getUserType()==-1){
                return MessageUtil.makeMsg(MessageCode.LOGIN_USER_BIN);
            }
            else return MessageUtil.makeMsg(MessageCode.LOGIN_USER_ERROR);
        }
    }

    @RequestMapping("/logout")
    public Message logout(){
        Boolean status = SessionUtil.removeSession();

        if(status){
            return MessageUtil.makeMsg(MessageCode.SUCCESS, MessageUtil.LOGOUT_SUCCESS_MSG);
        }
        return MessageUtil.makeMsg(MessageCode.ERROR, MessageUtil.LOGOUT_ERR_MSG);
    }

    @RequestMapping("/checkSession")
    public Message checkSession(){
        JSONObject auth = SessionUtil.getAuth();

        if(auth == null){
            return MessageUtil.makeMsg(MessageCode.NOT_LOGGED_IN_ERROR);
        }
        else{
            return MessageUtil.makeMsg(MessageCode.SUCCESS, MessageUtil.LOGIN_SUCCESS_MSG, auth);
        }
    }
}
