package io.github.jinputguard.builder.base.types.number;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.InputGuards;

public class IntegerInputGuardBuilder<IN> extends AbstractNumberInputGuardBuilder<IN, Integer, IntegerInputGuardBuilder<IN>> {

	public IntegerInputGuardBuilder(InputGuard<IN, Integer> previous) {
		super(previous);
	}

	@Override
	protected IntegerInputGuardBuilder<IN> newInstance(InputGuard<IN, Integer> guard) {
		return new IntegerInputGuardBuilder<>(guard);
	}

	public static IntegerInputGuardBuilder<Integer> newInstance() {
		return new IntegerInputGuardBuilder<>(InputGuards.noOpGuard());
	}

	@Override
	@SuppressWarnings("unchecked")
	public IntegerSanitizationBuilder<IN> sanitize() {
		return new IntegerSanitizationBuilder<>(this);
	}

	@Override
	@SuppressWarnings("unchecked")
	public IntegerValidationBuilder<IN> validateThat() {
		return new IntegerValidationBuilder<>(this);
	}

	public LongInputGuardBuilder<IN> mapToLong() {
		return map(value -> value.longValue(), LongInputGuardBuilder::new);
	}

}
