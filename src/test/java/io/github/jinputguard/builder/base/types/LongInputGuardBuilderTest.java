package io.github.jinputguard.builder.base.types;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.builder.base.types.number.LongInputGuardBuilder;
import io.github.jinputguard.result.GuardResultAssert;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class LongInputGuardBuilderTest extends AbstractNumberInputGuardBuilderTest<Long> {

	@Override
	@SuppressWarnings("unchecked")
	LongInputGuardBuilder<Long> initiateGuard() {
		return InputGuard.builder().forLong();
	}

	@Override
	Long getMinValue() {
		return Long.MIN_VALUE;
	}

	@Override
	Long getAbsMinValue() {
		return Long.MIN_VALUE;  // Math.abs() for Long.MIN_VALUE returns Long.MIN_VALUE
	}

	@Override
	Long getMinusOne() {
		return -1L;
	}

	@Override
	Long getZero() {
		return 0L;
	}

	@Override
	Long getOne() {
		return 1L;
	}

	@Override
	Long getMaxValue() {
		return Long.MAX_VALUE;
	}

	@Nested
	class Mapping {

		@Test
		void mapToInt() {
			var guard = initiateGuard().mapToInt().build();
			GuardResultAssert.assertThat(guard.process(getMinValue())).as("mapToLong(MIN)").isSuccessWithValue(getMinValue().intValue());
			GuardResultAssert.assertThat(guard.process(getMinusOne())).as("mapToLong(-1)").isSuccessWithValue(getMinusOne().intValue());
			GuardResultAssert.assertThat(guard.process(getZero())).as("mapToLong(0)").isSuccessWithValue(getZero().intValue());
			GuardResultAssert.assertThat(guard.process(getOne())).as("mapToLong(1)").isSuccessWithValue(getOne().intValue());
			GuardResultAssert.assertThat(guard.process(getMaxValue())).as("mapToLong(MAX)").isSuccessWithValue(getMaxValue().intValue());
		}

	}

}
