package io.github.jinputguard.builder.base.types;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.builder.base.types.number.FloatInputGuardBuilder;

class FloatInputGuardBuilderTest extends AbstractNumberInputGuardBuilderTest<Float> {

	@Override
	@SuppressWarnings("unchecked")
	FloatInputGuardBuilder<Float> initiateGuard() {
		return InputGuard.builder().forFloat();
	}

	@Override
	Float getMinValue() {
		return -Float.MAX_VALUE;
	}

	@Override
	Float getAbsMinValue() {
		return -getMinValue();
	}

	@Override
	Float getMinusOne() {
		return -1f;
	}

	@Override
	Float getZero() {
		return 0f;
	}

	@Override
	Float getOne() {
		return 1f;
	}

	@Override
	Float getMaxValue() {
		return Float.MAX_VALUE;
	}

}
