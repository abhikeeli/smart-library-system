package com.abhinav.smart_library_system.Controller;

import com.abhinav.smart_library_system.Models.User;
import com.abhinav.smart_library_system.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/me")
    public ResponseEntity<Map<String,Object>> getCurrentUser(Principal principal) {
        User user = userRepo.findByusername(principal.getName()).orElseThrow(null);
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("username", user.getUsername());
        userDetails.put("role", user.getRole());
        userDetails.put("creditScore", user.getCreditScore()); // Critical for unlocking books
        userDetails.put("membershipTier", user.getMembershipTier());
        userDetails.put("id",user.getId());

        response.put("user", userDetails);
        return ResponseEntity.ok(response);
    }
}
