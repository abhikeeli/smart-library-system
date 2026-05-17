package com.abhinav.smart_library_system.Controller;

import com.abhinav.smart_library_system.DTO.AddBookRequest;
import com.abhinav.smart_library_system.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private BookService bookService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-book")
    public ResponseEntity<String> addNewBook(@RequestBody AddBookRequest request) {
        try {
            bookService.addBookWithCopies(request);
            return ResponseEntity.ok("Book and Copy added successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


    @PutMapping("/update-tier/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateTier(@PathVariable Long id, @RequestParam int newTier) {
        // Logic to update book tier in BookService
        return ResponseEntity.ok("Tier updated successfully.");
    }
}