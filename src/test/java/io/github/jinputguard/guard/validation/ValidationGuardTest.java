package io.github.jinputguard.guard.validation;

import io.github.jinputguard.guard.validation.ValidationError;
import io.github.jinputguard.guard.validation.ValidationGuard;
import java.util.function.Function;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ValidationGuardTest {

	@Test
	void function_cannot_be_null() {
		Assertions.assertThatNullPointerException().isThrownBy(() -> new ValidationGuard<>(null));
	}

	@Nested
	class ToStringTest {

		@Test
		void nominal() {
			Function<String, ValidationError> function = t -> null;
			var guard = new ValidationGuard<>(function);

			var actual = guard.toString();

			Assertions.assertThat(actual)
				.startsWith("ValidationGuard")
				.contains(function.toString());
		}

	}

}
