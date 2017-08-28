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

  /**
   * Retrieve all projects from Mongo, mapped by category.
   *
   * @param publicOnly filter to only public projects
   * @return mapping of projects to category
   */
  Map<String, List<ProjectDTO>> getProjects(boolean publicOnly);

  /**
   * Retrieve all projects for the given category from Mongo.
   *
   * @param category   category to get projects for
   * @param publicOnly filter to only public projects
   * @return list of projects for category
   */
  List<ProjectDTO> getProjectsForCategory(String category, boolean publicOnly);

  /**
   * Retrieve all deleted projects, mapped by category.
   *
   * @return mapping of deleted projects to category
   */
  Map<String, List<ProjectDTO>> getDeletedProjects();

  /**
   * Retrieve all deleted projects for the given category.
   *
   * @param category category to get projects for
   * @return list of deleted projects for category
   */
  List<ProjectDTO> getDeletedProjectsForCategory(String category);

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

  /**
   * Restore the given project.
   *
   * @param id id of project to restore
   * @return restored project
   */
  ProjectDTO restoreProject(String id);
}
