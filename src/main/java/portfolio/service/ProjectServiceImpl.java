package portfolio.service;

import com.omwan.portfolio.domain.ProjectDTO;
import com.omwan.portfolio.mongo.document.Project;
import com.omwan.portfolio.mongo.repository.ProjectRepository;
import com.omwan.portfolio.util.ProjectUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
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

  @Override
  @Transactional
  public ProjectDTO saveProject(ProjectDTO project) {
    Project document = ProjectUtils.convertFromDomain(project);
    return ProjectUtils.convertFromDocument(projectRepository.save(document));
  }

  @Override
  @Transactional
  public ProjectDTO deleteProject(String id) {
    Project projectToDelete = projectRepository.findOne(id);
    projectToDelete.setDeleted(true);
    return ProjectUtils.convertFromDocument(projectRepository.save(projectToDelete));
  }

  private void addProjectToMap(Map<String, List<ProjectDTO>> map, ProjectDTO project) {
    String category = project.getCategory();
    if (map.containsKey(category)) {
      map.get(category).add(project);
    } else {
      map.put(category, Collections.singletonList(project));
    }
  }
}
