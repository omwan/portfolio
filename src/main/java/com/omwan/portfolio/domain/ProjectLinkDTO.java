package com.omwan.portfolio.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by olivi on 08/23/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectLinkDTO {

  private String title;
  private String href;

  public ProjectLinkDTO() {

  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }
}
