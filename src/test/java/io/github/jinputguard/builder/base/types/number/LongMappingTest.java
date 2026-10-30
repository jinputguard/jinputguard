package io.github.jinputguard.builder.base.types.number;

import io.github.jinputguard.InputGuard;

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

}
