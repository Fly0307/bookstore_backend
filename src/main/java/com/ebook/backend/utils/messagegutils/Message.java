package com.ebook.backend.utils.messagegutils;

import net.sf.json.JSONObject;


public class Message {
    private int status;
    private String msg;
    private JSONObject data;

    Message(MessageCode msg, JSONObject data){
        this.status = msg.getStatus();
        this.msg = msg.getMsg();
        this.data = data;
    }

    Message(MessageCode msg, String extra, JSONObject data){
        this.status = msg.getStatus();
        this.msg = extra;
        this.data = data;
    }

    Message(MessageCode msg){
        this.status = msg.getStatus();
        this.msg = msg.getMsg();
        this.data = null;
    }

    Message(MessageCode msg, String extra){
        this.status = msg.getStatus();
        this.msg = extra;
        this.data = null;
    }

    Message(int status, String extra, JSONObject data){
        this.status = status;
        this.msg = extra;
        this.data = data;
    }

    Message(int status, String extra){
        this.status = status;
        this.msg = extra;
        this.data = null;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }






}
