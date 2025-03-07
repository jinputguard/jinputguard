package io.github.jinputguard;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.result.GuardResult;
import io.github.jinputguard.result.ProcessResultAssert;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class InputGuardTest {

	@Nested
	class AndThen {

		@Test
		void andThen_instance() {
			InputGuard<String, String> subGuard1 = value -> GuardResult.success(value + "-1");
			InputGuard<String, String> subGuard2 = value -> GuardResult.success(value + "-2");
			InputGuard<String, String> subGuard3 = value -> GuardResult.success(value + "-3");

			var guard = subGuard1.andThen(subGuard2).andThen(subGuard3);

			ProcessResultAssert.assertThat(guard.process("plop")).isSuccessWithValue("plop-1-2-3");
		}

	}

	@Nested
	class Compose {

		@Test
		void compose_instance() {
			InputGuard<String, String> subGuard1 = value -> GuardResult.success(value + "-1");
			InputGuard<String, String> subGuard2 = value -> GuardResult.success(value + "-2");
			InputGuard<String, String> subGuard3 = value -> GuardResult.success(value + "-3");

			var guard = subGuard1.compose(subGuard2).compose(subGuard3);

			ProcessResultAssert.assertThat(guard.process("plop")).isSuccessWithValue("plop-3-2-1");
		}

	}

}
