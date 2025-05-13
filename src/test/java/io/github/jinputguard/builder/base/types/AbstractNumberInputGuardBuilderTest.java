package io.github.jinputguard.builder.base.types;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.builder.base.types.number.AbstractNumberInputGuardBuilder;
import io.github.jinputguard.result.GuardResultAssert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

abstract class AbstractNumberInputGuardBuilderTest<T extends Number> {

	abstract <B extends AbstractNumberInputGuardBuilder<T, T, B>> AbstractNumberInputGuardBuilder<T, T, B> initiateGuard();

	abstract T getMinValue();

	abstract T getAbsMinValue();

	abstract T getMinusOne();

	abstract T getZero();

	abstract T getOne();

	abstract T getMaxValue();

	@Nested
	class Sanitization {

		@Nested
		class Clamp {

			@Test
			void clamp_MIN_MIN() {
				var guard = initiateGuard().sanitize().clamp(getMinValue(), getMinValue()).then().build();
				GuardResultAssert.assertThat(guard.process(getMinValue())).as("clamp(MIN, MIN) & MIN = MIN").isSuccessWithValue(getMinValue());
				GuardResultAssert.assertThat(guard.process(getMinusOne())).as("clamp(MIN, MIN) & -1 = MIN").isSuccessWithValue(getMinValue());
				GuardResultAssert.assertThat(guard.process(getZero())).as("clamp(MIN, MIN) & 0 = MIN").isSuccessWithValue(getMinValue());
				GuardResultAssert.assertThat(guard.process(getOne())).as("clamp(MIN, MIN) & 1 = MIN").isSuccessWithValue(getMinValue());
				GuardResultAssert.assertThat(guard.process(getMaxValue())).as("clamp(MIN, MIN) & MAX = MIN").isSuccessWithValue(getMinValue());
			}

			@Test
			void clamp_MIN_MinusOne() {
				var guard = initiateGuard().sanitize().clamp(getMinValue(), getMinusOne()).then().build();
				GuardResultAssert.assertThat(guard.process(getMinValue())).as("clamp(MIN, -1) & MIN = MIN").isSuccessWithValue(getMinValue());
				GuardResultAssert.assertThat(guard.process(getMinusOne())).as("clamp(MIN, -1) & -1 = -1").isSuccessWithValue(getMinusOne());
				GuardResultAssert.assertThat(guard.process(getZero())).as("clamp(MIN, -1) & 0 = -1").isSuccessWithValue(getMinusOne());
				GuardResultAssert.assertThat(guard.process(getOne())).as("clamp(MIN, -1) & 1 = -1").isSuccessWithValue(getMinusOne());
				GuardResultAssert.assertThat(guard.process(getMaxValue())).as("clamp(MIN, -1) & MAX = -1").isSuccessWithValue(getMinusOne());
			}

			@Test
			void clamp_MIN_Zero() {
				var guard = initiateGuard().sanitize().clamp(getMinValue(), getZero()).then().build();
				GuardResultAssert.assertThat(guard.process(getMinValue())).as("clamp(MIN, 0) & MIN = MIN").isSuccessWithValue(getMinValue());
				GuardResultAssert.assertThat(guard.process(getMinusOne())).as("clamp(MIN, 0) & -1 = -1").isSuccessWithValue(getMinusOne());
				GuardResultAssert.assertThat(guard.process(getZero())).as("clamp(MIN, 0) & 0 = 0").isSuccessWithValue(getZero());
				GuardResultAssert.assertThat(guard.process(getOne())).as("clamp(MIN, 0) & 1 = 0").isSuccessWithValue(getZero());
				GuardResultAssert.assertThat(guard.process(getMaxValue())).as("clamp(MIN, 0) & MAX = 0").isSuccessWithValue(getZero());
			}

			@Test
			void clamp_MIN_One() {
				var guard = initiateGuard().sanitize().clamp(getMinValue(), getOne()).then().build();
				GuardResultAssert.assertThat(guard.process(getMinValue())).as("clamp(MIN, 1) & MIN = MIN").isSuccessWithValue(getMinValue());
				GuardResultAssert.assertThat(guard.process(getMinusOne())).as("clamp(MIN, 1) & -1 = -1").isSuccessWithValue(getMinusOne());
				GuardResultAssert.assertThat(guard.process(getZero())).as("clamp(MIN, 1) & 0 = 0").isSuccessWithValue(getZero());
				GuardResultAssert.assertThat(guard.process(getOne())).as("clamp(MIN, 1) & 1 = 1").isSuccessWithValue(getOne());
				GuardResultAssert.assertThat(guard.process(getMaxValue())).as("clamp(MIN, 1) & MAX = 1").isSuccessWithValue(getOne());
			}

			@Test
			void clamp_MIN_MAX() {
				var guard = initiateGuard().sanitize().clamp(getMinValue(), getMaxValue()).then().build();
				GuardResultAssert.assertThat(guard.process(getMinValue())).as("clamp(MIN, MAX) & MIN = MIN").isSuccessWithValue(getMinValue());
				GuardResultAssert.assertThat(guard.process(getMinusOne())).as("clamp(MIN, MAX) & -1 = -1").isSuccessWithValue(getMinusOne());
				GuardResultAssert.assertThat(guard.process(getZero())).as("clamp(MIN, MAX) & 0 = 0").isSuccessWithValue(getZero());
				GuardResultAssert.assertThat(guard.process(getOne())).as("clamp(MIN, MAX) & 1 = 1").isSuccessWithValue(getOne());
				GuardResultAssert.assertThat(guard.process(getMaxValue())).as("clamp(MIN, MAX) & MAX = MAX").isSuccessWithValue(getMaxValue());
			}

			@Test
			void clamp_MinusOne_MinusOne() {
				var guard = initiateGuard().sanitize().clamp(getMinusOne(), getMinusOne()).then().build();
				GuardResultAssert.assertThat(guard.process(getMinValue())).as("clamp(-1, -1) & MIN = -1").isSuccessWithValue(getMinusOne());
				GuardResultAssert.assertThat(guard.process(getMinusOne())).as("clamp(-1, -1) & -1 = -1").isSuccessWithValue(getMinusOne());
				GuardResultAssert.assertThat(guard.process(getZero())).as("clamp(-1, -1) & 0 = -1").isSuccessWithValue(getMinusOne());
				GuardResultAssert.assertThat(guard.process(getOne())).as("clamp(-1, -1) & 1 = -1").isSuccessWithValue(getMinusOne());
				GuardResultAssert.assertThat(guard.process(getMaxValue())).as("clamp(-1, -1) & MAX = -1").isSuccessWithValue(getMinusOne());
			}

