package com.abhinav.smart_library_system.DTO;

import lombok.Data;

@Data
public class BorrowerRequest {
    private Long bookId;
    private Long userId;
}
