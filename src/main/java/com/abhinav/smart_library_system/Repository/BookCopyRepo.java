package com.abhinav.smart_library_system.Repository;

import com.abhinav.smart_library_system.Models.BookCopy;
import com.abhinav.smart_library_system.Models.BookStatus;
import com.abhinav.smart_library_system.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookCopyRepo extends JpaRepository<BookCopy,Long> {
    List<BookCopy> findByBookIdAndStatus(Long bookId, BookStatus status);
    List<BookCopy> findByCurrentBorrowerId(Long userId);
    BookCopy findByBarcode(String barCode);
}
