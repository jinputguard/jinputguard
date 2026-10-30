package io.github.jinputguard.builder.base.types;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class StringMappingTest {

	@Nested
	class ParseToInteger {

		private static InputGuard<String, Integer> GUARD;

		@BeforeAll
		static void setup() {
			GUARD = InputGuard.builder().forString().parseToInt().build();
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
				.hasMessage("myVal cannot be parsed to integer")
				.hasCauseInstanceOf(NumberFormatException.class);
		}

	}

	@Nested
	class ParseToLong {

		private static InputGuard<String, Long> GUARD;

		@BeforeAll
		static void setup() {
			GUARD = InputGuard.builder().forString().parseToLong().build();
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
				.hasMessage("myVal cannot be parsed to long")
				.hasCauseInstanceOf(NumberFormatException.class);
		}

	}

	@Nested
	class ParseToFloat {

		private static InputGuard<String, Float> GUARD;

		@BeforeAll
		static void setup() {
			GUARD = InputGuard.builder().forString().parseToFloat().build();
		}

		@ParameterizedTest
		@ValueSource(
			strings = {
				"-1", "0", "1", "+1",
			}
		)
		void success(String input) {
			var actual = GUARD.process(input, "myVal");
			GuardResultAssert.assertThat(actual).isSuccess(Float.parseFloat(input));
		}

		@ParameterizedTest
		@ValueSource(
			strings = {
				"plop",
				"123 456",
			}
		)
		void failure(String input) {
			var actual = GUARD.process(input, "myVal");
			GuardResultAssert.assertThat(actual).isFailure()
				.hasMessage("myVal cannot be parsed to float")
				.hasCauseInstanceOf(NumberFormatException.class);
		}

	}

	@Nested
	class ParseToDouble {

		private static InputGuard<String, Double> GUARD;

		@BeforeAll
		static void setup() {
			GUARD = InputGuard.builder().forString().parseToDouble().build();
		}

		@ParameterizedTest
		@ValueSource(
			strings = {
				"-1", "0", "1", "+1",
			}
		)
		void success(String input) {
			var actual = GUARD.process(input, "myVal");
			GuardResultAssert.assertThat(actual).isSuccess(Double.parseDouble(input));
		}

		@ParameterizedTest
		@ValueSource(
			strings = {
				"plop",
				"123 456",
			}
		)
		void failure(String input) {
			var actual = GUARD.process(input, "myVal");
			GuardResultAssert.assertThat(actual).isFailure()
				.hasMessage("myVal cannot be parsed to double")
				.hasCauseInstanceOf(NumberFormatException.class);
		}

	}

	@Nested
	class ParseToUUID {

		private static InputGuard<String, UUID> GUARD;

		@BeforeAll
		static void setup() {
			GUARD = InputGuard.builder().forString().parseToUUID().build();
		}

		@ParameterizedTest
		@ValueSource(
			strings = {
				"00000000-0000-0000-0000-000000000000", // nil
				"8045c8c0-d4a7-11f1-a500-a3940c7a539f", // v1 -> Time-based UUID
				"3437ef8f-d4a8-21f1-bd00-b12f77154a97", // v2 -> DCE security UUID
				"64a4e8fa-ed1a-3aa0-bf8b-f0fc84938d25", // v3 -> Name-based, MD5 - from UUID.nameUUIDFromBytes("plop".getBytes())
				"dd2b94bf-b810-4d90-a05e-3f2bd5b6413b", // v4 Randomly generated UUID
				"fedb2fa3-8f5c-5189-80e6-f563dd1cb8f9", // v5 -> Name-based, SHA-1
				"1f1d4a7e-d6d0-6d50-abdf-2e9f804be071", // v6
				"01a18eae-5fd2-7690-8db8-16e674eda3de", // v7 Unix Epoch time-based UUID
				"2489e9ad-2ee2-8e00-8ec9-32d5f69181c0", // v8
			}
		)
		void success(String input) {
			var actual = GUARD.process(input, "myVal");
			GuardResultAssert.assertThat(actual).isSuccess(UUID.fromString(input));
		}

		@ParameterizedTest
		@ValueSource(
			strings = {
				"plop",
				"123 456",
			}
		)
		void failure(String input) {
			var actual = GUARD.process(input, "myVal");
			GuardResultAssert.assertThat(actual).isFailure()
				.hasMessage("myVal cannot be parsed to UUID")
				.hasCauseInstanceOf(IllegalArgumentException.class);
		}

	}

}