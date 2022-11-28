package com.example.newlibrary.controller;

import com.example.newlibrary.model.User;
import com.example.newlibrary.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    IUserService userService;

    @RequestMapping(path = "/login",method = RequestMethod.POST)
    public String login(
            @RequestParam(name = "name")
            String name,
            @RequestParam(name = "password")
            String password, Model model, HttpSession session){
        User user = userService.getUserByName(name);
        if(user == null){
            model.addAttribute("error","该用户不存在");
            return "login";
        }
        if(user.getIsDelete() == 1){
            model.addAttribute("error","该用户已注销，请重新注册");
            return "login";
        }
        if(user.getPassword().equals(password)){
            if(user.getIdentity() == 1){
                session.setAttribute("user",user);
                return "redirect:/userBoard";
            }else{
                session.setAttribute("user",user);
                return "redirect:/readerBoard";
            }
        }else{
            model.addAttribute("error","密码错误");
            return "login";
        }
    }

    @RequestMapping(path = "/register",method = RequestMethod.POST)
    public String register(
            @RequestParam(name = "name")
            String name,
            @RequestParam(name = "password")
            String password, Model model){
        User user = userService.getUserByName(name);
        if(user == null) {
            userService.newReader(name,password);
            model.addAttribute("error","注册成功，请登录");
            return "login";
        }
        if(user.getIdentity() != 2){
            model.addAttribute("error","该用户名为管理员，你没有权限激活");
            return "register";
        }
        if(user.getIsDelete() == 1){
            if(user.getPassword().equals(password)){
                userService.changeIsDelete(name);
                model.addAttribute("error","该账户已重新激活");
                return "login";
            }else{
                model.addAttribute("error","该账户早已注销，本次密码错误，激活失败");
                return "register";
            }
        }else{
            model.addAttribute("error","该用户名已存在，注册失败");
            return "register";
        }
    }

    @RequestMapping(path = "/userListPage",method = RequestMethod.POST)
    public String userListPage(
            @RequestParam(name = "number")
            int n,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        PageRequest of = PageRequest.of(n,10);
        Page<User> userPage = userService.getUserList(0,of);
        model.addAttribute("userList",userPage);
        return "userList";
    }

    @RequestMapping(path = "/userById",method = RequestMethod.POST)
    public String userById(
            @RequestParam(name = "id")
            int id,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        User user1 = userService.getUserById(id);
        if(user1 == null){
            model.addAttribute("tip","该用户不存在");
            return "userList";
        }else{
            model.addAttribute("userList",user1);
            return "userList";
        }
    }

    @RequestMapping(path = "/userByName",method = RequestMethod.POST)
    public String userByName(
            @RequestParam(name = "name")
            String name,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        User user1 = userService.getUserByName(name);
        if(user1 == null){
            model.addAttribute("tip","该用户不存在");
            return "userList";
        }else{
            model.addAttribute("userList",user1);
            return "userList";
        }
    }

    @RequestMapping(path = "/addUser",method = RequestMethod.POST)
    public String addUser(
            @RequestParam(name = "name")
            String name,
            @RequestParam(name = "password")
            String password, Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        User user1 = userService.getUserByName(name);
        if(user1 == null) {
            userService.newUser(name,password);
            model.addAttribute("tip","添加成功");
            return "addUser";
        }
        if(user1.getIsDelete() == 1){
            if(user1.getPassword().equals(password)){
                userService.changeIsDelete(name);
                model.addAttribute("tip","该账户已重新激活");
                return "addUser";
            }else{
                model.addAttribute("tip","该账户早已注销，本次密码错误，激活失败");
                return "addUser";
            }
        }else{
            model.addAttribute("tip","该用户名已存在，注册失败");
            return "addUser";
        }
    }

    @RequestMapping(path = "/delete",method = RequestMethod.POST)
    public String delete(
            @RequestParam(name = "id")
            int id,
            @RequestParam(name = "password")
            String password,
            Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        User user1 = userService.getUserById(id);
        if(user1 == null){
            model.addAttribute("tip","该用户不存在");
            return "delete";
        }
        if(user1.getName().equals("admin")){
            model.addAttribute("tip","该用户为超级管理员，无法删除");
            return "delete";
        }
        if(user1.getIsDelete() == 1){
            model.addAttribute("tip","该用户早已注销，无需删除");
            return "delete";
        }
        if(user1.getPassword().equals(password)){
            userService.deleteUser(id);
            model.addAttribute("tip","删除成功");
            return "delete";
        }else{
            model.addAttribute("tip","密码错误，删除失败");
            return "delete";
        }
    }

    @RequestMapping(path = "/change",method = RequestMethod.POST)
    public String change(
            @RequestParam(name = "name")
            String name,
            @RequestParam(name = "password")
            String password,
            @RequestParam(name = "newPassword")
            String newPassword,
            @RequestParam(name = "newPassword1")
            String newPassword1,
            Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        User user1 = userService.getUserByName(name);
        if(user1 == null){
            model.addAttribute("tip","该用户不存在");
            return "change";
        }
        if(user1.getName().equals("admin")){
            model.addAttribute("tip","该用户为超级管理员，无法更改密码");
            return "change";
        }
        if(user1.getIsDelete() == 1){
            model.addAttribute("tip","该用户早已注销，无法更改密码");
            return "change";
        }
        if(user1.getPassword().equals(password)){
            if(newPassword.equals(newPassword1) && !newPassword.equals(password)){
                userService.changePassword(name,newPassword);
                model.addAttribute("tip","更改成功");
                return "change";
            }else if(newPassword.equals(password) || newPassword1.equals(password)){
                model.addAttribute("tip","新密码和旧密码相同，更改失败");
                return "change";
            }else{
                model.addAttribute("tip","两次新密码不同，更改失败");
                return "change";
            }
        }else{
            model.addAttribute("tip","密码错误，更改失败");
            return "change";
        }
    }


    @RequestMapping(path = "/changePassword",method = RequestMethod.POST)
    public String changePassword(
            @RequestParam(name = "name")
            String name,
            @RequestParam(name = "password")
            String password,
            @RequestParam(name = "newPassword")
            String newPassword,
            @RequestParam(name = "newPassword1")
            String newPassword1,
            Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        User user1 = userService.getUserByName(name);
        if(user1 == null){
            model.addAttribute("tip","该用户不存在");
            return "changePassword";
        }
        if(user1.getName().equals("admin")){
            model.addAttribute("tip","该用户为超级管理员，无法更改密码");
            return "changePassword";
        }
        if(user1.getIsDelete() == 1){
            model.addAttribute("tip","该用户早已注销，无法更改密码");
            return "changePassword";
        }
        if(user1.getIdentity() == 1){
            model.addAttribute("tip","该用户为管理员，你没有权限更改密码");
            return "changePassword";
        }
        if(user1.getPassword().equals(password)){
            if(newPassword.equals(newPassword1) && !newPassword.equals(password)){
                userService.changePassword(name,newPassword);
                model.addAttribute("tip","更改成功");
                return "changePassword";
            }else if(newPassword.equals(password) || newPassword1.equals(password)){
                model.addAttribute("tip","新密码和旧密码相同，更改失败");
                return "changePassword";
            }else{
                model.addAttribute("tip","两次新密码不同，更改失败");
                return "changePassword";
            }
        }else{
            model.addAttribute("tip","密码错误，更改失败");
            return "changePassword";
        }
    }

    @RequestMapping(path = "/changeName",method = RequestMethod.POST)
    public String changeName(
            @RequestParam(name = "name")
            String name,
            @RequestParam(name = "newName")
            String newName,
            @RequestParam(name = "password")
            String password,Model model,HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        User user1 = userService.getUserByName(name);
        if(user1 ==null){
            model.addAttribute("tip","该用户不存在");
            return "changeName";
        }
        if(user1.getName().equals("admin")){
            model.addAttribute("tip","该用户为超级管理员，无法更改用户名");
            return "changeName";
        }
        if(user1.getIsDelete() == 1){
            model.addAttribute("tip","该用户早已注销，无法更改用户名");
            return "changeName";
        }
        if(user1.getIdentity() == 1){
            model.addAttribute("tip","该用户为管理员，你没有权限更改用户名");
            return "changeName";
        }
        if(user1.getPassword().equals(password)){
            userService.changeName(name,newName);
            model.addAttribute("tip","更改成功");
            return "changeName";
        }else{
            model.addAttribute("tip","密码错误");
            return "changeName";
        }
    }
}
