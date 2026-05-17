package com.abhinav.smart_library_system.Models;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class BorrowingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Book book;

    @ManyToOne
    private BookCopy bookCopy;

    private String bookCopyBarcode;

    private LocalDateTime issueDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnDate;

    private String status;
    private int pointsEarned;
}
