package com.abhinav.smart_library_system.Controller;

import com.abhinav.smart_library_system.Models.Book;
import com.abhinav.smart_library_system.Repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookRepo bookRepo;
    @GetMapping("/all")
    public List<Book> getAllBooks(){
        return bookRepo.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        return bookRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/filter/category")
    public List<Book> getBooksByCategory(@RequestParam String category){
        return bookRepo.findByCategory(category);
    }
    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String title) {
        return bookRepo.findByTitleContainingIgnoreCase(title);
    }
    @GetMapping("/eligible/{score}")
    public List<Book> getEligibleBooks(@PathVariable int score) {
        return bookRepo.findByRequiredTierLessThanEqual(calculateTierFromScore(score));
    }
    private int calculateTierFromScore(int score) {
        if (score >= 900) return 3;
        if (score >= 800) return 2;
        if (score >= 750) return 1;
        return 0;
    }

}