			@Test
			void clamp_MinusOne_Zero() {
				var guard = initiateGuard().sanitize().clamp(getMinusOne(), getZero()).then().build();
				GuardResultAssert.assertThat(guard.process(getMinValue())).as("clamp(-1, 0) & MIN = -1").isSuccessWithValue(getMinusOne());
				GuardResultAssert.assertThat(guard.process(getMinusOne())).as("clamp(-1, 0) & -1 = -1").isSuccessWithValue(getMinusOne());
				GuardResultAssert.assertThat(guard.process(getZero())).as("clamp(-1, 0) & 0 = 0").isSuccessWithValue(getZero());
				GuardResultAssert.assertThat(guard.process(getOne())).as("clamp(-1, 0) & 1 = 0").isSuccessWithValue(getZero());
				GuardResultAssert.assertThat(guard.process(getMaxValue())).as("clamp(-1, 0) & MAX = 0").isSuccessWithValue(getZero());
			}

			@Test
			void clamp_MinusOne_One() {
				var guard = initiateGuard().sanitize().clamp(getMinusOne(), getOne()).then().build();
				GuardResultAssert.assertThat(guard.process(getMinValue())).as("clamp(-1, 1) & MIN = -1").isSuccessWithValue(getMinusOne());
				GuardResultAssert.assertThat(guard.process(getMinusOne())).as("clamp(-1, 1) & -1 = -1").isSuccessWithValue(getMinusOne());
				GuardResultAssert.assertThat(guard.process(getZero())).as("clamp(-1, 1) & 0 = 0").isSuccessWithValue(getZero());
				GuardResultAssert.assertThat(guard.process(getOne())).as("clamp(-1, 1) & 1 = 1").isSuccessWithValue(getOne());
				GuardResultAssert.assertThat(guard.process(getMaxValue())).as("clamp(-1, 1) & MAX = 1").isSuccessWithValue(getOne());
			}

			@Test
			void clamp_MinusOne_MAX() {
				var guard = initiateGuard().sanitize().clamp(getMinusOne(), getMaxValue()).then().build();
				GuardResultAssert.assertThat(guard.process(getMinValue())).as("clamp(-1, MAX) & MIN = -1").isSuccessWithValue(getMinusOne());
				GuardResultAssert.assertThat(guard.process(getMinusOne())).as("clamp(-1, MAX) & -1 = -1").isSuccessWithValue(getMinusOne());
				GuardResultAssert.assertThat(guard.process(getZero())).as("clamp(-1, MAX) & 0 = 0").isSuccessWithValue(getZero());
				GuardResultAssert.assertThat(guard.process(getOne())).as("clamp(-1, MAX) & 1 = 1").isSuccessWithValue(getOne());
				GuardResultAssert.assertThat(guard.process(getMaxValue())).as("clamp(-1, MAX) & MAX = MAX").isSuccessWithValue(getMaxValue());
			}

			@Test
			void clamp_Zero_Zero() {
				var guard = initiateGuard().sanitize().clamp(getZero(), getZero()).then().build();
				GuardResultAssert.assertThat(guard.process(getMinValue())).as("clamp(0, 0) & MIN = 0").isSuccessWithValue(getZero());
				GuardResultAssert.assertThat(guard.process(getMinusOne())).as("clamp(0, 0) & -1 = 0").isSuccessWithValue(getZero());
				GuardResultAssert.assertThat(guard.process(getZero())).as("clamp(0, 0) & 0 = 0").isSuccessWithValue(getZero());
				GuardResultAssert.assertThat(guard.process(getOne())).as("clamp(0, 0) & 1 = 0").isSuccessWithValue(getZero());
				GuardResultAssert.assertThat(guard.process(getMaxValue())).as("clamp(0, 0) & MAX = 0").isSuccessWithValue(getZero());
			}

			@Test
			void clamp_Zero_One() {
				var guard = initiateGuard().sanitize().clamp(getZero(), getOne()).then().build();
				GuardResultAssert.assertThat(guard.process(getMinValue())).as("clamp(0, 1) & MIN = 0").isSuccessWithValue(getZero());
				GuardResultAssert.assertThat(guard.process(getMinusOne())).as("clamp(0, 1) & -1 = 0").isSuccessWithValue(getZero());
				GuardResultAssert.assertThat(guard.process(getZero())).as("clamp(0, 1) & 0 = 0").isSuccessWithValue(getZero());
				GuardResultAssert.assertThat(guard.process(getOne())).as("clamp(0, 1) & 1 = 1").isSuccessWithValue(getOne());
				GuardResultAssert.assertThat(guard.process(getMaxValue())).as("clamp(0, 1) & MAX = 1").isSuccessWithValue(getOne());
			}

			@Test
			void clamp_Zero_MAX() {
				var guard = initiateGuard().sanitize().clamp(getZero(), getMaxValue()).then().build();
				GuardResultAssert.assertThat(guard.process(getMinValue())).as("clamp(0, MAX) & MIN = 0").isSuccessWithValue(getZero());
				GuardResultAssert.assertThat(guard.process(getMinusOne())).as("clamp(0, MAX) & -1 = 0").isSuccessWithValue(getZero());
				GuardResultAssert.assertThat(guard.process(getZero())).as("clamp(0, MAX) & 0 = 0").isSuccessWithValue(getZero());
				GuardResultAssert.assertThat(guard.process(getOne())).as("clamp(0, MAX) & 1 = 1").isSuccessWithValue(getOne());
				GuardResultAssert.assertThat(guard.process(getMaxValue())).as("clamp(0, MAX) & MAX = MAX").isSuccessWithValue(getMaxValue());
			}

			@Test
			void clamp_One_One() {
				var guard = initiateGuard().sanitize().clamp(getOne(), getOne()).then().build();
				GuardResultAssert.assertThat(guard.process(getMinValue())).as("clamp(1, 1) & MIN = 0").isSuccessWithValue(getOne());
				GuardResultAssert.assertThat(guard.process(getMinusOne())).as("clamp(1, 1) & -1 = 0").isSuccessWithValue(getOne());
				GuardResultAssert.assertThat(guard.process(getZero())).as("clamp(1, 1) & 0 = 0").isSuccessWithValue(getOne());
				GuardResultAssert.assertThat(guard.process(getOne())).as("clamp(1, 1) & 1 = 1").isSuccessWithValue(getOne());
				GuardResultAssert.assertThat(guard.process(getMaxValue())).as("clamp(1, 1) & MAX = 1").isSuccessWithValue(getOne());
			}

