package io.github.jinputguard.builder.base.types;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class StringSanitizationTest {

	@Nested
	class Strip {

		@Test
		void nominal() {
			var guard = InputGuard.builder().forString().strip().build();
			var actualResult = guard.processToResult(" \t plop \r\n", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("plop");
		}

		@Test
		void full_whitespace() {
			var guard = InputGuard.builder().forString().strip().build();
			var actualResult = guard.processToResult(" \t \r\n", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("");
		}

	}

	@Nested
	class ToLowerCase {

		@Test
		void nominal() {
			var guard = InputGuard.builder().forString().toLowerCase().build();
			var actualResult = guard.processToResult("THIS IS Éé", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("this is éé");
		}

	}

	@Nested
	class ToUpperCase {

		@Test
		void nominal() {
			var guard = InputGuard.builder().forString().toUpperCase().build();
			var actualResult = guard.processToResult("THIS IS Éé", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("THIS IS ÉÉ");
		}

	}

	@Nested
	class Prefix {

		@Test
		void nominal() {
			var guard = InputGuard.builder().forString().prefix("p-").build();
			var actualResult = guard.processToResult("plop", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("p-plop");
		}

		@Test
		void null_prefix_throws_NPE() {
			Assertions.assertThatNullPointerException().isThrownBy(() -> InputGuard.builder().forString().prefix(null));
		}

		@Test
		void empty_prefix_does_nothing() {
			var guard = InputGuard.builder().forString().prefix("").build();
			var actualResult = guard.processToResult("plop", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("plop");
		}

		@Test
		void when_already_prefixed_do_nothing() {
			var guard = InputGuard.builder().forString().prefix("p-").build();
			var actualResult = guard.processToResult("p-plop", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("p-plop");
		}

		@Test
		void when_already_prefixed_with_other_case_then_do_prefix() {
			var guard = InputGuard.builder().forString().prefix("p-").build();
			var actualResult = guard.processToResult("P-plop", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("p-P-plop");
		}

	}

	@Nested
	class Suffix {

		@Test
		void nominal() {
			var guard = InputGuard.builder().forString().suffix("-s").build();
			var actualResult = guard.processToResult("plop", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("plop-s");
		}

		@Test
		void null_suffix_throws_NPE() {
			Assertions.assertThatNullPointerException().isThrownBy(() -> InputGuard.builder().forString().suffix(null));
		}

		@Test
		void empty_suffix_does_nothing() {
			var guard = InputGuard.builder().forString().suffix("").build();
			var actualResult = guard.processToResult("plop", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("plop");
		}

		@Test
		void when_already_suffixed_do_nothing() {
			var guard = InputGuard.builder().forString().suffix("-s").build();
			var actualResult = guard.processToResult("plop-s", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("plop-s");
		}

		@Test
		void when_already_suffixed_with_other_case_then_do_suffix() {
			var guard = InputGuard.builder().forString().suffix("-s").build();
			var actualResult = guard.processToResult("plop-S", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("plop-S-s");
		}

	}

	@Nested
	class Replace_Char_Char {

		@Test
		void nominal() {
			var guard = InputGuard.builder().forString().replace('p', 'a').build();
			var actualResult = guard.processToResult("plop", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("aloa");
		}

	}

	@Nested
	class Replace_CharSequence_CharSequence {

		@Test
		void target_null_throws_NPE() {
			Assertions.assertThatNullPointerException().isThrownBy(
				() -> InputGuard.builder().forString().replace(null, "zzz").build()
			);
		}

		@Test
		void replacement_null_throws_NPE() {
			Assertions.assertThatNullPointerException().isThrownBy(
				() -> InputGuard.builder().forString().replace("p", null).build()
			);
		}

		@Test
		void nominal() {
			var guard = InputGuard.builder().forString().replace("p", "a").build();
			var actualResult = guard.processToResult("plop", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("aloa");
		}

	}

	@Nested
	class ReplaceAll_String_String {

		@Test
		void regex_null_throws_NPE() {
			Assertions.assertThatNullPointerException().isThrownBy(
				() -> InputGuard.builder().forString().replaceAll((String) null, "zzz").build()
			);
		}

		@Test
		void regex_invalid_throws_PatternSyntaxException() {
			Assertions.assertThatExceptionOfType(PatternSyntaxException.class).isThrownBy(
				() -> InputGuard.builder().forString().replaceAll("a{0", "zzz").build()
			);
		}

		@Test
		void replacement_null_throws_NPE() {
			Assertions.assertThatNullPointerException().isThrownBy(
				() -> InputGuard.builder().forString().replaceAll("a.b[0-9]", null).build()
			);
		}

		@Test
		void nominal() {
			var guard = InputGuard.builder().forString().replaceAll("a.b[0-9]", "zzz").build();
			var actualResult = guard.processToResult("replaced: anb5 / replaced: anb5 / not replaced: ab5", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("replaced: zzz / replaced: zzz / not replaced: ab5");
		}

	}

	@Nested
	class ReplaceAll_Pattern_String {

		private static final Pattern PATTERN = Pattern.compile("a.b[0-9]");

		@Test
		void pattern_null_throws_NPE() {
			Assertions.assertThatNullPointerException().isThrownBy(
				() -> InputGuard.builder().forString().replaceAll((Pattern) null, "zzz").build()
			);
		}

		@Test
		void replacement_null_throws_NPE() {
			Assertions.assertThatNullPointerException().isThrownBy(
				() -> InputGuard.builder().forString().replaceAll(PATTERN, null).build()
			);
		}

		@Test
		void nominal() {
			var guard = InputGuard.builder().forString().replaceAll(PATTERN, "zzz").build();
			var actualResult = guard.processToResult("replaced: anb5 / replaced: anb5 / not replaced: ab5", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("replaced: zzz / replaced: zzz / not replaced: ab5");
		}

	}

	@Nested
	class ReplaceFirst_String {

		@Test
		void regex_null_throws_NPE() {
			Assertions.assertThatNullPointerException().isThrownBy(
				() -> InputGuard.builder().forString().replaceFirst((String) null, "zzz").build()
			);
		}

		@Test
		void regex_invalid_throws_PatternSyntaxException() {
			Assertions.assertThatExceptionOfType(PatternSyntaxException.class).isThrownBy(
				() -> InputGuard.builder().forString().replaceFirst("a{0", "zzz").build()
			);
		}

		@Test
		void replacement_null_throws_NPE() {
			Assertions.assertThatNullPointerException().isThrownBy(
				() -> InputGuard.builder().forString().replaceFirst("a.b[0-9]", null).build()
			);
		}

		@Test
		void nominal() {
			var guard = InputGuard.builder().forString().replaceFirst("a.b[0-9]", "zzz").build();
			var actualResult = guard.processToResult("replaced: anb5 / not replaced: anb5 / not replaced: ab5", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("replaced: zzz / not replaced: anb5 / not replaced: ab5");
		}

	}

	@Nested
	class ReplaceFirst_Pattern_String {

		private static final Pattern PATTERN = Pattern.compile("a.b[0-9]");

		@Test
		void pattern_null_throws_NPE() {
			Assertions.assertThatNullPointerException().isThrownBy(
				() -> InputGuard.builder().forString().replaceFirst((Pattern) null, "zzz").build()
			);
		}

		@Test
		void replacement_null_throws_NPE() {
			Assertions.assertThatNullPointerException().isThrownBy(
				() -> InputGuard.builder().forString().replaceFirst(PATTERN, null).build()
			);
		}

		@Test
		void nominal() {
			var guard = InputGuard.builder().forString().replaceFirst(PATTERN, "zzz").build();
			var actualResult = guard.processToResult("replaced: anb5 / not replaced: anb5 / not replaced: ab5", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("replaced: zzz / not replaced: anb5 / not replaced: ab5");
		}

	}

}