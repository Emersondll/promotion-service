package com.abinbev.b2b.promotion.relay.config.properties;

import com.abinbev.b2b.promotion.relay.exceptions.BadRequestException;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SingleToMultiVendorConversionProperties {

  @Value("#{${sync.countries.vendorIds}}")
  private Map<String, String> vendorIds;

  public String getVendorIdByCountry(final String country) {
    final String vendorId = this.vendorIds.get(country.toUpperCase());

    if (Objects.isNull(vendorId)) {
      throw BadRequestException.countryWithoutDefaultVendor(country);
    }

    return vendorId;
  }
}
