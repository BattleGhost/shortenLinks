package com.example.shortenlinks.dao;

import com.example.shortenlinks.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkDao extends JpaRepository<Link, Long> {
    Link findByShortenLink(String shortenLink);
    Link findByActualLink(String actualLink);
}
