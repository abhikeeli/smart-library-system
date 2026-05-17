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

import java.util.HashMap;
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
    public Map<String, Object> login(@RequestBody User loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        User user = userRepo.findByusername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtils.generateToken(authentication.getName(), user.getRole().name());

        // Create a response map that includes user info
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        // Pass only the fields the frontend needs
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("username", user.getUsername());
        userDetails.put("role", user.getRole());
        userDetails.put("creditScore", user.getCreditScore()); // Critical for unlocking books
        userDetails.put("membershipTier", user.getMembershipTier());
        userDetails.put("id",user.getId());

        response.put("user", userDetails);

        return response;
    }
    @PostMapping("/register")
    public User register(@RequestBody User user){
        return userService.registerUser(user);
    }

}
