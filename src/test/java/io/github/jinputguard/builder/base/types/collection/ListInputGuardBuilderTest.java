package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ListInputGuardBuilderTest {

	private static final String BASE_PATH = "myVal";

	@Nested
	class Sanitization {

		@Test
		void nominal() {
			var listGuard = InputGuard.builder().forList(String.class)
				.sanitize(list -> list.stream().filter(str -> str.length() >= 2).toList())
				.build();

			var actualResult = listGuard.process(List.of("a", "bb", "ccc", "d", "eeee", "f", ""), BASE_PATH);

			GuardResultAssert.assertThat(actualResult)
				.isSuccess(List.of("bb", "ccc", "eeee"));
		}

	}

	@Nested
	class Validation {

		@Test
		void nominal_success() {
			var listGuard = InputGuard.builder().forList(String.class)
				.validate(list -> list.isEmpty() ? new CollectionValidationError.CollectionIsEmpty() : null)
				.build();

			var actualResult = listGuard.process(List.of("a"), BASE_PATH);

			GuardResultAssert.assertThat(actualResult)
				.isSuccess(List.of("a"));

			Assertions.assertThat(actualResult.get())
				.isUnmodifiable();
		}

		@Test
		void nominal_failure() {
			var listGuard = InputGuard.builder().forList(String.class)
				.validate(list -> list.isEmpty() ? new CollectionValidationError.CollectionIsEmpty() : null)
				.build();

			var actualResult = listGuard.process(List.of(), BASE_PATH);

			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("is empty");
		}

	}

	@Nested
	class Mapping {

		@Test
		void nominal() {
			var listGuard = InputGuard.builder().forList(String.class)
				.map(list -> list.stream().map(Integer::parseInt).toList())
				.build();

			var actualResult = listGuard.process(List.of("0", "1", "2"), BASE_PATH);

			GuardResultAssert.assertThat(actualResult)
				.isSuccess(List.of(0, 1, 2));

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

			var actualResult = listGuard.process(List.of("", " ", " a", "b ", " c "), BASE_PATH);

			GuardResultAssert.assertThat(actualResult)
				.isSuccess(List.of(" ", " a", "b ", " c "));

			Assertions.assertThat(actualResult.get())
				.isUnmodifiable();
		}

		@Test
		void nominal_withCollector() {
			Predicate<String> filter = value -> !value.isEmpty();
			var listGuard = InputGuard.builder().forList(String.class)
				.filter(filter, Collectors.toCollection(() -> new LinkedList<>()))
				.build();

			var actualResult = listGuard.process(List.of("", " ", " a", "b ", " c "), BASE_PATH);

			GuardResultAssert.assertThat(actualResult)
				.isSuccess(List.of(" ", " a", "b ", " c "));

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

				var actualResult = listGuard.process(List.of("", " ", " a", "b ", " c "), BASE_PATH);

				GuardResultAssert.assertThat(actualResult)
					.isSuccess(List.of("", "", "a", "b", "c"));

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
				var actualResult = listGuard.process(listWithNull, BASE_PATH);

				GuardResultAssert.assertThat(actualResult)
					.isSuccess(List.of("", "", "a", "b", "c", "!"));

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

				var actualResult = listGuard.process(List.of("", " ", " a", "b ", " c "), BASE_PATH);

				GuardResultAssert.assertThat(actualResult)
					.isSuccess(List.of("", "a", "b", "c"));

				Assertions.assertThat(actualResult.get())
					.isUnmodifiable();
			}

			@Test
			void nominal_withCollector() {
				var elementGuard = InputGuard.builder().forString().sanitize().strip().then().build();
				var listGuard = InputGuard.builder().forList(String.class)
					.processEach(elementGuard, Collectors.toCollection(() -> new LinkedList<>()))
					.build();

				var actualResult = listGuard.process(List.of("", " ", " a", "b ", " c "), BASE_PATH);

				GuardResultAssert.assertThat(actualResult)
					.isSuccess(List.of("", "", "a", "b", "c"));

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

				var actualResult = listGuard.process(List.of("", " ", " a", "b ", " c "), BASE_PATH);

				GuardResultAssert.assertThat(actualResult)
					.isSuccess(List.of("", "a", "b", "c"));

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

				var actualResult = listGuard.process(List.of("", " ", " a", "b ", " c "), BASE_PATH);

				GuardResultAssert.assertThat(actualResult)
					.isSuccess(List.of("", " ", " a", "b ", " c "));

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
				var actualResult = listGuard.process(value, BASE_PATH);

				GuardResultAssert.assertThat(actualResult)
					.isFailure()
					.hasPathEqualTo("myVal")
					.hasMessage("""
						multiple errors:
						  - myVal[0] -> must not be empty
						  - myVal[2] -> must not be empty
						  - myVal[4] -> must not be empty
						""");
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

				var actualResult = listGuard.process(List.of("0", "1", "2"), BASE_PATH);

				GuardResultAssert.assertThat(actualResult)
					.isSuccess(List.of(0, 1, 2));

				Assertions.assertThat(actualResult.get())
					.isUnmodifiable();
			}

		}

	}

}
