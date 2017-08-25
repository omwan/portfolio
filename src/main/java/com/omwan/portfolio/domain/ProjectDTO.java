package com.omwan.portfolio.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

/**
 * Domain object to represent project information.
 *
 * Created by olivi on 07/16/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDTO {

  private String id;
  private String title;
  private String category;
  private String description;
  private String notes;
  private List<String> technologies;
  private List<ProjectLinkDTO> links;
  private boolean isDeleted;
  private boolean isPublic;
  private boolean isLocked;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM")
  private Date startDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM")
  private Date endDate;

  public ProjectDTO() {

  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public List<String> getTechnologies() {
    return technologies;
  }

  public void setTechnologies(List<String> technologies) {
    this.technologies = technologies;
  }

  public List<ProjectLinkDTO> getLinks() {
    return links;
  }

  public void setLinks(List<ProjectLinkDTO> links) {
    this.links = links;
  }

  public boolean isDeleted() {
    return isDeleted;
  }

  public void setDeleted(boolean deleted) {
    this.isDeleted = deleted;
  }

  public boolean isPublic() {
    return isPublic;
  }

  public void setPublic(boolean aPublic) {
    isPublic = aPublic;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public boolean isLocked() {
    return isLocked;
  }

  public void setLocked(boolean locked) {
    isLocked = locked;
  }
}
