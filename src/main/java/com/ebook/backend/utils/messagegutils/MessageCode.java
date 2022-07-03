package com.ebook.backend.utils.messagegutils;

public enum MessageCode {
    SUCCESS(MessageUtil.SUCCESS, MessageUtil.SUCCESS_MSG),
    ERROR(MessageUtil.ERROR, MessageUtil.ERROR_MSG),
    LOGIN_USER_ERROR(MessageUtil.LOGIN_USER_ERROR, MessageUtil.LOGIN_USER_ERROR_MSG),

    LOGIN_USER_BIN(MessageUtil.LOGIN_USER_BIN,MessageUtil.LOGIN_FORBIDDEN_MSG),
    NOT_LOGGED_IN_ERROR(MessageUtil.NOT_LOGGED_IN_ERROR, MessageUtil.NOT_LOGGED_IN_ERROR_MSG);

    private int status;
    private String message;

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return message;
    }

    private MessageCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
