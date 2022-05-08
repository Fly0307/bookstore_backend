package com.ebook.backend.service;

public interface CartService {
    void deleteBook( Integer cartId);

    void modifyCart(Integer bookId,Integer newPurchaseNum);
}
