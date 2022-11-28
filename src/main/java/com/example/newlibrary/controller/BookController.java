package com.example.newlibrary.controller;

import com.example.newlibrary.model.Book;
import com.example.newlibrary.model.User;
import com.example.newlibrary.service.IBookService;
import com.example.newlibrary.service.IHistoryService;
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
public class BookController {

    @Autowired
    IBookService bookService;
    @Autowired
    IHistoryService historyService;

    @RequestMapping(path = "/newBook",method = RequestMethod.POST)
    public String newBook(
            @RequestParam(name = "name")
            String name,
            @RequestParam(name = "price")
            double price,
            @RequestParam(name = "count")
            int count,
            @RequestParam(name = "anther")
            String anther,
            @RequestParam(name = "category")
            String category, Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        bookService.newBook(name,price,count,anther,category);
        Book book = bookService.getBookByName(name);
        historyService.addBookHistory(book.getId(),user.getId(),count);
        model.addAttribute("tip","添加成功");
        return "newBook";
    }


    @RequestMapping(path = "/bookListPage",method = RequestMethod.POST)
    public String bookList(
            @RequestParam(name = "number")
            int n,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        PageRequest of = PageRequest.of(n,10);
        Page<Book> bookPage =bookService.getBookList(of);
        model.addAttribute("bookList",bookPage);
        return "bookList";
    }

    @RequestMapping(path = "/bookListByAnther",method = RequestMethod.POST)
    public String bookListByAnther(
            @RequestParam(name = "anther")
            String anther,
            @RequestParam(name = "number")
            int n,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        PageRequest of = PageRequest.of(n,10);
        Page<Book> bookPage =bookService.getBookListByAnther(anther,of);
        if(bookPage.isEmpty()){
            model.addAttribute("tip","你寻找的书不存在");
            return "bookList";
        }
        model.addAttribute("bookList",bookPage);
        return "bookList";
    }

    @RequestMapping(path = "/bookListByCategory",method = RequestMethod.POST)
    public String bookListByCategory(
            @RequestParam(name = "category")
            String category,
            @RequestParam(name = "number")
            int n,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        PageRequest of = PageRequest.of(n,10);
        Page<Book> bookPage =bookService.getBookListByCategory(category,of);
        if(bookPage.isEmpty()){
            model.addAttribute("tip","你寻找的书不存在");
            return "bookList";
        }
        model.addAttribute("bookList",bookPage);
        return "bookList";
    }

    @RequestMapping(path = "/borrowBook",method = RequestMethod.POST)
    public String borrowBook(
            @RequestParam(name = "id")
            int id,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        Book book = bookService.getBookById(id);
        if(book == null){
            model.addAttribute("tip","本图书馆没有该图书，借阅失败");
            return "borrowBook";
        }
        if(book.getCount() == 0){
            model.addAttribute("tip","该书已全部借出，库存不足");
            return "borrowBook";
        }
        historyService.borrowBook(id, user.getId());
        bookService.borrowBook(id);
        model.addAttribute("tip","借取成功");
        return "borrowBook";
    }

    @RequestMapping(path = "/returnBook",method = RequestMethod.POST)
    public String returnBook(
            @RequestParam(name = "id")
            int id,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        Book book = bookService.getBookById(id);
        if(book == null){
            model.addAttribute("tip","本图书馆没有该图书，归还失败");
            return "returnBook";
        }
        if(book.getCount() == book.getInitialCount()){
            model.addAttribute("tip","本书不是本图书馆的书，归还失败");
            return "returnBook";
        }
        historyService.returnBook(id, user.getId());
        bookService.returnBook(id);
        model.addAttribute("tip","归还成功");
        return "returnBook";
    }
}
