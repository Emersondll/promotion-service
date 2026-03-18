package com.abinbev.b2b.promotion.helpers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class OffsetDateTimeHelperTest {

  @Test
  public void testConstructor()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
          InstantiationException {

    final Constructor<OffsetDateTimeHelper> constructor =
        OffsetDateTimeHelper.class.getDeclaredConstructor();
    constructor.setAccessible(true);
    Assertions.assertNotNull(constructor.newInstance());
  }

  @Test
  public void testFromTimestamp() {

    final OffsetDateTime offsetDateTime =
        OffsetDateTimeHelper.fromTimestamp(System.currentTimeMillis());
    Assertions.assertNotNull(offsetDateTime);
  }

  @Test
  public void testFromNullTimestamp() {

    final OffsetDateTime offsetDateTime = OffsetDateTimeHelper.fromTimestamp(null);
    Assertions.assertNotNull(offsetDateTime);
  }
}
