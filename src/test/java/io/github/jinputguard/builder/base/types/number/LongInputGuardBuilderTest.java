package io.github.jinputguard.builder.base.types.number;

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
			GuardResultAssert.assertThat(guard.process(getMinValue(), "myVal")).as("mapToLong(MIN)").isSuccess(getMinValue().intValue());
			GuardResultAssert.assertThat(guard.process(getMinusOne(), "myVal")).as("mapToLong(-1)").isSuccess(getMinusOne().intValue());
			GuardResultAssert.assertThat(guard.process(getZero(), "myVal")).as("mapToLong(0)").isSuccess(getZero().intValue());
			GuardResultAssert.assertThat(guard.process(getOne(), "myVal")).as("mapToLong(1)").isSuccess(getOne().intValue());
			GuardResultAssert.assertThat(guard.process(getMaxValue(), "myVal")).as("mapToLong(MAX)").isSuccess(getMaxValue().intValue());
		}

	}

}
