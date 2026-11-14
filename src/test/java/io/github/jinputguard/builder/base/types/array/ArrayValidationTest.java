package io.github.jinputguard.builder.base.types.array;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import java.util.function.BiPredicate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ArrayValidationTest {

	@Nested
	class RequireNotEmpty {

		@Test
		void when_one_element_then_success() {
			var guard = InputGuard.builder().forArrayOf(String.class).requireNotEmpty().build();
			var actualResult = guard.processToResult(new String[] { "one" }, "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_is_empty_then_failure() {
			var guard = InputGuard.builder().forArrayOf(String.class).requireNotEmpty().build();
			var actualResult = guard.processToResult(new String[] {}, "myVal");
			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("myVal must not be empty");
		}

	}

	@Nested
	class RequireLength {

		@Test
		void negative_length_throws_exception() {
			Assertions.assertThatIllegalArgumentException()
				.as("Min value must be lower or equal to max value")
				.isThrownBy(() -> InputGuard.builder().forArrayOf(String.class).requireLength(-1).build())
				.withMessageContaining("length cannot be negative");
		}

		@Test
		void nominal_success() {
			var guard = InputGuard.builder().forArrayOf(String.class).requireLength(2).build();
			var actualResult = guard.processToResult(new String[] { "a", "b" }, "myVal");
			GuardResultAssert.assertThat(actualResult)
				.isSuccess(new String[] { "a", "b" });
		}

		@Test
		void when_too_short_then_failure() {
			var guard = InputGuard.builder().forArrayOf(String.class).requireLength(2).build();
			var actualResult = guard.processToResult(new String[] { "a" }, "myVal");
			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("myVal must have exactly 2 elements");
		}

		@Test
		void when_too_long_then_failure() {
			var guard = InputGuard.builder().forArrayOf(String.class).requireLength(2).build();
			var actualResult = guard.processToResult(new String[] { "a", "b", "c" }, "myVal");
			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("myVal must have exactly 2 elements");
		}

	}

	@Nested
	class RequireMinLength {

		@Test
		void negative_length_throws_exception() {
			Assertions.assertThatIllegalArgumentException()
				.as("Min value must be lower or equal to max value")
				.isThrownBy(() -> InputGuard.builder().forArrayOf(String.class).requireMinLength(-1).build())
				.withMessageContaining("length cannot be negative");
		}

		@Test
		void when_exactMaxLength_then_success() {
			var guard = InputGuard.builder().forArrayOf(String.class)
				.requireMinLength(2)
				.build();

			var actualResult = guard.processToResult(new String[] { "a", "b" }, "myVal");

			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_shorter_then_failure() {
			var guard = InputGuard.builder().forArrayOf(String.class).requireMinLength(2).build();
			var actualResult = guard.processToResult(new String[] { "a" }, "myVal");
			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("myVal must have at least 2 elements");
		}

		@Test
		void when_longer_then_success() {
			var guard = InputGuard.builder().forArrayOf(String.class).requireMinLength(2).build();
			var actualResult = guard.processToResult(new String[] { "a", "b", "c" }, "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

	}

	@Nested
	class MaxLength {

		@Test
		void negative_length_throws_exception() {
			Assertions.assertThatIllegalArgumentException()
				.as("Min value must be lower or equal to max value")
				.isThrownBy(() -> InputGuard.builder().forArrayOf(String.class).requireMaxLength(-1).build())
				.withMessageContaining("length cannot be negative");
		}

		@Test
		void when_exactMaxLength_then_success() {
			var guard = InputGuard.builder().forArrayOf(String.class).requireMaxLength(2).build();
			var actualResult = guard.processToResult(new String[] { "a", "b" }, "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_shorter_then_success() {
			var guard = InputGuard.builder().forArrayOf(String.class).requireMaxLength(2).build();
			var actualResult = guard.processToResult(new String[] { "a" }, "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_longer_then_failure() {
			var guard = InputGuard.builder().forArrayOf(String.class).requireMaxLength(2).build();
			var actualResult = guard.processToResult(new String[] { "a", "b", "c" }, "myVal");
			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("myVal must have at most 2 elements");
		}

	}

	@Nested
	class MaxBetween {

		@Test
		void minLength_negative_throws_IllegalArgumentException() {
			Assertions.assertThatIllegalArgumentException()
				.as("Min value must be lower or equal to max value")
				.isThrownBy(() -> InputGuard.builder().forArrayOf(String.class).requireLengthBetween(-1, 2).build())
				.withMessageContaining("minLength and maxLength cannot be negative");
		}

		@Test
		void maxLength_negative_throws_IllegalArgumentException() {
			Assertions.assertThatIllegalArgumentException()
				.as("Min value must be lower or equal to max value")
				.isThrownBy(() -> InputGuard.builder().forArrayOf(String.class).requireLengthBetween(-1, 2).build())
				.withMessageContaining("minLength and maxLength cannot be negative");
		}

		@Test
		void minLength_greater_than_maxLength_throws_IllegalArgumentException() {
			Assertions.assertThatIllegalArgumentException().isThrownBy(
				() -> InputGuard.builder().forArrayOf(String.class).requireLengthBetween(4, 2).build()
			);
		}

		@Test
		void when_shorter_then_failure() {
			var guard = InputGuard.builder().forArrayOf(String.class).requireLengthBetween(2, 4).build();
			var actualResult = guard.processToResult(new String[] { "a" }, "myVal");
			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("myVal must have between 2 and 4 elements");
		}

		@Test
		void when_exactMinLength_then_success() {
			var guard = InputGuard.builder().forArrayOf(String.class).requireLengthBetween(2, 4).build();
			var actualResult = guard.processToResult(new String[] { "a", "b" }, "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_inRange_then_success() {
			var guard = InputGuard.builder().forArrayOf(String.class).requireLengthBetween(2, 4).build();
			var actualResult = guard.processToResult(new String[] { "a", "b", "c" }, "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_exactMaxLength_then_success() {
			var guard = InputGuard.builder().forArrayOf(String.class).requireLengthBetween(2, 4).build();
			var actualResult = guard.processToResult(new String[] { "a", "b", "c", "d" }, "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_longer_then_failure() {
			var guard = InputGuard.builder().forArrayOf(String.class).requireLengthBetween(2, 4).build();
			var actualResult = guard.processToResult(new String[] { "a", "b", "c", "d", "e" }, "myVal");
			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("myVal must have between 2 and 4 elements");
		}

	}

	@Nested
	class RequireNoDuplicateElements {

		@Test
		void when_empty_collection_then_success() {
			var guard = InputGuard.builder().forArrayOf(String.class).requireNoDuplicateElements().build();
			var actualResult = guard.processToResult(new String[] {}, "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_single_element_collection_then_success() {
			var guard = InputGuard.builder().forArrayOf(String.class).requireNoDuplicateElements().build();
			var actualResult = guard.processToResult(new String[] { "a" }, "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_distinct_elements_collection_then_success() {
			var guard = InputGuard.builder().forArrayOf(String.class).requireNoDuplicateElements().build();
			var actualResult = guard.processToResult(new String[] { "a", "b", "c" }, "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_duplicate_elements_collection_then_failure() {
			var guard = InputGuard.builder().forArrayOf(String.class).requireNoDuplicateElements().build();
			var value = new String[] { "a", "b", "c", "a" };
			var actualResult = guard.processToResult(value, "myVal");
			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("myVal contains duplicate elements");
		}

	}

	@Nested
	class RequireNoDuplicateElementsBiPredicate {

		private static final BiPredicate<String, String> HAVE_SAME_LENGTH = (a, b) -> a.length() == b.length();

		@Test
		void null_function_throws_IllegalArgumentException() {
			Assertions.assertThatIllegalArgumentException().isThrownBy(
				() -> InputGuard.builder().forArrayOf(String.class).requireNoDuplicateElements(null).build()
			);
		}

		@Test
		void when_single_element_collection_then_success() {
			var guard = InputGuard.builder().forArrayOf(String.class).requireNoDuplicateElements(HAVE_SAME_LENGTH).build();
			var actualResult = guard.processToResult(new String[] { "a" }, "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_distinct_elements_collection_then_success() {
			var guard = InputGuard.builder().forArrayOf(String.class).requireNoDuplicateElements(HAVE_SAME_LENGTH).build();
			var actualResult = guard.processToResult(new String[] { "a", "bb", "ccc" }, "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_duplicate_elements_collection_then_failure() {
			var guard = InputGuard.builder().forArrayOf(String.class).requireNoDuplicateElements(HAVE_SAME_LENGTH).build();
			var coll = new String[] { "a", "bb", "c" };
			var actualResult = guard.processToResult(coll, "myVal");
			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("myVal contains duplicate elements");
		}

	}

	@Nested
	class RequireNoNullElements {

		@Test
		void when_single_non_null_element_collection_then_success() {
			var guard = InputGuard.builder().forArrayOf(String.class).requireNoNullElements().build();
			var actualResult = guard.processToResult(new String[] { "a" }, "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_all_non_null_elements_collection_then_success() {
			var guard = InputGuard.builder().forArrayOf(String.class).requireNoNullElements().build();
			var actualResult = guard.processToResult(new String[] { "a", "b", "c" }, "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_one_null_element_collection_then_success() {
			var guard = InputGuard.builder().forArrayOf(String.class).requireNoNullElements().build();
			var actualResult = guard.processToResult(new String[] { "a", null, "c" }, "myVal");
			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("""
					myVal contains 1 illegal element:
					  - myVal[1] must not be null""");
		}

	}

}
