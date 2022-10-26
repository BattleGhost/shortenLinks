package com.example.shortenlinks.service.impl;

import com.example.shortenlinks.dao.LinkDao;
import com.example.shortenlinks.model.Link;
import com.example.shortenlinks.service.LinksCodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static java.util.UUID.randomUUID;
import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.hibernate.type.IntegerType.ZERO;


@Service
public class LinksCodingServiceImpl implements LinksCodingService {

    private static final String DASH = "-";
    private static final int MAX_LENGTH = 8;
    private LinkDao linkDao;

    @Override
    public String encode(String actualLink) {
        Link shortenLinkByActualLink = linkDao.findByActualLink(actualLink);

        if (nonNull(shortenLinkByActualLink)) {
            return shortenLinkByActualLink.getShortenLink();
        }

        String shortenLink;

        do {
            shortenLink = randomUUID().toString().replaceAll(DASH, EMPTY).substring(ZERO, MAX_LENGTH);
        } while (nonNull(getActualLinkByShorten(shortenLink)));

        linkDao.save(new Link(shortenLink, actualLink));

        return shortenLink;
    }

    @Override
    public String decode(String shortenLink) {
        return ofNullable(getActualLinkByShorten(shortenLink)).map(Link::getActualLink).orElse(EMPTY);
    }

    private Link getActualLinkByShorten(String shortenLink) {
        return linkDao.findByShortenLink(shortenLink);
    }

    @Autowired
    public void setLinkDao(LinkDao linkDao) {
        this.linkDao = linkDao;
    }
}
