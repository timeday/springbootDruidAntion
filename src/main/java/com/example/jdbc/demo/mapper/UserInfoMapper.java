package com.example.jdbc.demo.mapper;

import com.example.jdbc.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserInfoMapper {
    List<User> findUserList();
}
