package com.abhinav.smart_library_system.Repository;

import com.abhinav.smart_library_system.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    Optional<User> findByusername(String username);
}
