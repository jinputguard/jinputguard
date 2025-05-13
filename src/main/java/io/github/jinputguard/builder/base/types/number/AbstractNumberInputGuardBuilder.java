package io.github.jinputguard.builder.base.types.number;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.builder.base.AbstractInputGuardBuilder;

public abstract class AbstractNumberInputGuardBuilder<IN, T extends Number, SELF extends AbstractNumberInputGuardBuilder<IN, T, SELF>>
	extends AbstractInputGuardBuilder<IN, T, SELF> {

	protected AbstractNumberInputGuardBuilder(InputGuard<IN, T> previous) {
		super(previous);
	}

	public abstract <S extends AbstractNumberSanitizationBuilder<IN, T, SELF, S>> S sanitize();

	public abstract <V extends AbstractNumberValidationBuilder<IN, T, SELF, V>> V validateThat();

}
