package io.github.jinputguard.examples;

import io.github.jinputguard.InputGuard;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * In this example, we try to build various "LastName" value objects,
 * using the LastName.GUARD instanciated below.
 */
class ValueObjectTest {

	@Test
	void test_examples() {
		Assertions.assertThat(LastName.of(null).toString()).isEqualTo("N/A");
		Assertions.assertThat(LastName.of(" Smith  ").toString()).isEqualTo("SMITH");
		Assertions.assertThat(LastName.of("DOE").toString()).isEqualTo("DOE");
		Assertions.assertThatIllegalArgumentException().isThrownBy(() -> LastName.of("")).withMessageContaining("must not be empty");
		Assertions.assertThatIllegalArgumentException().isThrownBy(() -> LastName.of("ThisIsAWayTooLongLastName")).withMessageContaining(" must be 20 chars max, but is 25");
	}

	/**
	 * A simple value object representing lastname of a user.
	 */
	static class LastName {

		private static final int MAX_LENGTH = 20; // Keep low for the sake of this example. Real production code would of course allow greater value!

		/*
		 * A guard that will first apply some sanitization:
		 *   - If the input string is null, it defaults to "N/A"
		 *   - Removes leading and trailing whitespace
		 *   - Converts the string to uppercase
		 *   
		 * Then it perform a few validations:
		 *   - Ensures the string is not empty
		 *   - Ensures the string does not exceed a specified maximum length (MAX_LENGTH)
		 * 
		 * Finally, it maps the input string to the LastName value object, using its constructor.
		 */

		// @formatter:off
		private static final InputGuard<String, LastName> GUARD = InputGuard.builder()
			.forString()
			.ifNullThen().useDefault("N/A")
			.sanitize()
				.strip()
				.toUpperCase()
				.then()
			.validateThat()
				.isNotEmpty()
				.isMaxLength(MAX_LENGTH)
				.then()
			.map(LastName::new)
			.build();
		// @formatter:on

		private final String value;

		private LastName(String value) {
			this.value = value;
		}

		public static LastName of(String value) {
			return GUARD.process(value, "lastName").getOrThrow();
		}

		@Override
		public String toString() {
			return value;
		}

	}

}
