package io.github.jinputguard.guard;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import io.github.jinputguard.builder.InputGuards;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class NoOpGuardTest {

	@Test
	void nominal() {
		InputGuard<String, String> noOpGuard = new NoOpGuard<>();

		var actualResult = noOpGuard.process("OK", "myVal");

		GuardResultAssert.assertThat(actualResult).isSuccess("OK");
	}

	@Nested
	class AndThen {

		@Test
		void andThen_returns_otherGuard() {
			InputGuard<String, String> noOpGuard = new NoOpGuard<>();
			InputGuard<String, String> afterGuard = InputGuards.sanitizationGuard(String::strip);

			var newGuard = noOpGuard.andThen(afterGuard);

			GuardResultAssert.assertThat(noOpGuard.process(" plop ", "myVal")).isSuccess(" plop ");
			GuardResultAssert.assertThat(newGuard.process(" plop ", "myVal")).isSuccess("plop");
		}

	}

	@Nested
	class Compose {

		@Test
		void compose_returns_otherGuard() {
			InputGuard<String, String> noOpGuard = new NoOpGuard<>();
			InputGuard<String, String> beforeGuard = InputGuards.sanitizationGuard(String::strip);

			var newGuard = noOpGuard.compose(beforeGuard);

			GuardResultAssert.assertThat(noOpGuard.process(" plop ", "myVal")).isSuccess(" plop ");
			GuardResultAssert.assertThat(newGuard.process(" plop ", "myVal")).isSuccess("plop");
		}

	}

	@Nested
	class ToStringTest {

		@Test
		void nominal() {
			InputGuard<String, String> noOpGuard = new NoOpGuard<>();

			var actual = noOpGuard.toString();

			Assertions.assertThat(actual).startsWith("NoOpGuard");
		}

	}

}
