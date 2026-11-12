package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SetProcessEachTest {

	@Nested
	class Sanitization {

		@Test
		void nominal() {
			var elementGuardGuard = InputGuard.builder().forString().strip().build();
			var setGuard = InputGuard.builder().forSet(String.class)
				.processEach(elementGuardGuard)
				.build();

			var actualResult = setGuard.processToResult(Set.of("", " ", " a", "b ", " c "), "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isSuccess(Set.of("", "a", "b", "c"));
		}

		@Test
		void nominal_withNullDefaults() {
			var elementGuard = InputGuard.builder().forString()
				.ifNullThen().useDefault("!")
				.strip()
				.build();
			var setGuard = InputGuard.builder().forSet(String.class)
				.processEach(elementGuard)
				.build();

			var listWithNull = new HashSet<>(List.of("", " ", " a", "b ", " c "));
			listWithNull.add(null);
			var actualResult = setGuard.processToResult(listWithNull, "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isSuccess(Set.of("", "a", "b", "c", "!"));

			Assertions.assertThat(actualResult.get())
				.isUnmodifiable();
		}

	}

	@Nested
	class Validation {

		@Test
		void validation_all_success() {
			var elementGuardGuard = InputGuard.builder().forString().requireNotNull().build();
			var setGuard = InputGuard.builder().forSet(String.class)
				.processEach(elementGuardGuard)
				.build();

			var actualResult = setGuard.processToResult(Set.of("", " ", " a", "b ", " c "), "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isSuccess(Set.of("", " ", " a", "b ", " c "));
		}

		@Test
		void validation_fail() {
			var elementGuardGuard = InputGuard.builder().forString().requireNotEmpty().build();
			var setGuard = InputGuard.builder().forSet(String.class)
				.processEach(elementGuardGuard)
				.build();

			var value = Set.of("", "abc", "123");
			var actualResult = setGuard.processToResult(value, "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("""
					myVal contains 1 illegal element:
					  - myVal[?] must not be empty""");
		}

		@Test
		void validation_fail_atPath() {
			var elementGuardGuard = InputGuard.builder().forString().requireMaxLength(2).build();
			var setGuard = InputGuard.builder().forSet(String.class)
				.processEach(elementGuardGuard)
				.build();

			var value = Set.of("", "abc", "0", "123");
			var actualResult = setGuard.processToResult(value, "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("""
					myVal contains 2 illegal elements:
					  - myVal[?] is too long: 2 chars max
					  - myVal[?] is too long: 2 chars max""");
			;
		}

	}

}