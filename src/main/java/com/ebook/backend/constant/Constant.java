package com.ebook.backend.constant;

/*
1   control
2   service
3   repository  调用
4   dao         SQL
5   entity      对应的实现
*
* */
public class Constant {
    public static final String USER_ID = "userId";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String USER_TYPE = "userType";
    public static final String REMEMBER_ME = "remember";
    public static final String JSESSIONID = "JSESSIONID";

    public static final Integer NO_SUCH_USER = -1;
    public static final Integer MANAGER = 0;
    public static final Integer CUSTOMER = 1;
}