			@Test
			void clamp_One_MAX() {
				var guard = initiateGuard().sanitize().clamp(getOne(), getMaxValue()).then().build();
				GuardResultAssert.assertThat(guard.process(getMinValue())).as("clamp(1, MAX) & MIN = 0").isSuccessWithValue(getOne());
				GuardResultAssert.assertThat(guard.process(getMinusOne())).as("clamp(1, MAX) & -1 = 0").isSuccessWithValue(getOne());
				GuardResultAssert.assertThat(guard.process(getZero())).as("clamp(1, MAX) & 0 = 0").isSuccessWithValue(getOne());
				GuardResultAssert.assertThat(guard.process(getOne())).as("clamp(1, MAX) & 1 = 1").isSuccessWithValue(getOne());
				GuardResultAssert.assertThat(guard.process(getMaxValue())).as("clamp(1, MAX) & MAX = MAX").isSuccessWithValue(getMaxValue());
			}

			@Test
			void clamp_MAX_MAX() {
				var guard = initiateGuard().sanitize().clamp(getMaxValue(), getMaxValue()).then().build();
				GuardResultAssert.assertThat(guard.process(getMinValue())).as("clamp(MAX, MAX) & MIN = 0").isSuccessWithValue(getMaxValue());
				GuardResultAssert.assertThat(guard.process(getMinusOne())).as("clamp(MAX, MAX) & -1 = 0").isSuccessWithValue(getMaxValue());
				GuardResultAssert.assertThat(guard.process(getZero())).as("clamp(MAX, MAX) & 0 = 0").isSuccessWithValue(getMaxValue());
				GuardResultAssert.assertThat(guard.process(getOne())).as("clamp(MAX, MAX) & 1 = 1").isSuccessWithValue(getMaxValue());
				GuardResultAssert.assertThat(guard.process(getMaxValue())).as("clamp(MAX, MAX) & MAX = MAX").isSuccessWithValue(getMaxValue());
			}

			@Test
			void clamp_null_exceptions() {
				Assertions.assertThatNullPointerException()
					.as("Min clamp value cannot be null")
					.isThrownBy(() -> initiateGuard().sanitize().clamp(null, getOne()).then().build())
					.withMessage("min cannot be null");

				Assertions.assertThatNullPointerException()
					.as("Max clamp value cannot be null")
					.isThrownBy(() -> initiateGuard().sanitize().clamp(getMinusOne(), null).then().build())
					.withMessage("max cannot be null");
			}

			@Test
			void clamp_order_exceptions() {
				Assertions.assertThatIllegalArgumentException()
					.as("Min clamp value must be lower or equal to max clamp value")
					.isThrownBy(() -> initiateGuard().sanitize().clamp(getOne(), getMinusOne()).then().build())
					.withMessageContaining("Min value " + getOne() + " is greater than max value " + getMinusOne());
			}

		}

		@Nested
		class ClampMin {

			@Test
			void clampMin_MIN() {
				var guard = initiateGuard().sanitize().clampMin(getMinValue()).then().build();
				GuardResultAssert.assertThat(guard.process(getMinValue())).as("clampMin(MIN) & MIN = MIN").isSuccessWithValue(getMinValue());
				GuardResultAssert.assertThat(guard.process(getMinusOne())).as("clampMin(MIN) & -1 = -1").isSuccessWithValue(getMinusOne());
				GuardResultAssert.assertThat(guard.process(getZero())).as("clampMin(MIN) & 0 = 0").isSuccessWithValue(getZero());
				GuardResultAssert.assertThat(guard.process(getOne())).as("clampMin(MIN) & 1 = 1").isSuccessWithValue(getOne());
				GuardResultAssert.assertThat(guard.process(getMaxValue())).as("clampMin(MIN) & MAX = MAX").isSuccessWithValue(getMaxValue());
			}

			@Test
			void clampMin_MinusOne() {
				var guard = initiateGuard().sanitize().clampMin(getMinusOne()).then().build();
				GuardResultAssert.assertThat(guard.process(getMinValue())).as("clampMin(-1) & MIN = -1").isSuccessWithValue(getMinusOne());
				GuardResultAssert.assertThat(guard.process(getMinusOne())).as("clampMin(-1) & -1 = -1").isSuccessWithValue(getMinusOne());
				GuardResultAssert.assertThat(guard.process(getZero())).as("clampMin(-1) & 0 = 0").isSuccessWithValue(getZero());
				GuardResultAssert.assertThat(guard.process(getOne())).as("clampMin(-1) & 1 = 1").isSuccessWithValue(getOne());
				GuardResultAssert.assertThat(guard.process(getMaxValue())).as("clampMin(-1) & MAX = MAX").isSuccessWithValue(getMaxValue());
			}

			@Test
			void clampMin_Zero() {
				var guard = initiateGuard().sanitize().clampMin(getZero()).then().build();
				GuardResultAssert.assertThat(guard.process(getMinValue())).as("clampMin(0) & MIN = 0").isSuccessWithValue(getZero());
				GuardResultAssert.assertThat(guard.process(getMinusOne())).as("clampMin(0) & -1 = 0").isSuccessWithValue(getZero());
				GuardResultAssert.assertThat(guard.process(getZero())).as("clampMin(0) & 0 = 0").isSuccessWithValue(getZero());
				GuardResultAssert.assertThat(guard.process(getOne())).as("clampMin(0) & 1 = 1").isSuccessWithValue(getOne());
				GuardResultAssert.assertThat(guard.process(getMaxValue())).as("clampMin(0) & MAX = MAX").isSuccessWithValue(getMaxValue());
			}

			@Test
			void clampMin_One() {
				var guard = initiateGuard().sanitize().clampMin(getOne()).then().build();
				GuardResultAssert.assertThat(guard.process(getMinValue())).as("clampMin(1) & MIN = 1").isSuccessWithValue(getOne());
				GuardResultAssert.assertThat(guard.process(getMinusOne())).as("clampMin(1) & -1 = 1").isSuccessWithValue(getOne());
				GuardResultAssert.assertThat(guard.process(getZero())).as("clampMin(1) & 0 = 1").isSuccessWithValue(getOne());
				GuardResultAssert.assertThat(guard.process(getOne())).as("clampMin(1) & 1 = 1").isSuccessWithValue(getOne());
				GuardResultAssert.assertThat(guard.process(getMaxValue())).as("clampMin(1) & MAX = 1").isSuccessWithValue(getMaxValue());
			}

