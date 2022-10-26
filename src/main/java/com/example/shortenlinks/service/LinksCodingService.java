package com.example.shortenlinks.service;

public interface LinksCodingService {
    String encode(String link);
    String decode(String link);
}
