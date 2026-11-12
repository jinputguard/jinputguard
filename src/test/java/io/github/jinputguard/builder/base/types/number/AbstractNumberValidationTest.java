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
	class RequireGreaterThan {

		Arguments[] requireGreaterThan_arguments() {
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
		@MethodSource("requireGreaterThan_arguments")
		void requireGreaterThan(T value, T ref, boolean isSuccess) {
			var guard = initiateGuard().requireGreaterThan(ref).build();

			var actualResult = guard.processToResult(value, "myVal");

			if (isSuccess) {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " requireGreaterThan(" + ref + ") = SUCCESS")
					.isSuccess(value);
			} else {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " requireGreaterThan(" + ref + ") = FAIL")
					.isFailure()
					.hasMessage("myVal must be greater than " + ref);
			}
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class RequireGreaterOrEqualTo {

		Arguments[] requireGreaterOrEqualTo_arguments() {
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
		@MethodSource("requireGreaterOrEqualTo_arguments")
		void requireGreaterOrEqualTo(T value, T ref, boolean isSuccess) {
			var guard = initiateGuard().requireGreaterOrEqualTo(ref).build();

			var actualResult = guard.processToResult(value, "myVal");

			if (isSuccess) {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " requireGreaterOrEqualTo(" + ref + ") = SUCCESS")
					.isSuccess(value);
			} else {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " requireGreaterOrEqualTo(" + ref + ") = FAIL")
					.isFailure()
					.hasMessage("myVal must be greater than or equal to " + ref);
			}
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class RequireLessThan {

		Arguments[] requireLessThan_arguments() {
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
		@MethodSource("requireLessThan_arguments")
		void requireLessThan(T value, T ref, boolean isSuccess) {
			var guard = initiateGuard().requireLessThan(ref).build();

			var actualResult = guard.processToResult(value, "myVal");

			if (isSuccess) {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " requireLessThan(" + ref + ") = SUCCESS")
					.isSuccess(value);
			} else {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " requireLessThan(" + ref + ") = FAIL")
					.isFailure()
					.hasMessage("myVal must be less than " + ref);
			}
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class RequireLowerOrEqualTo {

		Arguments[] requireLowerOrEqualTo_arguments() {
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
		@MethodSource("requireLowerOrEqualTo_arguments")
		void requireLowerOrEqualTo(T value, T ref, boolean isSuccess) {
			var guard = initiateGuard().requireLessThanOrEqualTo(ref).build();

			var actualResult = guard.processToResult(value, "myVal");

			if (isSuccess) {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " requireLowerOrEqualTo(" + ref + ") = SUCCESS")
					.isSuccess(value);
			} else {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " requireLowerOrEqualTo(" + ref + ") = FAIL")
					.isFailure()
					.hasMessage("myVal must be less than or equal to " + ref);
			}
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class RequirePositive {

		Arguments[] requirePositive_arguments() {
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
		@MethodSource("requirePositive_arguments")
		void requirePositive(T value, boolean isSuccess) {
			var guard = initiateGuard().requirePositive().build();

			var actualResult = guard.processToResult(value, "myVal");

			if (isSuccess) {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " requirePositive() = SUCCESS")
					.isSuccess(value);
			} else {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " requirePositive() = FAIL")
					.isFailure()
					.hasMessage("myVal must be greater than " + getNumberProvider().getZero());
			}
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class RequirePositiveOrNul {

		Arguments[] requirePositiveOrNul_arguments() {
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
		@MethodSource("requirePositiveOrNul_arguments")
		void requirePositiveOrNul(T value, boolean isSuccess) {
			var guard = initiateGuard().requirePositiveOrNul().build();

			var actualResult = guard.processToResult(value, "myVal");

			if (isSuccess) {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " requirePositiveOrNul() = SUCCESS")
					.isSuccess(value);
			} else {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " requirePositiveOrNul() = FAIL")
					.isFailure()
					.hasMessage("myVal must be greater than or equal to " + getNumberProvider().getZero());
			}
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class RequireEqualToZero {

		Arguments[] requireEqualToZero_arguments() {
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
		@MethodSource("requireEqualToZero_arguments")
		void requireEqualToZero(T value, boolean isSuccess) {
			var guard = initiateGuard().requireEqualToZero().build();

			var actualResult = guard.processToResult(value, "myVal");

			if (isSuccess) {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " requireZero() = SUCCESS")
					.isSuccess(value);
			} else {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " requireZero() = FAIL")
					.isFailure()
					.hasMessage("myVal must be equal to " + getNumberProvider().getZero());
			}
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class RequireNegative {

		Arguments[] requireNegative_arguments() {
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
		@MethodSource("requireNegative_arguments")
		void requireNegative(T value, boolean isSuccess) {
			var guard = initiateGuard().requireNegative().build();

			var actualResult = guard.processToResult(value, "myVal");

			if (isSuccess) {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " requireNegative() = SUCCESS")
					.isSuccess(value);
			} else {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " requireNegative() = FAIL")
					.isFailure()
					.hasMessage("myVal must be less than " + getNumberProvider().getZero());
			}
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class RequireNegativeOrNul {

		Arguments[] requireNegativeOrNul_arguments() {
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
		@MethodSource("requireNegativeOrNul_arguments")
		void requireNegativeOrNul(T value, boolean isSuccess) {
			var guard = initiateGuard().requireNegativeOrNul().build();

			var actualResult = guard.processToResult(value, "myVal");

			if (isSuccess) {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " requireNegativeOrNul() = SUCCESS")
					.isSuccess(value);
			} else {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " requireNegativeOrNul() = FAIL")
					.isFailure()
					.hasMessage("myVal must be less than or equal to " + getNumberProvider().getZero());
			}
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class RequireBetween {

		Arguments[] requireBetween_arguments() {
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
		@MethodSource("requireBetween_arguments")
		void requireBetween(T value, T minRef, T maxRef, boolean isSuccess) {
			var guard = initiateGuard().requireBetween(minRef, maxRef).build();

			var actualResult = guard.processToResult(value, "myVal");

			if (isSuccess) {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " requireBetween(" + minRef + ", " + maxRef + ") = SUCCESS")
					.isSuccess(value);
			} else {
				GuardResultAssert.assertThat(actualResult)
					.as(value + " requireBetween(" + minRef + ", " + maxRef + ") = FAIL")
					.isFailure()
					.hasMessage("myVal must be between " + minRef + " and " + maxRef);
			}
		}

		@Test
		void requireBetween_null_exceptions() {
			Assertions.assertThatNullPointerException()
				.as("Min isBetween value cannot be null")
				.isThrownBy(() -> initiateGuard().requireBetween(null, getNumberProvider().getOne()).build())
				.withMessage("min cannot be null");

			Assertions.assertThatNullPointerException()
				.as("Max isBetween value cannot be null")
				.isThrownBy(() -> initiateGuard().requireBetween(getNumberProvider().getMinusOne(), null).build())
				.withMessage("max cannot be null");
		}

		@Test
		void requireBetween_order_exceptions() {
			Assertions.assertThatIllegalArgumentException()
				.as("Min value must be lower or equal to max value")
				.isThrownBy(() -> initiateGuard().requireBetween(getNumberProvider().getOne(), getNumberProvider().getMinusOne()).build())
				.withMessageContaining("Min value " + getNumberProvider().getOne() + " is greater than max value " + getNumberProvider().getMinusOne());
		}

	}

}
