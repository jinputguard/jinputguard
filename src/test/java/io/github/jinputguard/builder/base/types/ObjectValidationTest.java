package io.github.jinputguard.builder.base.types;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ObjectValidationTest {

	@Nested
	class ValidateTest {

		@Test
		void when_exception() {
			var exception = new RuntimeException("any runtime exception happening :-/");
			var guard = InputGuard.builder().forClass(Object.class)
				.validate((value, path) -> {
					throw exception;
				})
				.build();

			final var value = new Object();

			Assertions.assertThatRuntimeException()
				.isThrownBy(() -> guard.processToResult(value, "myVal"))
				.isEqualTo(exception);
		}

	}

	@Nested
	class Validate_Predicate_Function_Test {

		private static InputGuard<Object, Object> GUARD;

		@BeforeAll
		static void setup() {
			GUARD = InputGuard.builder().forClass(Object.class)
				.validate(Objects::isNull, p -> p + " is not null")
				.build();
		}

		@Test
		void success() {
			var actualResult = GUARD.processToResult(null, "myVal");

			GuardResultAssert.assertThat(actualResult).isSuccess(null);
		}

		@Test
		void failure() {
			var actualResult = GUARD.processToResult("plop", "myVal");

			GuardResultAssert.assertThat(actualResult).isFailure()
				.hasMessage("myVal is not null");
		}

	}

	@Nested
	class Validate_Predicate_String_Test {

		private static InputGuard<Object, Object> GUARD;

		@BeforeAll
		static void setup() {
			GUARD = InputGuard.builder().forClass(Object.class)
				.validate(Objects::isNull, p -> p + " is not null")
				.build();
		}

		@Test
		void success() {
			var actualResult = GUARD.processToResult(null, "myVal");

			GuardResultAssert.assertThat(actualResult).isSuccess(null);
		}

		@Test
		void failure() {
			var actualResult = GUARD.processToResult("plop", "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isFailure()
				.hasMessage("myVal is not null");
		}

	}

	@Nested
	class ValidateThatTest {

		@Nested
		class requireNotNullTest {

			private static InputGuard<Object, Object> GUARD;

			@BeforeAll
			static void setup() {
				GUARD = InputGuard.builder().forClass(Object.class)
					.requireNotNull()
					.build();
			}

			@Test
			void when_null_then_failure() {
				var actual = GUARD.processToResult(null, "myVal");
				GuardResultAssert.assertThat(actual)
					.isFailure()
					.hasMessage("myVal must not be null");
			}

			@Test
			void when_nonNull_then_success() {
				final var nonNullObject = new Object();
				var actual = GUARD.processToResult(nonNullObject, "myVal");
				GuardResultAssert.assertThat(actual).isSuccess(nonNullObject);
			}

			@Nested
			class IsInstanceOfTest {

				private static InputGuard<Number, Number> GUARD_FOR_NUMBER;
				private static InputGuard<Number, Number> GUARD_FOR_INTEGER;

				@BeforeAll
				static void setup() {
					GUARD_FOR_NUMBER = InputGuard.builder().forClass(Number.class)
						.requireInstanceOf(Number.class)
						.build();
					GUARD_FOR_INTEGER = InputGuard.builder().forClass(Number.class)
						.requireInstanceOf(Integer.class)
						.build();
				}

				@Test
				void when_null_then_failure() {
					// null is not instance of Integer
					var actual = GUARD_FOR_INTEGER.processToResult(null, "myVal");
					GuardResultAssert.assertThat(actual)
						.isFailure()
						.hasMessage("myVal must not be null");
				}

				@Test
				void when_sameType_then_success() {
					// Integer is instance of Integer
					final var intValue = Integer.valueOf(3);
					var actual = GUARD_FOR_INTEGER.processToResult(intValue, "myVal");
					GuardResultAssert.assertThat(actual).isSuccess(intValue);
				}

				@Test
				void when_superType_then_success() {
					// Integer is instance of Number
					final var intValue = Integer.valueOf(3);
					var actual = GUARD_FOR_NUMBER.processToResult(intValue, "myVal");
					GuardResultAssert.assertThat(actual).isSuccess(intValue);
				}

				@Test
				void when_otherType_then_failure() {
					// Long is not instance of Integer
					final var longValue = Long.valueOf(3L);
					var actual = GUARD_FOR_INTEGER.processToResult(longValue, "myVal");
					GuardResultAssert.assertThat(actual)
						.isFailure()
						.hasMessage("myVal must be an instance of " + Integer.class.getName());
				}

			}

			@Nested
			class RequireEqualTo {

				private static InputGuard<Object, Object> GUARD_FOR_STRING;
				private static Object expected = "<expected>";

				@BeforeAll
				static void setup() {
					GUARD_FOR_STRING = InputGuard.builder().forClass(Object.class)
						.requireEqualTo(expected)
						.build();
				}

				@Test
				void when_null_expected_and_null_input_then_success() {
					var actual = InputGuard.builder().forClass(Object.class)
						.requireEqualTo(null)
						.build()
						.processToResult(null, "myVal");
					GuardResultAssert.assertThat(actual).isSuccess(null);
				}

				@Test
				void when_null_expected_and_not_null_input_then_failure() {
					var actual = InputGuard.builder().forClass(Object.class)
						.requireEqualTo(null)
						.build()
						.processToResult("someValue", "myVal");
					GuardResultAssert.assertThat(actual).isFailure()
						.hasMessage("myVal must be null");
				}

				@Test
				void when_null_then_failure() {
					var actual = GUARD_FOR_STRING.processToResult(null, "myVal");
					GuardResultAssert.assertThat(actual).isFailure()
						.hasMessage("myVal must not be null");
				}

				@Test
				void when_notEqualValue_then_failure() {
					var actual = GUARD_FOR_STRING.processToResult("other", "myVal");
					GuardResultAssert.assertThat(actual).isFailure()
						.hasMessage("myVal must be equal to " + expected);
				}

				@Test
				void when_equalValue_then_failure() {
					var actual = GUARD_FOR_STRING.processToResult(expected, "myVal");
					GuardResultAssert.assertThat(actual).isSuccess(expected);
				}

			}

		}

	}

}