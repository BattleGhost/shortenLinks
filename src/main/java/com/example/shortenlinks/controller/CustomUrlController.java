package com.example.shortenlinks.controller;

import com.example.shortenlinks.service.LinksCodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;


@Controller
@RequestMapping("/")
public class CustomUrlController {

    private LinksCodingService linksCodingService;

    @GetMapping("/{shortenLink}")
    public RedirectView redirectToActualLink(@PathVariable String shortenLink) {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(linksCodingService.decode(shortenLink));
        return redirectView;
    }

    @GetMapping("/")
    public String getPage() {
        return "index";
    }

    @Autowired
    public void setLinksCodingService(LinksCodingService linksCodingService) {
        this.linksCodingService = linksCodingService;
    }
}
