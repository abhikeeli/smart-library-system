package com.abhinav.smart_library_system.Controller;

import com.abhinav.smart_library_system.Models.User;
import com.abhinav.smart_library_system.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService
    @PostMapping("/register")
    public User register(@RequestBody User user){
        return userService.registerUser(user);
    }

}
