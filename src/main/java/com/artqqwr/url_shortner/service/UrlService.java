package com.artqqwr.url_shortner.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artqqwr.url_shortner.exeption.UrlNotFoundException;
import com.artqqwr.url_shortner.model.ShortUrl;
import com.artqqwr.url_shortner.repository.UrlRepository;

@Service
public class UrlService {
    @Autowired
    private UrlRepository urlRepository;

    private static final String ALLOWED_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public ShortUrl createShortUrl(String originalUrl, LocalDateTime expiryDate) {
        String shortUrl = generateUniqueShortUrl();
        ShortUrl url = ShortUrl.builder()
                .originalUrl(originalUrl)
                .shortUrl(shortUrl)
                .creationDate(LocalDateTime.now())
                .expiryDate(expiryDate)
                .accessCount(0)
                .build();
        return urlRepository.save(url);
    }

    public String getOriginalUrl(String shortUrl) {
        ShortUrl url = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new UrlNotFoundException("URL not found"));
        url.setAccessCount(url.getAccessCount() + 1);
        urlRepository.save(url);
        return url.getOriginalUrl();
    }

    public ShortUrl updateUrl(Long id, String originalUrl, LocalDateTime expiryDate) {
        ShortUrl url = urlRepository.findById(id)
                .orElseThrow(() -> new UrlNotFoundException("URL not found"));
        url.setOriginalUrl(originalUrl);
        url.setExpiryDate(expiryDate);
        return urlRepository.save(url);
    }

    public void deleteUrl(Long id) {
        ShortUrl url = urlRepository.findById(id)
                .orElseThrow(() -> new UrlNotFoundException("URL not found"));
        urlRepository.delete(url);
    }

    private String generateUniqueShortUrl() {
        String shortUrl;
        do {
            shortUrl = generateShortUrl();
        } while (urlRepository.findByShortUrl(shortUrl).isPresent());
        return shortUrl;
    }

    private String generateShortUrl() {
        int length = new SecureRandom().nextInt(5) + 4; // длина от 4 до 8
        StringBuilder shortUrl = new StringBuilder(length);
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            shortUrl.append(ALLOWED_CHARS.charAt(random.nextInt(ALLOWED_CHARS.length())));
        }
        return shortUrl.toString();
    }
}