package com.example.newlibrary.model;


import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "book")
@DynamicUpdate
public class Book {
    @Id
    @Column(name= "id")
    int id;
    @Column(name = "name")
    String name;
    @Column(name = "price")
    double price;
    @Column(name = "count")
    int count;
    @Column(name = "anther")
    String anther;
    @Column(name = "category")
    String category;
    @Column(name = "initial_count")
    int initialCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getAnther() {
        return anther;
    }

    public void setAnther(String anther) {
        this.anther = anther;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getInitialCount() {
        return initialCount;
    }

    public void setInitialCount(int initialCount) {
        this.initialCount = initialCount;
    }
}
