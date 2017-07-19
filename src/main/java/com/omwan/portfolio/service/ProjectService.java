package com.omwan.portfolio.service;

import com.omwan.portfolio.domain.ProjectDTO;

import java.util.List;
import java.util.Map;

/**
 * Service to retrieve project information from mongo.
 *
 * Created by olivi on 07/16/2017.
 */
public interface ProjectService {

  Map<String, List<ProjectDTO>> getProjects(String category, boolean isPublic);

  /**
   * Save the given project to Mongo.
   *
   * @param project project to save
   * @return saved project
   */
  ProjectDTO saveProject(ProjectDTO project);

  /**
   * Soft delete the given project from Mongo.
   *
   * @param id id of project to delete
   * @return deleted project
   */
  ProjectDTO deleteProject(String id);
}
