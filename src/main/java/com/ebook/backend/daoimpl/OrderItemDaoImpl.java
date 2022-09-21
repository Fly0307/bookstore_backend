package com.ebook.backend.daoimpl;

import com.ebook.backend.dao.OrderItemDao;
import com.ebook.backend.entity.Book;
import com.ebook.backend.entity.OrderItem;
import com.ebook.backend.repository.BookRepository;
import com.ebook.backend.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class OrderItemDaoImpl implements OrderItemDao {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    OrderItemRepository orderItemRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void addOrderItem(Integer orderId, Integer bookId, Integer purchaseNumber) {
        OrderItem orderItem = new OrderItem();
        Book book=bookRepository.getBookById(bookId);
        orderItem.setOrderId(orderId);
        orderItem.setPurchaseNumber(purchaseNumber);
        orderItem.setBookId(bookId);
        orderItem.setPrice(book.getPrice());
         int result =10/0;
        orderItemRepository.save(orderItem);
    }
}
