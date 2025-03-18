package io.github.jinputguard.examples;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.result.InputGuardFailureException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Examples from the README file.
 */
public class ReadmeTest {

	@Nested
	class Usage {

		@Test
		void base() {
			InputGuard<String, Integer> myGuard = InputGuard.builder().forClass(String.class)
				.sanitize(String::strip)
				.sanitize(value -> value.replace(".0", ""))
				.validate(value -> value.length() <= 3, "value is too long")
				.map(Integer::parseInt)
				.build();

			Assertions.assertThat(myGuard.process(" 123.0 ").getOrThrow()).isEqualTo(123); // 123

			Assertions.assertThatExceptionOfType(InputGuardFailureException.class)
				.isThrownBy(() -> myGuard.process(" abc ").getOrThrow()); // throw InputGuardFailureException (extends IllegalArgumentException)
		}

		@Test
		void fluent() {
			InputGuard<String, Integer> myGuard = InputGuard.builder().forString()
				.sanitize().strip().replace(".0", "").then()
				.validateThat().isMaxLength(3).then()
				.mapToInteger()
				.build();

			Assertions.assertThat(myGuard.process(" 123.0 ").getOrThrow()).isEqualTo(123); // 123

			Assertions.assertThatExceptionOfType(InputGuardFailureException.class)
				.isThrownBy(() -> myGuard.process(" abc ").getOrThrow()); // throw InputGuardFailureException (extends IllegalArgumentException)
		}

	}

}
