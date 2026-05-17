package com.abhinav.smart_library_system.Controller;

import com.abhinav.smart_library_system.DTO.BorrowerRequest;
import com.abhinav.smart_library_system.Models.Book;
import com.abhinav.smart_library_system.Models.BookCopy;
import com.abhinav.smart_library_system.Service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/borrowing")
public class BorrowingController {
    @Autowired
    private BorrowingService borrowingService;

    @PostMapping("/issue")
    public ResponseEntity<String> issueBook(@RequestBody BorrowerRequest request){
        String result = borrowingService.borrowBook(request.getBookId(), request.getUserId());
        return ResponseEntity.ok(result);
    }
    @PostMapping("/return/{copyId}")
    public ResponseEntity<String> returnBook(@PathVariable Long copyId, Principal principal) {
        try {
            String message = borrowingService.returnBook(copyId, principal.getName());
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
    @GetMapping("/my-books")
    public ResponseEntity<List<BookCopy>> getMyBorrowedBooks(Principal principal) {
        // principal.getName() usually returns the 'username' or 'email' from your token
        String username = principal.getName();

        // Fetch books using the username instead of a raw ID
        List<BookCopy> borrowedBooks = borrowingService.getBooksByUsername(username);

        return ResponseEntity.ok(borrowedBooks);
    }
}
