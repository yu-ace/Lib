package com.example.newlibrary.controller;

import com.example.newlibrary.model.Book;
import com.example.newlibrary.model.History;
import com.example.newlibrary.model.User;
import com.example.newlibrary.service.IBookService;
import com.example.newlibrary.service.IHistoryService;
import com.example.newlibrary.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class PageController {

    @Autowired
    IHistoryService historyService;
    @Autowired
    IUserService userService;
    @Autowired
    IBookService bookService;

    @RequestMapping(path = "/",method = RequestMethod.GET)
    public String index(){
        return "login";
    }

    @RequestMapping(path = "/index",method = RequestMethod.GET)
    public String index1(){
        return "login";
    }

    @RequestMapping(path = "/login",method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(path = "/register",method = RequestMethod.GET)
    public String register(){
        return "register";
    }

    @RequestMapping(path = "/userBoard",method = RequestMethod.GET)
    public String userBoard(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        model.addAttribute("user",user);
        return "userBoard";
    }

    @RequestMapping(path = "/addBook",method = RequestMethod.GET)
    public String addBook(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        return "newBook";
    }

    @RequestMapping(path = "/history",method = RequestMethod.GET)
    public String history(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        PageRequest of = PageRequest.of(0,10);
        Page<History> historyPage = historyService.getHistoryList(of);
        model.addAttribute("history",historyPage);
        return "history";
    }

    @RequestMapping(path = "/userList",method = RequestMethod.GET)
    public String userList(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        PageRequest of = PageRequest.of(0,10);
        Page<User> userPage = userService.getUserList(0,of);
        model.addAttribute("userList",userPage);
        return "userList";
    }

    @RequestMapping(path = "/addUser",method = RequestMethod.GET)
    public String addUser(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        return "addUser";
    }

    @RequestMapping(path = "/delete",method = RequestMethod.GET)
    public String delete(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        return "delete";
    }

    @RequestMapping(path = "/change",method = RequestMethod.GET)
    public String change(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        return "change";
    }

    @RequestMapping(path = "/readerBoard",method = RequestMethod.GET)
    public String readerBoard(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        model.addAttribute("user",user);
        return "readerBoard";
    }



    @RequestMapping(path = "/bookList",method = RequestMethod.GET)
    public String bookList(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        PageRequest of = PageRequest.of(0,10);
        Page<Book> bookPage =bookService.getBookList(of);
        model.addAttribute("bookList",bookPage);
        return "bookList";
    }

    @RequestMapping(path = "/borrowBook",method = RequestMethod.GET)
    public String borrowBook(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        return "borrowBook";
    }

    @RequestMapping(path = "/returnBook",method = RequestMethod.GET)
    public String returnBook(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        return "returnBook";
    }

    @RequestMapping(path = "/changePassword",method = RequestMethod.GET)
    public String changePassword(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        return "changePassword";
    }

    @RequestMapping(path = "/changeName",method = RequestMethod.GET)
    public String changeName(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        return "changeName";
    }
}
