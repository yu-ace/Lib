package com.example.newlibrary.dao;

import com.example.newlibrary.model.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHistoryDao extends JpaRepository<History,Integer> {
    Page<History> findByBookId(int id, Pageable pageable);
    Page<History> findByUserId(int id,Pageable pageable);
    Page<History> findByType(int type,Pageable pageable);
}
