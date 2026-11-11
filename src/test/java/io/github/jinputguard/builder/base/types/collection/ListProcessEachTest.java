package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ListProcessEachTest {

	@Nested
	class Sanitization {

		@Test
		void nominal() {
			var elementGuard = InputGuard.builder().forString().strip().build();
			var listGuard = InputGuard.builder().forList(String.class)
				.processEach(elementGuard)
				.build();

			var actualResult = listGuard.processToResult(List.of("", " ", " a", "b ", " c "), "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isSuccess(List.of("", "", "a", "b", "c"));

			Assertions.assertThat(actualResult.get())
				.isUnmodifiable();
		}

		@Test
		void nominal_withNullDefaults() {
			var elementGuard = InputGuard.builder().forString()
				.ifNullThen().useDefault("!")
				.strip()
				.build();
			var listGuard = InputGuard.builder().forList(String.class)
				.processEach(elementGuard)
				.build();

			var listWithNull = new ArrayList<>(List.of("", " ", " a", "b ", " c "));
			listWithNull.add(null);
			var actualResult = listGuard.processToResult(listWithNull, "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isSuccess(List.of("", "", "a", "b", "c", "!"));

			Assertions.assertThat(actualResult.get())
				.isUnmodifiable();
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

			var actualResult = listGuard.processToResult(List.of("", " ", " a", "b ", " c "), "myVal");

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
			var actualResult = listGuard.processToResult(value, "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("""
					myVal contains 3 illegal elements:
					  - myVal[0] must not be empty
					  - myVal[2] must not be empty
					  - myVal[4] must not be empty""");
		}

	}

}