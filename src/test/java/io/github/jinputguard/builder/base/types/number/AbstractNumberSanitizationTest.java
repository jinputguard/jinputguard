package io.github.jinputguard.builder.base.types.number;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

abstract class AbstractNumberSanitizationTest<T extends Number> extends AbstractNumberTest<T> {

	@Nested
	class Clamp {

		@Test
		void clamp_MIN_MIN() {
			var guard = initiateGuard().clamp(getNumberProvider().getMinValue(), getNumberProvider().getMinValue()).build();
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinValue(), "myVal")).as("clamp(MIN, MIN) & MIN = MIN")
				.isSuccess(getNumberProvider().getMinValue());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinusOne(), "myVal")).as("clamp(MIN, MIN) & -1 = MIN")
				.isSuccess(getNumberProvider().getMinValue());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getZero(), "myVal")).as("clamp(MIN, MIN) & 0 = MIN")
				.isSuccess(getNumberProvider().getMinValue());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getOne(), "myVal")).as("clamp(MIN, MIN) & 1 = MIN").isSuccess(getNumberProvider().getMinValue());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMaxValue(), "myVal")).as("clamp(MIN, MIN) & MAX = MIN")
				.isSuccess(getNumberProvider().getMinValue());
		}

		@Test
		void clamp_MIN_MinusOne() {
			var guard = initiateGuard().clamp(getNumberProvider().getMinValue(), getNumberProvider().getMinusOne()).build();
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinValue(), "myVal")).as("clamp(MIN, -1) & MIN = MIN")
				.isSuccess(getNumberProvider().getMinValue());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinusOne(), "myVal")).as("clamp(MIN, -1) & -1 = -1")
				.isSuccess(getNumberProvider().getMinusOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getZero(), "myVal")).as("clamp(MIN, -1) & 0 = -1").isSuccess(getNumberProvider().getMinusOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getOne(), "myVal")).as("clamp(MIN, -1) & 1 = -1").isSuccess(getNumberProvider().getMinusOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMaxValue(), "myVal")).as("clamp(MIN, -1) & MAX = -1")
				.isSuccess(getNumberProvider().getMinusOne());
		}

		@Test
		void clamp_MIN_Zero() {
			var guard = initiateGuard().clamp(getNumberProvider().getMinValue(), getNumberProvider().getZero()).build();
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinValue(), "myVal")).as("clamp(MIN, 0) & MIN = MIN")
				.isSuccess(getNumberProvider().getMinValue());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinusOne(), "myVal")).as("clamp(MIN, 0) & -1 = -1")
				.isSuccess(getNumberProvider().getMinusOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getZero(), "myVal")).as("clamp(MIN, 0) & 0 = 0").isSuccess(getNumberProvider().getZero());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getOne(), "myVal")).as("clamp(MIN, 0) & 1 = 0").isSuccess(getNumberProvider().getZero());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMaxValue(), "myVal")).as("clamp(MIN, 0) & MAX = 0").isSuccess(getNumberProvider().getZero());
		}

		@Test
		void clamp_MIN_One() {
			var guard = initiateGuard().clamp(getNumberProvider().getMinValue(), getNumberProvider().getOne()).build();
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinValue(), "myVal")).as("clamp(MIN, 1) & MIN = MIN")
				.isSuccess(getNumberProvider().getMinValue());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinusOne(), "myVal")).as("clamp(MIN, 1) & -1 = -1")
				.isSuccess(getNumberProvider().getMinusOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getZero(), "myVal")).as("clamp(MIN, 1) & 0 = 0").isSuccess(getNumberProvider().getZero());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getOne(), "myVal")).as("clamp(MIN, 1) & 1 = 1").isSuccess(getNumberProvider().getOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMaxValue(), "myVal")).as("clamp(MIN, 1) & MAX = 1").isSuccess(getNumberProvider().getOne());
		}

		@Test
		void clamp_MIN_MAX() {
			var guard = initiateGuard().clamp(getNumberProvider().getMinValue(), getNumberProvider().getMaxValue()).build();
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinValue(), "myVal")).as("clamp(MIN, MAX) & MIN = MIN")
				.isSuccess(getNumberProvider().getMinValue());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinusOne(), "myVal")).as("clamp(MIN, MAX) & -1 = -1")
				.isSuccess(getNumberProvider().getMinusOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getZero(), "myVal")).as("clamp(MIN, MAX) & 0 = 0").isSuccess(getNumberProvider().getZero());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getOne(), "myVal")).as("clamp(MIN, MAX) & 1 = 1").isSuccess(getNumberProvider().getOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMaxValue(), "myVal")).as("clamp(MIN, MAX) & MAX = MAX")
				.isSuccess(getNumberProvider().getMaxValue());
		}

		@Test
		void clamp_MinusOne_MinusOne() {
			var guard = initiateGuard().clamp(getNumberProvider().getMinusOne(), getNumberProvider().getMinusOne()).build();
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinValue(), "myVal")).as("clamp(-1, -1) & MIN = -1")
				.isSuccess(getNumberProvider().getMinusOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinusOne(), "myVal")).as("clamp(-1, -1) & -1 = -1")
				.isSuccess(getNumberProvider().getMinusOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getZero(), "myVal")).as("clamp(-1, -1) & 0 = -1").isSuccess(getNumberProvider().getMinusOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getOne(), "myVal")).as("clamp(-1, -1) & 1 = -1").isSuccess(getNumberProvider().getMinusOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMaxValue(), "myVal")).as("clamp(-1, -1) & MAX = -1")
				.isSuccess(getNumberProvider().getMinusOne());
		}

		@Test
		void clamp_MinusOne_Zero() {
			var guard = initiateGuard().clamp(getNumberProvider().getMinusOne(), getNumberProvider().getZero()).build();
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinValue(), "myVal")).as("clamp(-1, 0) & MIN = -1")
				.isSuccess(getNumberProvider().getMinusOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinusOne(), "myVal")).as("clamp(-1, 0) & -1 = -1")
				.isSuccess(getNumberProvider().getMinusOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getZero(), "myVal")).as("clamp(-1, 0) & 0 = 0").isSuccess(getNumberProvider().getZero());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getOne(), "myVal")).as("clamp(-1, 0) & 1 = 0").isSuccess(getNumberProvider().getZero());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMaxValue(), "myVal")).as("clamp(-1, 0) & MAX = 0").isSuccess(getNumberProvider().getZero());
		}

		@Test
		void clamp_MinusOne_One() {
			var guard = initiateGuard().clamp(getNumberProvider().getMinusOne(), getNumberProvider().getOne()).build();
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinValue(), "myVal")).as("clamp(-1, 1) & MIN = -1")
				.isSuccess(getNumberProvider().getMinusOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinusOne(), "myVal")).as("clamp(-1, 1) & -1 = -1")
				.isSuccess(getNumberProvider().getMinusOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getZero(), "myVal")).as("clamp(-1, 1) & 0 = 0").isSuccess(getNumberProvider().getZero());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getOne(), "myVal")).as("clamp(-1, 1) & 1 = 1").isSuccess(getNumberProvider().getOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMaxValue(), "myVal")).as("clamp(-1, 1) & MAX = 1").isSuccess(getNumberProvider().getOne());
		}

		@Test
		void clamp_MinusOne_MAX() {
			var guard = initiateGuard().clamp(getNumberProvider().getMinusOne(), getNumberProvider().getMaxValue()).build();
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinValue(), "myVal")).as("clamp(-1, MAX) & MIN = -1")
				.isSuccess(getNumberProvider().getMinusOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinusOne(), "myVal")).as("clamp(-1, MAX) & -1 = -1")
				.isSuccess(getNumberProvider().getMinusOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getZero(), "myVal")).as("clamp(-1, MAX) & 0 = 0").isSuccess(getNumberProvider().getZero());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getOne(), "myVal")).as("clamp(-1, MAX) & 1 = 1").isSuccess(getNumberProvider().getOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMaxValue(), "myVal")).as("clamp(-1, MAX) & MAX = MAX")
				.isSuccess(getNumberProvider().getMaxValue());
		}

		@Test
		void clamp_Zero_Zero() {
			var guard = initiateGuard().clamp(getNumberProvider().getZero(), getNumberProvider().getZero()).build();
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinValue(), "myVal")).as("clamp(0, 0) & MIN = 0").isSuccess(getNumberProvider().getZero());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinusOne(), "myVal")).as("clamp(0, 0) & -1 = 0").isSuccess(getNumberProvider().getZero());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getZero(), "myVal")).as("clamp(0, 0) & 0 = 0").isSuccess(getNumberProvider().getZero());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getOne(), "myVal")).as("clamp(0, 0) & 1 = 0").isSuccess(getNumberProvider().getZero());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMaxValue(), "myVal")).as("clamp(0, 0) & MAX = 0").isSuccess(getNumberProvider().getZero());
		}

		@Test
		void clamp_Zero_One() {
			var guard = initiateGuard().clamp(getNumberProvider().getZero(), getNumberProvider().getOne()).build();
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinValue(), "myVal")).as("clamp(0, 1) & MIN = 0").isSuccess(getNumberProvider().getZero());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinusOne(), "myVal")).as("clamp(0, 1) & -1 = 0").isSuccess(getNumberProvider().getZero());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getZero(), "myVal")).as("clamp(0, 1) & 0 = 0").isSuccess(getNumberProvider().getZero());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getOne(), "myVal")).as("clamp(0, 1) & 1 = 1").isSuccess(getNumberProvider().getOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMaxValue(), "myVal")).as("clamp(0, 1) & MAX = 1").isSuccess(getNumberProvider().getOne());
		}

		@Test
		void clamp_Zero_MAX() {
			var guard = initiateGuard().clamp(getNumberProvider().getZero(), getNumberProvider().getMaxValue()).build();
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinValue(), "myVal")).as("clamp(0, MAX) & MIN = 0").isSuccess(getNumberProvider().getZero());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinusOne(), "myVal")).as("clamp(0, MAX) & -1 = 0").isSuccess(getNumberProvider().getZero());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getZero(), "myVal")).as("clamp(0, MAX) & 0 = 0").isSuccess(getNumberProvider().getZero());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getOne(), "myVal")).as("clamp(0, MAX) & 1 = 1").isSuccess(getNumberProvider().getOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMaxValue(), "myVal")).as("clamp(0, MAX) & MAX = MAX")
				.isSuccess(getNumberProvider().getMaxValue());
		}

		@Test
		void clamp_One_One() {
			var guard = initiateGuard().clamp(getNumberProvider().getOne(), getNumberProvider().getOne()).build();
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinValue(), "myVal")).as("clamp(1, 1) & MIN = 0").isSuccess(getNumberProvider().getOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinusOne(), "myVal")).as("clamp(1, 1) & -1 = 0").isSuccess(getNumberProvider().getOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getZero(), "myVal")).as("clamp(1, 1) & 0 = 0").isSuccess(getNumberProvider().getOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getOne(), "myVal")).as("clamp(1, 1) & 1 = 1").isSuccess(getNumberProvider().getOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMaxValue(), "myVal")).as("clamp(1, 1) & MAX = 1").isSuccess(getNumberProvider().getOne());
		}

		@Test
		void clamp_One_MAX() {
			var guard = initiateGuard().clamp(getNumberProvider().getOne(), getNumberProvider().getMaxValue()).build();
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinValue(), "myVal")).as("clamp(1, MAX) & MIN = 0").isSuccess(getNumberProvider().getOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinusOne(), "myVal")).as("clamp(1, MAX) & -1 = 0").isSuccess(getNumberProvider().getOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getZero(), "myVal")).as("clamp(1, MAX) & 0 = 0").isSuccess(getNumberProvider().getOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getOne(), "myVal")).as("clamp(1, MAX) & 1 = 1").isSuccess(getNumberProvider().getOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMaxValue(), "myVal")).as("clamp(1, MAX) & MAX = MAX")
				.isSuccess(getNumberProvider().getMaxValue());
		}

		@Test
		void clamp_MAX_MAX() {
			var guard = initiateGuard().clamp(getNumberProvider().getMaxValue(), getNumberProvider().getMaxValue()).build();
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinValue(), "myVal")).as("clamp(MAX, MAX) & MIN = 0")
				.isSuccess(getNumberProvider().getMaxValue());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinusOne(), "myVal")).as("clamp(MAX, MAX) & -1 = 0")
				.isSuccess(getNumberProvider().getMaxValue());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getZero(), "myVal")).as("clamp(MAX, MAX) & 0 = 0").isSuccess(getNumberProvider().getMaxValue());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getOne(), "myVal")).as("clamp(MAX, MAX) & 1 = 1").isSuccess(getNumberProvider().getMaxValue());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMaxValue(), "myVal")).as("clamp(MAX, MAX) & MAX = MAX")
				.isSuccess(getNumberProvider().getMaxValue());
		}

		@Test
		void clamp_null_exceptions() {
			Assertions.assertThatNullPointerException()
				.as("Min clamp value cannot be null")
				.isThrownBy(() -> initiateGuard().clamp(null, getNumberProvider().getOne()).build())
				.withMessage("min cannot be null");

			Assertions.assertThatNullPointerException()
				.as("Max clamp value cannot be null")
				.isThrownBy(() -> initiateGuard().clamp(getNumberProvider().getMinusOne(), null).build())
				.withMessage("max cannot be null");
		}

		@Test
		void clamp_order_exceptions() {
			Assertions.assertThatIllegalArgumentException()
				.as("Min clamp value must be lower or equal to max clamp value")
				.isThrownBy(() -> initiateGuard().clamp(getNumberProvider().getOne(), getNumberProvider().getMinusOne()).build())
				.withMessageContaining("Min value " + getNumberProvider().getOne() + " is greater than max value " + getNumberProvider().getMinusOne());
		}

	}

	@Nested
	class ClampMin {

		@Test
		void clampMin_MIN() {
			var guard = initiateGuard().clampMin(getNumberProvider().getMinValue()).build();
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinValue(), "myVal")).as("clampMin(MIN) & MIN = MIN")
				.isSuccess(getNumberProvider().getMinValue());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinusOne(), "myVal")).as("clampMin(MIN) & -1 = -1")
				.isSuccess(getNumberProvider().getMinusOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getZero(), "myVal")).as("clampMin(MIN) & 0 = 0").isSuccess(getNumberProvider().getZero());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getOne(), "myVal")).as("clampMin(MIN) & 1 = 1").isSuccess(getNumberProvider().getOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMaxValue(), "myVal")).as("clampMin(MIN) & MAX = MAX")
				.isSuccess(getNumberProvider().getMaxValue());
		}

		@Test
		void clampMin_MinusOne() {
			var guard = initiateGuard().clampMin(getNumberProvider().getMinusOne()).build();
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinValue(), "myVal")).as("clampMin(-1) & MIN = -1")
				.isSuccess(getNumberProvider().getMinusOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinusOne(), "myVal")).as("clampMin(-1) & -1 = -1")
				.isSuccess(getNumberProvider().getMinusOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getZero(), "myVal")).as("clampMin(-1) & 0 = 0").isSuccess(getNumberProvider().getZero());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getOne(), "myVal")).as("clampMin(-1) & 1 = 1").isSuccess(getNumberProvider().getOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMaxValue(), "myVal")).as("clampMin(-1) & MAX = MAX")
				.isSuccess(getNumberProvider().getMaxValue());
		}

		@Test
		void clampMin_Zero() {
			var guard = initiateGuard().clampMin(getNumberProvider().getZero()).build();
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinValue(), "myVal")).as("clampMin(0) & MIN = 0").isSuccess(getNumberProvider().getZero());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinusOne(), "myVal")).as("clampMin(0) & -1 = 0").isSuccess(getNumberProvider().getZero());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getZero(), "myVal")).as("clampMin(0) & 0 = 0").isSuccess(getNumberProvider().getZero());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getOne(), "myVal")).as("clampMin(0) & 1 = 1").isSuccess(getNumberProvider().getOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMaxValue(), "myVal")).as("clampMin(0) & MAX = MAX")
				.isSuccess(getNumberProvider().getMaxValue());
		}

		@Test
		void clampMin_One() {
			var guard = initiateGuard().clampMin(getNumberProvider().getOne()).build();
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinValue(), "myVal")).as("clampMin(1) & MIN = 1").isSuccess(getNumberProvider().getOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinusOne(), "myVal")).as("clampMin(1) & -1 = 1").isSuccess(getNumberProvider().getOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getZero(), "myVal")).as("clampMin(1) & 0 = 1").isSuccess(getNumberProvider().getOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getOne(), "myVal")).as("clampMin(1) & 1 = 1").isSuccess(getNumberProvider().getOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMaxValue(), "myVal")).as("clampMin(1) & MAX = 1")
				.isSuccess(getNumberProvider().getMaxValue());
		}

		@Test
		void clampMin_MAX() {
			var guard = initiateGuard().clampMin(getNumberProvider().getMaxValue()).build();
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinValue(), "myVal")).as("clampMin(MAX) & MIN = MAX")
				.isSuccess(getNumberProvider().getMaxValue());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinusOne(), "myVal")).as("clampMin(MAX) & -1 = MAX")
				.isSuccess(getNumberProvider().getMaxValue());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getZero(), "myVal")).as("clampMin(MAX) & 0 = MAX").isSuccess(getNumberProvider().getMaxValue());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getOne(), "myVal")).as("clampMin(MAX) & 1 = MAX").isSuccess(getNumberProvider().getMaxValue());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMaxValue(), "myVal")).as("clampMin(MAX) & MAX = MAX")
				.isSuccess(getNumberProvider().getMaxValue());
		}

		@Test
		void clampMin_exceptions() {
			Assertions.assertThatNullPointerException()
				.as("Min clamp value cannot be null")
				.isThrownBy(() -> InputGuard.builder().forInteger().clampMin(null).build())
				.withMessage("min cannot be null");
		}

	}

	@Nested
	class ClampMax {

		@Test
		void clampMax_MIN() {
			var guard = initiateGuard().clampMax(getNumberProvider().getMinValue()).build();
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinValue(), "myVal")).as("clampMax(MIN) & MIN = MIN")
				.isSuccess(getNumberProvider().getMinValue());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinusOne(), "myVal")).as("clampMax(MIN) & -1 = MIN")
				.isSuccess(getNumberProvider().getMinValue());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getZero(), "myVal")).as("clampMax(MIN) & 0 = MIN").isSuccess(getNumberProvider().getMinValue());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getOne(), "myVal")).as("clampMax(MIN) & 1 = MIN").isSuccess(getNumberProvider().getMinValue());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMaxValue(), "myVal")).as("clampMax(MIN) & MAX = MIN")
				.isSuccess(getNumberProvider().getMinValue());
		}

		@Test
		void clampMax_MinusOne() {
			var guard = initiateGuard().clampMax(getNumberProvider().getMinusOne()).build();
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinValue(), "myVal")).as("clampMax(-1) & MIN = MIN")
				.isSuccess(getNumberProvider().getMinValue());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinusOne(), "myVal")).as("clampMax(-1) & -1 = -1")
				.isSuccess(getNumberProvider().getMinusOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getZero(), "myVal")).as("clampMax(-1) & 0 = -1").isSuccess(getNumberProvider().getMinusOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getOne(), "myVal")).as("clampMax(-1) & 1 = -1").isSuccess(getNumberProvider().getMinusOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMaxValue(), "myVal")).as("clampMax(-1) & MAX = -1")
				.isSuccess(getNumberProvider().getMinusOne());
		}

		@Test
		void clampMax_Zero() {
			var guard = initiateGuard().clampMax(getNumberProvider().getZero()).build();
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinValue(), "myVal")).as("clampMax(0) & MIN = MIN")
				.isSuccess(getNumberProvider().getMinValue());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinusOne(), "myVal")).as("clampMax(0) & -1 = -1")
				.isSuccess(getNumberProvider().getMinusOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getZero(), "myVal")).as("clampMax(0) & 0 = 0").isSuccess(getNumberProvider().getZero());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getOne(), "myVal")).as("clampMax(0) & 1 = 0").isSuccess(getNumberProvider().getZero());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMaxValue(), "myVal")).as("clampMax(0) & MAX = 0").isSuccess(getNumberProvider().getZero());
		}

		@Test
		void clampMax_One() {
			var guard = initiateGuard().clampMax(getNumberProvider().getOne()).build();
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinValue(), "myVal")).as("clampMax(1) & MIN = MIN")
				.isSuccess(getNumberProvider().getMinValue());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinusOne(), "myVal")).as("clampMax(1) & -1 = -1")
				.isSuccess(getNumberProvider().getMinusOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getZero(), "myVal")).as("clampMax(1) & 0 = 0").isSuccess(getNumberProvider().getZero());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getOne(), "myVal")).as("clampMax(1) & 1 = 1").isSuccess(getNumberProvider().getOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMaxValue(), "myVal")).as("clampMax(1) & MAX = 1").isSuccess(getNumberProvider().getOne());
		}

		@Test
		void clampMax_MAX() {
			var guard = initiateGuard().clampMax(getNumberProvider().getMaxValue()).build();
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinValue(), "myVal")).as("clampMax(MAX) & MIN = MIN")
				.isSuccess(getNumberProvider().getMinValue());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMinusOne(), "myVal")).as("clampMax(MAX) & -1 = -1")
				.isSuccess(getNumberProvider().getMinusOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getZero(), "myVal")).as("clampMax(MAX) & 0 = 0").isSuccess(getNumberProvider().getZero());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getOne(), "myVal")).as("clampMax(MAX) & 1 = 1").isSuccess(getNumberProvider().getOne());
			GuardResultAssert.assertThat(guard.processToResult(getNumberProvider().getMaxValue(), "myVal")).as("clampMax(MAX) & MAX = MAX")
				.isSuccess(getNumberProvider().getMaxValue());
		}

		@Test
		void clampMax_exceptions() {
			Assertions.assertThatNullPointerException()
				.as("Max clamp value cannot be null")
				.isThrownBy(() -> InputGuard.builder().forInteger().clampMax(null).build())
				.withMessage("max cannot be null");
		}

	}

}