			@Test
			void clampMin_MAX() {
				var guard = initiateGuard().sanitize().clampMin(getMaxValue()).then().build();
				GuardResultAssert.assertThat(guard.process(getMinValue())).as("clampMin(MAX) & MIN = MAX").isSuccessWithValue(getMaxValue());
				GuardResultAssert.assertThat(guard.process(getMinusOne())).as("clampMin(MAX) & -1 = MAX").isSuccessWithValue(getMaxValue());
				GuardResultAssert.assertThat(guard.process(getZero())).as("clampMin(MAX) & 0 = MAX").isSuccessWithValue(getMaxValue());
				GuardResultAssert.assertThat(guard.process(getOne())).as("clampMin(MAX) & 1 = MAX").isSuccessWithValue(getMaxValue());
				GuardResultAssert.assertThat(guard.process(getMaxValue())).as("clampMin(MAX) & MAX = MAX").isSuccessWithValue(getMaxValue());
			}

			@Test
			void clampMin_exceptions() {
				Assertions.assertThatNullPointerException()
					.as("Min clamp value cannot be null")
					.isThrownBy(() -> InputGuard.builder().forInteger().sanitize().clampMin(null).then().build())
					.withMessage("min cannot be null");
			}

		}

		@Nested
		class ClampMax {

			@Test
			void clampMax_MIN() {
				var guard = initiateGuard().sanitize().clampMax(getMinValue()).then().build();
				GuardResultAssert.assertThat(guard.process(getMinValue())).as("clampMax(MIN) & MIN = MIN").isSuccessWithValue(getMinValue());
				GuardResultAssert.assertThat(guard.process(getMinusOne())).as("clampMax(MIN) & -1 = MIN").isSuccessWithValue(getMinValue());
				GuardResultAssert.assertThat(guard.process(getZero())).as("clampMax(MIN) & 0 = MIN").isSuccessWithValue(getMinValue());
				GuardResultAssert.assertThat(guard.process(getOne())).as("clampMax(MIN) & 1 = MIN").isSuccessWithValue(getMinValue());
				GuardResultAssert.assertThat(guard.process(getMaxValue())).as("clampMax(MIN) & MAX = MIN").isSuccessWithValue(getMinValue());
			}

			@Test
			void clampMax_MinusOne() {
				var guard = initiateGuard().sanitize().clampMax(getMinusOne()).then().build();
				GuardResultAssert.assertThat(guard.process(getMinValue())).as("clampMax(-1) & MIN = MIN").isSuccessWithValue(getMinValue());
				GuardResultAssert.assertThat(guard.process(getMinusOne())).as("clampMax(-1) & -1 = -1").isSuccessWithValue(getMinusOne());
				GuardResultAssert.assertThat(guard.process(getZero())).as("clampMax(-1) & 0 = -1").isSuccessWithValue(getMinusOne());
				GuardResultAssert.assertThat(guard.process(getOne())).as("clampMax(-1) & 1 = -1").isSuccessWithValue(getMinusOne());
				GuardResultAssert.assertThat(guard.process(getMaxValue())).as("clampMax(-1) & MAX = -1").isSuccessWithValue(getMinusOne());
			}

			@Test
			void clampMax_Zero() {
				var guard = initiateGuard().sanitize().clampMax(getZero()).then().build();
				GuardResultAssert.assertThat(guard.process(getMinValue())).as("clampMax(0) & MIN = MIN").isSuccessWithValue(getMinValue());
				GuardResultAssert.assertThat(guard.process(getMinusOne())).as("clampMax(0) & -1 = -1").isSuccessWithValue(getMinusOne());
				GuardResultAssert.assertThat(guard.process(getZero())).as("clampMax(0) & 0 = 0").isSuccessWithValue(getZero());
				GuardResultAssert.assertThat(guard.process(getOne())).as("clampMax(0) & 1 = 0").isSuccessWithValue(getZero());
				GuardResultAssert.assertThat(guard.process(getMaxValue())).as("clampMax(0) & MAX = 0").isSuccessWithValue(getZero());
			}

			@Test
			void clampMax_One() {
				var guard = initiateGuard().sanitize().clampMax(getOne()).then().build();
				GuardResultAssert.assertThat(guard.process(getMinValue())).as("clampMax(1) & MIN = MIN").isSuccessWithValue(getMinValue());
				GuardResultAssert.assertThat(guard.process(getMinusOne())).as("clampMax(1) & -1 = -1").isSuccessWithValue(getMinusOne());
				GuardResultAssert.assertThat(guard.process(getZero())).as("clampMax(1) & 0 = 0").isSuccessWithValue(getZero());
				GuardResultAssert.assertThat(guard.process(getOne())).as("clampMax(1) & 1 = 1").isSuccessWithValue(getOne());
				GuardResultAssert.assertThat(guard.process(getMaxValue())).as("clampMax(1) & MAX = 1").isSuccessWithValue(getOne());
			}

			@Test
			void clampMax_MAX() {
				var guard = initiateGuard().sanitize().clampMax(getMaxValue()).then().build();
				GuardResultAssert.assertThat(guard.process(getMinValue())).as("clampMax(MAX) & MIN = MIN").isSuccessWithValue(getMinValue());
				GuardResultAssert.assertThat(guard.process(getMinusOne())).as("clampMax(MAX) & -1 = -1").isSuccessWithValue(getMinusOne());
				GuardResultAssert.assertThat(guard.process(getZero())).as("clampMax(MAX) & 0 = 0").isSuccessWithValue(getZero());
				GuardResultAssert.assertThat(guard.process(getOne())).as("clampMax(MAX) & 1 = 1").isSuccessWithValue(getOne());
				GuardResultAssert.assertThat(guard.process(getMaxValue())).as("clampMax(MAX) & MAX = MAX").isSuccessWithValue(getMaxValue());
			}

			@Test
			void clampMax_exceptions() {
				Assertions.assertThatNullPointerException()
					.as("Max clamp value cannot be null")
					.isThrownBy(() -> InputGuard.builder().forInteger().sanitize().clampMax(null).then().build())
					.withMessage("max cannot be null");
			}

		}

