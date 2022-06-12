package com.ebook.backend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

//@SuppressWarnings("ALL")
@Data
@Entity
//@Table(name = "order_item")
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "fieldHandler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class OrderItem {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer id;

    private Integer bookId;
    private Integer orderId;

//    @SuppressWarnings("JpaModelReferenceInspection")
    @MapsId("bookId")
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bookId", referencedColumnName = "book_id")
    private Book book;

    private Integer purchaseNumber;
    private Integer price;
}

