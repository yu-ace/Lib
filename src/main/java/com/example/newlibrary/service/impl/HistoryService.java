package com.example.newlibrary.service.impl;

import com.example.newlibrary.dao.IHistoryDao;
import com.example.newlibrary.model.History;
import com.example.newlibrary.service.IHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HistoryService implements IHistoryService {

    @Autowired
    IHistoryDao historyDao;

    @Override
    public void addBookHistory(int bookId, int userId, int count) {
        History history = new History();
        history.setBookId(bookId);
        history.setUserId(userId);
        history.setType(0);
        history.setCount(count);
        history.setTime(new Date());
        historyDao.save(history);
    }

    @Override
    public void borrowBook(int bookId, int userId) {
        History history = new History();
        history.setBookId(bookId);
        history.setUserId(userId);
        history.setType(1);
        history.setCount(1);
        history.setTime(new Date());
        historyDao.save(history);
    }

    @Override
    public void returnBook(int bookId, int userId) {
        History history = new History();
        history.setBookId(bookId);
        history.setUserId(userId);
        history.setType(2);
        history.setCount(1);
        history.setTime(new Date());
        historyDao.save(history);
    }

    @Override
    public Page<History> getHistoryList(Pageable pageable) {
        return historyDao.findAll(pageable);
    }

    @Override
    public Page<History> getHistoryListByUserId(int id, Pageable pageable) {
        return historyDao.findByUserId(id,pageable);
    }

    @Override
    public Page<History> getHistoryListByBookId(int id, Pageable pageable) {
        return historyDao.findByBookId(id,pageable);
    }

    @Override
    public Page<History> getHistoryListByType(int type, Pageable pageable) {
        return historyDao.findByType(type,pageable);
    }
}
