package io.github.jinputguard.builder.base.types;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.builder.base.types.number.IntegerInputGuardBuilder;
import io.github.jinputguard.result.ProcessResultAssert;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class IntegerInputGuardBuilderTest extends AbstractNumberInputGuardBuilderTest<Integer> {

	@Override
	@SuppressWarnings("unchecked")
	IntegerInputGuardBuilder<Integer> initiateGuard() {
		return InputGuard.builder().forInteger();
	}

	@Override
	Integer getMinValue() {
		return Integer.MIN_VALUE;
	}

	@Override
	Integer getAbsMinValue() {
		return Integer.MIN_VALUE;  // Math.abs() for Integer.MIN_VALUE returns Integer.MIN_VALUE
	}

	@Override
	Integer getMinusOne() {
		return -1;
	}

	@Override
	Integer getZero() {
		return 0;
	}

	@Override
	Integer getOne() {
		return 1;
	}

	@Override
	Integer getMaxValue() {
		return Integer.MAX_VALUE;
	}

	@Nested
	class Mapping {

		@Test
		void mapToLong() {
			var guard = initiateGuard().mapToLong().build();
			ProcessResultAssert.assertThat(guard.process(getMinValue())).as("mapToLong(MIN)").isSuccessWithValue(getMinValue().longValue());
			ProcessResultAssert.assertThat(guard.process(getMinusOne())).as("mapToLong(-1)").isSuccessWithValue(getMinusOne().longValue());
			ProcessResultAssert.assertThat(guard.process(getZero())).as("mapToLong(0)").isSuccessWithValue(getZero().longValue());
			ProcessResultAssert.assertThat(guard.process(getOne())).as("mapToLong(1)").isSuccessWithValue(getOne().longValue());
			ProcessResultAssert.assertThat(guard.process(getMaxValue())).as("mapToLong(MAX)").isSuccessWithValue(getMaxValue().longValue());
		}

	}

}
