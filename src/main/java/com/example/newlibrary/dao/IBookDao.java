package com.example.newlibrary.dao;


import com.example.newlibrary.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookDao extends JpaRepository<Book,Integer> {
    Book findById(int id);
    Book findByName(String name);
    Page<Book> findByCategory(String category, Pageable pageable);
    Page<Book> findByAnther(String anther,Pageable pageable);
}
