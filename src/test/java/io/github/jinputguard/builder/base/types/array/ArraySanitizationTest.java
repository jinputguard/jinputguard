package io.github.jinputguard.builder.base.types.array;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ArraySanitizationTest {

	@Nested
	class Filter {

		@Test
		void when_null_filter_then_throw_IllegalArgumentException() {
			Assertions.assertThatIllegalArgumentException().isThrownBy(
				() -> InputGuard.builder().forArrayOf(String.class).filter(null).build()
			);
		}

		@Test
		void nominal() {
			var listGuard = InputGuard.builder().forArrayOf(String.class)
				.filter(value -> !value.isEmpty())
				.build();

			var value = new String[] { "", " ", " a", "b ", " c " };
			var actualResult = listGuard.processToResult(value, "myVal");

			var expectedValue = new String[] { " ", " a", "b ", " c " };
			GuardResultAssert.assertThat(actualResult)
				.isSuccess(expectedValue);
		}

	}

}
