package com.omwan.portfolio.mongo.repository;

import com.omwan.portfolio.mongo.document.Project;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Class to manage data in project collection.
 *
 * Created by olivi on 07/16/2017.
 */
@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {

  List<Project> findByCategoryAndIsDeleted(String category, boolean isDeleted);

  List<Project> findByIsDeleted(boolean isDeleted);
}
