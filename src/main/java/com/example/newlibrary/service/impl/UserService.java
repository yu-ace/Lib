package com.example.newlibrary.service.impl;

import com.example.newlibrary.dao.IUserDao;
import com.example.newlibrary.model.User;
import com.example.newlibrary.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    IUserDao userDao;

    @Override
    public void newReader(String name, String password) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setIdentity(2);
        user.setIsDelete(0);
        userDao.save(user);
    }

    @Override
    public void newUser(String name, String password) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setIdentity(1);
        user.setIsDelete(0);
        userDao.save(user);
    }

    @Override
    public User getUserById(int id) {
        return userDao.findById(id);
    }

    @Override
    public User getUserByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public void changePassword(String name, String password) {
        User user = userDao.findByName(name);
        user.setPassword(password);
        userDao.save(user);
    }

    @Override
    public void deleteUser(int id) {
        User user = userDao.findById(id);
        user.setIsDelete(1);
        userDao.save(user);
    }

    @Override
    public void changeIsDelete(String name) {
        User user = userDao.findByName(name);
        user.setIsDelete(0);
        userDao.save(user);
    }

    @Override
    public Page<User> getUserList(int isDelete, Pageable pageable) {
        return userDao.findByIsDelete(isDelete,pageable);
    }

    @Override
    public void changeName(String name,String newName) {
        User user = userDao.findByName(name);
        user.setName(newName);
        userDao.save(user);
    }
}
