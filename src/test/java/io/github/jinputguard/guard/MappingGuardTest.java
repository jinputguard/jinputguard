package io.github.jinputguard.guard;

import io.github.jinputguard.guard.MappingGuard;
import java.util.function.Function;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MappingGuardTest {

	@Test
	void function_cannot_be_null() {
		Assertions.assertThatNullPointerException().isThrownBy(() -> new MappingGuard<>(null));
	}

	@Nested
	class ToStringTest {

		@Test
		void nominal() {
			Function<String, Integer> function = Integer::parseInt;
			var guard = new MappingGuard<>(function);

			var actual = guard.toString();

			Assertions.assertThat(actual)
				.startsWith("MappingGuard")
				.contains(function.toString());
		}

	}

}
