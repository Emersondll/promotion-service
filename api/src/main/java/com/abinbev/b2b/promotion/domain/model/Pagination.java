package com.abinbev.b2b.promotion.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pagination {

  private static final Integer DEFAULT_PAGE = 0;
  private static final Integer DEFAULT_SIZE = 50;

  /** This page number */
  private Integer page;

  /** Size of each page */
  private Integer size;

  /** Number of pages */
  private Integer totalPages;

  public Pagination(final Integer page, final Integer size) {

    this.page = Optional.ofNullable(page).orElse(DEFAULT_PAGE);
    this.size = Optional.ofNullable(size).orElse(DEFAULT_SIZE);
  }

  public Pagination() {}

  @JsonIgnore
  public boolean isPaginationRequested() {

    return page != null && size != null;
  }

  @JsonIgnore
  public boolean isValidPageSize() {

    return size == null || size > 0;
  }

  @JsonIgnore
  public boolean isValidPageNumber() {

    return page == null || page >= 0;
  }

  public Integer getPage() {

    return page;
  }

  public void setPage(final Integer page) {

    this.page = page;
  }

  public Integer getSize() {

    return size;
  }

  public void setSize(final Integer size) {

    this.size = size;
  }

  public Integer getTotalPages() {

    return totalPages;
  }

  public void setTotalPages(final Integer totalPages) {

    this.totalPages = totalPages;
  }

  @Override
  public boolean equals(final Object o) {

    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final Pagination that = (Pagination) o;

    if (page != null ? !page.equals(that.page) : that.page != null) {
      return false;
    }
    if (size != null ? !size.equals(that.size) : that.size != null) {
      return false;
    }
    return totalPages != null ? totalPages.equals(that.totalPages) : that.totalPages == null;
  }

  @Override
  public int hashCode() {

    int result = page != null ? page.hashCode() : 0;
    result = 31 * result + (size != null ? size.hashCode() : 0);
    result = 31 * result + (totalPages != null ? totalPages.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {

    final StringBuilder report = new StringBuilder("Pagination{");
    report.append("page=").append(page);
    report.append(", size='").append(size);
    report.append(", totalPages=").append(totalPages);

    return report.toString();
  }
}
