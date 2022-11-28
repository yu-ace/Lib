package com.example.newlibrary.service.impl;

import com.example.newlibrary.dao.IBookDao;
import com.example.newlibrary.model.Book;
import com.example.newlibrary.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService implements IBookService {

    @Autowired
    IBookDao bookDao;

    @Override
    public void newBook(String name, double price, int count, String anther, String category) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setCount(count);
        book.setAnther(anther);
        book.setCategory(category);
        book.setInitialCount(count);
        bookDao.save(book);
    }

    @Override
    public Page<Book> getBookList(Pageable pageable) {
        return bookDao.findAll(pageable);
    }

    @Override
    public Page<Book> getBookListByAnther(String anther, Pageable pageable) {
        return bookDao.findByAnther(anther,pageable);
    }

    @Override
    public Page<Book> getBookListByCategory(String category, Pageable pageable) {
        return bookDao.findByCategory(category,pageable);
    }

    @Override
    public Book getBookById(int id) {
        return bookDao.findById(id);
    }

    @Override
    public Book getBookByName(String name) {
        return bookDao.findByName(name);
    }

    @Override
    public void borrowBook(int id) {
        Book book = bookDao.findById(id);
        book.setCount(book.getCount() - 1);
        bookDao.save(book);
    }

    @Override
    public void returnBook(int id) {
        Book book = bookDao.findById(id);
        book.setCount(book.getCount() + 1);
        bookDao.save(book);
    }

}
