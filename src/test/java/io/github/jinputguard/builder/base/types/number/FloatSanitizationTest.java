package io.github.jinputguard.builder.base.types.number;

import io.github.jinputguard.InputGuard;

class FloatSanitizationTest extends AbstractNumberSanitizationTest<Float> {

	@Override
	@SuppressWarnings("unchecked")
	FloatInputGuardBuilder<Float> initiateGuard() {
		return InputGuard.builder().forFloat();
	}

	@Override
	NumberProvider<Float> getNumberProvider() {
		return NumberProvider.forFloat();
	}

}
