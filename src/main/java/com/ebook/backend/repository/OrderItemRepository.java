package com.ebook.backend.repository;


import com.ebook.backend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

}
