package com.ebook.backend.serviceimpl;

import com.ebook.backend.dao.CartDao;
import com.ebook.backend.service.CartService;
import com.ebook.backend.utils.messagegutils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceimpl implements CartService {
    @Autowired
    CartDao  cartDao;
    
    @Override
    public void deleteBook(Integer bookId) {
        cartDao.deleteBook(bookId);
    }

    @Override
    public void modifyCart(Integer bookId, Integer newPurchaseNum) {
        cartDao.modifyCart(bookId, newPurchaseNum);
    }

    @Override
    public Message addNewCart(Integer userid, Integer bookId, Integer PurchaseNum) {
        cartDao.addNewCart(userid,bookId,PurchaseNum);
        return null;
    }
}
