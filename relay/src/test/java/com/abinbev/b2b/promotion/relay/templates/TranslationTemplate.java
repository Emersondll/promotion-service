package com.abinbev.b2b.promotion.relay.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.abinbev.b2b.promotion.relay.domain.Translation;

public class TranslationTemplate implements TemplateLoader {

  public static final String US_VALID = "us-valid";
  public static final String US_INVALID = "us-invalid";

  @Override
  public void load() {
    Fixture.of(Translation.class)
        .addTemplate(
            US_VALID,
            new Rule() {
              {
                add("title", regex("\\w{8}"));
                add("description", regex("\\w{8}"));
                add("image", "http://${title}.com");
              }
            })
        .addTemplate(US_INVALID)
        .inherits(
            US_VALID,
            new Rule() {
              {
                add("title", "");
              }
            });
  }
}
