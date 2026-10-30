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

class ListProcessEachTest {

	@Nested
	class Sanitization {

		@Test
		void nominal() {
			var elementGuard = InputGuard.builder().forString().sanitize().strip().then().build();
			var listGuard = InputGuard.builder().forList(String.class)
				.processEach(elementGuard)
				.build();

			var actualResult = listGuard.process(List.of("", " ", " a", "b ", " c "), "myVal");

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
			var actualResult = listGuard.process(listWithNull, "myVal");

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

			var actualResult = listGuard.process(List.of("", " ", " a", "b ", " c "), "myVal");

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

			var actualResult = listGuard.process(List.of("", " ", " a", "b ", " c "), "myVal");

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

			var actualResult = listGuard.process(List.of("", " ", " a", "b ", " c "), "myVal");

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

			var actualResult = listGuard.process(List.of("", " ", " a", "b ", " c "), "myVal");

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
			var actualResult = listGuard.process(value, "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("""
					myVal contains 3 illegal elements:
					  - myVal[0] must not be empty
					  - myVal[2] must not be empty
					  - myVal[4] must not be empty""");
		}

	}

	@Nested
	class Mapping {

		@Test
		void mapping_all_success() {
			var elementGuard = InputGuard.builder().forString().parseToInt().build();
			var listGuard = InputGuard.builder().forList(String.class)
				.processEach(elementGuard)
				.build();

			var actualResult = listGuard.process(List.of("0", "1", "2"), "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isSuccess(List.of(0, 1, 2));

			Assertions.assertThat(actualResult.get())
				.isUnmodifiable();
		}

	}

}