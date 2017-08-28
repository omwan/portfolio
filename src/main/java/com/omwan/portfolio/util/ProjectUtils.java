package com.omwan.portfolio.util;

import com.omwan.portfolio.domain.ProjectDTO;
import com.omwan.portfolio.mongo.document.Project;

import org.springframework.beans.BeanUtils;

/**
 * Util methods for project services.
 *
 * Created by olivi on 07/16/2017.
 */
public class ProjectUtils {

  /**
   * Convert a Mongo entity to a domain object.
   * TODO: replace reflection method with getter/setter methods.
   *
   * @param document entity to convert
   * @return converted domain object
   */
  public static ProjectDTO convertFromDocument(Project document) {
    ProjectDTO domain = new ProjectDTO();
    BeanUtils.copyProperties(document, domain);
    return domain;
  }

  /**
   * Convert a domain object to a Mongo entity.
   * TODO: replace reflection method with getter/setter methods.
   *
   * @param domain domain object to convert
   * @return converted mongo entity
   */
  public static Project convertFromDomain(ProjectDTO domain) {
    Project document = new Project();
    BeanUtils.copyProperties(domain, document);
    return document;
  }

  /**
   * Compare the dates of two projects for sorting, by descending endDate. A null end
   * date is interpreted as "present", ie: still ongoing, and therefore comes before
   * a non-null end date.
   *
   * @param p1 first project to compare
   * @param p2 second project to compare
   * @return integer representing which one has an earlier end date.
   */
  public static int compareDates(ProjectDTO p1, ProjectDTO p2) {
    if (p1.getEndDate() == null || p2.getEndDate() == null) {
      if (p1.getEndDate() == null && p2.getEndDate() != null) {
        return -1;
      } else if (p1.getEndDate() != null && p2.getEndDate() == null) {
        return 1;
      } else {
        return 0;
      }
    } else {
      return p2.getEndDate().compareTo(p1.getEndDate());
    }
  }
}
