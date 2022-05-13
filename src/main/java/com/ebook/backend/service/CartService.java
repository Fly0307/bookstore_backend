package com.ebook.backend.service;

import com.ebook.backend.utils.messagegutils.Message;

public interface CartService {
    void deleteBook( Integer cartId);
    void modifyCart(Integer bookId,Integer newPurchaseNum);
    Message addNewCart(Integer userid, Integer bookId, Integer PurchaseNum);
}
