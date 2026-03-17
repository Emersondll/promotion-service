package com.abinbev.b2b.promotion.domain.model;

import java.util.HashMap;
import java.util.Map;

public class OrderBy {
  private Map<String, OrderByDirection> fields = new HashMap();

  public OrderBy() {}

  public OrderBy(final String field, final OrderByDirection direction) {
    this.fields.put(field, direction);
  }

  public Map<String, OrderByDirection> getFields() {
    return fields;
  }

  public void setFields(Map<String, OrderByDirection> fields) {
    this.fields = fields;
  }
}
