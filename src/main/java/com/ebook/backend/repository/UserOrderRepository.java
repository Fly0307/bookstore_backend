package com.ebook.backend.repository;

import com.ebook.backend.entity.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Transactional
public interface UserOrderRepository extends JpaRepository<UserOrder,Integer> {
    @Query("from UserOrder where userId = ?1")
    ArrayList<UserOrder> getAllOrders(Integer userId);

    @Query(value = "from UserOrder where orderTime between ?1 and ?2")
    List<UserOrder> getOrderByDate(Date start, Date end);

    @Query(value = "from UserOrder where orderTime between ?2 and ?3 and userId = ?1")
    List<UserOrder> getOrdersInRange(Integer userId, Date start, Date end);

    @Query(value = "from UserOrder where orderTel=?2 and userId = ?1")
    UserOrder getUserOrderByUserIdAndOrderTel(Integer userId,String OrderTel);

    //    @Modifying
//    @Query("delete from UserOrder where orderId=?1")
//    void deleteOrderById(Integer orderId);

    @Modifying
    @Query("update UserOrder uo set uo.state = :orderState where uo.orderId = :orderId")
    void updateState(Integer orderId,Integer orderState);
}
