package io.github.jinputguard.builder.base.types;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ObjectMappingTest {

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class MapToString {

		InputGuard<Object, String> guard = InputGuard.builder().forClass(Object.class).mapToString().build();

		Arguments[] mapToString_arguments() {
			return new Arguments[] {
				// Value, ref, success
				Arguments.of(123, "123"),
				Arguments.of(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), "123e4567-e89b-12d3-a456-426614174000"),
			};
		}

		@ParameterizedTest
		@MethodSource("mapToString_arguments")
		void mapToString(Object testedValue, String expectedValue) {
			GuardResultAssert.assertThat(guard.process(testedValue, "myVal")).as("mapToString(" + testedValue + ")").isSuccess(expectedValue);
		}

	}

	@Test
	void when_exception_then_return_failure() {
		var exception = new RuntimeException("any runtime exception happening :-/");
		var guard = InputGuard.builder().forClass(Object.class)
			.map(value -> {
				throw exception;
			}, path -> "Invalid " + path)
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
			.map(value -> outputValue, path -> "Invalid " + path)
			.build();

		var actual = guard.process(inputValue, "myVal");

		GuardResultAssert.assertThat(actual).isSuccess(outputValue);
	}

}
