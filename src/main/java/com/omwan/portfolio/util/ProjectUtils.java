package com.omwan.portfolio.util;

import com.omwan.portfolio.domain.ProjectDTO;
import com.omwan.portfolio.mongo.document.Project;

import org.springframework.beans.BeanUtils;

/**
 * Util methods for project services.
 * todo replace these with getter/setter calls
 *
 * Created by olivi on 07/16/2017.
 */
public class ProjectUtils {

  public static ProjectDTO convertFromDocument(Project document) {
    ProjectDTO domain = new ProjectDTO();
    BeanUtils.copyProperties(document, domain);
    return domain;
  }

  public static Project convertFromDomain(ProjectDTO domain) {
    Project document = new Project();
    BeanUtils.copyProperties(domain, document);
    return document;
  }
}
