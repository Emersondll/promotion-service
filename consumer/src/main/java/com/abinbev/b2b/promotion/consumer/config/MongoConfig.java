package com.abinbev.b2b.promotion.consumer.config;

import com.google.common.collect.ImmutableList;
import com.mongodb.MongoClientSettings;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

@Configuration
@Import(value = MongoAutoConfiguration.class)
public class MongoConfig {

  private static final Integer TIMEOUT = 300000;

  @Bean
  public MongoCustomConversions customConversions() {
    return new MongoCustomConversions(
        ImmutableList.of(
            DateToOffsetDateTimeConverter.INSTANCE, OffsetDateTimeToDateConverter.INSTANCE));
  }

  @ReadingConverter
  private static final class DateToOffsetDateTimeConverter
      implements Converter<Date, OffsetDateTime> {

    public static final DateToOffsetDateTimeConverter INSTANCE =
        new DateToOffsetDateTimeConverter();

    private DateToOffsetDateTimeConverter() {}

    @Override
    public OffsetDateTime convert(final Date source) {
      return source != null ? OffsetDateTime.ofInstant(source.toInstant(), ZoneOffset.UTC) : null;
    }
  }

  @WritingConverter
  private static final class OffsetDateTimeToDateConverter
      implements Converter<OffsetDateTime, Date> {

    public static final OffsetDateTimeToDateConverter INSTANCE =
        new OffsetDateTimeToDateConverter();

    private OffsetDateTimeToDateConverter() {}

    @Override
    public Date convert(final OffsetDateTime source) {
      if (source == null) {
        return null;
      }
      return Date.from(source.toInstant());
    }
  }

  @Bean
  @Primary
  public MongoClientSettings mongoOptions() {
    return MongoClientSettings.builder()
        .applyToSocketSettings(
            builder -> {
              builder.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS);
              builder.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS);
            })
        .build();
  }
}
