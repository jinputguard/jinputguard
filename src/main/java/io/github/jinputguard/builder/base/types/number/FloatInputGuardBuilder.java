package io.github.jinputguard.builder.base.types.number;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.guard.InputGuards;

public class FloatInputGuardBuilder<IN> extends AbstractNumberInputGuardBuilder<IN, Float, FloatInputGuardBuilder<IN>> {

	public FloatInputGuardBuilder(InputGuard<IN, Float> previous) {
		super(previous, FloatTester.INSTANCE);
	}

	@Override
	protected FloatInputGuardBuilder<IN> newInstance(InputGuard<IN, Float> guard) {
		return new FloatInputGuardBuilder<>(guard);
	}

	public static FloatInputGuardBuilder<Float> newInstance() {
		return new FloatInputGuardBuilder<>(InputGuards.noOpGuard());
	}

}
