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
			var guard = InputGuard.builder().forString().sanitize().strip().then().build();
			var actualResult = guard.process(" \t plop \r\n", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("plop");
		}

		@Test
		void full_whitespace() {
			var guard = InputGuard.builder().forString().sanitize().strip().then().build();
			var actualResult = guard.process(" \t \r\n", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("");
		}

	}

	@Nested
	class ToLowerCase {

		@Test
		void nominal() {
			var guard = InputGuard.builder().forString().sanitize().toLowerCase().then().build();
			var actualResult = guard.process("THIS IS Éé", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("this is éé");
		}

	}

	@Nested
	class ToUpperCase {

		@Test
		void nominal() {
			var guard = InputGuard.builder().forString().sanitize().toUpperCase().then().build();
			var actualResult = guard.process("THIS IS Éé", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("THIS IS ÉÉ");
		}

	}

	@Nested
	class Prefix {

		@Test
		void nominal() {
			var guard = InputGuard.builder().forString().sanitize().prefix("p-").then().build();
			var actualResult = guard.process("plop", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("p-plop");
		}

		@Test
		void null_prefix_throws_NPE() {
			Assertions.assertThatNullPointerException().isThrownBy(() -> InputGuard.builder().forString().sanitize().prefix(null));
		}

		@Test
		void empty_prefix_does_nothing() {
			var guard = InputGuard.builder().forString().sanitize().prefix("").then().build();
			var actualResult = guard.process("plop", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("plop");
		}

		@Test
		void when_already_prefixed_do_nothing() {
			var guard = InputGuard.builder().forString().sanitize().prefix("p-").then().build();
			var actualResult = guard.process("p-plop", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("p-plop");
		}

		@Test
		void when_already_prefixed_with_other_case_then_do_prefix() {
			var guard = InputGuard.builder().forString().sanitize().prefix("p-").then().build();
			var actualResult = guard.process("P-plop", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("p-P-plop");
		}

	}

	@Nested
	class Suffix {

		@Test
		void nominal() {
			var guard = InputGuard.builder().forString().sanitize().suffix("-s").then().build();
			var actualResult = guard.process("plop", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("plop-s");
		}

		@Test
		void null_suffix_throws_NPE() {
			Assertions.assertThatNullPointerException().isThrownBy(() -> InputGuard.builder().forString().sanitize().suffix(null));
		}

		@Test
		void empty_suffix_does_nothing() {
			var guard = InputGuard.builder().forString().sanitize().suffix("").then().build();
			var actualResult = guard.process("plop", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("plop");
		}

		@Test
		void when_already_suffixed_do_nothing() {
			var guard = InputGuard.builder().forString().sanitize().suffix("-s").then().build();
			var actualResult = guard.process("plop-s", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("plop-s");
		}

		@Test
		void when_already_suffixed_with_other_case_then_do_suffix() {
			var guard = InputGuard.builder().forString().sanitize().suffix("-s").then().build();
			var actualResult = guard.process("plop-S", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("plop-S-s");
		}

	}

	@Nested
	class Replace_Char_Char {

		@Test
		void nominal() {
			var guard = InputGuard.builder().forString().sanitize().replace('p', 'a').then().build();
			var actualResult = guard.process("plop", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("aloa");
		}

	}

	@Nested
	class Replace_CharSequence_CharSequence {

		@Test
		void target_null_throws_NPE() {
			Assertions.assertThatNullPointerException().isThrownBy(
				() -> InputGuard.builder().forString().sanitize().replace(null, "zzz").then().build()
			);
		}

		@Test
		void replacement_null_throws_NPE() {
			Assertions.assertThatNullPointerException().isThrownBy(
				() -> InputGuard.builder().forString().sanitize().replace("p", null).then().build()
			);
		}

		@Test
		void nominal() {
			var guard = InputGuard.builder().forString().sanitize().replace("p", "a").then().build();
			var actualResult = guard.process("plop", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("aloa");
		}

	}

	@Nested
	class ReplaceAll_String_String {

		@Test
		void regex_null_throws_NPE() {
			Assertions.assertThatNullPointerException().isThrownBy(
				() -> InputGuard.builder().forString().sanitize().replaceAll((String) null, "zzz").then().build()
			);
		}

		@Test
		void regex_invalid_throws_PatternSyntaxException() {
			Assertions.assertThatExceptionOfType(PatternSyntaxException.class).isThrownBy(
				() -> InputGuard.builder().forString().sanitize().replaceAll("a{0", "zzz").then().build()
			);
		}

		@Test
		void replacement_null_throws_NPE() {
			Assertions.assertThatNullPointerException().isThrownBy(
				() -> InputGuard.builder().forString().sanitize().replaceAll("a.b[0-9]", null).then().build()
			);
		}

		@Test
		void nominal() {
			var guard = InputGuard.builder().forString().sanitize().replaceAll("a.b[0-9]", "zzz").then().build();
			var actualResult = guard.process("replaced: anb5 / replaced: anb5 / not replaced: ab5", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("replaced: zzz / replaced: zzz / not replaced: ab5");
		}

	}

	@Nested
	class ReplaceAll_Pattern_String {

		private static final Pattern PATTERN = Pattern.compile("a.b[0-9]");

		@Test
		void pattern_null_throws_NPE() {
			Assertions.assertThatNullPointerException().isThrownBy(
				() -> InputGuard.builder().forString().sanitize().replaceAll((Pattern) null, "zzz").then().build()
			);
		}

		@Test
		void replacement_null_throws_NPE() {
			Assertions.assertThatNullPointerException().isThrownBy(
				() -> InputGuard.builder().forString().sanitize().replaceAll(PATTERN, null).then().build()
			);
		}

		@Test
		void nominal() {
			var guard = InputGuard.builder().forString().sanitize().replaceAll(PATTERN, "zzz").then().build();
			var actualResult = guard.process("replaced: anb5 / replaced: anb5 / not replaced: ab5", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("replaced: zzz / replaced: zzz / not replaced: ab5");
		}

	}

	@Nested
	class ReplaceFirst_String {

		@Test
		void regex_null_throws_NPE() {
			Assertions.assertThatNullPointerException().isThrownBy(
				() -> InputGuard.builder().forString().sanitize().replaceFirst((String) null, "zzz").then().build()
			);
		}

		@Test
		void regex_invalid_throws_PatternSyntaxException() {
			Assertions.assertThatExceptionOfType(PatternSyntaxException.class).isThrownBy(
				() -> InputGuard.builder().forString().sanitize().replaceFirst("a{0", "zzz").then().build()
			);
		}

		@Test
		void replacement_null_throws_NPE() {
			Assertions.assertThatNullPointerException().isThrownBy(
				() -> InputGuard.builder().forString().sanitize().replaceFirst("a.b[0-9]", null).then().build()
			);
		}

		@Test
		void nominal() {
			var guard = InputGuard.builder().forString().sanitize().replaceFirst("a.b[0-9]", "zzz").then().build();
			var actualResult = guard.process("replaced: anb5 / not replaced: anb5 / not replaced: ab5", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("replaced: zzz / not replaced: anb5 / not replaced: ab5");
		}

	}

	@Nested
	class ReplaceFirst_Pattern_String {

		private static final Pattern PATTERN = Pattern.compile("a.b[0-9]");

		@Test
		void pattern_null_throws_NPE() {
			Assertions.assertThatNullPointerException().isThrownBy(
				() -> InputGuard.builder().forString().sanitize().replaceFirst((Pattern) null, "zzz").then().build()
			);
		}

		@Test
		void replacement_null_throws_NPE() {
			Assertions.assertThatNullPointerException().isThrownBy(
				() -> InputGuard.builder().forString().sanitize().replaceFirst(PATTERN, null).then().build()
			);
		}

		@Test
		void nominal() {
			var guard = InputGuard.builder().forString().sanitize().replaceFirst(PATTERN, "zzz").then().build();
			var actualResult = guard.process("replaced: anb5 / not replaced: anb5 / not replaced: ab5", "myVal");
			GuardResultAssert.assertThat(actualResult).isSuccess("replaced: zzz / not replaced: anb5 / not replaced: ab5");
		}

	}

}