package com.abinbev.b2b.promotion.relay.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Translation {

  @NotBlank private String title;

  @Size(min = 1)
  private String description;

  @Size(min = 1)
  private String image;

  public Translation() {}

  public Translation(final String title, final String description, final String image) {
    this.title = title;
    this.description = description;
    this.image = image;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }
}
