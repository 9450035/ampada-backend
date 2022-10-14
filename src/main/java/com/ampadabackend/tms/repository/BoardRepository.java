package com.ampadabackend.tms.repository;

import com.ampadabackend.tms.domain.Board;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends MongoRepository<Board, String> {

}
