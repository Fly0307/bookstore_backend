package com.ebook.backend.utils.messagegutils;

import net.sf.json.JSONObject;


public class Message {
    private int status;
    private String message;
    private JSONObject data;

    Message(MessageCode message, JSONObject data){
        this.status = message.getStatus();
        this.message = message.getMsg();
        this.data = data;
    }

    Message(MessageCode message, String extra, JSONObject data){
        this.status = message.getStatus();
        this.message = extra;
        this.data = data;
    }

    Message(MessageCode message){
        this.status = message.getStatus();
        this.message = message.getMsg();
        this.data = null;
    }
    Message(MessageCode message, String extra){
        this.status = message.getStatus();
        this.message = extra;
        this.data = null;
    }

    Message(int status, String extra, JSONObject data){
        this.status = status;
        this.message = extra;
        this.data = data;
    }

    Message(int status, String extra){
        this.status = status;
        this.message = extra;
        this.data = null;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String message) {
        this.message = message;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }


}
