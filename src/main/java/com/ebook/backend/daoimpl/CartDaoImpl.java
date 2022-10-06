package com.ebook.backend.daoimpl;

import com.ebook.backend.dao.CartDao;
import com.ebook.backend.repository.CartRepository;
import com.ebook.backend.utils.sessionutils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Repository
public class CartDaoImpl implements CartDao {

    @Autowired
    CartRepository cartRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteBook(Integer bookId, Integer userId) {
//        Integer userId = SessionUtil.getUserId();
        cartRepository.deleteBook(userId,bookId);
    }

    @Override
    public void modifyCart(Integer bookId, Integer newPurchaseNum) {
        Integer userId = SessionUtil.getUserId();
        cartRepository.modifyCart(bookId,newPurchaseNum,userId);
    }

    @Override
    public void addNewCart(Integer userId, Integer bookId,Integer PurchaseNum) {
        cartRepository.addNewCart(userId,bookId,PurchaseNum);

    }


}
