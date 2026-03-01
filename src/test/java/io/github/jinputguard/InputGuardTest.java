package io.github.jinputguard;

import io.github.jinputguard.result.DefaultGuardFailure;
import io.github.jinputguard.result.GuardResultAssert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class InputGuardTest {

	@Nested
	class AndThen {

		@Test
		void andThen_instance() {
			InputGuard<String, String> subGuard1 = (value, path) -> GuardResult.success(value + "-1");
			InputGuard<String, String> subGuard2 = (value, path) -> GuardResult.success(value + "-2");
			InputGuard<String, String> subGuard3 = (value, path) -> GuardResult.success(value + "-3");

			var guard = subGuard1.andThen(subGuard2).andThen(subGuard3);

			GuardResultAssert.assertThat(guard.process("plop", "myVal")).isSuccess("plop-1-2-3");
		}

	}

	@Nested
	class Compose {

		@Test
		void compose_instance() {
			InputGuard<String, String> subGuard1 = (value, path) -> GuardResult.success(value + "-1");
			InputGuard<String, String> subGuard2 = (value, path) -> GuardResult.success(value + "-2");
			InputGuard<String, String> subGuard3 = (value, path) -> GuardResult.success(value + "-3");

			var guard = subGuard1.compose(subGuard2).compose(subGuard3);

			GuardResultAssert.assertThat(guard.process("plop", "myVal")).isSuccess("plop-3-2-1");
		}

	}

	@Nested
	class Result {

		@Test
		void failure_toString() {
			InputGuard<String, String> guard = (value, path) -> {
				var failure = new DefaultGuardFailure(path, () -> "custom details", new RuntimeException("custom cause"));
				return GuardResult.failure(failure);
			};

			Assertions.assertThat(guard.process("plop", "myVal").getFailure().toString())
				.contains("myVal", "custom details", "custom cause");
		}

	}

}
