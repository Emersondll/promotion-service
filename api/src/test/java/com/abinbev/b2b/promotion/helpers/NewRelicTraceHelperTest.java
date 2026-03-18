package com.abinbev.b2b.promotion.helpers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;

class NewRelicTraceHelperTest {

	@Test
	void shouldNotThrowErrorWithNullValuesForIncludeRequestInfo() {

		assertDoesNotThrow(() -> NewRelicTraceHelper.includeRequestInfo(null, null));

		assertDoesNotThrow(
				() ->NewRelicTraceHelper.includeRequestInfo("requestURI", "method", "arg1", null, "arg2"));
	}

	@Test
	void shouldNotThrowErrorWithNullValuesForIncludeResponseInfo() {

		assertDoesNotThrow(
				() -> NewRelicTraceHelper.includeResponseInfo(null, null, null, null, null));

		var OK = HttpStatusCode.valueOf(200);
		assertDoesNotThrow(
				() -> NewRelicTraceHelper.includeResponseInfo(OK, 1, 10, "arg1", null, "arg3"));
	}

	@Test
	void shouldNotThrowErrorWithNullValuesForIncludeParameter() {

		assertDoesNotThrow(() -> NewRelicTraceHelper.includeParameter(null, (String) null));
		assertDoesNotThrow(() -> NewRelicTraceHelper.includeParameter(null, (Integer) null));
		assertDoesNotThrow(() -> NewRelicTraceHelper.includeParameter(null, (Boolean) null));
		assertDoesNotThrow(() -> NewRelicTraceHelper.includeParameter(null, (Object) null));

		assertDoesNotThrow(() -> NewRelicTraceHelper.includeParameter("name", "value"));
		assertDoesNotThrow(() -> NewRelicTraceHelper.includeParameter("name", 1));
		assertDoesNotThrow(() -> NewRelicTraceHelper.includeParameter("name", true));
		assertDoesNotThrow(() -> NewRelicTraceHelper.includeParameter("name", new Object()));
	}
}