		@Test
		void abs() {
			var guard = initiateGuard().sanitize().abs().then().build();
			GuardResultAssert.assertThat(guard.process(getMinValue())).as("abs() & MIN = -MIN").isSuccessWithValue(getAbsMinValue());
			GuardResultAssert.assertThat(guard.process(getMinusOne())).as("abs() & -1 = 1").isSuccessWithValue(getOne());
			GuardResultAssert.assertThat(guard.process(getZero())).as("abs() & 0 = 0").isSuccessWithValue(getZero());
			GuardResultAssert.assertThat(guard.process(getOne())).as("abs() & 1 = 1").isSuccessWithValue(getOne());
			GuardResultAssert.assertThat(guard.process(getMaxValue())).as("abs() & MAX = MAX").isSuccessWithValue(getMaxValue());
		}

	}

	@Nested
	class Validation {

		@Nested
		@TestInstance(Lifecycle.PER_CLASS)
		class IsGreaterThan {

			Arguments[] isGreaterThan_arguments() {
				return new Arguments[] {
					// Value, ref, success
					Arguments.of(getMinValue(), getMinValue(), false),
					Arguments.of(getMinusOne(), getMinValue(), true),
					Arguments.of(getZero(), getMinValue(), true),
					Arguments.of(getOne(), getMinValue(), true),
					Arguments.of(getMaxValue(), getMinValue(), true),

					Arguments.of(getMinValue(), getMinusOne(), false),
					Arguments.of(getMinusOne(), getMinusOne(), false),
					Arguments.of(getZero(), getMinusOne(), true),
					Arguments.of(getOne(), getMinusOne(), true),
					Arguments.of(getMaxValue(), getMinusOne(), true),

					Arguments.of(getMinValue(), getZero(), false),
					Arguments.of(getMinusOne(), getZero(), false),
					Arguments.of(getZero(), getZero(), false),
					Arguments.of(getOne(), getZero(), true),
					Arguments.of(getMaxValue(), getZero(), true),

					Arguments.of(getMinValue(), getOne(), false),
					Arguments.of(getMinusOne(), getOne(), false),
					Arguments.of(getZero(), getOne(), false),
					Arguments.of(getOne(), getOne(), false),
					Arguments.of(getMaxValue(), getOne(), true),

					Arguments.of(getMinValue(), getMaxValue(), false),
					Arguments.of(getMinusOne(), getMaxValue(), false),
					Arguments.of(getZero(), getMaxValue(), false),
					Arguments.of(getOne(), getMaxValue(), false),
					Arguments.of(getMaxValue(), getMaxValue(), false),

				};
			}

			@ParameterizedTest
			@MethodSource("isGreaterThan_arguments")
			void isGreaterThan(T value, T ref, boolean isSuccess) {
				var guard = initiateGuard().validateThat().isGreaterThan(ref).then().build();

				var actualResult = guard.process(value);

				if (isSuccess) {
					GuardResultAssert.assertThat(actualResult)
						.as(value + " isGreaterThan(" + ref + ") = SUCCESS")
						.isSuccessWithValue(value);
				} else {
					GuardResultAssert.assertThat(actualResult)
						.as(value + " isGreaterThan(" + ref + ") = FAIL")
						.isFailure()
						.isValidationFailure()
						.errorAssert(errorAssert -> errorAssert.isNumberMustBeGreaterThan(value, ref));
				}
			}

		}

		@Nested
		@TestInstance(Lifecycle.PER_CLASS)
		class IsGreaterOrEqualTo {

			Arguments[] isGreaterOrEqualTo_arguments() {
				return new Arguments[] {
					// Value, ref, success
					Arguments.of(getMinValue(), getMinValue(), true),
					Arguments.of(getMinusOne(), getMinValue(), true),
					Arguments.of(getZero(), getMinValue(), true),
					Arguments.of(getOne(), getMinValue(), true),
					Arguments.of(getMaxValue(), getMinValue(), true),

					Arguments.of(getMinValue(), getMinusOne(), false),
					Arguments.of(getMinusOne(), getMinusOne(), true),
					Arguments.of(getZero(), getMinusOne(), true),
					Arguments.of(getOne(), getMinusOne(), true),
					Arguments.of(getMaxValue(), getMinusOne(), true),

					Arguments.of(getMinValue(), getZero(), false),
					Arguments.of(getMinusOne(), getZero(), false),
					Arguments.of(getZero(), getZero(), true),
					Arguments.of(getOne(), getZero(), true),
					Arguments.of(getMaxValue(), getZero(), true),

					Arguments.of(getMinValue(), getOne(), false),
					Arguments.of(getMinusOne(), getOne(), false),
					Arguments.of(getZero(), getOne(), false),
					Arguments.of(getOne(), getOne(), true),
					Arguments.of(getMaxValue(), getOne(), true),

					Arguments.of(getMinValue(), getMaxValue(), false),
					Arguments.of(getMinusOne(), getMaxValue(), false),
					Arguments.of(getZero(), getMaxValue(), false),
					Arguments.of(getOne(), getMaxValue(), false),
					Arguments.of(getMaxValue(), getMaxValue(), true),

				};
			}

			@ParameterizedTest
			@MethodSource("isGreaterOrEqualTo_arguments")
			void isGreaterOrEqualTo(T value, T ref, boolean isSuccess) {
				var guard = initiateGuard().validateThat().isGreaterOrEqualTo(ref).then().build();

				var actualResult = guard.process(value);

				if (isSuccess) {
					GuardResultAssert.assertThat(actualResult)
						.as(value + " isGreaterOrEqualTo(" + ref + ") = SUCCESS")
						.isSuccessWithValue(value);
				} else {
					GuardResultAssert.assertThat(actualResult)
						.as(value + " isGreaterOrEqualTo(" + ref + ") = FAIL")
						.isFailure()
						.isValidationFailure()
						.errorAssert(errorAssert -> errorAssert.isNumberMustBeGreaterOrEqualTo(value, ref));
				}
			}

		}

		@Nested
		@TestInstance(Lifecycle.PER_CLASS)
		class IsLowerThan {

