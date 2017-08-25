package com.omwan.portfolio.component;

import com.omwan.portfolio.domain.ProjectCategory;
import com.omwan.portfolio.mongo.document.Project;
import com.omwan.portfolio.mongo.repository.ProjectRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;

import static org.junit.Assert.assertTrue;

/**
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