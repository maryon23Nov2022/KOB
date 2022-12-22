package com.example.backend.Service.Impl.User.Account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.Mapper.UserMapper;
import com.example.backend.Service.User.Account.RegisterService;
import com.example.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, String> register(String username, String password, String confirmedpassword) {
        Map<String, String> map = new HashMap<>();
        if(username == null || username.length() == 0) {
            map.put("error_message", "Please enter a valid username");
            return map;
        }
        username = username.trim();
        if(username.length() > 10){
            map.put("error_message", "The length of username can't exceed 10 characters");
            return map;
        }
        if(password.length() < 6 || password == null) {
            map.put("error_message", "Passwords must be six or more characters");
            return map;
        }
        if(password.length() > 100){
            map.put("error_message", "The length of password can't exceed 100 characters");
            return map;
        }
        if(!password.equals(confirmedpassword)){
            map.put("error_message", "Password do not match");
            return map;
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", username);
        List<User> users = userMapper.selectList(queryWrapper);
        if(!users.isEmpty()){
            map.put("error_message", "Username already exist");
        }
        else {
            String encodedpassword = passwordEncoder.encode(password);
            String photo = "https://userpic.codeforces.org/2258746/title/95b3a511f615a2cd.jpg";
            User user = new User(null, username, encodedpassword, photo, 1500);
            userMapper.insert(user);
            map.put("error_message", "Registration completed");
        }
        return map;
    }
}
