package io.github.jinputguard.builder.base.types;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ObjectSanitizationTest {

	@Test
	void when_sanitize_then_function_is_called() {
		final Object newObject = new Object();
		var guard = InputGuard.builder().forClass(Object.class).sanitize(v -> newObject).build();

		var actualResult = guard.processToResult(new Object(), "myVal");

		GuardResultAssert.assertThat(actualResult).isSuccess(newObject);
	}

	@Test
	void when_sanitizeWithNullInput_then_function_is_called() {
		final Object newObject = new Object();
		var guard = InputGuard.builder().forClass(Object.class).sanitize(v -> newObject).build();

		var actualResult = guard.processToResult(null, "myVal");

		GuardResultAssert.assertThat(actualResult).isSuccess(newObject);
	}

	@Test
	void when_sanitize_throw_exception() {
		var exception = new RuntimeException("any runtime exception happening :-/");
		var guard = InputGuard.builder().forClass(Object.class)
			.sanitize(value -> {
				throw exception;
			})
			.build();
		Assertions.assertThatRuntimeException()
			.isThrownBy(() -> guard.processToResult(new Object(), "myVal"))
			.isEqualTo(exception);
	}

}