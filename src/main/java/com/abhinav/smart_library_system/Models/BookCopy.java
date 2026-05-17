package com.abhinav.smart_library_system.Models;
import com.abhinav.smart_library_system.Models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "book_copy")
@AllArgsConstructor
@NoArgsConstructor
public class BookCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String barcode;
    @Enumerated(EnumType.STRING)
    private BookStatus status;
    @Enumerated(EnumType.STRING)
    private BookCondition condition;
    private boolean isBorrowed;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @ManyToOne
    @JoinColumn(name = "borrower_id")
    private User currentBorrower;
}