			Arguments[] isLowerThan_arguments() {
				return new Arguments[] {
					// Value, ref, success
					Arguments.of(getMinValue(), getMinValue(), false),
					Arguments.of(getMinusOne(), getMinValue(), false),
					Arguments.of(getZero(), getMinValue(), false),
					Arguments.of(getOne(), getMinValue(), false),
					Arguments.of(getMaxValue(), getMinValue(), false),

					Arguments.of(getMinValue(), getMinusOne(), true),
					Arguments.of(getMinusOne(), getMinusOne(), false),
					Arguments.of(getZero(), getMinusOne(), false),
					Arguments.of(getOne(), getMinusOne(), false),
					Arguments.of(getMaxValue(), getMinusOne(), false),

					Arguments.of(getMinValue(), getZero(), true),
					Arguments.of(getMinusOne(), getZero(), true),
					Arguments.of(getZero(), getZero(), false),
					Arguments.of(getOne(), getZero(), false),
					Arguments.of(getMaxValue(), getZero(), false),

					Arguments.of(getMinValue(), getOne(), true),
					Arguments.of(getMinusOne(), getOne(), true),
					Arguments.of(getZero(), getOne(), true),
					Arguments.of(getOne(), getOne(), false),
					Arguments.of(getMaxValue(), getOne(), false),

					Arguments.of(getMinValue(), getMaxValue(), true),
					Arguments.of(getMinusOne(), getMaxValue(), true),
					Arguments.of(getZero(), getMaxValue(), true),
					Arguments.of(getOne(), getMaxValue(), true),
					Arguments.of(getMaxValue(), getMaxValue(), false),

				};
			}

			@ParameterizedTest
			@MethodSource("isLowerThan_arguments")
			void isLowerThan(T value, T ref, boolean isSuccess) {
				var guard = initiateGuard().validateThat().isLowerThan(ref).then().build();

				var actualResult = guard.process(value);

				if (isSuccess) {
					GuardResultAssert.assertThat(actualResult)
						.as(value + " isLowerThan(" + ref + ") = SUCCESS")
						.isSuccessWithValue(value);
				} else {
					GuardResultAssert.assertThat(actualResult)
						.as(value + " isLowerThan(" + ref + ") = FAIL")
						.isFailure()
						.isValidationFailure()
						.errorAssert(errorAssert -> errorAssert.isNumberMustBeLowerThan(value, ref));
				}
			}

		}

		@Nested
		@TestInstance(Lifecycle.PER_CLASS)
		class IsLowerOrEqualTo {

			Arguments[] isLowerOrEqualTo_arguments() {
				return new Arguments[] {
					// Value, ref, success
					Arguments.of(getMinValue(), getMinValue(), true),
					Arguments.of(getMinusOne(), getMinValue(), false),
					Arguments.of(getZero(), getMinValue(), false),
					Arguments.of(getOne(), getMinValue(), false),
					Arguments.of(getMaxValue(), getMinValue(), false),

					Arguments.of(getMinValue(), getMinusOne(), true),
					Arguments.of(getMinusOne(), getMinusOne(), true),
					Arguments.of(getZero(), getMinusOne(), false),
					Arguments.of(getOne(), getMinusOne(), false),
					Arguments.of(getMaxValue(), getMinusOne(), false),

					Arguments.of(getMinValue(), getZero(), true),
					Arguments.of(getMinusOne(), getZero(), true),
					Arguments.of(getZero(), getZero(), true),
					Arguments.of(getOne(), getZero(), false),
					Arguments.of(getMaxValue(), getZero(), false),

					Arguments.of(getMinValue(), getOne(), true),
					Arguments.of(getMinusOne(), getOne(), true),
					Arguments.of(getZero(), getOne(), true),
					Arguments.of(getOne(), getOne(), true),
					Arguments.of(getMaxValue(), getOne(), false),

					Arguments.of(getMinValue(), getMaxValue(), true),
					Arguments.of(getMinusOne(), getMaxValue(), true),
					Arguments.of(getZero(), getMaxValue(), true),
					Arguments.of(getOne(), getMaxValue(), true),
					Arguments.of(getMaxValue(), getMaxValue(), true),

				};
			}

			@ParameterizedTest
			@MethodSource("isLowerOrEqualTo_arguments")
			void isLowerOrEqualTo(T value, T ref, boolean isSuccess) {
				var guard = initiateGuard().validateThat().isLowerOrEqualTo(ref).then().build();

				var actualResult = guard.process(value);

				if (isSuccess) {
					GuardResultAssert.assertThat(actualResult)
						.as(value + " isLowerOrEqualTo(" + ref + ") = SUCCESS")
						.isSuccessWithValue(value);
				} else {
					GuardResultAssert.assertThat(actualResult)
						.as(value + " isLowerOrEqualTo(" + ref + ") = FAIL")
						.isFailure()
						.isValidationFailure()
						.errorAssert(errorAssert -> errorAssert.isNumberMustBeLowerOrEqualTo(value, ref));
				}
			}

		}

		@Nested
		@TestInstance(Lifecycle.PER_CLASS)
		class IsPositive {

			Arguments[] isPositive_arguments() {
				return new Arguments[] {
					// Value,  success
					Arguments.of(getMinValue(), false),
					Arguments.of(getMinusOne(), false),
					Arguments.of(getZero(), false),
					Arguments.of(getOne(), true),
					Arguments.of(getMaxValue(), true),

				};
			}

			@ParameterizedTest
			@MethodSource("isPositive_arguments")
			void isPositive(T value, boolean isSuccess) {
				var guard = initiateGuard().validateThat().isPositive().then().build();

				var actualResult = guard.process(value);

				if (isSuccess) {
					GuardResultAssert.assertThat(actualResult)
						.as(value + " isPositive() = SUCCESS")
						.isSuccessWithValue(value);
				} else {
					GuardResultAssert.assertThat(actualResult)
						.as(value + " isPositive() = FAIL")
						.isFailure()
						.isValidationFailure()
						.errorAssert(errorAssert -> errorAssert.isNumberMustBeGreaterThan(value, getZero()));
				}
			}

		}

		@Nested
		@TestInstance(Lifecycle.PER_CLASS)
		class IsPositiveOrNul {

			Arguments[] isPositiveOrNul_arguments() {
				return new Arguments[] {
					// Value,  success
					Arguments.of(getMinValue(), false),
					Arguments.of(getMinusOne(), false),
					Arguments.of(getZero(), true),
					Arguments.of(getOne(), true),
					Arguments.of(getMaxValue(), true),

				};
			}

