package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import java.util.Set;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SetValidationTest {

	@Nested
	class IsNotEmpty {

		@Test
		void nominal_success() {
			var guard = InputGuard.builder().forSet(String.class)
				.validateThat().isNotEmpty()
				.then().build();

			var actualResult = guard.process(Set.of("a"), "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isSuccess(Set.of("a"));
		}

		@Test
		void nominal_failure() {
			var guard = InputGuard.builder().forSet(String.class)
				.validateThat().isNotEmpty()
				.then().build();

			var actualResult = guard.process(Set.of(), "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasPathEqualTo("myVal")
				.hasMessage("myVal must not be empty")
				.hasNoCause();
		}

	}

	@Nested
	class HasSize {

		@Test
		void nominal_success() {
			var guard = InputGuard.builder().forSet(String.class)
				.validateThat().hasSize(2)
				.then().build();

			var actualResult = guard.process(Set.of("a", "b"), "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isSuccess(Set.of("a", "b"));
		}

		@Test
		void when_too_short_then_failure() {
			var guard = InputGuard.builder().forSet(String.class)
				.validateThat().hasSize(2)
				.then().build();

			var actualResult = guard.process(Set.of("a"), "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasPathEqualTo("myVal")
				.hasMessage("myVal must have exactly 2 element(s)")
				.hasNoCause();
		}

		@Test
		void when_too_long_then_failure() {
			var guard = InputGuard.builder().forSet(String.class)
				.validateThat().hasSize(2)
				.then().build();

			var actualResult = guard.process(Set.of("a", "b", "c"), "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasPathEqualTo("myVal")
				.hasMessage("myVal must have exactly 2 element(s)")
				.hasNoCause();
		}

	}

}