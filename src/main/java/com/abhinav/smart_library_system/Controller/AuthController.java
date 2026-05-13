package com.abhinav.smart_library_system.Controller;

import com.abhinav.smart_library_system.Config.JwtUtils;
import com.abhinav.smart_library_system.Models.User;
import com.abhinav.smart_library_system.Repository.UserRepo;
import com.abhinav.smart_library_system.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public Map<String,String> login(@RequestBody User loginRequest){
        Authentication authentication =authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword())
        );
        String token = jwtUtils.generateToken(authentication.getName());
        return Map.of("token",token);
    }
    @PostMapping("/register")
    public User register(@RequestBody User user){
        return userService.registerUser(user);
    }

}
