package com.omwan.portfolio.controller;

import com.omwan.portfolio.domain.ProjectDTO;
import com.omwan.portfolio.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
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
  public Map<String, List<ProjectDTO>> getProjects(@RequestParam(required = false, value = "category", defaultValue = "") String category,
                                                   @RequestParam(required = false, value = "isPublic", defaultValue = "false") boolean isPublic) {
    return projectService.getProjects(category, isPublic);
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
}
