package com.example.newlibrary.service;

import com.example.newlibrary.model.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IHistoryService {
    void addBookHistory(int bookId,int userId,int count);
    void borrowBook(int bookId,int userId);
    void returnBook(int bookId,int userId);
    Page<History> getHistoryList(Pageable pageable);
    Page<History> getHistoryListByUserId(int id,Pageable pageable);
    Page<History> getHistoryListByBookId(int id,Pageable pageable);
    Page<History> getHistoryListByType(int type,Pageable pageable);
}
