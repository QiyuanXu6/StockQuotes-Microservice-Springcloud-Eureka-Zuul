package com.sr.stock.dbservice.repository;

import com.sr.stock.dbservice.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface QuotesRepository extends JpaRepository<Quote, Integer> {
    List<Quote> findByUsername(@Param("username") String username);
}
