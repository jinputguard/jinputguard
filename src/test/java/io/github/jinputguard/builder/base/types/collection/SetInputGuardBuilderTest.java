package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.result.Path;
import io.github.jinputguard.result.ProcessFailureAssert;
import io.github.jinputguard.result.ProcessResultAssert;
import io.github.jinputguard.result.ValidationError;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SetInputGuardBuilderTest {

	@Nested
	class Sanitization {

		@Test
		void nominal() {
			var setGuard = InputGuard.builder().forSet(String.class)
				.sanitize(set -> set.stream().filter(str -> str.length() >= 2).collect(Collectors.toSet()))
				.build();

			var actualResult = setGuard.process(Set.of("a", "bb", "ccc", "d", "eeee", "f", ""));

			ProcessResultAssert.assertThat(actualResult)
				.isSuccessWithValue(Set.of("bb", "ccc", "eeee"));
		}

	}

	@Nested
	class Validation {

		@Test
		void nominal_success() {
			var setGuard = InputGuard.builder().forSet(String.class)
				.validate(set -> set.isEmpty() ? new ValidationError.CollectionIsEmpty() : null)
				.build();

			var actualResult = setGuard.process(Set.of("a"));

			ProcessResultAssert.assertThat(actualResult)
				.isSuccessWithValue(Set.of("a"));
		}

		@Test
		void nominal_failure() {
			var setGuard = InputGuard.builder().forSet(String.class)
				.validate(set -> set.isEmpty() ? new ValidationError.CollectionIsEmpty() : null)
				.build();

			var actualResult = setGuard.process(Set.of());

			ProcessResultAssert.assertThat(actualResult)
				.isFailure()
				.isValidationFailure()
				.errorAssert(errorAssert -> errorAssert.isCollectionIsEmpty());
		}

	}

	@Nested
	class Mapping {

		@Test
		void nominal() {
			var setGuard = InputGuard.builder().forSet(String.class)
				.map(set -> set.stream().map(Integer::parseInt).collect(Collectors.toSet()))
				.build();

			var actualResult = setGuard.process(Set.of("0", "1", "2"));

			ProcessResultAssert.assertThat(actualResult)
				.isSuccessWithValue(Set.of(0, 1, 2));
		}

	}

	@Nested
	class Filter {

		@Test
		void nominal() {
			Predicate<String> filter = value -> !value.isEmpty();
			var setGuard = InputGuard.builder().forSet(String.class)
				.filter(filter)
				.build();

			var actualResult = setGuard.process(Set.of("", " ", " a", "b ", " c "));

			ProcessResultAssert.assertThat(actualResult)
				.isSuccessWithValue(Set.of(" ", " a", "b ", " c "));

			Assertions.assertThat(actualResult.get())
				.isUnmodifiable();
		}

		@Test
		void nominal_withCollector() {
			Predicate<String> filter = value -> !value.isEmpty();
			var setGuard = InputGuard.builder().forSet(String.class)
				.filter(filter, Collectors.toCollection(() -> new HashSet<>()))
				.build();

			var actualResult = setGuard.process(Set.of("", " ", " a", "b ", " c "));

			ProcessResultAssert.assertThat(actualResult)
				.isSuccessWithValue(Set.of(" ", " a", "b ", " c "));

			Assertions.assertThat(actualResult.get())
				.isExactlyInstanceOf(HashSet.class);
		}

	}

	@Nested
	class ProcessEach {

		@Nested
		class Sanitization {

			@Test
			void nominal() {
				var elementGuardGuard = InputGuard.builder().forString().sanitize().strip().then().build();
				var setGuard = InputGuard.builder().forSet(String.class)
					.processEach(elementGuardGuard)
					.build();

				var actualResult = setGuard.process(Set.of("", " ", " a", "b ", " c "));

				ProcessResultAssert.assertThat(actualResult)
					.isSuccessWithValue(Set.of("", "a", "b", "c"));
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
				var actualResult = setGuard.process(listWithNull);

				ProcessResultAssert.assertThat(actualResult)
					.isSuccessWithValue(Set.of("", "a", "b", "c", "!"));

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

				var actualResult = listGuard.process(Set.of("", " ", " a", "b ", " c "));

				ProcessResultAssert.assertThat(actualResult)
					.isSuccessWithValue(Set.of("", "a", "b", "c"));

				Assertions.assertThat(actualResult.get())
					.isUnmodifiable();
			}

			@Test
			void nominal_withCollector() {
				var elementGuardGuard = InputGuard.builder().forString().sanitize().strip().then().build();
				var setGuard = InputGuard.builder().forSet(String.class)
					.processEach(elementGuardGuard, Collectors.toCollection(() -> new HashSet<>()))
					.build();

				var actualResult = setGuard.process(Set.of("", " ", " a", "b ", " c "));

				ProcessResultAssert.assertThat(actualResult)
					.isSuccessWithValue(Set.of("", "a", "b", "c"));

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

				var actualResult = setGuard.process(Set.of("", " ", " a", "b ", " c "));

				ProcessResultAssert.assertThat(actualResult)
					.isSuccessWithValue(Set.of("", "a", "b", "c"));

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

				var actualResult = setGuard.process(Set.of("", " ", " a", "b ", " c "));

				ProcessResultAssert.assertThat(actualResult)
					.isSuccessWithValue(Set.of("", " ", " a", "b ", " c "));
			}

			@Test
			void validation_fail() {
				var elementGuardGuard = InputGuard.builder().forString().validateThat().isNotEmpty().then().build();
				var setGuard = InputGuard.builder().forSet(String.class)
					.processEach(elementGuardGuard)
					.build();

				var value = Set.of("", "abc", "123");
				var actualResult = setGuard.process(value);

				ProcessResultAssert.assertThat(actualResult)
					.isFailure()
					.isMultiFailure()
					.failuresAssert(
						assertor -> assertor.satisfiesExactly(
							fail1 -> ProcessFailureAssert.assertThat(fail1)
								.isValidationFailure()
								.errorAssert(subAssertor -> subAssertor.isStringIsEmpty())
						)
					);
			}

			@Test
			void validation_fail_atPath() {
				var elementGuardGuard = InputGuard.builder().forString().validateThat().isNotEmpty().then().build();
				var setGuard = InputGuard.builder().forSet(String.class)
					.processEach(elementGuardGuard)
					.build();

				var value = Set.of("", "abc", "123");
				var actualResult = setGuard.process(value).atPath(Path.createPropertyPath("mySet"));

				ProcessResultAssert.assertThat(actualResult)
					.isFailure()
					.isMultiFailure()
					.hasPathEqualTo("mySet")
					.failuresAssert(
						assertor -> assertor.satisfiesExactly(
							fail1 -> ProcessFailureAssert.assertThat(fail1)
								.isValidationFailure()
								.hasPathEqualTo("mySet")
								.errorAssert(subAssertor -> subAssertor.isStringIsEmpty())
						)
					);
			}

		}

		@Nested
		class Mapping {

			@Test
			void mapping_all_success() {
				var elementGuardGuard = InputGuard.builder().forString().mapToInteger().build();
				var setGuard = InputGuard.builder().forSet(String.class)
					.processEach(elementGuardGuard)
					.build();

				var actualResult = setGuard.process(Set.of("0", "1", "2"));

				ProcessResultAssert.assertThat(actualResult)
					.isSuccessWithValue(Set.of(0, 1, 2));

				Assertions.assertThat(actualResult.get())
					.isUnmodifiable();
			}

		}

	}

}
