package com.abinbev.b2b.promotion.services;

import com.abinbev.b2b.promotion.domain.model.Pagination;
import com.abinbev.b2b.promotion.exceptions.BadRequestException;
import com.abinbev.b2b.promotion.exceptions.NotFoundException;
import com.abinbev.b2b.promotion.rest.vo.GetPromotionsVO;

public class ServiceValidations {

  private ServiceValidations() {}

  static void validatePromotionsNotFound(final GetPromotionsVO getPromotionsVO) {

    if (!getPromotionsVO.getPromotions().isEmpty()) {
      return;
    }

    throw NotFoundException.noPromotionsFound();
  }

  static void validatePagination(final Pagination pagination) {

    if (!pagination.isValidPageSize()) {
      throw BadRequestException.invalidPageSize();
    }

    if (!pagination.isValidPageNumber()) {
      throw BadRequestException.invalidPageNumber();
    }
  }
}
