package com.abinbev.b2b.promotion.relay.helpers;

import com.abinbev.b2b.promotion.relay.exceptions.BasicAuthorizationException;
import java.util.Base64;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SecurityHelperTest {
  SecurityHelper securityHelper = new SecurityHelper();

  private static String createAuthorization(final String json) {
    return "." + Base64.getEncoder().encodeToString(json.getBytes()) + ".";
  }

  @Test
  public void testValidBearerJwtToken() {
    final String token =
        "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJXTzJkU1dXR1RfTGhQa1hJNXFtc29FWFExYllNbkRYQTg2ajV0aEVOd21vIn0.eyJleHAiOjE2MTc5MDU0MjYsImlhdCI6MTYxNzkwMTgyNiwianRpIjoiY2ZlOGJmYTItYjUzOS00MzVmLWE3Y2YtMGZhYjM5NzA0NTlmIiwiaXNzIjoiaHR0cDovL2tleWNsb2FrLXNlcnZpY2UvYXV0aC9yZWFsbXMvYmVlcy1yZWFsbSIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJmMWVmZGVhMC1lODA4LTQ0ZjAtODQ5NS1kNGVkN2QwNGYyZmYiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiIxZGEyMmJkMy1hNjBlLTRmNDItYTIxMi0yOWJmNDRiY2ZiZWEiLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBlbWFpbCBwcm9maWxlIiwiY2xpZW50SWQiOiIxZGEyMmJkMy1hNjBlLTRmNDItYTIxMi0yOWJmNDRiY2ZiZWEiLCJjbGllbnRIb3N0IjoiMTAuMTI2LjcuMTMwIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJyb2xlcyI6WyJXcml0ZSIsIlJlYWQiXSwidmVuZG9ySWQiOiJzZmVycmVpcmEtdmVuZG9ySWQtZGV2IiwicHJlZmVycmVkX3VzZXJuYW1lIjoic2VydmljZS1hY2NvdW50LTFkYTIyYmQzLWE2MGUtNGY0Mi1hMjEyLTI5YmY0NGJjZmJlYSIsImNsaWVudEFkZHJlc3MiOiIxMC4xMjYuNy4xMzAifQ.SiTteI8wHdn8gm5jAGeGadABczldqwC6BmlHngZl-BWIKhD5CQgbv5Awu7f4e2iA57I_IL1eZo0Iq8XJSpDc2SQggFtaAyOFXXe47cZMUMzFMI67qN1GvwxR6f9mrG7F_CkJgtixaJ-QC4Iox1HiI673_YtWuRrgbH6TCETbG1JnSKzZQjVMnzK_6K4yMu7Dxo9kIT6hqlFGVNAYXeySjiw72G9YP0Vr0r3fQjdoKE2am07fEXcTp5Et7FRUFJ9GB9Xqk4LpDvdhmgGcu05AnVocAlC6URsW-R-mZWJWXeIdxhi3_fFOIUkt78cD0GbbtOs9ILA9gMMvpE8vg8Hg_A";
    final String vendorId = securityHelper.getValidVendorId(token);
    Assertions.assertEquals("sferreira-vendorId-dev", vendorId);
  }

  @Test
  public void testNullInput() {
    Assertions.assertThrows(
        BasicAuthorizationException.class,
        () -> {
          securityHelper.getValidVendorId(null);
        });
  }

  @Test
  public void testEmptyInput() {
    Assertions.assertThrows(
        BasicAuthorizationException.class,
        () -> {
          securityHelper.getValidVendorId(createAuthorization(""));
        });
  }

  @Test
  public void testEmptyObjectInput() {
    Assertions.assertThrows(
        BasicAuthorizationException.class,
        () -> {
          securityHelper.getValidVendorId(createAuthorization("{}"));
        });
  }

  @Test
  public void testUndefinedObjectVendorInput() {
    Assertions.assertThrows(
        BasicAuthorizationException.class,
        () -> {
          securityHelper.getValidVendorId(createAuthorization("{\"vendorId\":undefined}"));
        });
  }

  @Test
  public void testNullObjectVendorInput() {
    Assertions.assertThrows(
        BasicAuthorizationException.class,
        () -> {
          securityHelper.getValidVendorId(createAuthorization("{\"vendorId\":null}"));
        });
  }

  @Test
  public void testEmptyObjectVendorInput() {
    Assertions.assertThrows(
        BasicAuthorizationException.class,
        () -> {
          securityHelper.getValidVendorId(createAuthorization("{\"vendorId\":\"\"}"));
        });
  }
}
