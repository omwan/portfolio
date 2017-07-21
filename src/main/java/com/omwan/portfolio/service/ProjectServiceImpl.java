package com.omwan.portfolio.service;

import com.omwan.portfolio.domain.ProjectDTO;
import com.omwan.portfolio.mongo.document.Project;
import com.omwan.portfolio.mongo.repository.ProjectRepository;
import com.omwan.portfolio.util.ProjectUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
  private ProjectRepository projectRepository;

  @Override
  @Transactional(readOnly = true)
  public Map<String, List<ProjectDTO>> getProjects(boolean publicOnly) {
    List<Project> projects = projectRepository.findByIsDeleted(false);
    Map<String, List<ProjectDTO>> mappedProjects = new HashMap<>();
    filterAndConvertDocuments(projects, publicOnly)
            .forEach(project -> addProjectToMap(mappedProjects, project));
    return mappedProjects;
  }

  @Override
  public List<ProjectDTO> getProjectsForCategory(String category, boolean publicOnly) {
    List<Project> projects = projectRepository.findByCategoryAndIsDeleted(category.toUpperCase(), false);
    return filterAndConvertDocuments(projects, publicOnly);
  }

  @Override
  public Map<String, List<ProjectDTO>> getDeletedProjects() {
    List<Project> projects = projectRepository.findByIsDeleted(true);
    Map<String, List<ProjectDTO>> mappedProjects = new HashMap<>();
    projects.stream()
            .map(ProjectUtils::convertFromDocument)
            .forEach(project -> addProjectToMap(mappedProjects, project));
    return mappedProjects;
  }

  @Override
  public List<ProjectDTO> getDeletedProjectsForCategory(String category) {
    List<Project> projects = projectRepository.findByCategoryAndIsDeleted(category.toUpperCase(), true);
    return projects.stream()
            .map(ProjectUtils::convertFromDocument)
            .collect(Collectors.toList());
  }

  private List<ProjectDTO> filterAndConvertDocuments(List<Project> projects, boolean publicOnly) {
    return projects.stream()
            .filter(project -> !publicOnly || project.isPublic())
            .map(ProjectUtils::convertFromDocument)
            .collect(Collectors.toList());
  }

  /**
   * Save the given project to Mongo. If a project already exists in the collection for
   * the given title, an IllegalArgumentException will be thrown.
   *
   * @param project project to save
   * @return saved project
   */
  @Override
  @Transactional
  public ProjectDTO saveProject(ProjectDTO project) {
    Project document = ProjectUtils.convertFromDomain(project);
    try {
      return ProjectUtils.convertFromDocument(projectRepository.save(document));
    } catch (DuplicateKeyException e) {
      throw new IllegalArgumentException("project found with the given title");
    }
  }

  /**
   * Soft delete the given project from Mongo. If there is no project in the collection
   * for the given id, an IllegalArgumentException will be thrown.
   *
   * @param id id of project to delete
   * @return deleted project
   */
  @Override
  @Transactional
  public ProjectDTO deleteProject(String id) {
    return updateProjectDeleted(id, true);
  }

  @Override
  public ProjectDTO restoreProject(String id) {
    return updateProjectDeleted(id, false);
  }

  private ProjectDTO updateProjectDeleted(String id, boolean deleted) {
    Project projectToRestore = projectRepository.findOne(id);
    if (projectToRestore == null) {
      throw new IllegalArgumentException("no project found for the given ID");
    }
    projectToRestore.setDeleted(deleted);
    return ProjectUtils.convertFromDocument(projectRepository.save(projectToRestore));
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
