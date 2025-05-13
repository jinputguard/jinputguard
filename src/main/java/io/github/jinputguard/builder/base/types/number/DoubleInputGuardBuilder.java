package io.github.jinputguard.builder.base.types.number;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.InputGuards;

public class DoubleInputGuardBuilder<IN> extends AbstractNumberInputGuardBuilder<IN, Double, DoubleInputGuardBuilder<IN>> {

	public DoubleInputGuardBuilder(InputGuard<IN, Double> previous) {
		super(previous);
	}

	@Override
	protected DoubleInputGuardBuilder<IN> newInstance(InputGuard<IN, Double> guard) {
		return new DoubleInputGuardBuilder<>(guard);
	}

	public static DoubleInputGuardBuilder<Double> newInstance() {
		return new DoubleInputGuardBuilder<>(InputGuards.noOpGuard());
	}

	@Override
	@SuppressWarnings("unchecked")
	public DoubleSanitizationBuilder<IN> sanitize() {
		return new DoubleSanitizationBuilder<>(this);
	}

	@Override
	@SuppressWarnings("unchecked")
	public DoubleValidationBuilder<IN> validateThat() {
		return new DoubleValidationBuilder<>(this);
	}

}
