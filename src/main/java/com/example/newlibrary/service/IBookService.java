package com.example.newlibrary.service;

import com.example.newlibrary.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBookService {
    void newBook(String name,double price,int count,String anther,String category);
    Page<Book> getBookList(Pageable pageable);
    Page<Book> getBookListByAnther(String anther,Pageable pageable);
    Page<Book> getBookListByCategory(String category,Pageable pageable);
    Book getBookById(int id);
    Book getBookByName(String name);
    void borrowBook(int id);
    void returnBook(int id);
}
