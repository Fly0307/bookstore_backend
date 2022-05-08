package com.ebook.backend.serviceimpl;

import com.ebook.backend.dao.CartDao;
import com.ebook.backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("session")
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
}
