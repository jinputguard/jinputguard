package io.github.jinputguard.builder.base.types.array;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ArrayProcessEachTest {

	@Nested
	class Sanitization {

		@Test
		void nominal() {
			var elementGuard = InputGuard.builder().forString().strip().build();
			var guard = InputGuard.builder().forArrayOf(String.class)
				.processEach(elementGuard)
				.build();

			var value = new String[] { "", " ", " a", "b ", " c " };
			var actualResult = guard.processToResult(value, "myVal");

			var expectedValue = new String[] { "", "", "a", "b", "c" };
			GuardResultAssert.assertThat(actualResult)
				.isSuccess(expectedValue)
				.isNotSameAs(value);
		}

		@Test
		void nominal_withNullDefaults() {
			var elementGuard = InputGuard.builder().forString()
				.ifNullThen().useDefault("!")
				.strip()
				.build();
			var listGuard = InputGuard.builder().forArrayOf(String.class)
				.processEach(elementGuard)
				.build();

			var value = new String[] { "", " ", " a", "b ", " c ", null };
			var actualResult = listGuard.processToResult(value, "myVal");

			var expectedValue = new String[] { "", "", "a", "b", "c", "!" };
			GuardResultAssert.assertThat(actualResult)
				.isSuccess(expectedValue)
				.isNotSameAs(value);
		}

	}

	@Nested
	class Validation {

		@Test
		void validation_all_success() {
			var elementGuard = InputGuard.builder().forString().requireNotNull().build();
			var guard = InputGuard.builder().forArrayOf(String.class)
				.processEach(elementGuard)
				.build();

			var value = new String[] { "", " ", " a", "b ", " c " };
			var actualResult = guard.processToResult(value, "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isSuccess(value);
		}

		@Test
		void singleFailures() {
			var elementGuard = InputGuard.builder().forString().requireNotEmpty().build();
			var guard = InputGuard.builder().forArrayOf(String.class)
				.processEach(elementGuard)
				.build();

			var value = new String[] { "", "abc", "123" };
			var actualResult = guard.processToResult(value, "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage(
					"""
						myVal contains 1 illegal element:
						  - myVal[0] must not be empty"""
				);
		}

		@Test
		void multipleFailures() {
			var elementGuard = InputGuard.builder().forString().requireLength(3).build();
			var listGuard = InputGuard.builder().forArrayOf(String.class)
				.processEach(elementGuard)
				.build();

			var value = new String[] { "a", "abc", "b", "123", "c" };
			var actualResult = listGuard.processToResult(value, "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage(
					"""
						myVal contains 3 illegal elements:
						  - myVal[0] must be exactly 3 characters long
						  - myVal[2] must be exactly 3 characters long
						  - myVal[4] must be exactly 3 characters long"""
				);
		}

	}

}
