package io.github.jinputguard.guard;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.guard.ChainedGuard;
import io.github.jinputguard.result.GuardResult;
import io.github.jinputguard.result.GuardResultAssert;
import io.github.jinputguard.result.ValidationError;
import io.github.jinputguard.result.ValidationFailure;
import java.util.concurrent.atomic.AtomicBoolean;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ChainedGuardTest {

	@Test
	void firstGuard_cannot_be_null() {
		InputGuard<String, String> subGuard1 = null;
		InputGuard<String, String> subGuard2 = value -> GuardResult.success(value + "-2");
		Assertions.assertThatNullPointerException().isThrownBy(() -> new ChainedGuard<>(subGuard1, subGuard2));
	}

	@Test
	void secondGuard_cannot_be_null() {
		InputGuard<String, String> subGuard1 = value -> GuardResult.success(value + "-1");
		InputGuard<String, String> subGuard2 = null;
		Assertions.assertThatNullPointerException().isThrownBy(() -> new ChainedGuard<>(subGuard1, subGuard2));
	}

	@Test
	void nominal() {
		InputGuard<String, String> subGuard1 = value -> GuardResult.success(value + "-1");
		InputGuard<String, String> subGuard2 = value -> GuardResult.success(value + "-2");

		var guard = new ChainedGuard<>(subGuard1, subGuard2);
		var actualResult = guard.process("0");

		GuardResultAssert.assertThat(actualResult).isSuccessWithValue("0-1-2");
	}

	@Test
	void when_error_in_first_then_second_is_not_processed() {

		var validationError = new ValidationError.ObjectIsNull();
		var validationFailure = new ValidationFailure("0", validationError);
		InputGuard<String, String> subGuard1 = value -> GuardResult.failure(validationFailure);

		var secondGuardIsCalled = new AtomicBoolean(false);
		InputGuard<String, String> subGuard2 = value -> {
			secondGuardIsCalled.set(true);
			return GuardResult.success("2");
		};

		var guard = new ChainedGuard<>(subGuard1, subGuard2);

		var actualResult = guard.process("0");

		GuardResultAssert.assertThat(actualResult).isFailure(validationFailure);
		Assertions.assertThat(secondGuardIsCalled).isFalse();
	}

	@Nested
	class AndThen {

		@Test
		void andThen_instance() {
			InputGuard<String, String> subGuard1 = value -> GuardResult.success(value + "-1");
			InputGuard<String, String> subGuard2 = value -> GuardResult.success(value + "-2");
			InputGuard<String, String> subGuard3 = value -> GuardResult.success(value + "-3");

			var chainedGuard1and2 = new ChainedGuard<>(subGuard1, subGuard2);
			var guard = chainedGuard1and2.andThen(subGuard3);

			var actualResult = guard.process("0");

			GuardResultAssert.assertThat(actualResult).isSuccessWithValue("0-1-2-3");
		}

	}

	@Nested
	class Compose {

		@Test
		void compose_instance() {
			InputGuard<String, String> subGuard1 = value -> GuardResult.success(value + "-1");
			InputGuard<String, String> subGuard2 = value -> GuardResult.success(value + "-2");
			InputGuard<String, String> subGuard3 = value -> GuardResult.success(value + "-3");

			var subGuard1and2 = new ChainedGuard<>(subGuard1, subGuard2);
			var guard = subGuard1and2.compose(subGuard3);

			var actualResult = guard.process("0");

			GuardResultAssert.assertThat(actualResult).isSuccessWithValue("0-3-1-2");
		}

	}

	@Nested
	class ToStringTest {

		@Test
		void nominal() {
			InputGuard<String, String> subGuard1 = value -> GuardResult.success(value + "-1");
			InputGuard<String, String> subGuard2 = value -> GuardResult.success(value + "-2");

			var guard = new ChainedGuard<>(subGuard1, subGuard2);
			var actual = guard.toString();

			Assertions.assertThat(actual)
				.startsWith("ChainedGuard")
				.contains("  " + subGuard1.toString())
				.contains("  " + subGuard2.toString());
		}

	}

}
