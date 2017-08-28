package com.omwan.portfolio.service;

import com.omwan.portfolio.component.ProjectComponent;
import com.omwan.portfolio.domain.ProjectDTO;
import com.omwan.portfolio.mongo.document.Project;
import com.omwan.portfolio.util.ProjectUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation of service to retrieve project information from mongo.
 *
 * Created by olivi on 07/16/2017.
 */
@Service
public class ProjectServiceImpl implements ProjectService {

  @Autowired
  private ProjectComponent projectComponent;

  /**
   * Retrieve all projects from Mongo, mapped by category.
   */
  @Override
  @Transactional(readOnly = true)
  public Map<String, List<ProjectDTO>> getProjects(boolean publicOnly) {
    List<Project> projects = projectComponent.getAllProjects();
    Map<String, List<ProjectDTO>> mappedProjects = new HashMap<>();
    filterAndConvertDocuments(projects, publicOnly)
            .forEach(project -> addProjectToMap(mappedProjects, project));
    return mappedProjects;
  }

  /**
   * Retrieve all projects for the given category from Mongo.
   */
  @Override
  public List<ProjectDTO> getProjectsForCategory(String category, boolean publicOnly) {
    List<Project> projects = projectComponent.getProjectsForCategory(category);
    return filterAndConvertDocuments(projects, publicOnly);
  }

  /**
   * Retrieve all deleted projects, mapped by category.
   */
  @Override
  public Map<String, List<ProjectDTO>> getDeletedProjects() {
    List<Project> projects = projectComponent.getDeletedProjects();
    Map<String, List<ProjectDTO>> mappedProjects = new HashMap<>();
    projects.stream()
            .map(ProjectUtils::convertFromDocument)
            .forEach(project -> addProjectToMap(mappedProjects, project));
    return mappedProjects;
  }

  /**
   * Retrieve all deleted projects for he given category.
   */
  @Override
  public List<ProjectDTO> getDeletedProjectsForCategory(String category) {
    List<Project> projects = projectComponent.getDeletedProjectsForCategory(category);
    return projects.stream()
            .map(ProjectUtils::convertFromDocument)
            .collect(Collectors.toList());
  }

  /**
   * Save the given project to Mongo.
   *
   * @param project project to save
   * @return saved project
   */
  @Override
  @Transactional
  public ProjectDTO saveProject(ProjectDTO project) {
    Project projectToSave = ProjectUtils.convertFromDomain(project);
    return ProjectUtils.convertFromDocument(projectComponent.saveProject(projectToSave));
  }

  /**
   * Soft delete the given project from Mongo.
   *
   * @param id id of project to delete
   * @return deleted project
   */
  @Override
  @Transactional
  public ProjectDTO deleteProject(String id) {
    return ProjectUtils.convertFromDocument(projectComponent.updateProjectDeletedFlag(id, true));
  }

  /**
   * Restore the given project.
   *
   * @param id id of project to restore
   * @return restored project
   */
  @Override
  public ProjectDTO restoreProject(String id) {
    return ProjectUtils.convertFromDocument(projectComponent.updateProjectDeletedFlag(id, false));
  }

  /**
   * Filters the list of projects by public flag if needed, and converts them to domain objects.
   *
   * @param projects   list of projects to be processed
   * @param publicOnly whether to filter by public projects
   * @return list of filtered + converted projects
   */
  private List<ProjectDTO> filterAndConvertDocuments(List<Project> projects, boolean publicOnly) {
    return projects.stream()
            .filter(project -> !publicOnly || project.isPublic())
            .map(ProjectUtils::convertFromDocument)
            .collect(Collectors.toList());
  }

  /**
   * Adds the given project to the mapping of projects by category.
   *
   * @param map     mapping of projects by category
   * @param project project to add to map
   */
  private void addProjectToMap(Map<String, List<ProjectDTO>> map, ProjectDTO project) {
    String category = project.getCategory();
    if (map.containsKey(category)) {
      List<ProjectDTO> projects = map.get(category);
      projects.add(project);
    } else {
      List<ProjectDTO> projects = new ArrayList<>();
      projects.add(project);
      map.put(category, projects);
    }
  }
}
