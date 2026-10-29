package io.github.jinputguard.builder.base.types.number;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import org.junit.jupiter.api.Test;

class LongMappingTest extends AbstractNumberMappingTest<Long> {

	@Override
	@SuppressWarnings("unchecked")
	LongInputGuardBuilder<Long> initiateGuard() {
		return InputGuard.builder().forLong();
	}

	@Override
	NumberProvider<Long> getNumberProvider() {
		return NumberProvider.forLong();
	}

	@Test
	void mapToInt() {
		var guard = initiateGuard().mapToInt().build();
		GuardResultAssert.assertThat(guard.process(getNumberProvider().getMinValue(), "myVal")).as("mapToLong(MIN)").isSuccess(getNumberProvider().getMinValue().intValue());
		GuardResultAssert.assertThat(guard.process(getNumberProvider().getMinusOne(), "myVal")).as("mapToLong(-1)").isSuccess(getNumberProvider().getMinusOne().intValue());
		GuardResultAssert.assertThat(guard.process(getNumberProvider().getZero(), "myVal")).as("mapToLong(0)").isSuccess(getNumberProvider().getZero().intValue());
		GuardResultAssert.assertThat(guard.process(getNumberProvider().getOne(), "myVal")).as("mapToLong(1)").isSuccess(getNumberProvider().getOne().intValue());
		GuardResultAssert.assertThat(guard.process(getNumberProvider().getMaxValue(), "myVal")).as("mapToLong(MAX)").isSuccess(getNumberProvider().getMaxValue().intValue());
	}

}
