package com.ebook.backend.dao;

public interface OrderItemDao {
    void addOrderItem(Integer orderId, Integer bookId, Integer purchaseNumber);
}
