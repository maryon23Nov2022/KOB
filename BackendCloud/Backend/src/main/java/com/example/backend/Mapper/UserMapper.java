package com.example.backend.Mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.backend.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}