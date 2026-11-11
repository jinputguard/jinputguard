package io.github.jinputguard.builder.base.types.number;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.guard.InputGuards;

public class DoubleInputGuardBuilder<IN> extends AbstractNumberInputGuardBuilder<IN, Double, DoubleInputGuardBuilder<IN>> {

	public DoubleInputGuardBuilder(InputGuard<IN, Double> previous) {
		super(previous, DoubleTester.INSTANCE);
	}

	@Override
	protected DoubleInputGuardBuilder<IN> newInstance(InputGuard<IN, Double> guard) {
		return new DoubleInputGuardBuilder<>(guard);
	}

	public static DoubleInputGuardBuilder<Double> newInstance() {
		return new DoubleInputGuardBuilder<>(InputGuards.noOpGuard());
	}

}
