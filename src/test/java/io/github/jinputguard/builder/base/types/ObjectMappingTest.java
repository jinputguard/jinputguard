package io.github.jinputguard.builder.base.types;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import org.junit.jupiter.api.Test;

class ObjectMappingTest {

	@Test
	void when_exception_then_return_failure() {
		var exception = new RuntimeException("any runtime exception happening :-/");
		var guard = InputGuard.builder().forClass(Object.class)
			.map(value -> {
				throw exception;
			})
			.build();

		final var value = new Object();
		var actualResult = guard.process(value, "myVal");

		GuardResultAssert.assertThat(actualResult).isFailure()
			.hasMessage("Invalid myVal")
			.hasCause(exception);
	}

	@Test
	void when_map_then_success() {
		final var inputValue = new Object();
		final var outputValue = "newOutPut";
		var guard = InputGuard.builder().forClass(Object.class)
			.map(value -> outputValue)
			.build();

		var actual = guard.process(inputValue, "myVal");

		GuardResultAssert.assertThat(actual).isSuccess(outputValue);
	}

}
