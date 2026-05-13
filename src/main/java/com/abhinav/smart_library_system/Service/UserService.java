package com.abhinav.smart_library_system.Service;

import com.abhinav.smart_library_system.Models.Tier;
import com.abhinav.smart_library_system.Models.User;
import com.abhinav.smart_library_system.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public User registerUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreditScore(700);
        user.setMembershipTier(Tier.BRONZE);
        return userRepo.save(user);
    }
}
