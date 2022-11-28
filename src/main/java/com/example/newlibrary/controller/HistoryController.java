package com.example.newlibrary.controller;

import com.example.newlibrary.model.History;
import com.example.newlibrary.model.User;
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
public class HistoryController {

    @Autowired
    IHistoryService historyService;

    @RequestMapping(path = "/historyPage",method = RequestMethod.POST)
    public String history(
            @RequestParam(name = "number")
            int n,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        PageRequest of = PageRequest.of(n,10);
        Page<History> historyPage = historyService.getHistoryList(of);
        model.addAttribute("history",historyPage);
        return "history";
    }

    @RequestMapping(path = "/historyByBookId",method = RequestMethod.POST)
    public String historyByBookId(
            @RequestParam(name = "id")
            int id,
            @RequestParam(name = "number")
            int n,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        PageRequest of = PageRequest.of(n,10);
        Page<History> historyPage = historyService.getHistoryListByBookId(id,of);
        model.addAttribute("history",historyPage);
        return "history";
    }

    @RequestMapping(path = "/historyByUserId",method = RequestMethod.POST)
    public String historyByUserId(
            @RequestParam(name = "userId")
            int id,
            @RequestParam(name = "number")
            int n,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        PageRequest of = PageRequest.of(n,10);
        Page<History> historyPage1 = historyService.getHistoryListByUserId(id,of);
        model.addAttribute("history",historyPage1);
        return "history";
    }

    @RequestMapping(path = "/historyByType",method = RequestMethod.POST)
    public String historyByType(
            @RequestParam(name = "type")
            int type,
            @RequestParam(name = "number")
            int n,Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        PageRequest of = PageRequest.of(n,10);
        Page<History> historyPage = historyService.getHistoryListByType(type,of);
        model.addAttribute("history",historyPage);
        return "history";
    }
}
