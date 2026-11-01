package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.guard.InputGuards;
import java.util.List;

public class ListInputGuardBuilder<IN, T> extends AbstractCollectionInputGuardBuilder<IN, List<T>, T, ListInputGuardBuilder<IN, T>> {

	public ListInputGuardBuilder(InputGuard<IN, List<T>> previous) {
		super(previous);
	}

	@Override
	protected ListInputGuardBuilder<IN, T> newInstance(InputGuard<IN, List<T>> guard) {
		return new ListInputGuardBuilder<>(guard);
	}

	public static <T> ListInputGuardBuilder<List<T>, T> newInstance() {
		return new ListInputGuardBuilder<>(InputGuards.noOpGuard());
	}

	@Override
	@SuppressWarnings("unchecked")
	public ListValidationBuilder<IN, T> validateThat() {
		return new ListValidationBuilder<>(this);
	}

	@Override
	@SuppressWarnings("unchecked")
	public ListSanitizationBuilder<IN, T> sanitize() {
		return new ListSanitizationBuilder<>(this);
	}

}
