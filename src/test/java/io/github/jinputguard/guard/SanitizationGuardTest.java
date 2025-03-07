package io.github.jinputguard.guard;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.guard.SanitizationGuard;
import io.github.jinputguard.result.ProcessResultAssert;
import java.util.function.Function;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SanitizationGuardTest {

	@Test
	void function_cannot_be_null() {
		Assertions.assertThatNullPointerException().isThrownBy(() -> new SanitizationGuard<>(null));
	}

	@Test
	void nominal() {
		InputGuard<String, String> guard = new SanitizationGuard<>(String::strip);

		var actualResult = guard.process(" plop ");

		ProcessResultAssert.assertThat(actualResult).isSuccessWithValue("plop");
	}

	@Test
	void can_return_null() {
		InputGuard<String, String> guard = new SanitizationGuard<>(value -> null);

		var actualResult = guard.process(" plop ");

		ProcessResultAssert.assertThat(actualResult).isSuccessWithValue(null);
	}

	@Test
	void nominal_exception() {
		var exception = new IllegalStateException("cause");
		InputGuard<String, String> guard = new SanitizationGuard<>(value -> {
			throw exception;
		});

		Assertions.assertThatIllegalStateException()
			.isThrownBy(() -> guard.process("plop"))
			.withMessage("cause");
	}

	@Nested
	class ToStringTest {

		@Test
		void nominal() {
			Function<String, String> function = value -> String.valueOf(value);
			var guard = new SanitizationGuard<>(function);

			var actual = guard.toString();

			Assertions.assertThat(actual)
				.startsWith("SanitizationGuard")
				.contains(function.toString());
		}

	}

}
