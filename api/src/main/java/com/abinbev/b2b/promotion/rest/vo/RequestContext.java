package com.abinbev.b2b.promotion.rest.vo;

import static com.abinbev.b2b.promotion.v2.helpers.LocaleHelper.validOrEmptyLocale;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import com.abinbev.b2b.promotion.properties.NonOfficialSupportedLanguages;

public class RequestContext {
  private final String country;
  private final String acceptLanguage;

  private RequestContext(final RequestContextBuilder builder) {
    this.country = builder.country;
    this.acceptLanguage = builder.acceptLanguage;
  }

  public String getCountry() {
    return country;
  }

  public String getAcceptLanguage() {
    return acceptLanguage;
  }

  public static RequestContextBuilder builder() {
    return new RequestContextBuilder();
  }

  public static class RequestContextBuilder {

    private List<String> nonOfficialSupportedLanguages;
    private String country;
    private String acceptLanguage;

    public RequestContext build() {
      return new RequestContext(this);
    }

    public RequestContextBuilder withCountry(final String country) {
      this.country = country;
      return this;
    }

    public RequestContextBuilder withNonOfficialSupportedLanguages(final List<String> nonOfficialSupportedLanguages) {
      this.nonOfficialSupportedLanguages = nonOfficialSupportedLanguages;
      return this;
    }

    public RequestContextBuilder withAcceptLanguage(final String acceptLanguage) {

      final List<String> nonOfficialSupportedLanguagesNullSafe = nonOfficialSupportedLanguages == null ? Collections.emptyList() : nonOfficialSupportedLanguages;

      this.acceptLanguage = validOrEmptyLocale(acceptLanguage, nonOfficialSupportedLanguagesNullSafe);
      return this;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof RequestContext)) return false;
    RequestContext that = (RequestContext) o;
    return Objects.equals(country, that.country)
        && Objects.equals(acceptLanguage, that.acceptLanguage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(country, acceptLanguage);
  }
}
