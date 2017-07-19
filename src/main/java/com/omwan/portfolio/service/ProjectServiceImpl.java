package com.omwan.portfolio.service;

import com.omwan.portfolio.domain.ProjectDTO;
import com.omwan.portfolio.mongo.document.Project;
import com.omwan.portfolio.mongo.repository.ProjectRepository;
import com.omwan.portfolio.util.ProjectUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
  public Map<String, List<ProjectDTO>> getProjects(String category, boolean isPublic) {
    List<Project> projects;
    if (category.equals("")) {
      if (isPublic) {
        projects = projectRepository.findByIsPublic(isPublic);
      } else {
        projects = projectRepository.findAll();
      }
    } else {
      if (isPublic) {
        projects = projectRepository.findByCategoryAndIsPublic(category, isPublic);
      } else {
        projects = projectRepository.findByCategory(category);
      }
    }

    Map<String, List<ProjectDTO>> mappedProjects = new HashMap<>();
    projects.stream()
            .map(ProjectUtils::convertFromDocument)
            .forEach(project -> addProjectToMap(mappedProjects, project));

    return mappedProjects;
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
    Project projectToDelete = projectRepository.findOne(id);
    if (projectToDelete == null) {
      throw new IllegalArgumentException("no project found for the given ID");
    }
    projectToDelete.setDeleted(true);
    return ProjectUtils.convertFromDocument(projectRepository.save(projectToDelete));
  }

  /**
   * Adds the given project to the mapping of projects by category.
   *
   * @param map     mapping of projects by category
   * @param project project to add to map
   */
  private void addProjectToMap(Map<String, List<ProjectDTO>> map, ProjectDTO project) {
    String category = project.getCategory().toString();
    if (map.containsKey(category)) {
      map.get(category).add(project);
    } else {
      map.put(category, Collections.singletonList(project));
    }
  }
}
