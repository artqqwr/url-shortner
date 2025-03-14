package com.artqqwr.url_shortner.exeption;

public class UrlNotFoundException extends RuntimeException {

    public UrlNotFoundException(String message) {
        super(message);
    }
}
