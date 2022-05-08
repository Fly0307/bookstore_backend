package com.ebook.backend.repository;
import com.ebook.backend.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Modifying
    @Query(value = "insert into cart (book_id,user_id,purchase_num) values (?1,?2,?3)", nativeQuery = true)
    void add2repository(Integer id, Integer userid,Integer num);

    @Query(value = "FROM Cart WHERE userId =:userid")
    List<Cart> getCart(@Param("userid") Integer userid);

    @Modifying
    @Query(value = "delete from Cart where  userId=?1 and bookId=?2")
    void deleteBook(Integer userId,Integer bookid);

    @Modifying
    @Query(value = "update Cart set purchaseNum = ?2  where bookId=?1 and userId =?3")
    void modifyCart(Integer bookId,Integer newPurchaseNum ,Integer userId);


}
