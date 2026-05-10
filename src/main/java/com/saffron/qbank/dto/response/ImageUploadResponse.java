package com.saffron.qbank.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImageUploadResponse {
    private String imageFileName;
    private String imageUrl;
}
