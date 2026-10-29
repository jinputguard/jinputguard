package io.github.jinputguard.builder.base.types;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ObjectSanitizationTest {

	@Test
	void when_exception() {
		var exception = new RuntimeException("any runtime exception happening :-/");
		var guard = InputGuard.builder().forClass(Object.class)
			.sanitize(value -> {
				throw exception;
			})
			.build();
		Assertions.assertThatRuntimeException()
			.isThrownBy(() -> guard.process(new Object(), "myVal"))
			.isEqualTo(exception);
	}

	@Nested
	class ApplyTest {

		private static final Object NEW_OBJECT = new Object();
		private static InputGuard<Object, Object> GUARD;

		@BeforeAll
		static void setup() {
			GUARD = InputGuard.builder().forClass(Object.class)
				.sanitize().apply(v -> NEW_OBJECT).then()
				.build();
		}

		@Test
		void when_applyWithNull_then_function_is_called() {
			var actualResult = GUARD.process(null, "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess(NEW_OBJECT);
		}

		@Test
		void when_apply_then_function_is_called() {
			var nonNullObject = new Object();
			var actualResult = GUARD.process(nonNullObject, "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess(NEW_OBJECT);
		}

	}

}