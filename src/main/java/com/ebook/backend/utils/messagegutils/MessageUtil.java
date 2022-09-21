package com.ebook.backend.utils.messagegutils;

import net.sf.json.JSONObject;


public class MessageUtil {

    public static final int SUCCESS = 0;
    public static final int ERROR = -1;
    public static final int LOGIN_USER_ERROR = -100;
    public static final int NOT_LOGGED_IN_ERROR = -101;
    public static final int LOGIN_USER_BIN=-102;


    public static final String SUCCESS_MSG = "成功！";
    public static final String LOGIN_SUCCESS_MSG = "书城欢迎您！";
    public static final String LOGOUT_SUCCESS_MSG = "退出成功！";
    public static final String LOGOUT_ERR_MSG = "退出异常！";
    public static final String ERROR_MSG = "错误！";
    public static final String LOGIN_USER_ERROR_MSG = "用户名或密码错误，请重新输入！";
    public static final String NOT_LOGGED_IN_ERROR_MSG = "登录失效，请重新登录！";
    public static final int BAN_ERROR_CODE = -3;
    public static final String LOGIN_FORBIDDEN_MSG = "该账户已被禁止登录！";
    public static final String BAN_ERROR_MSG = "找不到该用户！";
    public static final String BAN_SUCCESS_MSG = "封禁成功！";
    public static final String BAN_ADMIN_MSG = "无法封禁管理员账号！";
    public static final String LIFT_SUCCESS_MSG = "解禁成功！";




    public static Message makeMsg(MessageCode code, JSONObject data){
        return new Message(code, data);
    }

    public static Message makeMsg(MessageCode code, String message, JSONObject data){
        return new Message(code, message, data);
    }

    public static Message makeMsg(MessageCode code){
        return new Message(code);
    }

    public static Message makeMsg(MessageCode code, String message){
        return new Message(code, message);
    }

    public static Message makeMsg(int status, String message, JSONObject data){
        return new Message(status, message, data);
    }

    public static Message makeMsg(int status, String message){
        return new Message(status, message);
    }
}
