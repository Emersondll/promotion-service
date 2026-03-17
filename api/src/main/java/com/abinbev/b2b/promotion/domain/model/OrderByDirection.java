package com.abinbev.b2b.promotion.domain.model;

import org.springframework.data.domain.Sort.Direction;

public enum OrderByDirection {
  ASC(Direction.ASC),
  DESC(Direction.DESC);

  private final Direction direction;

  private OrderByDirection(Direction direction) {
    this.direction = direction;
  }

  public Direction getDirection() {
    return direction;
  }
}
