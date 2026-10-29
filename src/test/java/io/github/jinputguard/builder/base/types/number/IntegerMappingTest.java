package io.github.jinputguard.builder.base.types.number;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import org.junit.jupiter.api.Test;

class IntegerMappingTest extends AbstractNumberMappingTest<Integer> {

	@Override
	@SuppressWarnings("unchecked")
	IntegerInputGuardBuilder<Integer> initiateGuard() {
		return InputGuard.builder().forInteger();
	}

	@Override
	NumberProvider<Integer> getNumberProvider() {
		return NumberProvider.forInteger();
	}

	@Test
	void mapToLong() {
		var guard = initiateGuard().mapToLong().build();
		GuardResultAssert.assertThat(guard.process(getNumberProvider().getMinValue(), "myVal")).as("mapToLong(MIN)").isSuccess(getNumberProvider().getMinValue().longValue());
		GuardResultAssert.assertThat(guard.process(getNumberProvider().getMinusOne(), "myVal")).as("mapToLong(-1)").isSuccess(getNumberProvider().getMinusOne().longValue());
		GuardResultAssert.assertThat(guard.process(getNumberProvider().getZero(), "myVal")).as("mapToLong(0)").isSuccess(getNumberProvider().getZero().longValue());
		GuardResultAssert.assertThat(guard.process(getNumberProvider().getOne(), "myVal")).as("mapToLong(1)").isSuccess(getNumberProvider().getOne().longValue());
		GuardResultAssert.assertThat(guard.process(getNumberProvider().getMaxValue(), "myVal")).as("mapToLong(MAX)").isSuccess(getNumberProvider().getMaxValue().longValue());
	}

}
