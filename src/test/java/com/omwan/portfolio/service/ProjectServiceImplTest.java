package com.omwan.portfolio.service;

import com.omwan.portfolio.domain.ProjectCategory;
import com.omwan.portfolio.domain.ProjectDTO;
import com.omwan.portfolio.mongo.document.Project;
import com.omwan.portfolio.mongo.repository.ProjectRepository;
import com.omwan.portfolio.util.ProjectUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DuplicateKeyException;

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
  private ProjectRepository projectRepository;

  @Before
  public void setup() throws Exception {
    projectService = new ProjectServiceImpl();
  }

  /**
   * Asserts that if a duplicate key exception is thrown by the repository due to having
   * a non-unique title, the service throws an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSaveProjectDuplicateKey() throws Exception {
    new Expectations() {{
      projectRepository.save((Project) any);
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
    projectToSave.setCategory(ProjectCategory.WORK);
    final Project projectAsDocument = ProjectUtils.convertFromDomain(projectToSave);
    mockConvertFromDomain(projectAsDocument);

    new Expectations() {{
      projectRepository.save(projectAsDocument);
      returns(projectAsDocument);
    }};

    ProjectDTO actual = projectService.saveProject(projectToSave);
    assertEquals(actual.getTitle(), projectToSave.getTitle());
    assertEquals(actual.getCategory(), projectToSave.getCategory());
  }

  /**
   * Asserts that if there is no project in the collection for the given ID, the
   * service throws an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testDeleteProjectInvalidKey() throws Exception {
    final String id = "id";
    new Expectations() {{
      projectRepository.findOne(id);
      returns(null);
    }};

    projectService.deleteProject(id);
  }

  @Test
  public void testDeleteProject() throws Exception {
    final ProjectDTO projectToDelete = new ProjectDTO();
    String title = "project title";
    ProjectCategory category = ProjectCategory.WORK;
    projectToDelete.setTitle(title);
    projectToDelete.setCategory(category);
    projectToDelete.setId("1");
    projectToDelete.setDeleted(false);

    Project projectAsDocument = new Project();
    projectAsDocument.setTitle(title);
    projectAsDocument.setCategory(category);
    projectAsDocument.setId("1");
    mockConvertFromDomain(projectAsDocument);

    new Expectations() {{
      projectRepository.findOne("1");
      returns(projectAsDocument);

      projectRepository.save(projectAsDocument);
      returns(projectAsDocument);
    }};

    ProjectDTO actual = projectService.deleteProject(projectToDelete.getId());
    assertTrue(actual.isDeleted());
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