package com.example.backend.controller.User;

import com.example.backend.Mapper.UserMapper;
import com.example.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserMapper userMapper;

    @GetMapping("/user/all/")
    public List<User> getAll(){
        return userMapper.selectList(null);
    }

    @GetMapping("/user/{id}/")
    public User getuser(@PathVariable int id) {
        return userMapper.selectById(id);
    }

    @GetMapping("/user/add/{id}/{name}/{password}/")
    public String adduser(@PathVariable int id,
                          @PathVariable String name,
                          @PathVariable String password){
        String str = "{noop}" + password;
        User obj = new User(id, name, str);
        userMapper.insert(obj);
        return "Successfully";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteuser(@PathVariable int id){
        userMapper.deleteById(id);
        return "Successfully";
    }
}
