package io.github.jinputguard.builder.base.types.number;

import io.github.jinputguard.GuardResultAssert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

abstract class AbstractNumberValidationTest<T extends Number> extends AbstractNumberTest<T> {

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class IsGreaterThan {

		Arguments[] isGreaterThan_arguments() {
			return new Arguments[] {
				// Value, ref, success
				Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getMinValue(), false),
				Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getMinValue(), true),
				Arguments.of(getNumberProvider().getZero(), getNumberProvider().getMinValue(), true),
				Arguments.of(getNumberProvider().getOne(), getNumberProvider().getMinValue(), true),
				Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getMinValue(), true),

				Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getMinusOne(), false),
				Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getMinusOne(), false),
				Arguments.of(getNumberProvider().getZero(), getNumberProvider().getMinusOne(), true),
				Arguments.of(getNumberProvider().getOne(), getNumberProvider().getMinusOne(), true),
				Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getMinusOne(), true),

				Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getZero(), false),
				Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getZero(), false),
				Arguments.of(getNumberProvider().getZero(), getNumberProvider().getZero(), false),
				Arguments.of(getNumberProvider().getOne(), getNumberProvider().getZero(), true),
				Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getZero(), true),

				Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getOne(), false),
				Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getOne(), false),
				Arguments.of(getNumberProvider().getZero(), getNumberProvider().getOne(), false),
				Arguments.of(getNumberProvider().getOne(), getNumberProvider().getOne(), false),
				Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getOne(), true),

				Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getMaxValue(), false),
				Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getMaxValue(), false),
				Arguments.of(getNumberProvider().getZero(), getNumberProvider().getMaxValue(), false),
				Arguments.of(getNumberProvider().getOne(), getNumberProvider().getMaxValue(), false),
				Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getMaxValue(), false),

			};
		}

		@ParameterizedTest
		@MethodSource("isGreaterThan_arguments")
		void isGreaterThan(T value, T ref, boolean isSuccess) {
			var guard = initiateGuard().validateThat().isGreaterThan(ref).then().build();

			var actualResult = guard.process(value, "myVal");

			if (isSuccess) {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " isGreaterThan(" + ref + ") = SUCCESS")
					.isSuccess(value);
			} else {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " isGreaterThan(" + ref + ") = FAIL")
					.isFailure()
					.hasMessage("myVal must be greater than " + ref);
			}
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class IsGreaterOrEqualTo {

		Arguments[] isGreaterOrEqualTo_arguments() {
			return new Arguments[] {
				// Value, ref, success
				Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getMinValue(), true),
				Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getMinValue(), true),
				Arguments.of(getNumberProvider().getZero(), getNumberProvider().getMinValue(), true),
				Arguments.of(getNumberProvider().getOne(), getNumberProvider().getMinValue(), true),
				Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getMinValue(), true),

				Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getMinusOne(), false),
				Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getMinusOne(), true),
				Arguments.of(getNumberProvider().getZero(), getNumberProvider().getMinusOne(), true),
				Arguments.of(getNumberProvider().getOne(), getNumberProvider().getMinusOne(), true),
				Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getMinusOne(), true),

				Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getZero(), false),
				Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getZero(), false),
				Arguments.of(getNumberProvider().getZero(), getNumberProvider().getZero(), true),
				Arguments.of(getNumberProvider().getOne(), getNumberProvider().getZero(), true),
				Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getZero(), true),

				Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getOne(), false),
				Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getOne(), false),
				Arguments.of(getNumberProvider().getZero(), getNumberProvider().getOne(), false),
				Arguments.of(getNumberProvider().getOne(), getNumberProvider().getOne(), true),
				Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getOne(), true),

				Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getMaxValue(), false),
				Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getMaxValue(), false),
				Arguments.of(getNumberProvider().getZero(), getNumberProvider().getMaxValue(), false),
				Arguments.of(getNumberProvider().getOne(), getNumberProvider().getMaxValue(), false),
				Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getMaxValue(), true),

			};
		}

		@ParameterizedTest
		@MethodSource("isGreaterOrEqualTo_arguments")
		void isGreaterOrEqualTo(T value, T ref, boolean isSuccess) {
			var guard = initiateGuard().validateThat().isGreaterOrEqualTo(ref).then().build();

			var actualResult = guard.process(value, "myVal");

			if (isSuccess) {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " isGreaterOrEqualTo(" + ref + ") = SUCCESS")
					.isSuccess(value);
			} else {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " isGreaterOrEqualTo(" + ref + ") = FAIL")
					.isFailure()
					.hasMessage("myVal must be greater or equal to " + ref);
			}
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class IsLowerThan {

		Arguments[] isLowerThan_arguments() {
			return new Arguments[] {
				// Value, ref, success
				Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getMinValue(), false),
				Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getMinValue(), false),
				Arguments.of(getNumberProvider().getZero(), getNumberProvider().getMinValue(), false),
				Arguments.of(getNumberProvider().getOne(), getNumberProvider().getMinValue(), false),
				Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getMinValue(), false),

				Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getMinusOne(), true),
				Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getMinusOne(), false),
				Arguments.of(getNumberProvider().getZero(), getNumberProvider().getMinusOne(), false),
				Arguments.of(getNumberProvider().getOne(), getNumberProvider().getMinusOne(), false),
				Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getMinusOne(), false),

				Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getZero(), true),
				Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getZero(), true),
				Arguments.of(getNumberProvider().getZero(), getNumberProvider().getZero(), false),
				Arguments.of(getNumberProvider().getOne(), getNumberProvider().getZero(), false),
				Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getZero(), false),

				Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getOne(), true),
				Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getOne(), true),
				Arguments.of(getNumberProvider().getZero(), getNumberProvider().getOne(), true),
				Arguments.of(getNumberProvider().getOne(), getNumberProvider().getOne(), false),
				Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getOne(), false),

				Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getMaxValue(), true),
				Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getMaxValue(), true),
				Arguments.of(getNumberProvider().getZero(), getNumberProvider().getMaxValue(), true),
				Arguments.of(getNumberProvider().getOne(), getNumberProvider().getMaxValue(), true),
				Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getMaxValue(), false),

			};
		}

		@ParameterizedTest
		@MethodSource("isLowerThan_arguments")
		void isLowerThan(T value, T ref, boolean isSuccess) {
			var guard = initiateGuard().validateThat().isLowerThan(ref).then().build();

			var actualResult = guard.process(value, "myVal");

			if (isSuccess) {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " isLowerThan(" + ref + ") = SUCCESS")
					.isSuccess(value);
			} else {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " isLowerThan(" + ref + ") = FAIL")
					.isFailure()
					.hasMessage("myVal must be lower than " + ref);
			}
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class IsLowerOrEqualTo {

		Arguments[] isLowerOrEqualTo_arguments() {
			return new Arguments[] {
				// Value, ref, success
				Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getMinValue(), true),
				Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getMinValue(), false),
				Arguments.of(getNumberProvider().getZero(), getNumberProvider().getMinValue(), false),
				Arguments.of(getNumberProvider().getOne(), getNumberProvider().getMinValue(), false),
				Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getMinValue(), false),

				Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getMinusOne(), true),
				Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getMinusOne(), true),
				Arguments.of(getNumberProvider().getZero(), getNumberProvider().getMinusOne(), false),
				Arguments.of(getNumberProvider().getOne(), getNumberProvider().getMinusOne(), false),
				Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getMinusOne(), false),

				Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getZero(), true),
				Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getZero(), true),
				Arguments.of(getNumberProvider().getZero(), getNumberProvider().getZero(), true),
				Arguments.of(getNumberProvider().getOne(), getNumberProvider().getZero(), false),
				Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getZero(), false),

				Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getOne(), true),
				Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getOne(), true),
				Arguments.of(getNumberProvider().getZero(), getNumberProvider().getOne(), true),
				Arguments.of(getNumberProvider().getOne(), getNumberProvider().getOne(), true),
				Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getOne(), false),

				Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getMaxValue(), true),
				Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getMaxValue(), true),
				Arguments.of(getNumberProvider().getZero(), getNumberProvider().getMaxValue(), true),
				Arguments.of(getNumberProvider().getOne(), getNumberProvider().getMaxValue(), true),
				Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getMaxValue(), true),

			};
		}

		@ParameterizedTest
		@MethodSource("isLowerOrEqualTo_arguments")
		void isLowerOrEqualTo(T value, T ref, boolean isSuccess) {
			var guard = initiateGuard().validateThat().isLowerOrEqualTo(ref).then().build();

			var actualResult = guard.process(value, "myVal");

			if (isSuccess) {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " isLowerOrEqualTo(" + ref + ") = SUCCESS")
					.isSuccess(value);
			} else {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " isLowerOrEqualTo(" + ref + ") = FAIL")
					.isFailure()
					.hasMessage("myVal must be lower or equal to " + ref);
			}
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class IsPositive {

		Arguments[] isPositive_arguments() {
			return new Arguments[] {
				// Value,  success
				Arguments.of(getNumberProvider().getMinValue(), false),
				Arguments.of(getNumberProvider().getMinusOne(), false),
				Arguments.of(getNumberProvider().getZero(), false),
				Arguments.of(getNumberProvider().getOne(), true),
				Arguments.of(getNumberProvider().getMaxValue(), true),

			};
		}

		@ParameterizedTest
		@MethodSource("isPositive_arguments")
		void isPositive(T value, boolean isSuccess) {
			var guard = initiateGuard().validateThat().isPositive().then().build();

			var actualResult = guard.process(value, "myVal");

			if (isSuccess) {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " isPositive() = SUCCESS")
					.isSuccess(value);
			} else {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " isPositive() = FAIL")
					.isFailure()
					.hasMessage("myVal must be greater than " + getNumberProvider().getZero());
			}
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class IsPositiveOrNul {

		Arguments[] isPositiveOrNul_arguments() {
			return new Arguments[] {
				// Value,  success
				Arguments.of(getNumberProvider().getMinValue(), false),
				Arguments.of(getNumberProvider().getMinusOne(), false),
				Arguments.of(getNumberProvider().getZero(), true),
				Arguments.of(getNumberProvider().getOne(), true),
				Arguments.of(getNumberProvider().getMaxValue(), true),

			};
		}

		@ParameterizedTest
		@MethodSource("isPositiveOrNul_arguments")
		void isPositiveOrNul(T value, boolean isSuccess) {
			var guard = initiateGuard().validateThat().isPositiveOrNul().then().build();

			var actualResult = guard.process(value, "myVal");

			if (isSuccess) {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " isPositiveOrNul() = SUCCESS")
					.isSuccess(value);
			} else {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " isPositiveOrNul() = FAIL")
					.isFailure()
					.hasMessage("myVal must be greater or equal to " + getNumberProvider().getZero());
			}
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class IsZero {

		Arguments[] isZero_arguments() {
			return new Arguments[] {
				// Value,  success
				Arguments.of(getNumberProvider().getMinValue(), false),
				Arguments.of(getNumberProvider().getMinusOne(), false),
				Arguments.of(getNumberProvider().getZero(), true),
				Arguments.of(getNumberProvider().getOne(), false),
				Arguments.of(getNumberProvider().getMaxValue(), false),

			};
		}

		@ParameterizedTest
		@MethodSource("isZero_arguments")
		void isZero(T value, boolean isSuccess) {
			var guard = initiateGuard().validateThat().isZero().then().build();

			var actualResult = guard.process(value, "myVal");

			if (isSuccess) {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " isZero() = SUCCESS")
					.isSuccess(value);
			} else {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " isZero() = FAIL")
					.isFailure()
					.hasMessage("myVal must be equal to " + getNumberProvider().getZero());
			}
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class IsNegative {

		Arguments[] isNegative_arguments() {
			return new Arguments[] {
				// Value,  success
				Arguments.of(getNumberProvider().getMinValue(), true),
				Arguments.of(getNumberProvider().getMinusOne(), true),
				Arguments.of(getNumberProvider().getZero(), false),
				Arguments.of(getNumberProvider().getOne(), false),
				Arguments.of(getNumberProvider().getMaxValue(), false),

			};
		}

		@ParameterizedTest
		@MethodSource("isNegative_arguments")
		void isNegative(T value, boolean isSuccess) {
			var guard = initiateGuard().validateThat().isNegative().then().build();

			var actualResult = guard.process(value, "myVal");

			if (isSuccess) {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " isNegative() = SUCCESS")
					.isSuccess(value);
			} else {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " isNegative() = FAIL")
					.isFailure()
					.hasMessage("myVal must be lower than " + getNumberProvider().getZero());
			}
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class IsNegativeOrNul {

		Arguments[] isNegativeOrNul_arguments() {
			return new Arguments[] {
				// Value,  success
				Arguments.of(getNumberProvider().getMinValue(), true),
				Arguments.of(getNumberProvider().getMinusOne(), true),
				Arguments.of(getNumberProvider().getZero(), true),
				Arguments.of(getNumberProvider().getOne(), false),
				Arguments.of(getNumberProvider().getMaxValue(), false),

			};
		}

		@ParameterizedTest
		@MethodSource("isNegativeOrNul_arguments")
		void isNegativeOrNul(T value, boolean isSuccess) {
			var guard = initiateGuard().validateThat().isNegativeOrNul().then().build();

			var actualResult = guard.process(value, "myVal");

			if (isSuccess) {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " isNegativeOrNul() = SUCCESS")
					.isSuccess(value);
			} else {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " isNegativeOrNul() = FAIL")
					.isFailure()
					.hasMessage("myVal must be lower or equal to " + getNumberProvider().getZero());
			}
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class IsBetween {

		Arguments[] isBetween_arguments() {
			return new Arguments[] {
				// @formatter:off

					// 			 Value, 		minRef, 		maxRef 			success
					// MinValue
					Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getMinValue(), 	getNumberProvider().getMinValue(), 	true),
					Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getMinValue(), 	getNumberProvider().getMinValue(), 	false),
					Arguments.of(getNumberProvider().getZero(), 	getNumberProvider().getMinValue(), 	getNumberProvider().getMinValue(), 	false),
					Arguments.of(getNumberProvider().getOne(), 		getNumberProvider().getMinValue(), 	getNumberProvider().getMinValue(), 	false),
					Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getMinValue(), 	getNumberProvider().getMinValue(), 	false),
					
					Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getMinValue(), 	getNumberProvider().getMinusOne(), 	true),
					Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getMinValue(), 	getNumberProvider().getMinusOne(), 	true),
					Arguments.of(getNumberProvider().getZero(), 	getNumberProvider().getMinValue(), 	getNumberProvider().getMinusOne(), 	false),
					Arguments.of(getNumberProvider().getOne(), 		getNumberProvider().getMinValue(), 	getNumberProvider().getMinusOne(), 	false),
					Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getMinValue(), 	getNumberProvider().getMinusOne(), 	false),
					
					Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getMinValue(), 	getNumberProvider().getZero(),	 	true),
					Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getMinValue(), 	getNumberProvider().getZero(),	 	true),
					Arguments.of(getNumberProvider().getZero(), 	getNumberProvider().getMinValue(), 	getNumberProvider().getZero(),	 	true),
					Arguments.of(getNumberProvider().getOne(), 		getNumberProvider().getMinValue(), 	getNumberProvider().getZero(), 		false),
					Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getMinValue(), 	getNumberProvider().getZero(), 		false),
					
					Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getMinValue(), 	getNumberProvider().getOne(), 		true),
					Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getMinValue(), 	getNumberProvider().getOne(),	 	true),
					Arguments.of(getNumberProvider().getZero(), 	getNumberProvider().getMinValue(), 	getNumberProvider().getOne(), 		true),
					Arguments.of(getNumberProvider().getOne(), 		getNumberProvider().getMinValue(), 	getNumberProvider().getOne(), 		true),
					Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getMinValue(), 	getNumberProvider().getOne(), 		false),
					
					Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getMinValue(), 	getNumberProvider().getMaxValue(), 	true),
					Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getMinValue(), 	getNumberProvider().getMaxValue(), 	true),
					Arguments.of(getNumberProvider().getZero(), 	getNumberProvider().getMinValue(), 	getNumberProvider().getMaxValue(), 	true),
					Arguments.of(getNumberProvider().getOne(), 		getNumberProvider().getMinValue(), 	getNumberProvider().getMaxValue(), 	true),
					Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getMinValue(), 	getNumberProvider().getMaxValue(), 	true),
					
					// MinusOne
					Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getMinusOne(), 	getNumberProvider().getMinusOne(), 	false),
					Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getMinusOne(), 	getNumberProvider().getMinusOne(), 	true),
					Arguments.of(getNumberProvider().getZero(), 	getNumberProvider().getMinusOne(), 	getNumberProvider().getMinusOne(), 	false),
					Arguments.of(getNumberProvider().getOne(), 		getNumberProvider().getMinusOne(), 	getNumberProvider().getMinusOne(), 	false),
					Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getMinusOne(), 	getNumberProvider().getMinusOne(), 	false),
					
					Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getMinusOne(), 	getNumberProvider().getZero(),	 	false),
					Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getMinusOne(), 	getNumberProvider().getZero(),	 	true),
					Arguments.of(getNumberProvider().getZero(), 	getNumberProvider().getMinusOne(), 	getNumberProvider().getZero(),	 	true),
					Arguments.of(getNumberProvider().getOne(), 		getNumberProvider().getMinusOne(), 	getNumberProvider().getZero(), 		false),
					Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getMinusOne(), 	getNumberProvider().getZero(), 		false),
					
					Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getMinusOne(), 	getNumberProvider().getOne(), 		false),
					Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getMinusOne(), 	getNumberProvider().getOne(),	 	true),
					Arguments.of(getNumberProvider().getZero(), 	getNumberProvider().getMinusOne(), 	getNumberProvider().getOne(), 		true),
					Arguments.of(getNumberProvider().getOne(), 		getNumberProvider().getMinusOne(), 	getNumberProvider().getOne(), 		true),
					Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getMinusOne(), 	getNumberProvider().getOne(), 		false),
					
					Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getMinusOne(), 	getNumberProvider().getMaxValue(), 	false),
					Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getMinusOne(), 	getNumberProvider().getMaxValue(), 	true),
					Arguments.of(getNumberProvider().getZero(), 	getNumberProvider().getMinusOne(), 	getNumberProvider().getMaxValue(), 	true),
					Arguments.of(getNumberProvider().getOne(), 		getNumberProvider().getMinusOne(), 	getNumberProvider().getMaxValue(), 	true),
					Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getMinusOne(), 	getNumberProvider().getMaxValue(), 	true),
					
					// Zero
					Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getZero(), 	getNumberProvider().getZero(),	 		false),
					Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getZero(), 	getNumberProvider().getZero(),		 	false),
					Arguments.of(getNumberProvider().getZero(), 	getNumberProvider().getZero(), 	getNumberProvider().getZero(),	 		true),
					Arguments.of(getNumberProvider().getOne(), 		getNumberProvider().getZero(), 	getNumberProvider().getZero(), 			false),
					Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getZero(), 	getNumberProvider().getZero(), 			false),
					
					Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getZero(), 	getNumberProvider().getOne(), 			false),
					Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getZero(), 	getNumberProvider().getOne(),	 		false),
					Arguments.of(getNumberProvider().getZero(), 	getNumberProvider().getZero(), 	getNumberProvider().getOne(), 			true),
					Arguments.of(getNumberProvider().getOne(), 		getNumberProvider().getZero(), 	getNumberProvider().getOne(), 			true),
					Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getZero(), 	getNumberProvider().getOne(), 			false),
					
					Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getZero(), 	getNumberProvider().getMaxValue(),	 	false),
					Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getZero(), 	getNumberProvider().getMaxValue(), 		false),
					Arguments.of(getNumberProvider().getZero(), 	getNumberProvider().getZero(), 	getNumberProvider().getMaxValue(), 		true),
					Arguments.of(getNumberProvider().getOne(), 		getNumberProvider().getZero(), 	getNumberProvider().getMaxValue(), 		true),
					Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getZero(), 	getNumberProvider().getMaxValue(), 		true),
					
					// One
					Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getOne(), 	getNumberProvider().getOne(), 			false),
					Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getOne(), 	getNumberProvider().getOne(),	 		false),
					Arguments.of(getNumberProvider().getZero(), 	getNumberProvider().getOne(), 	getNumberProvider().getOne(), 			false),
					Arguments.of(getNumberProvider().getOne(), 		getNumberProvider().getOne(), 	getNumberProvider().getOne(), 			true),
					Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getOne(), 	getNumberProvider().getOne(), 			false),
					
					Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getOne(), 	getNumberProvider().getMaxValue(), 		false),
					Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getOne(), 	getNumberProvider().getMaxValue(),	 	false),
					Arguments.of(getNumberProvider().getZero(), 	getNumberProvider().getOne(), 	getNumberProvider().getMaxValue(), 		false),
					Arguments.of(getNumberProvider().getOne(), 		getNumberProvider().getOne(), 	getNumberProvider().getMaxValue(), 		true),
					Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getOne(), 	getNumberProvider().getMaxValue(), 		true),
					
					// MaxValue
					Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getMaxValue(), 	getNumberProvider().getMaxValue(), 	false),
					Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getMaxValue(), 	getNumberProvider().getMaxValue(), 	false),
					Arguments.of(getNumberProvider().getZero(), 	getNumberProvider().getMaxValue(), 	getNumberProvider().getMaxValue(), 	false),
					Arguments.of(getNumberProvider().getOne(), 		getNumberProvider().getMaxValue(), 	getNumberProvider().getMaxValue(), 	false),
					Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getMaxValue(), 	getNumberProvider().getMaxValue(), 	true),

					// @formatter:on
			};
		}

		@ParameterizedTest
		@MethodSource("isBetween_arguments")
		void isBetween(T value, T minRef, T maxRef, boolean isSuccess) {
			var guard = initiateGuard().validateThat().isBetween(minRef, maxRef).then().build();

			var actualResult = guard.process(value, "myVal");

			if (isSuccess) {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " isBetween(" + minRef + ", " + maxRef + ") = SUCCESS")
					.isSuccess(value);
			} else {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " isBetween(" + minRef + ", " + maxRef + ") = FAIL")
					.isFailure()
					.hasMessage("myVal must be between " + minRef + " and " + maxRef);
			}
		}

		@Test
		void isBetween_null_exceptions() {
			Assertions.assertThatNullPointerException()
				.as("Min isBetween value cannot be null")
				.isThrownBy(() -> initiateGuard().validateThat().isBetween(null, getNumberProvider().getOne()).then().build())
				.withMessage("min cannot be null");

			Assertions.assertThatNullPointerException()
				.as("Max isBetween value cannot be null")
				.isThrownBy(() -> initiateGuard().validateThat().isBetween(getNumberProvider().getMinusOne(), null).then().build())
				.withMessage("max cannot be null");
		}

		@Test
		void isBetween_order_exceptions() {
			Assertions.assertThatIllegalArgumentException()
				.as("Min value must be lower or equal to max value")
				.isThrownBy(() -> initiateGuard().validateThat().isBetween(getNumberProvider().getOne(), getNumberProvider().getMinusOne()).then().build())
				.withMessageContaining("Min value " + getNumberProvider().getOne() + " is greater than max value " + getNumberProvider().getMinusOne());
		}

	}

}
