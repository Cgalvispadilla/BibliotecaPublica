package com.sofkau.biblioteca.repositories;

import com.sofkau.biblioteca.models.Resource;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends MongoRepository<Resource, String> {
    List<Resource> findAllByType(String type);
    List<Resource> findAllBytheme(String theme);

}
