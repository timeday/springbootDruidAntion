package com.example.jdbc.demo;

import com.example.jdbc.demo.config.DataSourceContextHolder;
import com.example.jdbc.demo.config.DataSourceKey;
import com.example.jdbc.demo.entity.Order;
import com.example.jdbc.demo.entity.User;
import com.example.jdbc.demo.service.OrderService;
import com.example.jdbc.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.List;
import java.util.Objects;

//开启aop切面
@EnableAspectJAutoProxy(proxyTargetClass=true)
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Override
    public void run(String... args) throws Exception {

        DataSourceContextHolder.setDBType(DataSourceKey.ZHWDEMO.name());
        List<User> userList = userService.list();
        userList.stream().filter(Objects::nonNull).forEach(user -> {
            System.out.println(user.toString());
        });

        DataSourceContextHolder.setDBType(DataSourceKey.WSS.name());
        List<Order> orderList = orderService.list();
        orderList.stream().filter(Objects::nonNull).forEach(order -> {
            System.out.println(order.toString());
        });
    }
}
