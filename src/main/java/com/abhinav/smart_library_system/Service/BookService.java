package com.abhinav.smart_library_system.Service;

import com.abhinav.smart_library_system.DTO.AddBookRequest;
import com.abhinav.smart_library_system.Models.Book;
import com.abhinav.smart_library_system.Models.BookCondition;
import com.abhinav.smart_library_system.Models.BookCopy;
import com.abhinav.smart_library_system.Models.BookStatus;
import com.abhinav.smart_library_system.Repository.BookCopyRepo;
import com.abhinav.smart_library_system.Repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private BookCopyRepo bookCopyRepo;
    @Transactional
    public void addBookWithCopies(AddBookRequest request) {

        Book book = bookRepo.findByIsbn(request.getIsbn())
                .orElseGet(() -> {
                    Book newBook = new Book();
                    newBook.setAuthor(request.getAuthor());
                    newBook.setTitle(request.getTitle());
                    newBook.setIsbn(request.getIsbn());
                    newBook.setCategory(request.getCategory());
                    newBook.setImageUrl(request.getImageUrl());
                    newBook.setTotalCopies(0);
                    newBook.setAvailableCopies(0);
                    newBook.setRequiredTier(0);
                    return bookRepo.save(newBook);
                });

        BookCopy bookCopy = new BookCopy();
        bookCopy.setStatus(BookStatus.AVAILABLE);
        bookCopy.setBorrowed(false);
        bookCopy.setBarcode(request.getBarcode());
        bookCopy.setBook(book);
        bookCopy.setCondition(BookCondition.NEW);


        book.setTotalCopies(book.getTotalCopies() + 1);
        book.setAvailableCopies(book.getAvailableCopies() + 1);

        bookRepo.save(book);
        bookCopyRepo.save(bookCopy);
    }

    public Book updateBookTier(Long bookId, int newTier) {
        Book book = bookRepo.findById(bookId).orElseThrow();
        book.setRequiredTier(newTier);
        return bookRepo.save(book);
    }

}
