package com.ebook.backend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import com.alibaba.fastjson.JSON;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
//@Table(name = "book")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "bookId")
public class Book {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer bookId;

    private String isbn;
    private String name;
    private String type;
    private String author;
    private Integer price;
    private String description;
    private Integer num;
    private String image;
    private Boolean state;
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
