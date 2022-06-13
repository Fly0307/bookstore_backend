package com.ebook.backend.serviceimpl;

import com.ebook.backend.dao.BookDao;
import com.ebook.backend.dao.UserDao;
import com.ebook.backend.dao.UserOrderDao;
import com.ebook.backend.entity.Book;
import com.ebook.backend.entity.OrderItem;
import com.ebook.backend.entity.User;
import com.ebook.backend.entity.UserOrder;
import com.ebook.backend.service.StatisticsService;
import com.ebook.backend.utils.sessionutils.SessionUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class StatisticsServiceimpl implements StatisticsService {
    @Autowired
   UserOrderDao userOrderDao;
    @Autowired
    BookDao bookDao;
    @Autowired
    UserDao userDao;
    @Override
    public JSONArray getBookConsumption(Date start, Date end) {
        List<UserOrder> allUserOrder =userOrderDao.getOrderByDate(start,end);
        List<Book> books =bookDao.manageBooks();
        JSONArray ret = new JSONArray();
        HashMap<Integer,Integer> idToConsumption = new HashMap<>();

        for (UserOrder order : allUserOrder) {
            Set<OrderItem> items= order.getOrders();
            for (OrderItem item : items) {
                if(idToConsumption.containsKey(item.getBookId())) {
                    Integer sales=idToConsumption.get(item.getBookId());
                   idToConsumption.put(item.getBookId(),sales+item.getPurchaseNumber());
                }
                else{
                    idToConsumption.put(item.getBookId(),item.getPurchaseNumber());
                }

            }
        }

        for (Book book : books) {
            if(idToConsumption.containsKey(book.getBookId())){
                JSONObject obj = JSONObject.fromObject(book);
                obj.put("consumption",idToConsumption.get(book.getBookId()));
                ret.add(obj);
            }
            else {
                JSONObject obj = JSONObject.fromObject(book);
                obj.put("consumption",0);
                ret.add(obj);
            }
        }
        ret.sort(
                new Comparator<JSONObject>() {
                    @Override
                    public int compare(JSONObject o1, JSONObject o2) {
                        int a = o1.getInt("consumption");
                        int b = o2.getInt("consumption");
                        if (a > b) {
                            return -1;
                        } else if(a == b) {
                            return 0;
                        } else
                            return 1;
                    }
                }
        );
        return ret;
    }

    @Override
    public JSONArray getUserConsumption(Date start, Date end) {
        List<UserOrder> allUserOrder =userOrderDao.getOrderByDate(start,end);
        List<User> allUser = userDao.getAll();
        JSONArray ret = new JSONArray();
        HashMap<Integer,BigDecimal> idToConsumption = new HashMap<>();


        for (UserOrder order : allUserOrder) {
            Set<OrderItem> items = order.getOrders();
            Integer userId = order.getUserId();
            for (OrderItem item : items) {
                BigDecimal sale = new BigDecimal(item.getPurchaseNumber().doubleValue());
                BigDecimal price = BigDecimal.valueOf(item.getBook().getPrice());
                if (idToConsumption.containsKey(userId)) {
                    BigDecimal lastConsumption = idToConsumption.get(userId);
                    idToConsumption.put(userId, lastConsumption.add(price.multiply(sale)));
                } else {
                    idToConsumption.put(userId, price.multiply(sale));
                }
            }
        }

        for (User user : allUser) {
            if(idToConsumption.containsKey(user.getUserId())){
                JSONObject obj = JSONObject.fromObject(user);
                obj.put("consumption",idToConsumption.get(user.getUserId()));
                ret.add(obj);
            }
            else {
                JSONObject obj = JSONObject.fromObject(user);
                obj.put("consumption",0);
                ret.add(obj);
            }
        }
        ret.sort(
                new Comparator<JSONObject>() {
                    @Override
                    public int compare(JSONObject o1, JSONObject o2) {
                        int a = o1.getInt("consumption");
                        int b = o2.getInt("consumption");
                        if (a > b) {
                            return -1;
                        } else if(a == b) {
                            return 0;
                        } else
                            return 1;
                    }
                }
        );
        return ret;
    }

    @Override
    public JSONObject userToBook(Date start, Date end) {
        Integer userId = SessionUtil.getUserId();
        JSONObject ret = new JSONObject();
        JSONArray retBooks = new JSONArray();
        List<Book> books =bookDao.manageBooks();
        List<UserOrder> onesOrder = userOrderDao.getOnesOrder(start,end,userId);
        HashMap<Integer,Integer> idToConsumption = new HashMap<>();
        BigDecimal sumConsumption = new BigDecimal(0);
        int sumBookNumber = 0;
        for (UserOrder order : onesOrder) {
            Set<OrderItem> items= order.getOrders();
            for (OrderItem item : items) {
                BigDecimal price = BigDecimal.valueOf(item.getBook().getPrice());

                if(idToConsumption.containsKey(item.getBookId())) {
                    Integer sales=idToConsumption.get(item.getBookId());
                    idToConsumption.put(item.getBookId(),sales+item.getPurchaseNumber());
                }
                else{
                    idToConsumption.put(item.getBookId(),item.getPurchaseNumber());
                }
                BigDecimal sale = BigDecimal.valueOf(item.getPurchaseNumber().doubleValue());
                sumConsumption=sumConsumption.add(price.multiply(sale));
                sumBookNumber = sumBookNumber+ item.getPurchaseNumber();
            }
        }


        for (Book book : books) {
            if(idToConsumption.containsKey(book.getBookId())){
                JSONObject obj = JSONObject.fromObject(book);
                obj.put("consumption",idToConsumption.get(book.getBookId()));
                retBooks.add(obj);
            }
        }
        ret.put("books",retBooks);
        ret.put("sumConsumption",sumConsumption.doubleValue());
        ret.put("sumBookNumber",sumBookNumber);
        return ret;
    }
}
