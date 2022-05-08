package com.ebook.backend.repository;

import com.ebook.backend.entity.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface UserOrderRepository extends JpaRepository<UserOrder,Integer> {
    @Query(value = "from UserOrder where userId = ?1")
    ArrayList<UserOrder> getAllOrders(Integer userId);

    @Query(value = "from UserOrder where orderTime between ?1 and ?2")
    List<UserOrder> getOrderByDate(Date start, Date end);

    @Query(value = "from UserOrder where orderTime between ?2 and ?3 and userId = ?1")
    List<UserOrder> getOrdersInRange(Integer userId, Date start, Date end);

}
