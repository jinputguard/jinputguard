package io.github.jinputguard.builder.base.types;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.result.ProcessResultAssert;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class StringInputGuardBuilderTest {

	@Nested
	class Sanitization {

		@Nested
		class Strip {

			@Test
			void nominal() {
				var guard = InputGuard.builder().forString().sanitize().strip().then().build();
				var actualResult = guard.process(" \t plop \r\n");
				ProcessResultAssert.assertThat(actualResult).isSuccessWithValue("plop");
			}

			@Test
			void full_whitespace() {
				var guard = InputGuard.builder().forString().sanitize().strip().then().build();
				var actualResult = guard.process(" \t \r\n");
				ProcessResultAssert.assertThat(actualResult).isSuccessWithValue("");
			}

		}

		@Nested
		class ToLowerCase {

			@Test
			void nominal() {
				var guard = InputGuard.builder().forString().sanitize().toLowerCase().then().build();
				var actualResult = guard.process("THIS IS Éé");
				ProcessResultAssert.assertThat(actualResult).isSuccessWithValue("this is éé");
			}

		}

		@Nested
		class ToUpperCase {

			@Test
			void nominal() {
				var guard = InputGuard.builder().forString().sanitize().toUpperCase().then().build();
				var actualResult = guard.process("THIS IS Éé");
				ProcessResultAssert.assertThat(actualResult).isSuccessWithValue("THIS IS ÉÉ");
			}

		}

		@Nested
		class Prefix {

			@Test
			void nominal() {
				var guard = InputGuard.builder().forString().sanitize().prefix("p-").then().build();
				var actualResult = guard.process("plop");
				ProcessResultAssert.assertThat(actualResult).isSuccessWithValue("p-plop");
			}

			@Test
			void null_prefix_throws_NPE() {
				Assertions.assertThatNullPointerException().isThrownBy(() -> InputGuard.builder().forString().sanitize().prefix(null));
			}

			@Test
			void empty_prefix_does_nothing() {
				var guard = InputGuard.builder().forString().sanitize().prefix("").then().build();
				var actualResult = guard.process("plop");
				ProcessResultAssert.assertThat(actualResult).isSuccessWithValue("plop");
			}

			@Test
			void when_already_prefixed_do_nothing() {
				var guard = InputGuard.builder().forString().sanitize().prefix("p-").then().build();
				var actualResult = guard.process("p-plop");
				ProcessResultAssert.assertThat(actualResult).isSuccessWithValue("p-plop");
			}

			@Test
			void when_already_prefixed_with_other_case_then_do_prefix() {
				var guard = InputGuard.builder().forString().sanitize().prefix("p-").then().build();
				var actualResult = guard.process("P-plop");
				ProcessResultAssert.assertThat(actualResult).isSuccessWithValue("p-P-plop");
			}

		}

		@Nested
		class Suffix {

			@Test
			void nominal() {
				var guard = InputGuard.builder().forString().sanitize().suffix("-s").then().build();
				var actualResult = guard.process("plop");
				ProcessResultAssert.assertThat(actualResult).isSuccessWithValue("plop-s");
			}

			@Test
			void null_suffix_throws_NPE() {
				Assertions.assertThatNullPointerException().isThrownBy(() -> InputGuard.builder().forString().sanitize().suffix(null));
			}

			@Test
			void empty_suffix_does_nothing() {
				var guard = InputGuard.builder().forString().sanitize().suffix("").then().build();
				var actualResult = guard.process("plop");
				ProcessResultAssert.assertThat(actualResult).isSuccessWithValue("plop");
			}

			@Test
			void when_already_suffixed_do_nothing() {
				var guard = InputGuard.builder().forString().sanitize().suffix("-s").then().build();
				var actualResult = guard.process("plop-s");
				ProcessResultAssert.assertThat(actualResult).isSuccessWithValue("plop-s");
			}

			@Test
			void when_already_suffixed_with_other_case_then_do_suffix() {
				var guard = InputGuard.builder().forString().sanitize().suffix("-s").then().build();
				var actualResult = guard.process("plop-S");
				ProcessResultAssert.assertThat(actualResult).isSuccessWithValue("plop-S-s");
			}

		}

		@Nested
		class Replace_Char_Char {

			@Test
			void nominal() {
				var guard = InputGuard.builder().forString().sanitize().replace('p', 'a').then().build();
				var actualResult = guard.process("plop");
				ProcessResultAssert.assertThat(actualResult).isSuccessWithValue("aloa");
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
				var actualResult = guard.process("plop");
				ProcessResultAssert.assertThat(actualResult).isSuccessWithValue("aloa");
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
				var actualResult = guard.process("replaced: anb5 / replaced: anb5 / not replaced: ab5");
				ProcessResultAssert.assertThat(actualResult).isSuccessWithValue("replaced: zzz / replaced: zzz / not replaced: ab5");
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
				var actualResult = guard.process("replaced: anb5 / replaced: anb5 / not replaced: ab5");
				ProcessResultAssert.assertThat(actualResult).isSuccessWithValue("replaced: zzz / replaced: zzz / not replaced: ab5");
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
				var actualResult = guard.process("replaced: anb5 / not replaced: anb5 / not replaced: ab5");
				ProcessResultAssert.assertThat(actualResult).isSuccessWithValue("replaced: zzz / not replaced: anb5 / not replaced: ab5");
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
				var actualResult = guard.process("replaced: anb5 / not replaced: anb5 / not replaced: ab5");
				ProcessResultAssert.assertThat(actualResult).isSuccessWithValue("replaced: zzz / not replaced: anb5 / not replaced: ab5");
			}

		}

	}

	@Nested
	class Validation {

		@Nested
		class IsMaxLength {

			private static InputGuard<String, String> GUARD;

			@BeforeAll
			static void setup() {
				GUARD = InputGuard.builder().forString().validateThat().isMaxLength(5).then().build();
			}

			@Test
			void maxLength_negative_throws_IllegalArgumentException() {
				Assertions.assertThatIllegalArgumentException().isThrownBy(
					() -> InputGuard.builder().forString().validateThat().isMaxLength(-1).then().build()
				);
			}

			@Test
			void when_shorter_then_success() {
				var actual = GUARD.process("1234");
				ProcessResultAssert.assertThat(actual).isSuccessWithValue("1234");
			}

			@Test
			void when_exactMaxLength_then_success() {
				var actual = GUARD.process("12345");
				ProcessResultAssert.assertThat(actual).isSuccessWithValue("12345");
			}

			@Test
			void when_longer_then_failure() {
				var actual = GUARD.process("123456");
				ProcessResultAssert.assertThat(actual).isFailure()
					.isValidationFailure()
					.errorAssert(assertor -> assertor.isStringIsTooLong(6, 5));
			}

		}

		@Nested
		class CanBeParsedToInteger {

			private static InputGuard<String, String> GUARD;

			@BeforeAll
			static void setup() {
				GUARD = InputGuard.builder().forString().validateThat().canBeParsedToInteger().then().build();
			}

			@ParameterizedTest
			@ValueSource(
				strings = {
					"-1", "0", "1", "+1"
				}
			)
			void when_valid_then_success(String value) {
				var actual = GUARD.process(value);
				ProcessResultAssert.assertThat(actual).isSuccessWithValue(value);
			}

			@Test
			void when_minValue_then_success() {
				var actual = GUARD.process(String.valueOf(Integer.MIN_VALUE));
				ProcessResultAssert.assertThat(actual).isSuccessWithValue(String.valueOf(Integer.MIN_VALUE));
			}

			@Test
			void when_maxValue_then_success() {
				var actual = GUARD.process(String.valueOf(Integer.MAX_VALUE));
				ProcessResultAssert.assertThat(actual).isSuccessWithValue(String.valueOf(Integer.MAX_VALUE));
			}

			@ParameterizedTest
			@ValueSource(
				strings = {
					"-21474836481", // Integer.MIN_VALUE - 1 (64 bits)
					"2147483648", // Integer.MAX_VALUE + 1 (64 bits)
				}
			)
			void when_invalid_then_failure(String value) {
				var actual = GUARD.process(value);
				ProcessResultAssert.assertThat(actual).isFailure()
					.isValidationFailure()
					.errorAssert(assertor -> assertor.isStringMustBeParseableToInteger())
					.hasValidationMessage("is not parseable to Integer");
			}

		}

		@Nested
		class Matches_String {

			private static final String REGEX = "abc[0-9]";
			private static InputGuard<String, String> GUARD;

			@BeforeAll
			static void setup() {
				GUARD = InputGuard.builder().forString().validateThat().matches(REGEX).then().build();
			}

			@Test
			void pattern_null_throws_NPE() {
				Assertions.assertThatNullPointerException().isThrownBy(
					() -> InputGuard.builder().forString().validateThat().matches((String) null).then().build()
				);
			}

			@Test
			void pattern_null_throws_PatternSyntaxException() {
				Assertions.assertThatExceptionOfType(PatternSyntaxException.class).isThrownBy(
					() -> InputGuard.builder().forString().validateThat().matches("a{0").then().build()
				);
			}

			@Test
			void when_entireRegion_then_success() {
				var actual = GUARD.process("abc9");
				ProcessResultAssert.assertThat(actual).isSuccessWithValue("abc9");
			}

			@Test
			void when_subRegion_then_failure() {
				var actual = GUARD.process("zabc9z");
				ProcessResultAssert.assertThat(actual).isFailure()
					.isValidationFailure()
					.errorAssert(assertor -> assertor.isStringMustMatchPattern(REGEX))
					.hasValidationMessage("must match pattern " + REGEX);
			}

		}

		@Nested
		class Matches_Pattern {

			private static final Pattern PATTERN = Pattern.compile("abc[0-9]");
			private static InputGuard<String, String> GUARD;

			@BeforeAll
			static void setup() {
				GUARD = InputGuard.builder().forString().validateThat().matches(PATTERN).then().build();
			}

			@Test
			void pattern_null_throws_NPE() {
				Assertions.assertThatNullPointerException().isThrownBy(
					() -> InputGuard.builder().forString().validateThat().matches((Pattern) null).then().build()
				);
			}

			@Test
			void when_entireRegion_then_success() {
				var actual = GUARD.process("abc9");
				ProcessResultAssert.assertThat(actual).isSuccessWithValue("abc9");
			}

			@Test
			void when_subRegion_then_failure() {
				var actual = GUARD.process("zabc9z");
				ProcessResultAssert.assertThat(actual).isFailure()
					.isValidationFailure()
					.errorAssert(assertor -> assertor.isStringMustMatchPattern(PATTERN))
					.hasValidationMessage("must match pattern " + PATTERN.pattern());
			}

		}

	}

	@Nested
	class Mapping {

		@Nested
		class MapToInteger {

			private static InputGuard<String, Integer> GUARD;

			@BeforeAll
			static void setup() {
				GUARD = InputGuard.builder().forString().mapToInteger().build();
			}

			@Test
			void when_negative_then_success() {
				var actual = GUARD.process("-1");
				ProcessResultAssert.assertThat(actual).isSuccessWithValue(-1);
			}

			@Test
			void when_zero_then_success() {
				var actual = GUARD.process("0");
				ProcessResultAssert.assertThat(actual).isSuccessWithValue(0);
			}

			@Test
			void when_positive_then_success() {
				var actual = GUARD.process("1");
				ProcessResultAssert.assertThat(actual).isSuccessWithValue(1);
			}

			@Test
			void when_positiveWithSign_then_success() {
				var actual = GUARD.process("+1");
				ProcessResultAssert.assertThat(actual).isSuccessWithValue(1);
			}

			@Test
			void when_minValue_then_success() {
				var actual = GUARD.process(String.valueOf(Integer.MIN_VALUE));
				ProcessResultAssert.assertThat(actual).isSuccessWithValue(Integer.MIN_VALUE);
			}

			@Test
			void when_maxValue_then_success() {
				var actual = GUARD.process(String.valueOf(Integer.MAX_VALUE));
				ProcessResultAssert.assertThat(actual).isSuccessWithValue(Integer.MAX_VALUE);
			}

			@Test
			void when_beforeMinValue_then_failure() {
				var actual = GUARD.process("-2147483649"); // Integer.MIN_VALUE - 1 (64 bits)

				ProcessResultAssert.assertThat(actual).isFailure()
					.isMappingFailure()
					.hasValueEqualTo("-2147483649")
					.hasCauseInstanceOf(NumberFormatException.class);
			}

			@Test
			void when_beyondMaxValue_then_failure() {
				var actual = GUARD.process("2147483648"); // Integer.MAX_VALUE + 1 (64 bits)

				ProcessResultAssert.assertThat(actual).isFailure()
					.isMappingFailure()
					.hasValueEqualTo("2147483648")
					.hasCauseInstanceOf(NumberFormatException.class);
			}

		}

		@Nested
		class MapToLong {

			private static InputGuard<String, Long> GUARD;

			@BeforeAll
			static void setup() {
				GUARD = InputGuard.builder().forString().mapToLong().build();
			}

			@Test
			void when_negative_then_success() {
				var actual = GUARD.process("-1");
				ProcessResultAssert.assertThat(actual).isSuccessWithValue(-1L);
			}

			@Test
			void when_zero_then_success() {
				var actual = GUARD.process("0");
				ProcessResultAssert.assertThat(actual).isSuccessWithValue(0L);
			}

			@Test
			void when_positive_then_success() {
				var actual = GUARD.process("1");
				ProcessResultAssert.assertThat(actual).isSuccessWithValue(1L);
			}

			@Test
			void when_positiveWithSign_then_success() {
				var actual = GUARD.process("+1");
				ProcessResultAssert.assertThat(actual).isSuccessWithValue(1L);
			}

			@Test
			void when_minValue_then_success() {
				var actual = GUARD.process(String.valueOf(Long.MIN_VALUE));
				ProcessResultAssert.assertThat(actual).isSuccessWithValue(Long.MIN_VALUE);
			}

			@Test
			void when_maxValue_then_success() {
				var actual = GUARD.process(String.valueOf(Long.MAX_VALUE));
				ProcessResultAssert.assertThat(actual).isSuccessWithValue(Long.MAX_VALUE);
			}

			@Test
			void when_beforeMinValue_then_failure() {
				var actual = GUARD.process("-9223372036854775809"); // Long.MIN_VALUE - 1 (64 bits)

				ProcessResultAssert.assertThat(actual).isFailure()
					.hasValueEqualTo("-9223372036854775809")
					.isMappingFailure()
					.hasCauseInstanceOf(NumberFormatException.class);
			}

			@Test
			void when_beyondMaxValue_then_failure() {
				var actual = GUARD.process("9223372036854775808"); // Long.MAX_VALUE + 1 (64 bits)

				ProcessResultAssert.assertThat(actual).isFailure()
					.hasValueEqualTo("9223372036854775808")
					.isMappingFailure()
					.hasCauseInstanceOf(NumberFormatException.class);
			}

		}

	}

}
