package com.abinbev.b2b.promotion.v3.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Paged<T> {
  private final List<T> content;
  private final Pagination pagination;

  public static <T> Paged<T> empty() {
    return new Paged<>(0);
  }

  public Paged(int page) {
    this.content = new ArrayList<>();
    this.pagination = new Pagination(page);
  }

  public Paged(List<T> content, Pagination pagination) {
    this.content = Optional.ofNullable(content).orElse(new ArrayList<>());
    this.pagination = pagination;
  }

  public Paged(List<T> content, boolean hasNext, int page) {
    this.content = Optional.ofNullable(content).orElse(new ArrayList<>());
    this.pagination = new Pagination(page, hasNext);
  }

  public List<T> getContent() {
    return content;
  }

  public Pagination getPagination() {
    return pagination;
  }

  @JsonIgnore
  public boolean isEmpty() {
    return content.isEmpty();
  }
}
