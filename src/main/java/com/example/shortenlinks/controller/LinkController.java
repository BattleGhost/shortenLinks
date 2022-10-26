package com.example.shortenlinks.controller;

import com.example.shortenlinks.model.Link;
import com.example.shortenlinks.service.LinksCodingService;
import com.example.shortenlinks.service.impl.LinkService;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("api/v1/links")
public class LinkController {

    private static final String SLASH = "/";
    private static final String HTTP = "http";
    private static final String HTTPS = "https";
    private static final int MIN_AMOUNT = 1;
    private static final int MAX_AMOUNT = 10;
    private LinksCodingService linksCodingService;
    private LinkService linkService;

    @PostMapping
    public String createShortenLink(HttpServletRequest httpServletRequest, String link) throws MalformedURLException {
        String[] schemes = {HTTP, HTTPS};
        UrlValidator urlValidator = new UrlValidator(schemes);

        if (!urlValidator.isValid(link)) {
            return null;
        }

        String domain = httpServletRequest.getRequestURL().toString()
                .replaceAll(httpServletRequest.getRequestURI(), SLASH);

        if (equalUrlHosts(domain, link)) {
            return null;
        }

        return domain + linksCodingService.encode(link);
    }

    @GetMapping
    public List<Link> getLastCreatedLinks(@RequestParam(defaultValue = "10") int amount) {
        if (amount < MIN_AMOUNT || amount > MAX_AMOUNT)
            return null;
        return linkService.getLastCreatedLinks(amount);
    }

    private boolean equalUrlHosts(String stringUrl1, String stringUrl2) throws MalformedURLException {
        URL url1 = new URL(stringUrl1);
        URL url2 = new URL(stringUrl2);

        return url1.getHost().equals(url2.getHost());
    }


    @Autowired
    public void setLinksCodingService(LinksCodingService linksCodingService) {
        this.linksCodingService = linksCodingService;
    }

    @Autowired
    public void setLinkService(LinkService linkService) {
        this.linkService = linkService;
    }
}
