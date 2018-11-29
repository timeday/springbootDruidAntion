package com.example.jdbc.demo.controller;

import com.example.jdbc.demo.entity.Order;
import com.example.jdbc.demo.entity.User;
import com.example.jdbc.demo.service.OrderService;
import com.example.jdbc.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommonController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @RequestMapping("/userList")
    public Object getList(){
        List<User> userList = userService.list();
        return userList;
    }
    @RequestMapping("/orderList")
    public Object getorderList(){
        List<Order> orderList = orderService.list();
        return orderList;
    }

}
