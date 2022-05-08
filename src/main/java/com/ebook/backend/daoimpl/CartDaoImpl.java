package com.ebook.backend.daoimpl;

import com.ebook.backend.dao.CartDao;
import com.ebook.backend.repository.CartRepository;
import com.ebook.backend.utils.sessionutils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class CartDaoImpl implements CartDao {

    @Autowired
    CartRepository cartRepository;

    @Override
    public void deleteBook(Integer bookId) {
        Integer userId = SessionUtil.getUserId();
        cartRepository.deleteBook(userId,bookId);
    }

    @Override
    public void modifyCart(Integer bookId, Integer newPurchaseNum) {
        Integer userId = SessionUtil.getUserId();
        cartRepository.modifyCart(bookId,newPurchaseNum,userId);
    }


}
