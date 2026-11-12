package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.GuardResultAssert;
import java.util.Collection;
import java.util.function.BiPredicate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

abstract class AbstractCollectionValidationTest<C extends Collection<String>, B extends AbstractCollectionInputGuardBuilder<C, C, String, B>> extends AbstractCollectionTest<C, B> {

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class RequireNotEmpty {

		@Test
		void when_one_element_then_success() {
			var guard = initiateGuard().requireNotEmpty().build();
			var actualResult = guard.processToResult(buildCollection("one"), "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_is_empty_then_failure() {
			var guard = initiateGuard().requireNotEmpty().build();
			var actualResult = guard.processToResult(buildCollection(), "myVal");
			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("myVal must not be empty");
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class RequireSize {

		@Test
		void negative_size_throws_exception() {
			Assertions.assertThatIllegalArgumentException()
				.as("Min value must be lower or equal to max value")
				.isThrownBy(() -> initiateGuard().requireSize(-1).build())
				.withMessageContaining("size cannot be negative");
		}

		@Test
		void nominal_success() {
			var guard = initiateGuard().requireSize(2).build();
			var actualResult = guard.processToResult(buildCollection("a", "b"), "myVal");
			GuardResultAssert.assertThat(actualResult)
				.isSuccess(buildCollection("a", "b"));
		}

		@Test
		void when_too_short_then_failure() {
			var guard = initiateGuard().requireSize(2).build();
			var actualResult = guard.processToResult(buildCollection("a"), "myVal");
			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("myVal must have exactly 2 elements");
		}

		@Test
		void when_too_long_then_failure() {
			var guard = initiateGuard().requireSize(2).build();
			var actualResult = guard.processToResult(buildCollection("a", "b", "c"), "myVal");
			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("myVal must have exactly 2 elements");
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class RequireMinSize {

		@Test
		void negative_size_throws_exception() {
			Assertions.assertThatIllegalArgumentException()
				.as("Min value must be lower or equal to max value")
				.isThrownBy(() -> initiateGuard().requireMinSize(-1).build())
				.withMessageContaining("size cannot be negative");
		}

		@Test
		void when_exactMaxSize_then_success() {
			var guard = initiateGuard()
				.requireMinSize(2)
				.build();

			var actualResult = guard.processToResult(buildCollection("a", "b"), "myVal");

			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_shorter_then_failure() {
			var guard = initiateGuard().requireMinSize(2).build();
			var actualResult = guard.processToResult(buildCollection("a"), "myVal");
			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("myVal must have at least 2 elements");
		}

		@Test
		void when_longer_then_success() {
			var guard = initiateGuard().requireMinSize(2).build();
			var actualResult = guard.processToResult(buildCollection("a", "b", "c"), "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class MaxSize {

		@Test
		void negative_size_throws_exception() {
			Assertions.assertThatIllegalArgumentException()
				.as("Min value must be lower or equal to max value")
				.isThrownBy(() -> initiateGuard().requireMaxSize(-1).build())
				.withMessageContaining("size cannot be negative");
		}

		@Test
		void when_exactMaxSize_then_success() {
			var guard = initiateGuard().requireMaxSize(2).build();
			var actualResult = guard.processToResult(buildCollection("a", "b"), "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_shorter_then_success() {
			var guard = initiateGuard().requireMaxSize(2).build();
			var actualResult = guard.processToResult(buildCollection("a"), "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_longer_then_failure() {
			var guard = initiateGuard().requireMaxSize(2).build();
			var actualResult = guard.processToResult(buildCollection("a", "b", "c"), "myVal");
			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("myVal must have at most 2 elements");
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class MaxBetween {

		@Test
		void minSize_negative_throws_IllegalArgumentException() {
			Assertions.assertThatIllegalArgumentException()
				.as("Min value must be lower or equal to max value")
				.isThrownBy(() -> initiateGuard().requireSizeBetween(-1, 2).build())
				.withMessageContaining("minSize and maxSize cannot be negative");
		}

		@Test
		void maxSize_negative_throws_IllegalArgumentException() {
			Assertions.assertThatIllegalArgumentException()
				.as("Min value must be lower or equal to max value")
				.isThrownBy(() -> initiateGuard().requireSizeBetween(-1, 2).build())
				.withMessageContaining("minSize and maxSize cannot be negative");
		}

		@Test
		void minSize_greater_than_maxSize_throws_IllegalArgumentException() {
			Assertions.assertThatIllegalArgumentException().isThrownBy(
				() -> initiateGuard().requireSizeBetween(4, 2).build()
			);
		}

		@Test
		void when_shorter_then_failure() {
			var guard = initiateGuard().requireSizeBetween(2, 4).build();
			var actualResult = guard.processToResult(buildCollection("a"), "myVal");
			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("myVal must have between 2 and 4 elements");
		}

		@Test
		void when_exactMinSize_then_success() {
			var guard = initiateGuard().requireSizeBetween(2, 4).build();
			var actualResult = guard.processToResult(buildCollection("a", "b"), "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_inRange_then_success() {
			var guard = initiateGuard().requireSizeBetween(2, 4).build();
			var actualResult = guard.processToResult(buildCollection("a", "b", "c"), "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_exactMaxSize_then_success() {
			var guard = initiateGuard().requireSizeBetween(2, 4).build();
			var actualResult = guard.processToResult(buildCollection("a", "b", "c", "d"), "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_longer_then_failure() {
			var guard = initiateGuard().requireSizeBetween(2, 4).build();
			var actualResult = guard.processToResult(buildCollection("a", "b", "c", "d", "e"), "myVal");
			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("myVal must have between 2 and 4 elements");
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class RequireNoDuplicateElements {

		@Test
		void when_empty_collection_then_success() {
			var guard = initiateGuard().requireNoDuplicateElements().build();
			var actualResult = guard.processToResult(buildCollection(), "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_single_element_collection_then_success() {
			var guard = initiateGuard().requireNoDuplicateElements().build();
			var actualResult = guard.processToResult(buildCollection("a"), "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_distinct_elements_collection_then_success() {
			var guard = initiateGuard().requireNoDuplicateElements().build();
			var actualResult = guard.processToResult(buildCollection("a", "b", "c"), "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_duplicate_elements_collection_then_failure() {
			Assumptions.assumeTrue(areDuplicatesAllowed(), "Disabled because underlying collection does not allow duplicate elements by definition (e.g. Set)");

			var guard = initiateGuard().requireNoDuplicateElements().build();
			var coll = buildCollection("a", "b", "c", "a");
			var actualResult = guard.processToResult(coll, "myVal");
			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("myVal contains duplicate elements");
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class RequireNoDuplicateElementsBiPredicate {

		private static final BiPredicate<String, String> HAVE_SAME_LENGTH = (a, b) -> a.length() == b.length();
		@Test
		void null_function_throws_IllegalArgumentException() {
			Assertions.assertThatIllegalArgumentException().isThrownBy(
				() -> initiateGuard().requireNoDuplicateElements(null).build()
			);
		}

		@Test
		void when_single_element_collection_then_success() {
			var guard = initiateGuard().requireNoDuplicateElements(HAVE_SAME_LENGTH).build();
			var actualResult = guard.processToResult(buildCollection("a"), "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_distinct_elements_collection_then_success() {
			var guard = initiateGuard().requireNoDuplicateElements(HAVE_SAME_LENGTH).build();
			var actualResult = guard.processToResult(buildCollection("a", "bb", "ccc"), "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_duplicate_elements_collection_then_failure() {
			var guard = initiateGuard().requireNoDuplicateElements(HAVE_SAME_LENGTH).build();
			var coll = buildCollection("a", "bb", "c");
			var actualResult = guard.processToResult(coll, "myVal");
			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("myVal contains duplicate elements");
		}

	}

}
