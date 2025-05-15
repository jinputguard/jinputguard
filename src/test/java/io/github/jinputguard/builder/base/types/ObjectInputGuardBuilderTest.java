package io.github.jinputguard.builder.base.types;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.result.GuardResultAssert;
import io.github.jinputguard.result.InputGuardFailureExceptionAssert;
import io.github.jinputguard.result.Path;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ObjectInputGuardBuilderTest {

	@Nested
	class NullStrategyTest {

		@Test
		void when_no_nullStrategy_then_nullIsProcessed() {
			var guard = InputGuard.builder().forString()
				// no null strategy
				.sanitize(value -> value + "-1")
				.build();

			var actualResult = guard.process(null);

			GuardResultAssert.assertThat(actualResult).isSuccessWithValue("null-1");
		}

		@Test
		void process() {
			var guard = InputGuard.builder().forString()
				.ifNullThen().process()
				.sanitize(value -> value + "-1")
				.build();

			GuardResultAssert.assertThat(guard.process(null)).isSuccessWithValue("null-1");
			GuardResultAssert.assertThat(guard.process("val")).isSuccessWithValue("val-1");
		}

		@Test
		void skipProcess() {
			var guard = InputGuard.builder().forString()
				.ifNullThen().skipProcess()
				.sanitize(value -> value + "-1")
				.build();

			GuardResultAssert.assertThat(guard.process(null)).isSuccessWithValue(null);
			GuardResultAssert.assertThat(guard.process("val")).isSuccessWithValue("val-1");
		}

		@Test
		void fail() {
			var guard = InputGuard.builder().forString()
				.ifNullThen().fail()
				.sanitize(value -> value + "-1")
				.build();

			GuardResultAssert.assertThat(guard.process(null)).isFailure().isValidationFailure();
			GuardResultAssert.assertThat(guard.process("val")).isSuccessWithValue("val-1");
		}

		@Test
		void useDefault() {
			var guard = InputGuard.builder().forString()
				.ifNullThen().useDefault("default")
				.sanitize(value -> value + "-1")
				.build();

			GuardResultAssert.assertThat(guard.process(null)).isSuccessWithValue("default-1");
			GuardResultAssert.assertThat(guard.process("val")).isSuccessWithValue("val-1");
		}

		@Test
		void when_useDefault_with_null_then_NPE() {
			Assertions.assertThatNullPointerException()
				.isThrownBy(
					() -> InputGuard.builder().forString()
						.ifNullThen().useDefault(null) // null default value is not allowed
						.build()
				);
		}

		@Test
		void mixedStrategies_1() {
			var guard = InputGuard.builder().forString()
				.ifNullThen().skipProcess()
				.ifNullThen().process()
				.ifNullThen().skipProcess()
				.sanitize(value -> value + "-1")
				.build();

			GuardResultAssert.assertThat(guard.process(null)).isSuccessWithValue(null);
			GuardResultAssert.assertThat(guard.process("val")).isSuccessWithValue("val-1");
		}

		@Test
		void mixedStrategies_2() {
			var guard = InputGuard.builder().forString()
				.ifNullThen().skipProcess()
				.ifNullThen().process()
				.ifNullThen().useDefault("plop")
				.ifNullThen().skipProcess()
				.ifNullThen().process()
				.sanitize(value -> value + "-1")
				.build();

			GuardResultAssert.assertThat(guard.process(null)).isSuccessWithValue("plop-1");
			GuardResultAssert.assertThat(guard.process("val")).isSuccessWithValue("val-1");
		}

		@Test
		void mixedStrategies_3() {
			var guard = InputGuard.builder().forString()
				.ifNullThen().skipProcess()
				.sanitize(value -> value + "-1")
				.ifNullThen().process()
				.sanitize(value -> value + "-2")
				.ifNullThen().skipProcess()
				.sanitize(value -> value + "-3")
				.ifNullThen().process()
				.sanitize(value -> value + "-4")
				.ifNullThen().useDefault("default")
				.sanitize(value -> value + "-5")
				.build();

			GuardResultAssert.assertThat(guard.process(null)).isSuccessWithValue("null-2-3-4-5");
			GuardResultAssert.assertThat(guard.process("val")).isSuccessWithValue("val-1-2-3-4-5");
		}

	}

	@Nested
	class SanitizationTest {

		@Test
		void when_exception() {
			var exception = new RuntimeException("any runtime exception happening :-/");
			var guard = InputGuard.builder().forClass(Object.class)
				.sanitize(value -> {
					throw exception;
				})
				.build();
			Assertions.assertThatRuntimeException()
				.isThrownBy(() -> guard.process(new Object()))
				.isEqualTo(exception);
		}

		@Nested
		class ApplyTest {

			private static final Object NEW_OBJECT = new Object();
			private static InputGuard<Object, Object> GUARD;

			@BeforeAll
			static void setup() {
				GUARD = InputGuard.builder().forClass(Object.class)
					.sanitize().apply(v -> NEW_OBJECT).then()
					.build();
			}

			@Test
			void when_applyWithNull_then_function_is_called() {
				var actualResult = GUARD.process(null);
				GuardResultAssert.assertThat(actualResult).isSuccessWithValue(NEW_OBJECT);
			}

			@Test
			void when_apply_then_function_is_called() {
				var nonNullObject = new Object();
				var actualResult = GUARD.process(nonNullObject);
				GuardResultAssert.assertThat(actualResult).isSuccessWithValue(NEW_OBJECT);
			}

		}

	}

	@Nested
	class ValidationTest {

		@Nested
		class ValidateTest {

			@Test
			void when_exception() {
				var exception = new RuntimeException("any runtime exception happening :-/");
				var guard = InputGuard.builder().forClass(Object.class)
					.validate(value -> {
						throw exception;
					})
					.build();

				final var value = new Object();

				Assertions.assertThatRuntimeException()
					.isThrownBy(() -> guard.process(value))
					.isEqualTo(exception);
			}

		}

		@Nested
		class Validate_Predicate_Function_Test {

			private static InputGuard<Object, Object> GUARD;

			@BeforeAll
			static void setup() {
				GUARD = InputGuard.builder().forClass(Object.class)
					.validate(Objects::isNull, value -> "object is not null: " + value)
					.build();
			}

			@Test
			void success() {
				var actualResult = GUARD.process(null);

				GuardResultAssert.assertThat(actualResult).isSuccessWithValue(null);
			}

			@Test
			void failure() {
				var actualResult = GUARD.process("plop");

				GuardResultAssert.assertThat(actualResult)
					.isFailure()
					.isValidationFailure()
					.errorAssert(assertor -> assertor.isGenericError("object is not null: plop"));
			}

		}

		@Nested
		class Validate_Predicate_String_Test {

			private static InputGuard<Object, Object> GUARD;

			@BeforeAll
			static void setup() {
				GUARD = InputGuard.builder().forClass(Object.class)
					.validate(Objects::isNull, "object is not null")
					.build();
			}

			@Test
			void success() {
				var actualResult = GUARD.process(null);

				GuardResultAssert.assertThat(actualResult).isSuccessWithValue(null);
			}

			@Test
			void failure() {
				var actualResult = GUARD.process("plop");

				GuardResultAssert.assertThat(actualResult)
					.isFailure()
					.isValidationFailure()
					.errorAssert(assertor -> assertor.isGenericError("object is not null"));

				InputGuardFailureExceptionAssert.assertThat(actualResult.getFailure().toException())
					.hasMessage(Path.root(), "object is not null");
			}

		}

	}

	@Nested
	class ValidateThatTest {

		@Nested
		class IsNotNullTest {

			private static InputGuard<Object, Object> GUARD;

			@BeforeAll
			static void setup() {
				GUARD = InputGuard.builder().forClass(Object.class)
					.validateThat().isNotNull().then()
					.build();
			}

			@Test
			void when_null_then_failure() {
				var actual = GUARD.process(null);
				GuardResultAssert.assertThat(actual)
					.isFailure()
					.isValidationFailure()
					.errorAssert(errorAssert -> errorAssert.isObjectIsNull());
			}

			@Test
			void when_nonNull_then_success() {
				final var nonNullObject = new Object();
				var actual = GUARD.process(nonNullObject);
				GuardResultAssert.assertThat(actual).isSuccessWithValue(nonNullObject);
			}

			@Nested
			class IsInstanceOfTest {

				private static InputGuard<Number, Number> GUARD_FOR_NUMBER;
				private static InputGuard<Number, Number> GUARD_FOR_INTEGER;

				@BeforeAll
				static void setup() {
					GUARD_FOR_NUMBER = InputGuard.builder().forClass(Number.class)
						.validateThat().isInstanceOf(Number.class).then()
						.build();
					GUARD_FOR_INTEGER = InputGuard.builder().forClass(Number.class)
						.validateThat().isInstanceOf(Integer.class).then()
						.build();
				}

				@Test
				void when_null_then_failure() {
					// null is not instance of Integer
					var actual = GUARD_FOR_INTEGER.process(null);
					GuardResultAssert.assertThat(actual)
						.isFailure()
						.isValidationFailure()
						.errorAssert(errorAssert -> errorAssert.isObjectMustBeInstanceOf(null, Integer.class));
				}

				@Test
				void when_sameType_then_success() {
					// Integer is instance of Integer
					final var intValue = Integer.valueOf(3);
					var actual = GUARD_FOR_INTEGER.process(intValue);
					GuardResultAssert.assertThat(actual).isSuccessWithValue(intValue);
				}

				@Test
				void when_superType_then_success() {
					// Integer is instance of Number
					final var intValue = Integer.valueOf(3);
					var actual = GUARD_FOR_NUMBER.process(intValue);
					GuardResultAssert.assertThat(actual).isSuccessWithValue(intValue);
				}

				@Test
				void when_otherType_then_failure() {
					// Long is not instance of Integer
					final var longValue = Long.valueOf(3L);
					var actual = GUARD_FOR_INTEGER.process(longValue);
					GuardResultAssert.assertThat(actual)
						.isFailure()
						.isValidationFailure()
						.errorAssert(errorAssert -> errorAssert.isObjectMustBeInstanceOf(Long.class, Integer.class));
				}

			}

			@Nested
			class IsEqualTo {

				private static InputGuard<String, String> GUARD_FOR_STRING;

				@BeforeAll
				static void setup() {
					GUARD_FOR_STRING = InputGuard.builder().forString()
						.validateThat().isEqualTo("expected").then()
						.build();
				}

				@Test
				void when_null_then_failure() {
					var actual = GUARD_FOR_STRING.process(null);
					GuardResultAssert.assertThat(actual).isFailure()
						.isValidationFailure()
						.errorAssert(errorAssert -> errorAssert.isObjectMustBeEqualTo("expected"));
				}

				@Test
				void when_notEqualValue_then_failure() {
					var actual = GUARD_FOR_STRING.process("other");
					GuardResultAssert.assertThat(actual).isFailure()
						.isValidationFailure()
						.errorAssert(errorAssert -> errorAssert.isObjectMustBeEqualTo("expected"));
				}

				@Test
				void when_equalValue_then_failure() {
					var actual = GUARD_FOR_STRING.process("expected");
					GuardResultAssert.assertThat(actual).isSuccessWithValue("expected");
				}

			}

		}

	}

	@Nested
	class MappingTest {

		@Test
		void when_exception_then_return_failure() {
			var exception = new RuntimeException("any runtime exception happening :-/");
			var guard = InputGuard.builder().forClass(Object.class)
				.map(value -> {
					throw exception;
				})
				.build();

			final var value = new Object();
			var actualResult = guard.process(value);

			GuardResultAssert.assertThat(actualResult).isFailure()
				.hasValueEqualTo(value)
				.isMappingFailure()
				.hasSameCause(exception);
		}

		@Test
		void when_map_then_success() {
			final var inputValue = new Object();
			final var outputValue = "newOutPut";
			var guard = InputGuard.builder().forClass(Object.class)
				.map(value -> outputValue)
				.build();

			var actual = guard.process(inputValue);

			GuardResultAssert.assertThat(actual).isSuccessWithValue(outputValue);
		}

	}

}
