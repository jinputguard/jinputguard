package io.github.jinputguard.builder.base.types.number;

import io.github.jinputguard.InputGuard;

class IntegerSanitizationTest extends AbstractNumberSanitizationTest<Integer> {

	@Override
	@SuppressWarnings("unchecked")
	IntegerInputGuardBuilder<Integer> initiateGuard() {
		return InputGuard.builder().forInteger();
	}

	@Override
	NumberProvider<Integer> getNumberProvider() {
		return NumberProvider.forInteger();
	}

}
