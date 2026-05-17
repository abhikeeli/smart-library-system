package com.abhinav.smart_library_system.Repository;

import com.abhinav.smart_library_system.Models.BorrowingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowingHistoryRepo extends JpaRepository<BorrowingHistory, Long> {
    // Find all books currently with the user
    List<BorrowingHistory> findByUserIdAndStatus(Long userId, String status);

    // Find everything the user has ever borrowed (Full History)
    List<BorrowingHistory> findByUserIdOrderByIssueDateDesc(Long userId);

    List<BorrowingHistory> findByBookId(Long bookId);

    List<BorrowingHistory> findByBookCopyId(Long bookCopyId);
}