package com.example.backend.Service.User.Account;

import java.util.Map;

public interface RegisterService {
    Map<String, String> register(String username, String password, String confirmedpassword);
}
