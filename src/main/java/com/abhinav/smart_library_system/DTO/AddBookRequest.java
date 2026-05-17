package com.abhinav.smart_library_system.DTO;

import lombok.Data;

@Data
public class AddBookRequest {
    private String title;
    private String author;
    private String isbn;
    private String category;
    private String imageUrl;
    private String barcode;
}
