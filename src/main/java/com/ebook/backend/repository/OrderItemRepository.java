package com.ebook.backend.repository;


import com.ebook.backend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    @Modifying
    @Query("delete from OrderItem where orderId=?1")
    void deleteOrderById(Integer orderId);

    @Query("FROM OrderItem where orderId=?1")
    List<OrderItem> getOrderItemByOrderId(Integer orderId);
}
