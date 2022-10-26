package com.example.shortenlinks.service.impl;

import com.example.shortenlinks.dao.LinkDao;
import com.example.shortenlinks.model.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkService {

    private LinkDao linkDao;

    public List<Link> getLastCreatedLinks(int amount) {
        Pageable pageable = PageRequest.of(0, amount, Sort.by("created").descending());
        return linkDao.findAll(pageable).getContent();
    }

    @Autowired
    public void setLinkDao(LinkDao linkDao) {
        this.linkDao = linkDao;
    }
}
