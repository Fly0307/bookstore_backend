package com.ebook.backend.repository;

import com.ebook.backend.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface CartRepository extends JpaRepository<Cart, Integer> {
    //@Query("ins  cart (book_id,user_id,purchase_num) values (?1,?2,?3)")

    @Query("from Cart where userId=:userid ")
    List<Cart> getCart(Integer userid);

    @Modifying
    @Query("delete from Cart where  userId=?1 and bookId=?2")
    void deleteBook(Integer userId,Integer bookId);

    @Modifying
    @Query("update Cart set purchaseNum = ?2  where bookId=?1 and userId =?3")
    void modifyCart(Integer bookId,Integer newPurchaseNum ,Integer userId);

    @Modifying
    @Query(value = "insert into cart (cart_id,user_id,book_id,purchase_num) values (default ,?1,?2,?3)",nativeQuery = true)
    void addNewCart(Integer userId, Integer bookId, Integer PurchaseNum);
}
