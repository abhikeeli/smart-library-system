package com.abhinav.smart_library_system.Service;

import com.abhinav.smart_library_system.Models.*;
import com.abhinav.smart_library_system.Repository.BookCopyRepo;
import com.abhinav.smart_library_system.Repository.BookRepo;
import com.abhinav.smart_library_system.Repository.BorrowingHistoryRepo;
import com.abhinav.smart_library_system.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BorrowingService {
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private BookCopyRepo bookCopyRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BorrowingHistoryRepo borrowingHistoryRepo;
    @Transactional
    public String borrowBook(Long bookId,Long userId){
        User user =userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepo.findById(bookId).orElseThrow(()->new RuntimeException("Book not Found"));
        int requiredScore = getRequiredScoreForTier(book.getRequiredTier());
        List<BookCopy> availableCopies = bookCopyRepo.findByBookIdAndStatus(bookId, BookStatus.AVAILABLE);
        if(availableCopies.isEmpty()){
            return "No physical copies currently available.";
        }
        if(user.getCreditScore()< requiredScore){
            return "view score is low to borrow this book";
        }
        BookCopy bookCopy=availableCopies.get(0);
        bookCopy.setCurrentBorrower(user);
        bookCopy.setStatus(BookStatus.BORROWED);
        book.setAvailableCopies(book.getAvailableCopies()-1);

        BorrowingHistory history = new BorrowingHistory();
        history.setUser(user);
        history.setBook(book);
        history.setBookCopy(bookCopy);
        history.setBookCopyBarcode(bookCopy.getBarcode());
        history.setIssueDate(LocalDateTime.now());
        history.setDueDate(LocalDateTime.now().plusDays(14));
        history.setStatus("ACTIVE");
        history.setPointsEarned(0);
        bookCopyRepo.save(bookCopy);
        bookRepo.save(book);
        borrowingHistoryRepo.save(history);

        return  "Success: " + book.getTitle() + " has been issued to " + user.getUsername();
    }
    @Transactional
    public String returnBook(Long bookCopyId, String currentUsername) {
        // 1. Find the active borrowing record specifically for this copy and this user
        BorrowingHistory history = borrowingHistoryRepo.findByBookCopyId(bookCopyId)
                .stream()
                .filter(h -> h.getReturnDate() == null)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No active borrowing record found for this copy."));

        // 2. SECURITY CHECK: Ensure the person returning the book is the one who borrowed it
        if (!history.getUser().getUsername().equals(currentUsername)) {
            throw new RuntimeException("Security Alert: You are not authorized to return this book.");
        }

        User user = history.getUser();
        Book book = history.getBook();
        BookCopy copy = history.getBookCopy();

        LocalDateTime now = LocalDateTime.now();
        history.setReturnDate(now);

        // 3. Points Logic (On-time vs Late)
        int pointsChange = now.isAfter(history.getDueDate()) ? -20 : 10;
        history.setStatus(pointsChange > 0 ? "RETURNED_ON_TIME" : "RETURNED_LATE");

        // 4. Update User Reputation
        user.setCreditScore(user.getCreditScore() + pointsChange);
        history.setPointsEarned(pointsChange);

        // 5. Update Inventory and Copy Status
        copy.setStatus(BookStatus.AVAILABLE);
        copy.setCurrentBorrower(null);
        book.setAvailableCopies(book.getAvailableCopies() + 1);

        // 6. Save all changes
        userRepo.save(user);
        bookRepo.save(book);
        bookCopyRepo.save(copy);
        borrowingHistoryRepo.save(history);

        return String.format("Return Successful! %s%d points. New Reputation: %d",
                (pointsChange > 0 ? "+" : ""), pointsChange, user.getCreditScore());
    }
    private int getRequiredScoreForTier(int tier) {
        return switch (tier) {
            case 1 -> 750;
            case 2 -> 800;
            case 3 -> 900;
            default -> 0;
        };
    }

    public List<BookCopy> getBooksByUsername(String username) {
        User user = userRepo.findByusername(username).orElseThrow(()->new RuntimeException("not found"));
        return bookCopyRepo.findByCurrentBorrowerId(user.getId());
    }
}
