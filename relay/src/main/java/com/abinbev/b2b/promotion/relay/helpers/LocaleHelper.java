package com.abinbev.b2b.promotion.relay.helpers;

import com.abinbev.b2b.promotion.relay.constants.LogConstant;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.LocaleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocaleHelper {

  private static final Logger LOGGER = LoggerFactory.getLogger(LocaleHelper.class);

  private LocaleHelper() {}

  public static boolean isValidOrEmpty(
      final String value, final List<String> nonOfficialSupportedLanguageList) {
    if (StringUtils.isEmpty(value)) {
      return true;
    }
    try {
      return isLocaleValid(value, nonOfficialSupportedLanguageList);
    } catch (Exception ex) {
      LOGGER.error(LogConstant.ERROR.INVALID_ACCEPT_LANGUAGE, value);
      return false;
    }
  }

  public static boolean isLocaleSetValid(
      final Set<String> values, final List<String> nonOfficialSupportedLanguageList) {
    try {
      return values.stream()
          .allMatch(value -> isLocaleValid(value, nonOfficialSupportedLanguageList));
    } catch (Exception ex) {
      LOGGER.error(LogConstant.ERROR.INVALID_ACCEPT_LANGUAGE, values);
      return false;
    }
  }

  private static boolean isLocaleValid(
      final String value, final List<String> nonOfficialSupportedLanguageList) {
    final Locale locale = Locale.forLanguageTag(value);
    return StringUtils.isNotBlank(locale.getISO3Language())
        && StringUtils.isNotBlank(locale.getISO3Country())
        && (nonOfficialSupportedLanguageList.contains(value)
            || LocaleUtils.isAvailableLocale(locale));
  }

  public static String normalizeValid(
      String value, final List<String> nonOfficialSupportedLanguageList) {
    if (isValidOrEmpty(value, nonOfficialSupportedLanguageList) && StringUtils.isNotBlank(value)) {
      return Locale.forLanguageTag(value).toLanguageTag();
    } else {
      return value;
    }
  }
}
