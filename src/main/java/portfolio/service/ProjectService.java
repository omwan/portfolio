package portfolio.service;

import com.omwan.portfolio.domain.ProjectDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by olivi on 07/16/2017.
 */
public interface ProjectService {

  Map<String, List<ProjectDTO>> getProjects(String category, boolean isPublic);

  ProjectDTO saveProject(ProjectDTO project);

  ProjectDTO deleteProject(String id);
}
