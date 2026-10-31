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
	class IsNotEmpty {

		@Test
		void when_one_element_then_success() {
			var guard = initiateGuard().validateThat().isNotEmpty().then().build();
			var actualResult = guard.process(buildCollection("one"), "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_is_empty_then_failure() {
			var guard = initiateGuard().validateThat().isNotEmpty().then().build();
			var actualResult = guard.process(buildCollection(), "myVal");
			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("myVal must not be empty");
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class HasSize {

		@Test
		void negative_size_throws_exception() {
			Assertions.assertThatIllegalArgumentException()
				.as("Min value must be lower or equal to max value")
				.isThrownBy(() -> initiateGuard().validateThat().hasSize(-1).then().build())
				.withMessageContaining("size cannot be negative");
		}

		@Test
		void nominal_success() {
			var guard = initiateGuard().validateThat().hasSize(2).then().build();
			var actualResult = guard.process(buildCollection("a", "b"), "myVal");
			GuardResultAssert.assertThat(actualResult)
				.isSuccess(buildCollection("a", "b"));
		}

		@Test
		void when_too_short_then_failure() {
			var guard = initiateGuard().validateThat().hasSize(2).then().build();
			var actualResult = guard.process(buildCollection("a"), "myVal");
			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("myVal must have exactly 2 element(s)");
		}

		@Test
		void when_too_long_then_failure() {
			var guard = initiateGuard().validateThat().hasSize(2).then().build();
			var actualResult = guard.process(buildCollection("a", "b", "c"), "myVal");
			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("myVal must have exactly 2 element(s)");
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class HasMinSize {

		@Test
		void negative_size_throws_exception() {
			Assertions.assertThatIllegalArgumentException()
				.as("Min value must be lower or equal to max value")
				.isThrownBy(() -> initiateGuard().validateThat().hasMinSize(-1).then().build())
				.withMessageContaining("size cannot be negative");
		}

		@Test
		void when_exactMaxSize_then_success() {
			var guard = initiateGuard()
				.validateThat().hasMinSize(2)
				.then().build();

			var actualResult = guard.process(buildCollection("a", "b"), "myVal");

			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_shorter_then_failure() {
			var guard = initiateGuard().validateThat().hasMinSize(2).then().build();
			var actualResult = guard.process(buildCollection("a"), "myVal");
			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("myVal must have at least 2 element(s)");
		}

		@Test
		void when_longer_then_success() {
			var guard = initiateGuard().validateThat().hasMinSize(2).then().build();
			var actualResult = guard.process(buildCollection("a", "b", "c"), "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class HasMaxSize {

		@Test
		void negative_size_throws_exception() {
			Assertions.assertThatIllegalArgumentException()
				.as("Min value must be lower or equal to max value")
				.isThrownBy(() -> initiateGuard().validateThat().hasMaxSize(-1).then().build())
				.withMessageContaining("size cannot be negative");
		}

		@Test
		void when_exactMaxSize_then_success() {
			var guard = initiateGuard().validateThat().hasMaxSize(2).then().build();
			var actualResult = guard.process(buildCollection("a", "b"), "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_shorter_then_success() {
			var guard = initiateGuard().validateThat().hasMaxSize(2).then().build();
			var actualResult = guard.process(buildCollection("a"), "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_longer_then_failure() {
			var guard = initiateGuard().validateThat().hasMaxSize(2).then().build();
			var actualResult = guard.process(buildCollection("a", "b", "c"), "myVal");
			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("myVal must have at most 2 element(s)");
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class HasMaxBetween {

		@Test
		void minSize_negative_throws_IllegalArgumentException() {
			Assertions.assertThatIllegalArgumentException()
				.as("Min value must be lower or equal to max value")
				.isThrownBy(() -> initiateGuard().validateThat().hasSizeBetween(-1, 2).then().build())
				.withMessageContaining("minSize and maxSize cannot be negative");
		}

		@Test
		void maxSize_negative_throws_IllegalArgumentException() {
			Assertions.assertThatIllegalArgumentException()
				.as("Min value must be lower or equal to max value")
				.isThrownBy(() -> initiateGuard().validateThat().hasSizeBetween(-1, 2).then().build())
				.withMessageContaining("minSize and maxSize cannot be negative");
		}

		@Test
		void minSize_greater_than_maxSize_throws_IllegalArgumentException() {
			Assertions.assertThatIllegalArgumentException().isThrownBy(
				() -> initiateGuard().validateThat().hasSizeBetween(4, 2).then().build()
			);
		}

		@Test
		void when_shorter_then_failure() {
			var guard = initiateGuard().validateThat().hasSizeBetween(2, 4).then().build();
			var actualResult = guard.process(buildCollection("a"), "myVal");
			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("myVal must have between 2 and 4 element(s)");
		}

		@Test
		void when_exactMinSize_then_success() {
			var guard = initiateGuard().validateThat().hasSizeBetween(2, 4).then().build();
			var actualResult = guard.process(buildCollection("a", "b"), "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_inRange_then_success() {
			var guard = initiateGuard().validateThat().hasSizeBetween(2, 4).then().build();
			var actualResult = guard.process(buildCollection("a", "b", "c"), "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_exactMaxSize_then_success() {
			var guard = initiateGuard().validateThat().hasSizeBetween(2, 4).then().build();
			var actualResult = guard.process(buildCollection("a", "b", "c", "d"), "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_longer_then_failure() {
			var guard = initiateGuard().validateThat().hasSizeBetween(2, 4).then().build();
			var actualResult = guard.process(buildCollection("a", "b", "c", "d", "e"), "myVal");
			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("myVal must have between 2 and 4 element(s)");
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class HasNoDuplicateElements {

		@Test
		void when_empty_collection_then_success() {
			var guard = initiateGuard().validateThat().hasNoDuplicateElements().then().build();
			var actualResult = guard.process(buildCollection(), "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_single_element_collection_then_success() {
			var guard = initiateGuard().validateThat().hasNoDuplicateElements().then().build();
			var actualResult = guard.process(buildCollection("a"), "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_distinct_elements_collection_then_success() {
			var guard = initiateGuard().validateThat().hasNoDuplicateElements().then().build();
			var actualResult = guard.process(buildCollection("a", "b", "c"), "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_duplicate_elements_collection_then_failure() {
			Assumptions.assumeTrue(allowDuplicates(), "Disabled because underlying collection does not allow duplicate elements by definition (e.g. Set)");

			var guard = initiateGuard().validateThat().hasNoDuplicateElements().then().build();
			var coll = buildCollection("a", "b", "c", "a");
			var actualResult = guard.process(coll, "myVal");
			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("myVal must not contain duplicate elements");
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class HasNoDuplicateElementsBiPredicate {

		private static final BiPredicate<String, String> HAVE_SAME_LENGTH = (a, b) -> a.length() == b.length();
		@Test
		void null_function_throws_IllegalArgumentException() {
			Assertions.assertThatIllegalArgumentException().isThrownBy(
				() -> initiateGuard().validateThat().hasNoDuplicateElements(null).then().build()
			);
		}

		@Test
		void when_single_element_collection_then_success() {
			var guard = initiateGuard().validateThat().hasNoDuplicateElements(HAVE_SAME_LENGTH).then().build();
			var actualResult = guard.process(buildCollection("a"), "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_distinct_elements_collection_then_success() {
			var guard = initiateGuard().validateThat().hasNoDuplicateElements(HAVE_SAME_LENGTH).then().build();
			var actualResult = guard.process(buildCollection("a", "bb", "ccc"), "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess();
		}

		@Test
		void when_duplicate_elements_collection_then_failure() {
			var guard = initiateGuard().validateThat().hasNoDuplicateElements(HAVE_SAME_LENGTH).then().build();
			var coll = buildCollection("a", "bb", "c");
			var actualResult = guard.process(coll, "myVal");
			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("myVal must not contain duplicate elements");
		}

	}

}
