package com.ebook.backend.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "bookimage")
public class BookImage {
    @Id
    private int id;

    private String bookname;
    @Field("imageBase64")
    private String ImageBase64;

    public BookImage(int id,String bookname, String ImageBase64) {
        this.id = id;
        this.bookname=bookname;
        this.ImageBase64 = ImageBase64;
    }

    public String getImageBase64() {
        return ImageBase64;
    }

    public void setImageBase64(String ImageBase64) {
        this.ImageBase64 = ImageBase64;
    }
}
