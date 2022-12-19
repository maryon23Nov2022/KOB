package com.example.backend.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.Mapper.UserMapper;
import com.example.backend.Service.Impl.Utilities.UserDetailsImpl;
import com.example.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", username);
        User user = userMapper.selectOne(queryWrapper);
        if(user == null){
            throw new RuntimeException("No such user");
        }
        return new UserDetailsImpl(user);
    }
}
