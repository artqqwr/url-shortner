package com.artqqwr.url_shortner.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.artqqwr.url_shortner.model.ShortUrl;
import com.artqqwr.url_shortner.service.UrlService;

@Controller
public class UrlController {
    @Autowired
    private UrlService urlService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/create")
    public String createShortUrl(@RequestParam String originalUrl,
                                 @RequestParam LocalDateTime expiryDate,
                                 RedirectAttributes redirectAttributes) {
        ShortUrl shortUrl = urlService.createShortUrl(originalUrl, expiryDate);
        redirectAttributes.addFlashAttribute("message", "Short URL created: " + shortUrl.getShortUrl());
        return "redirect:/";
    }

    @GetMapping("/{shortUrl}")
    public String redirectUrl(@PathVariable String shortUrl) {
        String originalUrl = urlService.getOriginalUrl(shortUrl);

        if (!originalUrl.startsWith("http://") && !originalUrl.startsWith("https://")) {
            originalUrl = "http://" + originalUrl;
        }
        
        return "redirect:" + originalUrl;
    }

    @PostMapping("/delete/{id}")
    public String deleteUrl(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        urlService.deleteUrl(id);
        redirectAttributes.addFlashAttribute("message", "URL deleted successfully");
        return "redirect:/";
    }
}