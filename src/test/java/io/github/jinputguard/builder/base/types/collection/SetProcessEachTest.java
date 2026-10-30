package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SetProcessEachTest {

	@Nested
	class Sanitization {

		@Test
		void nominal() {
			var elementGuardGuard = InputGuard.builder().forString().sanitize().strip().then().build();
			var setGuard = InputGuard.builder().forSet(String.class)
				.processEach(elementGuardGuard)
				.build();

			var actualResult = setGuard.process(Set.of("", " ", " a", "b ", " c "), "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isSuccess(Set.of("", "a", "b", "c"));
		}

		@Test
		void nominal_withNullDefaults() {
			var elementGuard = InputGuard.builder().forString()
				.ifNullThen().useDefault("!")
				.sanitize()
				.strip().then()
				.build();
			var setGuard = InputGuard.builder().forSet(String.class)
				.processEach(elementGuard)
				.build();

			var listWithNull = new HashSet<>(List.of("", " ", " a", "b ", " c "));
			listWithNull.add(null);
			var actualResult = setGuard.process(listWithNull, "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isSuccess(Set.of("", "a", "b", "c", "!"));

			Assertions.assertThat(actualResult.get())
				.isUnmodifiable();
		}

		@Test
		void nominal_withFilter() {
			Predicate<String> filter = value -> !value.isEmpty();
			var elementGuard = InputGuard.builder().forString().sanitize().strip().then().build();
			var listGuard = InputGuard.builder().forSet(String.class)
				.filterAndProcessEach(filter, elementGuard)
				.build();

			var actualResult = listGuard.process(Set.of("", " ", " a", "b ", " c "), "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isSuccess(Set.of("", "a", "b", "c"));

			Assertions.assertThat(actualResult.get())
				.isUnmodifiable();
		}

		@Test
		void nominal_withCollector() {
			var elementGuardGuard = InputGuard.builder().forString().sanitize().strip().then().build();
			var setGuard = InputGuard.builder().forSet(String.class)
				.processEach(elementGuardGuard, Collectors.toCollection(() -> new HashSet<>()))
				.build();

			var actualResult = setGuard.process(Set.of("", " ", " a", "b ", " c "), "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isSuccess(Set.of("", "a", "b", "c"));

			Assertions.assertThat(actualResult.get())
				.isExactlyInstanceOf(HashSet.class);
		}

		@Test
		void nominal_withFilterAndCollector() {
			Predicate<String> filter = value -> !value.isEmpty();
			var elementGuardGuard = InputGuard.builder().forString().sanitize().strip().then().build();
			var setGuard = InputGuard.builder().forSet(String.class)
				.filterAndProcessEach(filter, elementGuardGuard, Collectors.toCollection(() -> new HashSet<>()))
				.build();

			var actualResult = setGuard.process(Set.of("", " ", " a", "b ", " c "), "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isSuccess(Set.of("", "a", "b", "c"));

			Assertions.assertThat(actualResult.get())
				.isExactlyInstanceOf(HashSet.class);
		}

	}

	@Nested
	class Validation {

		@Test
		void validation_all_success() {
			var elementGuardGuard = InputGuard.builder().forString().validateThat().isNotNull().then().build();
			var setGuard = InputGuard.builder().forSet(String.class)
				.processEach(elementGuardGuard)
				.build();

			var actualResult = setGuard.process(Set.of("", " ", " a", "b ", " c "), "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isSuccess(Set.of("", " ", " a", "b ", " c "));
		}

		@Test
		void validation_fail() {
			var elementGuardGuard = InputGuard.builder().forString().validateThat().isNotEmpty().then().build();
			var setGuard = InputGuard.builder().forSet(String.class)
				.processEach(elementGuardGuard)
				.build();

			var value = Set.of("", "abc", "123");
			var actualResult = setGuard.process(value, "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("""
					myVal contains 1 illegal element:
					  - myVal[X] must not be empty""");
		}

		@Test
		void validation_fail_atPath() {
			var elementGuardGuard = InputGuard.builder().forString().validateThat().isMaxLength(2).then().build();
			var setGuard = InputGuard.builder().forSet(String.class)
				.processEach(elementGuardGuard)
				.build();

			var value = Set.of("", "abc", "0", "123");
			var actualResult = setGuard.process(value, "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("""
					myVal contains 2 illegal elements:
					  - myVal[X] is too long: 2 chars max
					  - myVal[X] is too long: 2 chars max""");
			;
		}

	}

	@Nested
	class Mapping {

		@Test
		void mapping_all_success() {
			var elementGuardGuard = InputGuard.builder().forString().parseToInt().build();
			var setGuard = InputGuard.builder().forSet(String.class)
				.processEach(elementGuardGuard)
				.build();

			var actualResult = setGuard.process(Set.of("0", "1", "2"), "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isSuccess(Set.of(0, 1, 2));

			Assertions.assertThat(actualResult.get())
				.isUnmodifiable();
		}

	}

}