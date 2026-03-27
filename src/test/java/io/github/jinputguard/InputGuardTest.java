package io.github.jinputguard;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class InputGuardTest {

	@Nested
	class AndThen {

		@Test
		void verify_andThen_are_chained() {
			InputGuard<String, String> subGuard1 = (value, path) -> GuardResult.success(value + "-1");
			InputGuard<String, String> subGuard2 = (value, path) -> GuardResult.success(value + "-2");
			InputGuard<String, String> subGuard3 = (value, path) -> GuardResult.success(value + "-3");

			var guard = subGuard1.andThen(subGuard2).andThen(subGuard3);

			GuardResultAssert.assertThat(guard.process("plop")).isSuccess("plop-1-2-3");
		}

	}

	@Nested
	class Compose {

		@Test
		void verify_compose_are_chained() {
			InputGuard<String, String> subGuard1 = (value, path) -> GuardResult.success(value + "-1");
			InputGuard<String, String> subGuard2 = (value, path) -> GuardResult.success(value + "-2");
			InputGuard<String, String> subGuard3 = (value, path) -> GuardResult.success(value + "-3");

			var guard = subGuard1.compose(subGuard2).compose(subGuard3);

			GuardResultAssert.assertThat(guard.process("plop")).isSuccess("plop-3-2-1");
		}

	}

}
