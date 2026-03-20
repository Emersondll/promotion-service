package com.abinbev.b2b.promotion.consumer.assertion;

import com.abinbev.b2b.promotion.consumer.domain.PromotionModel;
import java.util.List;
import org.junit.jupiter.api.Assertions;

public class PromotionModelAssertion {

  public static void assertEquals(List<PromotionModel> expected, List<PromotionModel> result) {
    Assertions.assertNotNull(result);
    int index = 0;
    for (PromotionModel promotionModel : result) {
      assertEquals(expected.get(index), promotionModel);
      ++index;
    }
  }

  public static void assertEquals(PromotionModel expected, PromotionModel result) {

    Assertions.assertNotNull(result);
    Assertions.assertEquals(expected.getTitle(), result.getTitle());
    Assertions.assertEquals(expected.getBudget(), result.getBudget());
    Assertions.assertEquals(expected.getQuantityLimit(), expected.getQuantityLimit());
    Assertions.assertEquals(expected.getDescription(), result.getDescription());
    Assertions.assertEquals(expected.getEndDate(), result.getEndDate());
    Assertions.assertEquals(expected.getId(), result.getId());
    Assertions.assertEquals(expected.getPromotionId(), result.getPromotionId());
    Assertions.assertEquals(expected.getPromotionType(), result.getPromotionType());
    Assertions.assertEquals(expected.getStartDate(), result.getStartDate());
    Assertions.assertEquals(expected.getImage(), result.getImage());
  }
}
