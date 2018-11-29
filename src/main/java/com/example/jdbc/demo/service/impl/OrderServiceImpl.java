package com.example.jdbc.demo.service.impl;

import com.example.jdbc.demo.config.DataSourceKey;
import com.example.jdbc.demo.config.TargetDataSource;
import com.example.jdbc.demo.entity.Order;
import com.example.jdbc.demo.mapper.OrderInfoMapper;
import com.example.jdbc.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderInfoMapper orderMapper;


    @Override
    @TargetDataSource(dataSourceKey = DataSourceKey.WSS)
    public List<Order> list() {
        return orderMapper.findOrderList();
    }
}