			@ParameterizedTest
			@MethodSource("isPositiveOrNul_arguments")
			void isPositiveOrNul(T value, boolean isSuccess) {
				var guard = initiateGuard().validateThat().isPositiveOrNul().then().build();

				var actualResult = guard.process(value);

				if (isSuccess) {
					GuardResultAssert.assertThat(actualResult)
						.as(value + " isPositiveOrNul() = SUCCESS")
						.isSuccessWithValue(value);
				} else {
					GuardResultAssert.assertThat(actualResult)
						.as(value + " isPositiveOrNul() = FAIL")
						.isFailure()
						.isValidationFailure()
						.errorAssert(errorAssert -> errorAssert.isNumberMustBeGreaterOrEqualTo(value, getZero()));
				}
			}

		}

		@Nested
		@TestInstance(Lifecycle.PER_CLASS)
		class IsZero {

			Arguments[] isZero_arguments() {
				return new Arguments[] {
					// Value,  success
					Arguments.of(getMinValue(), false),
					Arguments.of(getMinusOne(), false),
					Arguments.of(getZero(), true),
					Arguments.of(getOne(), false),
					Arguments.of(getMaxValue(), false),

				};
			}

			@ParameterizedTest
			@MethodSource("isZero_arguments")
			void isZero(T value, boolean isSuccess) {
				var guard = initiateGuard().validateThat().isZero().then().build();

				var actualResult = guard.process(value);

				if (isSuccess) {
					GuardResultAssert.assertThat(actualResult)
						.as(value + " isZero() = SUCCESS")
						.isSuccessWithValue(value);
				} else {
					GuardResultAssert.assertThat(actualResult)
						.as(value + " isZero() = FAIL")
						.isFailure()
						.isValidationFailure()
						.errorAssert(errorAssert -> errorAssert.isObjectMustBeEqualTo(getZero()));
				}
			}

		}

		@Nested
		@TestInstance(Lifecycle.PER_CLASS)
		class IsNegative {

			Arguments[] isNegative_arguments() {
				return new Arguments[] {
					// Value,  success
					Arguments.of(getMinValue(), true),
					Arguments.of(getMinusOne(), true),
					Arguments.of(getZero(), false),
					Arguments.of(getOne(), false),
					Arguments.of(getMaxValue(), false),

				};
			}

			@ParameterizedTest
			@MethodSource("isNegative_arguments")
			void isNegative(T value, boolean isSuccess) {
				var guard = initiateGuard().validateThat().isNegative().then().build();

				var actualResult = guard.process(value);

				if (isSuccess) {
					GuardResultAssert.assertThat(actualResult)
						.as(value + " isNegative() = SUCCESS")
						.isSuccessWithValue(value);
				} else {
					GuardResultAssert.assertThat(actualResult)
						.as(value + " isNegative() = FAIL")
						.isFailure()
						.isValidationFailure()
						.errorAssert(errorAssert -> errorAssert.isNumberMustBeLowerThan(value, getZero()));
				}
			}

		}

		@Nested
		@TestInstance(Lifecycle.PER_CLASS)
		class IsNegativeOrNul {

			Arguments[] isNegativeOrNul_arguments() {
				return new Arguments[] {
					// Value,  success
					Arguments.of(getMinValue(), true),
					Arguments.of(getMinusOne(), true),
					Arguments.of(getZero(), true),
					Arguments.of(getOne(), false),
					Arguments.of(getMaxValue(), false),

				};
			}

