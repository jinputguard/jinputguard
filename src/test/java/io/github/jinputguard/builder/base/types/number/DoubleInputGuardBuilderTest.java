package io.github.jinputguard.builder.base.types.number;

import io.github.jinputguard.InputGuard;

class DoubleInputGuardBuilderTest extends AbstractNumberInputGuardBuilderTest<Double> {

	@Override
	@SuppressWarnings("unchecked")
	DoubleInputGuardBuilder<Double> initiateGuard() {
		return InputGuard.builder().forDouble();
	}

	@Override
	Double getMinValue() {
		return -Double.MAX_VALUE;
	}

	@Override
	Double getAbsMinValue() {
		return -getMinValue();
	}

	@Override
	Double getMinusOne() {
		return -1.;
	}

	@Override
	Double getZero() {
		return 0.;
	}

	@Override
	Double getOne() {
		return 1.;
	}

	@Override
	Double getMaxValue() {
		return Double.MAX_VALUE;
	}

}
