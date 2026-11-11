package io.github.jinputguard.builder.base.types;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class StringValidationTest {

	@Nested
	class IsNotEmpty {

		private static InputGuard<String, String> GUARD;

		@BeforeAll
		static void setup() {
			GUARD = InputGuard.builder().forString().isNotEmpty().build();
		}

		@Test
		void when_empty_then_failure() {
			var actual = GUARD.processToResult("", "myVal");
			GuardResultAssert.assertThat(actual).isFailure()
				.hasMessage("myVal must not be empty");
		}

		@Test
		void when_notEmpty_then_success() {
			var actual = GUARD.processToResult("plop", "myVal");
			GuardResultAssert.assertThat(actual).isSuccess("plop");
		}

	}

	@Nested
	class isNotBlank {

		private static InputGuard<String, String> GUARD;

		@BeforeAll
		static void setup() {
			GUARD = InputGuard.builder().forString().isNotBlank().build();
		}

		@Test
		void when_blank_then_failure() {
			var actual = GUARD.processToResult(" \t \r\n", "myVal");
			GuardResultAssert.assertThat(actual).isFailure()
				.hasMessage("myVal must not be blank");
		}

		@Test
		void when_notBlank_then_success() {
			var actual = GUARD.processToResult("plop", "myVal");
			GuardResultAssert.assertThat(actual).isSuccess("plop");
		}

	}

	@Nested
	class HasExactLength {

		private static InputGuard<String, String> GUARD;

		@BeforeAll
		static void setup() {
			GUARD = InputGuard.builder().forString().hasLength(5).build();
		}

		@Test
		void length_negative_throws_IllegalArgumentException() {
			Assertions.assertThatIllegalArgumentException().isThrownBy(
				() -> InputGuard.builder().forString().hasLength(-1).build()
			);
		}

		@Test
		void when_longer_then_failure() {
			var actual = GUARD.processToResult("123456", "myVal");
			GuardResultAssert.assertThat(actual).isFailure()
				.hasMessage("myVal must be exactly 5 char(s) long");
		}

		@Test
		void when_exactMaxLength_then_success() {
			var actual = GUARD.processToResult("12345", "myVal");
			GuardResultAssert.assertThat(actual).isSuccess("12345");
		}

		@Test
		void when_shorter_then_failure() {
			var actual = GUARD.processToResult("1234", "myVal");
			GuardResultAssert.assertThat(actual).isFailure()
				.hasMessage("myVal must be exactly 5 char(s) long");
		}

	}

	@Nested
	class HasLengthBetween {

		private static InputGuard<String, String> GUARD;

		@BeforeAll
		static void setup() {
			GUARD = InputGuard.builder().forString().hasLengthBetween(4, 6).build();
		}

		@Test
		void minLength_negative_throws_IllegalArgumentException() {
			Assertions.assertThatIllegalArgumentException().isThrownBy(
				() -> InputGuard.builder().forString().hasLengthBetween(-1, 6).build()
			);
		}

		@Test
		void maxLength_negative_throws_IllegalArgumentException() {
			Assertions.assertThatIllegalArgumentException().isThrownBy(
				() -> InputGuard.builder().forString().hasLengthBetween(4, -1).build()
			);
		}

		@Test
		void minLength_greater_than_maxLength_throws_IllegalArgumentException() {
			Assertions.assertThatIllegalArgumentException().isThrownBy(
				() -> InputGuard.builder().forString().hasLengthBetween(6, 4).build()
			);
		}

		@Test
		void when_shorter_then_failure() {
			var actual = GUARD.processToResult("123", "myVal");
			GuardResultAssert.assertThat(actual).isFailure()
				.hasMessage("myVal must be between 4 and 6 char(s) long");
		}

		@Test
		void when_exactMinLength_then_success() {
			var actual = GUARD.processToResult("1234", "myVal");
			GuardResultAssert.assertThat(actual).isSuccess("1234");
		}

		@Test
		void when_inRange_then_success() {
			var actual = GUARD.processToResult("12345", "myVal");
			GuardResultAssert.assertThat(actual).isSuccess("12345");
		}

		@Test
		void when_exactMaxLength_then_success() {
			var actual = GUARD.processToResult("123456", "myVal");
			GuardResultAssert.assertThat(actual).isSuccess("123456");
		}

		@Test
		void when_longer_then_failure() {
			var actual = GUARD.processToResult("1234567", "myVal");
			GuardResultAssert.assertThat(actual).isFailure()
				.hasMessage("myVal must be between 4 and 6 char(s) long");
		}

	}

	@Nested
	class HasMinLength {

		private static InputGuard<String, String> GUARD;

		@BeforeAll
		static void setup() {
			GUARD = InputGuard.builder().forString().hasMinLength(5).build();
		}

		@Test
		void minLength_negative_throws_IllegalArgumentException() {
			Assertions.assertThatIllegalArgumentException().isThrownBy(
				() -> InputGuard.builder().forString().hasMinLength(-1).build()
			);
		}

		@Test
		void when_longer_then_success() {
			var actual = GUARD.processToResult("123456", "myVal");
			GuardResultAssert.assertThat(actual).isSuccess("123456");
		}

		@Test
		void when_exactMaxLength_then_success() {
			var actual = GUARD.processToResult("12345", "myVal");
			GuardResultAssert.assertThat(actual).isSuccess("12345");
		}

		@Test
		void when_shorter_then_failure() {
			var actual = GUARD.processToResult("1234", "myVal");
			GuardResultAssert.assertThat(actual).isFailure()
				.hasMessage("myVal is too short: 5 chars min");
		}

	}

	@Nested
	class HasMaxLength {

		private static InputGuard<String, String> GUARD;

		@BeforeAll
		static void setup() {
			GUARD = InputGuard.builder().forString().hasMaxLength(5).build();
		}

		@Test
		void maxLength_negative_throws_IllegalArgumentException() {
			Assertions.assertThatIllegalArgumentException().isThrownBy(
				() -> InputGuard.builder().forString().hasMaxLength(-1).build()
			);
		}

		@Test
		void when_shorter_then_success() {
			var actual = GUARD.processToResult("1234", "myVal");
			GuardResultAssert.assertThat(actual).isSuccess("1234");
		}

		@Test
		void when_exactMaxLength_then_success() {
			var actual = GUARD.processToResult("12345", "myVal");
			GuardResultAssert.assertThat(actual).isSuccess("12345");
		}

		@Test
		void when_longer_then_failure() {
			var actual = GUARD.processToResult("123456", "myVal");
			GuardResultAssert.assertThat(actual).isFailure()
				.hasMessage("myVal is too long: 5 chars max");
		}

	}

	@Nested
	class Matches_String {

		private static final String REGEX = "abc[0-9]";
		private static InputGuard<String, String> GUARD;

		@BeforeAll
		static void setup() {
			GUARD = InputGuard.builder().forString().matches(REGEX).build();
		}

		@Test
		void pattern_null_throws_NPE() {
			Assertions.assertThatNullPointerException().isThrownBy(
				() -> InputGuard.builder().forString().matches((String) null).build()
			);
		}

		@Test
		void pattern_null_throws_PatternSyntaxException() {
			Assertions.assertThatExceptionOfType(PatternSyntaxException.class).isThrownBy(
				() -> InputGuard.builder().forString().matches("a{0").build()
			);
		}

		@Test
		void when_entireRegion_then_success() {
			var actual = GUARD.processToResult("abc9", "myVal");
			GuardResultAssert.assertThat(actual).isSuccess("abc9");
		}

		@Test
		void when_subRegion_then_failure() {
			var actual = GUARD.processToResult("zabc9z", "myVal");
			GuardResultAssert.assertThat(actual).isFailure()
				.hasMessage("myVal must match pattern " + REGEX);
		}

	}

	@Nested
	class Matches_Pattern {

		private static final Pattern PATTERN = Pattern.compile("abc[0-9]");
		private static InputGuard<String, String> GUARD;

		@BeforeAll
		static void setup() {
			GUARD = InputGuard.builder().forString().matches(PATTERN).build();
		}

		@Test
		void pattern_null_throws_NPE() {
			Assertions.assertThatNullPointerException().isThrownBy(
				() -> InputGuard.builder().forString().matches((Pattern) null).build()
			);
		}

		@Test
		void when_entireRegion_then_success() {
			var actual = GUARD.processToResult("abc9", "myVal");
			GuardResultAssert.assertThat(actual).isSuccess("abc9");
		}

		@Test
		void when_subRegion_then_failure() {
			var actual = GUARD.processToResult("zabc9z", "myVal");
			GuardResultAssert.assertThat(actual).isFailure()
				.hasMessage("myVal must match pattern " + PATTERN.pattern());
		}

	}

}