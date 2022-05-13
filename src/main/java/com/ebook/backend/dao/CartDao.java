package com.ebook.backend.dao;

public interface CartDao {
    void deleteBook(Integer bookid);

    void modifyCart(Integer bookId, Integer newPurchaseNum);

    void addNewCart(Integer userId,Integer bookId,Integer PurchaseNum);
}
