package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.guard.InputGuards;
import java.util.Set;

public class SetInputGuardBuilder<IN, T> extends AbstractCollectionInputGuardBuilder<IN, Set<T>, T, SetInputGuardBuilder<IN, T>> {

	public SetInputGuardBuilder(InputGuard<IN, Set<T>> previous) {
		super(previous);
	}

	@Override
	protected SetInputGuardBuilder<IN, T> newInstance(InputGuard<IN, Set<T>> guard) {
		return new SetInputGuardBuilder<>(guard);
	}

	public static <T> SetInputGuardBuilder<Set<T>, T> newInstance() {
		return new SetInputGuardBuilder<>(InputGuards.noOpGuard());
	}

	@Override
	@SuppressWarnings("unchecked")
	public SetValidationBuilder<IN, T> validateThat() {
		return new SetValidationBuilder<>(this);
	}

	@Override
	@SuppressWarnings("unchecked")
	public SetSanitizationBuilder<IN, T> sanitize() {
		return new SetSanitizationBuilder<>(this);
	}

}
