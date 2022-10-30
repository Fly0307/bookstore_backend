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
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@CrossOrigin
@RestController
//@Scope("prototype")
public class LoginController {
    @Autowired
    private UserService userService;
//    @Autowired
//    private TimerService timerService;
    @ResponseBody
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
//            增加计时器，初始化计时器开始计时
//            timerService.StartCountTime();
//            System.out.println(timerService+"开始计时");

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
//            long time=timerService.GetTime();
//            System.out.println(timerService+"结束计时"+time);
//            String timeInfo="登录登出用时"+time+"ms";
//            return MessageUtil.makeMsg(MessageCode.SUCCESS, MessageUtil.LOGOUT_SUCCESS_MSG+timeInfo);
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
