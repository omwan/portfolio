package com.omwan.portfolio.component;

import com.omwan.portfolio.domain.ProjectCategory;
import com.omwan.portfolio.mongo.document.Project;
import com.omwan.portfolio.mongo.repository.ProjectRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DuplicateKeyException;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;

import static org.junit.Assert.assertTrue;

/**
 * Class to test methods for component to call repository methods.
 *
 * Created by olivi on 08/25/2017.
 */
@RunWith(JMockit.class)
public class ProjectComponentTest {

  @Tested
  private ProjectComponent projectComponent;

  @Injectable
  private ProjectRepository projectRepository;

  @Before
  public void setup() {
    projectComponent = new ProjectComponent();
  }

  /**
   * Asserts that if a project has the locked flag enabled, the project cannot be saved.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSaveLockedProject() throws Exception {
    final Project lockedProject = new Project();
    lockedProject.setLocked(true);
    projectComponent.saveProject(lockedProject);
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

    projectComponent.saveProject(new Project());
  }

  @Test
  public void testSaveProject() throws Exception {

  }

  /**
   * Asserts that if a project has the locked flag enabled, the project cannot be deleted.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testDeleteLockedProject() throws Exception {
    final Project lockedProject = new Project();
    lockedProject.setLocked(true);

    final String id = "1";

    new Expectations() {{
      projectRepository.findOne(id);
      returns(lockedProject);
    }};

    projectComponent.updateProjectDeletedFlag(id, true);
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

    projectComponent.updateProjectDeletedFlag(id, true);
  }

  @Test
  public void testDeleteProject() throws Exception {
    final Project projectToDelete = new Project();
    String title = "project title";
    ProjectCategory category = ProjectCategory.WORK;
    projectToDelete.setTitle(title);
    projectToDelete.setCategory(category.toString());
    projectToDelete.setId("1");
    projectToDelete.setDeleted(false);

    new Expectations() {{
      projectRepository.findOne("1");
      returns(projectToDelete);

      projectRepository.save(projectToDelete);
      returns(projectToDelete);
    }};

    Project actual = projectComponent.updateProjectDeletedFlag(projectToDelete.getId(), true);
    assertTrue(actual.isDeleted());
  }
}