package com.artqqwr.url_shortner.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artqqwr.url_shortner.model.ShortUrl;

public interface UrlRepository extends JpaRepository<ShortUrl, Long> {
    Optional<ShortUrl> findByShortUrl(String shortUrl);
}
