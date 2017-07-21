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

  Map<String, List<ProjectDTO>> getProjects(boolean publicOnly);

  List<ProjectDTO> getProjectsForCategory(String category, boolean publicOnly);

  Map<String, List<ProjectDTO>> getDeletedProjects();

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
