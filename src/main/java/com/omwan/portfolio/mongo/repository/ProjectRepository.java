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

  List<Project> findByCategory(String category);

  List<Project> findByCategoryAndIsPublic(String category, boolean isPublic);

  List<Project> findByIsPublic(boolean isPublic);
}
