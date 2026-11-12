package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import java.util.Collection;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

abstract class AbstractCollectionProcessEachTest<C extends Collection<String>, B extends AbstractCollectionInputGuardBuilder<C, C, String, B>>
	extends AbstractCollectionTest<C, B> {

	@Nested
	class Sanitization {

		@Test
		void nominal() {
			var elementGuard = InputGuard.builder().forString().strip().build();
			var listGuard = initiateGuard()
				.processEach(elementGuard)
				.build();

			var actualResult = listGuard.processToResult(buildMutableCollection("", " ", " a", "b ", " c "), "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isSuccess(buildMutableCollection("", "", "a", "b", "c"));

			Assertions.assertThat(actualResult.get())
				.isUnmodifiable();
		}

		@Test
		void nominal_withNullDefaults() {
			var elementGuard = InputGuard.builder().forString()
				.ifNullThen().useDefault("!")
				.strip()
				.build();
			var listGuard = initiateGuard()
				.processEach(elementGuard)
				.build();

			var actualResult = listGuard.processToResult(buildMutableCollection("", " ", " a", "b ", " c ", null), "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isSuccess(buildMutableCollection("", "", "a", "b", "c", "!"));

			Assertions.assertThat(actualResult.get())
				.isUnmodifiable();
		}

	}

	@Nested
	class Validation {

		@Test
		void validation_all_success() {
			var elementGuard = InputGuard.builder().forString().requireNotNull().build();
			var listGuard = initiateGuard()
				.processEach(elementGuard)
				.build();

			var actualResult = listGuard.processToResult(buildMutableCollection("", " ", " a", "b ", " c "), "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isSuccess(buildMutableCollection("", " ", " a", "b ", " c "));

			Assertions.assertThat(actualResult.get())
				.isUnmodifiable();
		}

		@Test
		void singleFailures() {
			var elementGuard = InputGuard.builder().forString().requireNotEmpty().build();
			var listGuard = initiateGuard()
				.processEach(elementGuard)
				.build();

			var value = buildMutableCollection("", "abc", "123");
			var actualResult = listGuard.processToResult(value, "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage(
					"""
						myVal contains 1 illegal element:
						  - %s must not be empty"""
						.formatted(
							getElementName("myVal", 0)
						)
				);
		}

		@Test
		void multipleFailures() {
			var elementGuard = InputGuard.builder().forString().requireLength(3).build();
			var listGuard = initiateGuard()
				.processEach(elementGuard)
				.build();

			var value = buildMutableCollection("a", "abc", "b", "123", "c");
			var actualResult = listGuard.processToResult(value, "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage(
					"""
						myVal contains 3 illegal elements:
						  - %s must be exactly 3 characters long
						  - %s must be exactly 3 characters long
						  - %s must be exactly 3 characters long"""
						.formatted(
							getElementName("myVal", 0),
							getElementName("myVal", 2),
							getElementName("myVal", 4)
						)
				);
		}

	}

}
