package com.omwan.portfolio.component;

import com.omwan.portfolio.mongo.document.Project;
import com.omwan.portfolio.mongo.repository.ProjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Class to handle communication with repository interface.
 *
 * Created by olivi on 08/25/2017.
 */
@Component
public class ProjectComponent {

  @Autowired
  private ProjectRepository projectRepository;

  /**
   * Get all non-deleted projects from Mongo.
   *
   * @return list of projects
   */
  @Transactional(readOnly = true)
  public List<Project> getAllProjects() {
    return projectRepository.findByIsDeleted(false);
  }

  /**
   * Get all non-deleted projects for the given category from Mongo.
   *
   * @param category category of projects
   * @return list of projects
   */
  @Transactional(readOnly = true)
  public List<Project> getProjectsForCategory(String category) {
    return projectRepository.findByCategoryAndIsDeleted(category.toUpperCase(), false);
  }

  /**
   * Get all deleted projects from Mongo.
   *
   * @return list of projects
   */
  @Transactional(readOnly = true)
  public List<Project> getDeletedProjects() {
    return projectRepository.findByIsDeleted(true);
  }

  /**
   * Get all deleted projects for the given category from Mongo.
   *
   * @param category category of projects
   * @return list of projects
   */
  @Transactional(readOnly = true)
  public List<Project> getDeletedProjectsForCategory(String category) {
    return projectRepository.findByCategoryAndIsDeleted(category, true);
  }

  /**
   * Save the given project to Mongo.
   *
   * @param project project to save
   * @return saved project
   */
  @Transactional
  public Project saveProject(Project project) {
    return projectRepository.save(project);
  }

  /**
   * Update the deleted flag for the given document.
   *
   * @param id      id of document to soft-delete
   * @param deleted value to set deleted flag to
   * @return updated document
   */
  @Transactional
  public Project updateProjectDeletedFlag(String id, boolean deleted) {
    Project project = projectRepository.findOne(id);
    if (project == null) {
      throw new IllegalArgumentException("no project found for the given ID");
    } else if (project.isLocked()) {
      throw new IllegalArgumentException("Project cannot be modified");
    }
    project.setDeleted(deleted);
    return projectRepository.save(project);
  }
}
