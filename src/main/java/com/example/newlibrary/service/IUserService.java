package com.example.newlibrary.service;

import com.example.newlibrary.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    void newReader(String name,String password);
    void newUser(String name,String password);
    User getUserById(int id);
    User getUserByName(String name);
    void changePassword(String name,String password);
    void deleteUser(int id);
    void changeIsDelete(String name);
    Page<User> getUserList(int isDelete,Pageable pageable);
    void changeName(String name,String newName);
}
