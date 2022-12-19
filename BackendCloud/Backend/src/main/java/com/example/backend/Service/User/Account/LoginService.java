package com.example.backend.Service.User.Account;

import java.util.Map;

public interface LoginService {
    public Map<String, String> getToken(String username, String password);
}
