package com.ebook.backend.controller;

import com.ebook.backend.service.BookService;
import com.ebook.backend.service.StatisticsService;
import com.ebook.backend.service.UserOrderService;
import com.ebook.backend.utils.sessionutils.SessionUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


@RestController
public class StatisticsController {
    @Autowired
    BookService bookService;
    UserOrderService userOrderService;
    @Autowired
    StatisticsService statisticsService;



    @Autowired
    void setUserOrderService(UserOrderService userOrderService) {
        this.userOrderService = userOrderService;
    }

    @RequestMapping("/manage/getBookConsumption")
    public JSONArray getBookConsumption(@RequestBody Map<String, String> params) throws ParseException {
        Integer type = SessionUtil.getUserType();
        if(type>0){
            String startT = params.get("start");
            String endT =params.get("end");
            if(!startT.equals("")&&!endT.equals("")){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date start = sdf.parse(startT);
                SimpleDateFormat sbf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date end = sbf.parse(endT);
                return statisticsService.getBookConsumption(start,end);
            }
            return statisticsService.getBookConsumption(new Date(0),new Date());
        }
        return null;
    }

    @RequestMapping("/manage/getUserConsumption")
    public JSONArray getUserConsumption(@RequestBody Map<String, String> params) throws ParseException {
        Integer type = SessionUtil.getUserType();
        if(type>0){
            String startT = params.get("start");
            String endT =params.get("end");
            if(!startT.equals("")&&!endT.equals("")){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date start = sdf.parse(startT);
                SimpleDateFormat sbf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date end = sbf.parse(endT);
                return statisticsService.getUserConsumption(start,end);
            }
            return statisticsService.getUserConsumption(new Date(0),new Date());
        }
        return null;
    }

    @RequestMapping("/userToBook")
    public JSONObject userToBook(@RequestBody Map<String, String> params) throws ParseException {
            String startT = params.get("start");
            String endT =params.get("end");
            if(!startT.equals("")&&!endT.equals("")){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date start = sdf.parse(startT);
                SimpleDateFormat sbf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date end = sbf.parse(endT);
                return statisticsService.userToBook(start,end);
            }
            return statisticsService.userToBook(new Date(0),new Date());

    }
}
