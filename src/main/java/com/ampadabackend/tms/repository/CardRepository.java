package com.ampadabackend.tms.repository;

import com.ampadabackend.tms.domain.Card;
import com.ampadabackend.tms.service.dto.CardViewModel;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends MongoRepository<Card, String> {
}
