package com.abhinav.smart_library_system.Service;

import com.abhinav.smart_library_system.Models.Tier;
import com.abhinav.smart_library_system.Models.User;
import com.abhinav.smart_library_system.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    public User registerUser(User user){
        user.setCreditScore(700);
        user.setMembershipTier(Tier.BRONZE);
        return userRepo.save(user);
    }
}
