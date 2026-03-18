package com.abinbev.b2b.promotion.v2.helpers;

import com.abinbev.b2b.promotion.constants.LogConstant;
import com.abinbev.b2b.promotion.constants.LogConstant.INFO;

import java.util.List;
import java.util.Locale;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.LocaleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.abinbev.b2b.promotion.properties.NonOfficialSupportedLanguages;

public final class LocaleHelper {

  private LocaleHelper() {}

  private static final Logger LOGGER = LoggerFactory.getLogger(LocaleHelper.class);

  public static String validOrEmptyLocale(final String language, final List<String> nonOfficialSupportedLanguagesList) {
    try {

      if (StringUtils.isBlank(language)) {
        LOGGER.debug(INFO.EMPTY_ACCEPT_LANGUAGE);
        return StringUtils.EMPTY;
      }

      final Locale locale = Locale.forLanguageTag(language);

      if (!isValidLocale(language, nonOfficialSupportedLanguagesList)) {
        LOGGER.error(LogConstant.ERROR.INVALID_ACCEPT_LANGUAGE, language);
        return StringUtils.EMPTY;
      }

      return locale.toLanguageTag();
    } catch (final Exception ex) {
      LOGGER.error(LogConstant.ERROR.UNEXPECTED_ACCEPT_LANGUAGE, language, ex);
      return StringUtils.EMPTY;
    }
  }

  private static boolean isValidLocale(final String language, final List<String> nonOfficialSupportedLanguagesList) {
    final Locale locale = Locale.forLanguageTag(language);
    return StringUtils.isNotBlank(locale.getISO3Language())
        && StringUtils.isNotBlank(locale.getISO3Country())
        && (nonOfficialSupportedLanguagesList.contains(language) || LocaleUtils.isAvailableLocale(locale));
  }
}
