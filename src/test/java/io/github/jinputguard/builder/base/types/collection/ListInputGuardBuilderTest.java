package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.result.Path;
import io.github.jinputguard.result.GuardFailureAssert;
import io.github.jinputguard.result.GuardResultAssert;
import io.github.jinputguard.result.ValidationError;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ListInputGuardBuilderTest {

	@Nested
	class Sanitization {

		@Test
		void nominal() {
			var listGuard = InputGuard.builder().forList(String.class)
				.sanitize(list -> list.stream().filter(str -> str.length() >= 2).toList())
				.build();

			var actualResult = listGuard.process(List.of("a", "bb", "ccc", "d", "eeee", "f", ""));

			GuardResultAssert.assertThat(actualResult)
				.isSuccessWithValue(List.of("bb", "ccc", "eeee"));
		}

	}

	@Nested
	class Validation {

		@Test
		void nominal_success() {
			var listGuard = InputGuard.builder().forList(String.class)
				.validate(list -> list.isEmpty() ? new ValidationError.CollectionIsEmpty() : null)
				.build();

			var actualResult = listGuard.process(List.of("a"));

			GuardResultAssert.assertThat(actualResult)
				.isSuccessWithValue(List.of("a"));

			Assertions.assertThat(actualResult.get())
				.isUnmodifiable();
		}

		@Test
		void nominal_failure() {
			var listGuard = InputGuard.builder().forList(String.class)
				.validate(list -> list.isEmpty() ? new ValidationError.CollectionIsEmpty() : null)
				.build();

			var actualResult = listGuard.process(List.of());

			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.isValidationFailure()
				.errorAssert(errorAssert -> errorAssert.isCollectionIsEmpty());
		}

	}

	@Nested
	class Mapping {

		@Test
		void nominal() {
			var listGuard = InputGuard.builder().forList(String.class)
				.map(list -> list.stream().map(Integer::parseInt).toList())
				.build();

			var actualResult = listGuard.process(List.of("0", "1", "2"));

			GuardResultAssert.assertThat(actualResult)
				.isSuccessWithValue(List.of(0, 1, 2));

			Assertions.assertThat(actualResult.get())
				.isUnmodifiable();
		}

	}

	@Nested
	class Filter {

		@Test
		void nominal() {
			Predicate<String> filter = value -> !value.isEmpty();
			var listGuard = InputGuard.builder().forList(String.class)
				.filter(filter)
				.build();

			var actualResult = listGuard.process(List.of("", " ", " a", "b ", " c "));

			GuardResultAssert.assertThat(actualResult)
				.isSuccessWithValue(List.of(" ", " a", "b ", " c "));

			Assertions.assertThat(actualResult.get())
				.isUnmodifiable();
		}

		@Test
		void nominal_withCollector() {
			Predicate<String> filter = value -> !value.isEmpty();
			var listGuard = InputGuard.builder().forList(String.class)
				.filter(filter, Collectors.toCollection(() -> new LinkedList<>()))
				.build();

			var actualResult = listGuard.process(List.of("", " ", " a", "b ", " c "));

			GuardResultAssert.assertThat(actualResult)
				.isSuccessWithValue(List.of(" ", " a", "b ", " c "));

			Assertions.assertThat(actualResult.get())
				.isExactlyInstanceOf(LinkedList.class);
		}

	}

	@Nested
	class ProcessEach {

		@Nested
		class Sanitization {

			@Test
			void nominal() {
				var elementGuard = InputGuard.builder().forString().sanitize().strip().then().build();
				var listGuard = InputGuard.builder().forList(String.class)
					.processEach(elementGuard)
					.build();

				var actualResult = listGuard.process(List.of("", " ", " a", "b ", " c "));

				GuardResultAssert.assertThat(actualResult)
					.isSuccessWithValue(List.of("", "", "a", "b", "c"));

				Assertions.assertThat(actualResult.get())
					.isUnmodifiable();
			}

			@Test
			void nominal_withNullDefaults() {
				var elementGuard = InputGuard.builder().forString()
					.ifNullThen().useDefault("!")
					.sanitize()
					.strip().then()
					.build();
				var listGuard = InputGuard.builder().forList(String.class)
					.processEach(elementGuard)
					.build();

				var listWithNull = new ArrayList<>(List.of("", " ", " a", "b ", " c "));
				listWithNull.add(null);
				var actualResult = listGuard.process(listWithNull);

				GuardResultAssert.assertThat(actualResult)
					.isSuccessWithValue(List.of("", "", "a", "b", "c", "!"));

				Assertions.assertThat(actualResult.get())
					.isUnmodifiable();
			}

			@Test
			void nominal_withFilter() {
				Predicate<String> filter = value -> !value.isEmpty();
				var elementGuard = InputGuard.builder().forString().sanitize().strip().then().build();
				var listGuard = InputGuard.builder().forList(String.class)
					.filterAndProcessEach(filter, elementGuard)
					.build();

				var actualResult = listGuard.process(List.of("", " ", " a", "b ", " c "));

				GuardResultAssert.assertThat(actualResult)
					.isSuccessWithValue(List.of("", "a", "b", "c"));

				Assertions.assertThat(actualResult.get())
					.isUnmodifiable();
			}

			@Test
			void nominal_withCollector() {
				var elementGuard = InputGuard.builder().forString().sanitize().strip().then().build();
				var listGuard = InputGuard.builder().forList(String.class)
					.processEach(elementGuard, Collectors.toCollection(() -> new LinkedList<>()))
					.build();

				var actualResult = listGuard.process(List.of("", " ", " a", "b ", " c "));

				GuardResultAssert.assertThat(actualResult)
					.isSuccessWithValue(List.of("", "", "a", "b", "c"));

				Assertions.assertThat(actualResult.get())
					.isExactlyInstanceOf(LinkedList.class);
			}

			@Test
			void nominal_withFilterAndCollector() {
				Predicate<String> filter = value -> !value.isEmpty();
				var elementGuard = InputGuard.builder().forString().sanitize().strip().then().build();
				var listGuard = InputGuard.builder().forList(String.class)
					.filterAndProcessEach(filter, elementGuard, Collectors.toCollection(() -> new LinkedList<>()))
					.build();

				var actualResult = listGuard.process(List.of("", " ", " a", "b ", " c "));

				GuardResultAssert.assertThat(actualResult)
					.isSuccessWithValue(List.of("", "a", "b", "c"));

				Assertions.assertThat(actualResult.get())
					.isExactlyInstanceOf(LinkedList.class);
			}

		}

		@Nested
		class Validation {

			@Test
			void validation_all_success() {
				var elementGuard = InputGuard.builder().forString().validateThat().isNotNull().then().build();
				var listGuard = InputGuard.builder().forList(String.class)
					.processEach(elementGuard)
					.build();

				var actualResult = listGuard.process(List.of("", " ", " a", "b ", " c "));

				GuardResultAssert.assertThat(actualResult)
					.isSuccessWithValue(List.of("", " ", " a", "b ", " c "));

				Assertions.assertThat(actualResult.get())
					.isUnmodifiable();
			}

			@Test
			void subGuardFailure() {
				var elementGuard = InputGuard.builder().forString().validateThat().isNotEmpty().then().build();
				var listGuard = InputGuard.builder().forList(String.class)
					.processEach(elementGuard)
					.build();

				var value = List.of("", "abc", "", "123", "");
				var actualResult = listGuard.process(value);

				GuardResultAssert.assertThat(actualResult)
					.isFailure()
					.isMultiFailure()
					.hasRootPath()
					.failuresAssert(
						assertor -> assertor.satisfiesExactly(
							fail1 -> GuardFailureAssert.assertThat(fail1)
								.isValidationFailure()
								.hasPathEqualTo(Path.createIndexPath(0))
								.errorAssert(subAssertor -> subAssertor.isStringIsEmpty()),
							fail2 -> GuardFailureAssert.assertThat(fail2)
								.isValidationFailure()
								.hasPathEqualTo(Path.createIndexPath(2))
								.errorAssert(subAssertor -> subAssertor.isStringIsEmpty()),
							fail3 -> GuardFailureAssert.assertThat(fail3)
								.isValidationFailure()
								.hasPathEqualTo(Path.createIndexPath(4))
								.errorAssert(subAssertor -> subAssertor.isStringIsEmpty())
						)
					);
			}

			@Test
			void subGuardFailure_atPath() {
				var elementGuard = InputGuard.builder().forString().validateThat().isNotEmpty().then().build();
				var listGuard = InputGuard.builder().forList(String.class)
					.processEach(elementGuard)
					.build();

				var value = List.of("", "abc", "", "123", "");
				var actualResult = listGuard.process(value).atPath(Path.createPropertyPath("myList"));

				GuardResultAssert.assertThat(actualResult)
					.isFailure()
					.isMultiFailure()
					.hasPathEqualTo("myList")
					.failuresAssert(
						assertor -> assertor.satisfiesExactly(
							fail1 -> GuardFailureAssert.assertThat(fail1)
								.isValidationFailure()
								.hasPathEqualTo("myList[0]")
								.errorAssert(subAssertor -> subAssertor.isStringIsEmpty()),
							fail2 -> GuardFailureAssert.assertThat(fail2)
								.isValidationFailure()
								.hasPathEqualTo("myList[2]")
								.errorAssert(subAssertor -> subAssertor.isStringIsEmpty()),
							fail3 -> GuardFailureAssert.assertThat(fail3)
								.isValidationFailure()
								.hasPathEqualTo("myList[4]")
								.errorAssert(subAssertor -> subAssertor.isStringIsEmpty())
						)
					);
			}

		}

		@Nested
		class Mapping {

			@Test
			void mapping_all_success() {
				var elementGuard = InputGuard.builder().forString().mapToInteger().build();
				var listGuard = InputGuard.builder().forList(String.class)
					.processEach(elementGuard)
					.build();

				var actualResult = listGuard.process(List.of("0", "1", "2"));

				GuardResultAssert.assertThat(actualResult)
					.isSuccessWithValue(List.of(0, 1, 2));

				Assertions.assertThat(actualResult.get())
					.isUnmodifiable();
			}

		}

	}

}
