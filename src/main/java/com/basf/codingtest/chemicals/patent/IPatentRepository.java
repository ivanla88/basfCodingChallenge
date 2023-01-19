package com.basf.codingtest.chemicals.patent;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPatentRepository extends MongoRepository<Patent, Integer> {



}
