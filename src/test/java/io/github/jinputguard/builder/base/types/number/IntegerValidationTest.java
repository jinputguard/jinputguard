package io.github.jinputguard.builder.base.types.number;

import io.github.jinputguard.InputGuard;

class IntegerValidationTest extends AbstractNumberValidationTest<Integer> {

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
