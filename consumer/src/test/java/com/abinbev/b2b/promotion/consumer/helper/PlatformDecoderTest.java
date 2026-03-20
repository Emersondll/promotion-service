package com.abinbev.b2b.promotion.consumer.helper;

import static org.assertj.core.api.Assertions.assertThat;

import com.abinbev.b2b.commons.platformId.core.PlatformIdEncoderDecoder;
import com.abinbev.b2b.commons.platformId.core.enuns.PlatformIdEnum;
import com.abinbev.b2b.commons.platformId.core.vo.PromotionPlatformId;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class PlatformDecoderTest {

  private static final String VENDOR_ID_1 = "d27407a1-84af-4d09-bb7b-29b1ae2ff3cb";
  private static final String VENDOR_PROMOTION_ID_1 = "PROMOTION_SC";
  private static final String VENDOR_PROMOTION_ID_2 = "PROMOTION_SC_1";
  private static final String PLATFORM_ID_1 =
      "MG5RSG9ZU3ZUUW03ZXlteHJpL3p5dz09O1BST01PVElPTl9TQw==";
  private static final String PLATFORM_ID_1_URL_ENCODED =
      "MG5RSG9ZU3ZUUW03ZXlteHJpL3p5dz09O1BST01PVElPTl9TQw%3D%3D";
  private static final String PLATFORM_ID_2 =
      "MG5RSG9ZU3ZUUW03ZXlteHJpL3p5dz09O1BST01PVElPTl9TQ18x";

  private final PlatformIdEncoderDecoder platformIdHelper = new PlatformIdEncoderDecoder();

  @Test
  public void testUrlDecodeAndEncode() {
    assertThat(URLEncoder.encode(PLATFORM_ID_1, StandardCharsets.UTF_8))
        .isEqualTo(PLATFORM_ID_1_URL_ENCODED);
    assertThat(URLDecoder.decode(PLATFORM_ID_1_URL_ENCODED, StandardCharsets.UTF_8))
        .isEqualTo(PLATFORM_ID_1);

    assertThat(URLEncoder.encode(PLATFORM_ID_2, StandardCharsets.UTF_8)).isEqualTo(PLATFORM_ID_2);
    assertThat(URLDecoder.decode(PLATFORM_ID_2, StandardCharsets.UTF_8)).isEqualTo(PLATFORM_ID_2);
  }

  @Test
  public void testShouldEncode() {
    assertThat(
            platformIdHelper.encodePlatformIdURLSafe(
                new PromotionPlatformId(VENDOR_ID_1, VENDOR_PROMOTION_ID_1)))
        .isEqualTo(PLATFORM_ID_1_URL_ENCODED);

    assertThat(
            platformIdHelper.encodePlatformId(
                new PromotionPlatformId(VENDOR_ID_1, VENDOR_PROMOTION_ID_2)))
        .isEqualTo(PLATFORM_ID_2);
  }

  @Test
  public void testShouldDecode() {
    assertThat(platformIdHelper.decodePlatformId(PLATFORM_ID_1, PlatformIdEnum.PROMOTION))
        .usingRecursiveComparison()
        .isEqualTo(new PromotionPlatformId(VENDOR_ID_1, VENDOR_PROMOTION_ID_1));

    assertThat(
            platformIdHelper.decodePlatformIdURLSafe(
                PLATFORM_ID_1_URL_ENCODED, PlatformIdEnum.PROMOTION))
        .usingRecursiveComparison()
        .isEqualTo(new PromotionPlatformId(VENDOR_ID_1, VENDOR_PROMOTION_ID_1));

    assertThat(platformIdHelper.decodePlatformId(PLATFORM_ID_2, PlatformIdEnum.PROMOTION))
        .usingRecursiveComparison()
        .isEqualTo(new PromotionPlatformId(VENDOR_ID_1, VENDOR_PROMOTION_ID_2));
  }
}
