package com.omwan.portfolio.service;

import com.omwan.portfolio.component.ProjectComponent;
import com.omwan.portfolio.domain.ProjectCategory;
import com.omwan.portfolio.domain.ProjectDTO;
import com.omwan.portfolio.mongo.document.Project;
import com.omwan.portfolio.util.ProjectUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DuplicateKeyException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Tested;
import mockit.integration.junit4.JMockit;

import static org.junit.Assert.*;

/**
 * Class to test methods from service to retrieve project data from mongo.
 *
 * Created by olivi on 07/17/2017.
 */
@RunWith(JMockit.class)
public class ProjectServiceImplTest {

  @Tested
  private ProjectService projectService;

  @Injectable
  private ProjectComponent projectComponent;

  @Before
  public void setup() throws Exception {
    projectService = new ProjectServiceImpl();
  }

  @Test
  public void testGetProjects() throws Exception {
    final String category1 = "category1";
    final String category2 = "category2";
    final List<Project> projects = new ArrayList<>();
    projects.addAll(createMockedProjects(2, category1));
    projects.addAll(createMockedProjects(2, category2));

    new Expectations() {{
      projectComponent.getAllProjects();
      returns(projects);
    }};

    Map<String, List<ProjectDTO>> actual = projectService.getProjects(false);
    assertTrue(actual.keySet().contains(category1));
    assertTrue(actual.keySet().contains(category2));

    List<ProjectDTO> projectsForCategory1 = actual.get(category1);
    for (int i = 0; i < projectsForCategory1.size(); i++) {
      ProjectDTO project = projectsForCategory1.get(i);
      assertEquals(project.getTitle(), projects.get(i).getTitle());
      assertEquals(project.getCategory(), category1);
      assertEquals(project.getDescription(), projects.get(i).getDescription());
    }

    List<ProjectDTO> projectsForCategory2 = actual.get(category2);
    for (int i = 0; i < projectsForCategory2.size(); i++) {
      ProjectDTO project = projectsForCategory2.get(i);
      assertEquals(project.getTitle(), projects.get(i + projectsForCategory1.size()).getTitle());
      assertEquals(project.getCategory(), category2);
      assertEquals(project.getDescription(), projects.get(i + projectsForCategory1.size()).getDescription());
    }
  }

  @Test
  public void testGetProjectsForCategory() throws Exception {
    final String category = "category";
    final List<Project> projectsForCategory = createMockedProjects(2, category);

    new Expectations() {{
      projectComponent.getProjectsForCategory(category);
      returns(projectsForCategory);
    }};

    List<ProjectDTO> actual = projectService.getProjectsForCategory(category, false);
    for (int i = 0; i < actual.size(); i++) {
      ProjectDTO project = actual.get(i);
      assertEquals(project.getTitle(), projectsForCategory.get(i).getTitle());
      assertEquals(project.getCategory(), category);
      assertEquals(project.getDescription(), projectsForCategory.get(i).getDescription());
    }
  }

  @Test
  public void testGetDeletedProjectsForCategory() throws Exception {
    final String category = "category1";
    final List<Project> deletedProjects = createMockedProjects(2, category).stream()
            .map(project -> {
              project.setDeleted(true);
              return project;
            }).collect(Collectors.toList());

    new Expectations() {{
      projectComponent.getDeletedProjectsForCategory(category);
      returns(deletedProjects);
    }};

    List<ProjectDTO> actual = projectService.getDeletedProjectsForCategory(category);
    for (ProjectDTO projectDTO : actual) {
      assertTrue(projectDTO.isDeleted());
    }
  }

  /**
   * Asserts that if a duplicate key exception is thrown by the repository due to having
   * a non-unique title, the service throws an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSaveProjectDuplicateKey() throws Exception {
    new Expectations() {{
      projectComponent.saveProject((Project) any);
      result = new DuplicateKeyException("duplicate key");
    }};

    projectService.saveProject(new ProjectDTO());
  }

  /**
   * Asserts that the service to save a
   */
  @Test
  public void testSaveProject() throws Exception {
    final ProjectDTO projectToSave = new ProjectDTO();
    projectToSave.setTitle("project title");
    projectToSave.setCategory(ProjectCategory.WORK.toString());
    final Project projectAsDocument = ProjectUtils.convertFromDomain(projectToSave);
    mockConvertFromDomain(projectAsDocument);

    new Expectations() {{
      projectComponent.saveProject(projectAsDocument);
      returns(projectAsDocument);
    }};

    ProjectDTO actual = projectService.saveProject(projectToSave);
    assertEquals(actual.getTitle(), projectToSave.getTitle());
    assertEquals(actual.getCategory(), projectToSave.getCategory());
  }

  @Test
  public void testDeleteProject() throws Exception {
    final String id = "id";
    Project deletedProject = new Project("title", "category", "description");
    deletedProject.setDeleted(true);

    new Expectations() {{
      projectComponent.updateProjectDeletedFlag(id, true);
      returns(deletedProject);
    }};

    ProjectDTO actual = projectService.deleteProject(id);
    assertTrue(actual.isDeleted());
    assertEquals(actual.getTitle(), deletedProject.getTitle());
    assertEquals(actual.getCategory(), deletedProject.getCategory());
    assertEquals(actual.getDescription(), deletedProject.getDescription());
  }

  private List<Project> createMockedProjects(int numProjects, String category) {
    List<Project> projects = new ArrayList<>();
    for (int i = 0; i < numProjects; i++) {
      projects.add(new Project("title" + i, category, "desc2" + i));
    }
    return projects;
  }

  private MockUp<ProjectUtils> mockConvertFromDomain(Project document) {
    return new MockUp<ProjectUtils>() {
      @Mock
      Project convertFromDomain(ProjectDTO projectDTO) {
        return document;
      }
    };
  }
}