package io.github.jinputguard.builder.base.types.number;

import io.github.jinputguard.InputGuard;

class DoubleMappingTest extends AbstractNumberMappingTest<Double> {

	@Override
	@SuppressWarnings("unchecked")
	DoubleInputGuardBuilder<Double> initiateGuard() {
		return InputGuard.builder().forDouble();
	}

	@Override
	NumberProvider<Double> getNumberProvider() {
		return NumberProvider.forDouble();
	}

}
