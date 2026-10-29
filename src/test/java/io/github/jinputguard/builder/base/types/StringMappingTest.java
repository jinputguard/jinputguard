package io.github.jinputguard.builder.base.types;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class StringMappingTest {

	@Nested
	class MapToInteger {

		private static InputGuard<String, Integer> GUARD;

		@BeforeAll
		static void setup() {
			GUARD = InputGuard.builder().forString().mapToInteger().build();
		}

		@ParameterizedTest
		@ValueSource(
			strings = {
				"-1", "0", "1", "+1",
				"-2147483648", // Integer.MIN_VALUE
				"2147483647", // Integer.MAX_VALUE
			}
		)
		void when_valid_then_success(String input) {
			var actual = GUARD.process(input, "myVal");
			GuardResultAssert.assertThat(actual).isSuccess(Integer.parseInt(input));
		}

		@ParameterizedTest
		@ValueSource(
			strings = {
				"plop",
				"123 456",
				"-2147483649", // Integer.MIN_VALUE - 1
				"2147483648", // Integer.MAX_VALUE + 1
			}
		)
		void when_invalid_then_failure(String input) {
			var actual = GUARD.process(input, "myVal");
			GuardResultAssert.assertThat(actual).isFailure()
				.hasMessage("Invalid myVal")
				.hasCauseInstanceOf(NumberFormatException.class);
		}

	}

	@Nested
	class MapToLong {

		private static InputGuard<String, Long> GUARD;

		@BeforeAll
		static void setup() {
			GUARD = InputGuard.builder().forString().mapToLong().build();
		}

		@ParameterizedTest
		@ValueSource(
			strings = {
				"-1", "0", "1", "+1",
				"-9223372036854775808", // Long.MIN_VALUE
				"9223372036854775807", // Long.MAX_VALUE
			}
		)
		void success(String input) {
			var actual = GUARD.process(input, "myVal");
			GuardResultAssert.assertThat(actual).isSuccess(Long.parseLong(input));
		}

		@ParameterizedTest
		@ValueSource(
			strings = {
				"plop",
				"123 456",
				"-9223372036854775809", // Long.MIN_VALUE - 1
				"9223372036854775808", // Long.MAX_VALUE + 1
			}
		)
		void failure(String input) {
			var actual = GUARD.process(input, "myVal");
			GuardResultAssert.assertThat(actual).isFailure()
				.hasMessage("Invalid myVal")
				.hasCauseInstanceOf(NumberFormatException.class);
		}

	}

}