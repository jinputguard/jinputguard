package io.github.jinputguard.builder.base.types.number;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.InputGuards;

public class FloatInputGuardBuilder<IN> extends AbstractNumberInputGuardBuilder<IN, Float, FloatInputGuardBuilder<IN>> {

	public FloatInputGuardBuilder(InputGuard<IN, Float> previous) {
		super(previous);
	}

	@Override
	protected FloatInputGuardBuilder<IN> newInstance(InputGuard<IN, Float> guard) {
		return new FloatInputGuardBuilder<>(guard);
	}

	public static FloatInputGuardBuilder<Float> newInstance() {
		return new FloatInputGuardBuilder<>(InputGuards.noOpGuard());
	}

	@Override
	@SuppressWarnings("unchecked")
	public FloatSanitizationBuilder<IN> sanitize() {
		return new FloatSanitizationBuilder<>(this);
	}

	@Override
	@SuppressWarnings("unchecked")
	public FloatValidationBuilder<IN> validateThat() {
		return new FloatValidationBuilder<>(this);
	}

}
