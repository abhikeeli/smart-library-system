package com.abhinav.smart_library_system.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name= "books")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String category;
    private String imageUrl;
    private int totalCopies;
    private int availableCopies;
    public boolean isAvailable() {
        return this.availableCopies > 0;
    }
    private int requiredTier = 0;
}
