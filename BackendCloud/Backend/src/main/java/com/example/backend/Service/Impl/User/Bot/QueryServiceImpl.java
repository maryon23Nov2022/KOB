package com.example.backend.Service.Impl.User.Bot;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.Mapper.BotMapper;
import com.example.backend.Service.Impl.Utilities.UserDetailsImpl;
import com.example.backend.Service.User.Bot.QueryService;
import com.example.backend.pojo.Bot;
import com.example.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryServiceImpl implements QueryService {
    @Autowired
    private BotMapper botMapper;

    @Override
    public List<Bot> GetList() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userLoggedIn = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = userLoggedIn.getUser();

        QueryWrapper<Bot> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        return botMapper.selectList(queryWrapper);
    }
}
