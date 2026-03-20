package com.abinbev.b2b.promotion.relay.config.properties;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NonOfficialSupportedLanguages {

  @Value("${nonOfficialSupportedLanguages}")
  private List<String> nonOfficialSupportedLanguages;

  public List<String> getNonOfficialSupportedLanguages() {
    return nonOfficialSupportedLanguages;
  }
}
