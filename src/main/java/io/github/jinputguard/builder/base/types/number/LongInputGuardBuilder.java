package io.github.jinputguard.builder.base.types.number;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.InputGuards;

public class LongInputGuardBuilder<IN> extends AbstractNumberInputGuardBuilder<IN, Long, LongInputGuardBuilder<IN>> {

	public LongInputGuardBuilder(InputGuard<IN, Long> previous) {
		super(previous);
	}

	@Override
	protected LongInputGuardBuilder<IN> newInstance(InputGuard<IN, Long> guard) {
		return new LongInputGuardBuilder<>(guard);
	}

	public static LongInputGuardBuilder<Long> newInstance() {
		return new LongInputGuardBuilder<>(InputGuards.noOpGuard());
	}

	@Override
	@SuppressWarnings("unchecked")
	public LongSanitizationBuilder<IN> sanitize() {
		return new LongSanitizationBuilder<>(this);
	}

	@Override
	@SuppressWarnings("unchecked")
	public LongValidationBuilder<IN> validateThat() {
		return new LongValidationBuilder<>(this);
	}

	public IntegerInputGuardBuilder<IN> mapToInt() {
		return map(value -> value.intValue(), IntegerInputGuardBuilder::new);
	}

}
