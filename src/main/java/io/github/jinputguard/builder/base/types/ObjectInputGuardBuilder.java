package io.github.jinputguard.builder.base.types;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.InputGuards;
import io.github.jinputguard.builder.base.AbstractInputGuardBuilder;

public class ObjectInputGuardBuilder<IN, OUT> extends AbstractInputGuardBuilder<IN, OUT, ObjectInputGuardBuilder<IN, OUT>> {

	public ObjectInputGuardBuilder(InputGuard<IN, OUT> previous) {
		super(previous);
	}

	@Override
	protected ObjectInputGuardBuilder<IN, OUT> newInstance(InputGuard<IN, OUT> guard) {
		return new ObjectInputGuardBuilder<>(guard);
	}

	public static <OUT> ObjectInputGuardBuilder<OUT, OUT> newInstance() {
		return new ObjectInputGuardBuilder<>(InputGuards.noOpGuard());
	}

	public ObjectSanitizationBuilder<IN, OUT> sanitize() {
		return new ObjectSanitizationBuilder<>(this);
	}

	public ObjectValidationBuilder<IN, OUT> validateThat() {
		return new ObjectValidationBuilder<>(this);
	}

}
