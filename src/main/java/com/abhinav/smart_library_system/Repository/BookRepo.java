package com.abhinav.smart_library_system.Repository;

import com.abhinav.smart_library_system.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book,Long> {
    List<Book> findByCategory(String category);
    List<Book> findByAuthor(String author);
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAvailableCopiesGreaterThan(int count);
    List<Book> findByRequiredTierLessThanEqual(int userTier);
    Optional<Book> findByIsbn(String isbn);
}
