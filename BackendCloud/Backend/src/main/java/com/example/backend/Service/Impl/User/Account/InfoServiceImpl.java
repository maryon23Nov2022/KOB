package com.example.backend.Service.Impl.User.Account;

import com.example.backend.Service.Impl.Utilities.UserDetailsImpl;
import com.example.backend.Service.User.Account.InfoService;
import com.example.backend.pojo.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.SecureClassLoader;
import java.util.HashMap;
import java.util.Map;

@Service
public class InfoServiceImpl implements InfoService {
    @Override
    public Map<String, String> getinfo() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginuser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginuser.getUser();

        Map<String, String> map = new HashMap<>();
        map.put("error_message", "success");
        map.put("id", user.getId().toString());
        map.put("username", user.getName());
        map.put("photo", user.getPhoto());
        return map;
    }
}
