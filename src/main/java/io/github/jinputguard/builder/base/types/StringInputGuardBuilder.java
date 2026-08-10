package io.github.jinputguard.builder.base.types;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.builder.base.AbstractInputGuardBuilder;
import io.github.jinputguard.builder.base.types.number.IntegerInputGuardBuilder;
import io.github.jinputguard.builder.base.types.number.LongInputGuardBuilder;
import io.github.jinputguard.guard.InputGuards;

public class StringInputGuardBuilder<IN> extends AbstractInputGuardBuilder<IN, String, StringInputGuardBuilder<IN>> {

	public StringInputGuardBuilder(InputGuard<IN, String> previous) {
		super(previous);
	}

	@Override
	protected StringInputGuardBuilder<IN> newInstance(InputGuard<IN, String> guard) {
		return new StringInputGuardBuilder<>(guard);
	}

	public static StringInputGuardBuilder<String> newInstance() {
		return new StringInputGuardBuilder<>(InputGuards.noOpGuard());
	}

	public StringSanitizationBuilder<IN> sanitize() {
		return new StringSanitizationBuilder<>(this);
	}

	public StringValidationBuilder<IN> validateThat() {
		return new StringValidationBuilder<>(this);
	}

	public IntegerInputGuardBuilder<IN> mapToInteger() {
		return map(Integer::parseInt, IntegerInputGuardBuilder::new, Integer.class);
	}

	public LongInputGuardBuilder<IN> mapToLong() {
		return map(Long::parseLong, LongInputGuardBuilder::new, Long.class);
	}

}
