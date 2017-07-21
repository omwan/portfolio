package com.omwan.portfolio.controller;

import com.omwan.portfolio.domain.ProjectDTO;
import com.omwan.portfolio.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Controller to retrieve project information from mongo.
 *
 * Created by olivi on 07/16/2017.
 */
@RestController
@RequestMapping("/api")
public class ProjectServiceController {

  @Autowired
  private ProjectService projectService;

  @RequestMapping(value = "/projects", method = RequestMethod.GET)
  @ResponseBody
  public Map<String, List<ProjectDTO>> getProjects(@RequestParam(required = false, value = "publicOnly", defaultValue = "false") boolean publicOnly) {
    return projectService.getProjects(publicOnly);
  }

  @RequestMapping(value = "/projects/{category}", method = RequestMethod.GET)
  @ResponseBody
  public List<ProjectDTO> getProjectsForCategory(@PathVariable(value = "category") String category,
                                                 @RequestParam(required = false, value = "publicOnly", defaultValue = "false") boolean publicOnly) {
    return projectService.getProjectsForCategory(category, publicOnly);
  }

  @RequestMapping(value = "/projects/deletedprojects", method = RequestMethod.GET)
  public Map<String, List<ProjectDTO>> getDeletedProjects() {
    return projectService.getDeletedProjects();
  }

  @RequestMapping(value = "/projects/deletedprojects/{category}", method = RequestMethod.GET)
  public List<ProjectDTO> getDeletedProjectsForCategory(@PathVariable(value = "category") String category) {
    return projectService.getDeletedProjectsForCategory(category);
  }

  @RequestMapping(value = "/projects", method = RequestMethod.POST)
  @ResponseBody
  public ProjectDTO saveProject(@RequestBody ProjectDTO project) {
    return projectService.saveProject(project);
  }

  @RequestMapping(value = "/projects", method = RequestMethod.DELETE)
  @ResponseBody
  public ProjectDTO deleteProject(@RequestParam(value = "id") String id) {
    return projectService.deleteProject(id);
  }

  @RequestMapping(value = "/projects", method = RequestMethod.PUT)
  @ResponseBody
  public ProjectDTO restoreProject(@RequestParam(value = "id") String id) {
    return projectService.restoreProject(id);
  }
}
