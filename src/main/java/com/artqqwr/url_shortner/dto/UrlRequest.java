package com.artqqwr.url_shortner.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UrlRequest {
    private String originalUrl;
    private LocalDateTime expiryDate;
}