			@ParameterizedTest
			@MethodSource("isNegativeOrNul_arguments")
			void isNegativeOrNul(T value, boolean isSuccess) {
				var guard = initiateGuard().validateThat().isNegativeOrNul().then().build();

				var actualResult = guard.process(value);

				if (isSuccess) {
					GuardResultAssert.assertThat(actualResult)
						.as(value + " isNegativeOrNul() = SUCCESS")
						.isSuccessWithValue(value);
				} else {
					GuardResultAssert.assertThat(actualResult)
						.as(value + " isNegativeOrNul() = FAIL")
						.isFailure()
						.isValidationFailure()
						.errorAssert(errorAssert -> errorAssert.isNumberMustBeLowerOrEqualTo(value, getZero()));
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
					Arguments.of(getMinValue(), getMinValue(), 	getMinValue(), 	true),
					Arguments.of(getMinusOne(), getMinValue(), 	getMinValue(), 	false),
					Arguments.of(getZero(), 	getMinValue(), 	getMinValue(), 	false),
					Arguments.of(getOne(), 		getMinValue(), 	getMinValue(), 	false),
					Arguments.of(getMaxValue(), getMinValue(), 	getMinValue(), 	false),
					
					Arguments.of(getMinValue(), getMinValue(), 	getMinusOne(), 	true),
					Arguments.of(getMinusOne(), getMinValue(), 	getMinusOne(), 	true),
					Arguments.of(getZero(), 	getMinValue(), 	getMinusOne(), 	false),
					Arguments.of(getOne(), 		getMinValue(), 	getMinusOne(), 	false),
					Arguments.of(getMaxValue(), getMinValue(), 	getMinusOne(), 	false),
					
					Arguments.of(getMinValue(), getMinValue(), 	getZero(),	 	true),
					Arguments.of(getMinusOne(), getMinValue(), 	getZero(),	 	true),
					Arguments.of(getZero(), 	getMinValue(), 	getZero(),	 	true),
					Arguments.of(getOne(), 		getMinValue(), 	getZero(), 		false),
					Arguments.of(getMaxValue(), getMinValue(), 	getZero(), 		false),
					
					Arguments.of(getMinValue(), getMinValue(), 	getOne(), 		true),
					Arguments.of(getMinusOne(), getMinValue(), 	getOne(),	 	true),
					Arguments.of(getZero(), 	getMinValue(), 	getOne(), 		true),
					Arguments.of(getOne(), 		getMinValue(), 	getOne(), 		true),
					Arguments.of(getMaxValue(), getMinValue(), 	getOne(), 		false),
					
					Arguments.of(getMinValue(), getMinValue(), 	getMaxValue(), 	true),
					Arguments.of(getMinusOne(), getMinValue(), 	getMaxValue(), 	true),
					Arguments.of(getZero(), 	getMinValue(), 	getMaxValue(), 	true),
					Arguments.of(getOne(), 		getMinValue(), 	getMaxValue(), 	true),
					Arguments.of(getMaxValue(), getMinValue(), 	getMaxValue(), 	true),
					
					// MinusOne
					Arguments.of(getMinValue(), getMinusOne(), 	getMinusOne(), 	false),
					Arguments.of(getMinusOne(), getMinusOne(), 	getMinusOne(), 	true),
					Arguments.of(getZero(), 	getMinusOne(), 	getMinusOne(), 	false),
					Arguments.of(getOne(), 		getMinusOne(), 	getMinusOne(), 	false),
					Arguments.of(getMaxValue(), getMinusOne(), 	getMinusOne(), 	false),
					
					Arguments.of(getMinValue(), getMinusOne(), 	getZero(),	 	false),
					Arguments.of(getMinusOne(), getMinusOne(), 	getZero(),	 	true),
					Arguments.of(getZero(), 	getMinusOne(), 	getZero(),	 	true),
					Arguments.of(getOne(), 		getMinusOne(), 	getZero(), 		false),
					Arguments.of(getMaxValue(), getMinusOne(), 	getZero(), 		false),
					
					Arguments.of(getMinValue(), getMinusOne(), 	getOne(), 		false),
					Arguments.of(getMinusOne(), getMinusOne(), 	getOne(),	 	true),
					Arguments.of(getZero(), 	getMinusOne(), 	getOne(), 		true),
					Arguments.of(getOne(), 		getMinusOne(), 	getOne(), 		true),
					Arguments.of(getMaxValue(), getMinusOne(), 	getOne(), 		false),
					
					Arguments.of(getMinValue(), getMinusOne(), 	getMaxValue(), 	false),
					Arguments.of(getMinusOne(), getMinusOne(), 	getMaxValue(), 	true),
					Arguments.of(getZero(), 	getMinusOne(), 	getMaxValue(), 	true),
					Arguments.of(getOne(), 		getMinusOne(), 	getMaxValue(), 	true),
					Arguments.of(getMaxValue(), getMinusOne(), 	getMaxValue(), 	true),
					
					// Zero
					Arguments.of(getMinValue(), getZero(), 	getZero(),	 		false),
					Arguments.of(getMinusOne(), getZero(), 	getZero(),		 	false),
					Arguments.of(getZero(), 	getZero(), 	getZero(),	 		true),
					Arguments.of(getOne(), 		getZero(), 	getZero(), 			false),
					Arguments.of(getMaxValue(), getZero(), 	getZero(), 			false),
					
					Arguments.of(getMinValue(), getZero(), 	getOne(), 			false),
					Arguments.of(getMinusOne(), getZero(), 	getOne(),	 		false),
					Arguments.of(getZero(), 	getZero(), 	getOne(), 			true),
					Arguments.of(getOne(), 		getZero(), 	getOne(), 			true),
					Arguments.of(getMaxValue(), getZero(), 	getOne(), 			false),
					
					Arguments.of(getMinValue(), getZero(), 	getMaxValue(),	 	false),
					Arguments.of(getMinusOne(), getZero(), 	getMaxValue(), 		false),
					Arguments.of(getZero(), 	getZero(), 	getMaxValue(), 		true),
					Arguments.of(getOne(), 		getZero(), 	getMaxValue(), 		true),
					Arguments.of(getMaxValue(), getZero(), 	getMaxValue(), 		true),
					
					// One
					Arguments.of(getMinValue(), getOne(), 	getOne(), 			false),
					Arguments.of(getMinusOne(), getOne(), 	getOne(),	 		false),
					Arguments.of(getZero(), 	getOne(), 	getOne(), 			false),
					Arguments.of(getOne(), 		getOne(), 	getOne(), 			true),
					Arguments.of(getMaxValue(), getOne(), 	getOne(), 			false),
					
					Arguments.of(getMinValue(), getOne(), 	getMaxValue(), 		false),
					Arguments.of(getMinusOne(), getOne(), 	getMaxValue(),	 	false),
					Arguments.of(getZero(), 	getOne(), 	getMaxValue(), 		false),
					Arguments.of(getOne(), 		getOne(), 	getMaxValue(), 		true),
					Arguments.of(getMaxValue(), getOne(), 	getMaxValue(), 		true),
					
					// MaxValue
					Arguments.of(getMinValue(), getMaxValue(), 	getMaxValue(), 	false),
					Arguments.of(getMinusOne(), getMaxValue(), 	getMaxValue(), 	false),
					Arguments.of(getZero(), 	getMaxValue(), 	getMaxValue(), 	false),
					Arguments.of(getOne(), 		getMaxValue(), 	getMaxValue(), 	false),
					Arguments.of(getMaxValue(), getMaxValue(), 	getMaxValue(), 	true),

					// @formatter:on
				};
			}

			@ParameterizedTest
			@MethodSource("isBetween_arguments")
			void isBetween(T value, T minRef, T maxRef, boolean isSuccess) {
				var guard = initiateGuard().validateThat().isBetween(minRef, maxRef).then().build();

				var actualResult = guard.process(value);

				if (isSuccess) {
					GuardResultAssert.assertThat(actualResult)
						.as(value + " isBetween(" + minRef + ", " + maxRef + ") = SUCCESS")
						.isSuccessWithValue(value);
				} else {
					GuardResultAssert.assertThat(actualResult)
						.as(value + " isBetween(" + minRef + ", " + maxRef + ") = FAIL")
						.isFailure()
						.isValidationFailure()
						.errorAssert(errorAssert -> errorAssert.isNumberMustBeBetween(value, minRef, maxRef));
				}
			}

			@Test
			void isBetween_null_exceptions() {
				Assertions.assertThatNullPointerException()
					.as("Min isBetween value cannot be null")
					.isThrownBy(() -> initiateGuard().validateThat().isBetween(null, getOne()).then().build())
					.withMessage("min cannot be null");

				Assertions.assertThatNullPointerException()
					.as("Max isBetween value cannot be null")
					.isThrownBy(() -> initiateGuard().validateThat().isBetween(getMinusOne(), null).then().build())
					.withMessage("max cannot be null");
			}

			@Test
			void isBetween_order_exceptions() {
				Assertions.assertThatIllegalArgumentException()
					.as("Min value must be lower or equal to max value")
					.isThrownBy(() -> initiateGuard().validateThat().isBetween(getOne(), getMinusOne()).then().build())
					.withMessageContaining("Min value " + getOne() + " is greater than max value " + getMinusOne());
			}

		}

	}

}
