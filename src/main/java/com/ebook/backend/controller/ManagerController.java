package com.ebook.backend.controller;

import com.ebook.backend.service.BookService;
import com.ebook.backend.service.UserOrderService;
import com.ebook.backend.service.UserService;
import com.ebook.backend.utils.messagegutils.Message;
import com.ebook.backend.utils.messagegutils.MessageUtil;
import com.ebook.backend.utils.sessionutils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ManagerController {
    @Autowired
    UserService userService;

    @Autowired
    UserOrderService userOrderService;

    @Autowired
    BookService bookService;

    @RequestMapping("/modifyBook")
    Message modifyBook(@RequestBody Map<String, String> params) {
        if(SessionUtil.getUserType()<=0) return MessageUtil.makeMsg(-1,"无权访问") ;

        return bookService.modifyBook(params);
    }
}
