package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.guard.InputGuards;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class SetInputGuardBuilder<IN, T> extends AbstractCollectionInputGuardBuilder<IN, Set<T>, T, SetInputGuardBuilder<IN, T>> {

	public SetInputGuardBuilder(InputGuard<IN, Set<T>> previous, Class<T> elementClass) {
		super(previous, elementClass);
	}

	@Override
	protected SetInputGuardBuilder<IN, T> newInstance(InputGuard<IN, Set<T>> guard) {
		return new SetInputGuardBuilder<>(guard, elementClass);
	}

	public static <T> SetInputGuardBuilder<Set<T>, T> newInstance(Class<T> elementClass) {
		return new SetInputGuardBuilder<>(InputGuards.noOpGuard(), elementClass);
	}

	@Override
	protected Collector<T, ?, Set<T>> unmodifiableCollector() {
		return Collectors.toUnmodifiableSet();
	}

	public ListInputGuardBuilder<IN, T> mapToUnmodifiableList() {
		return map(
			List::copyOf,
			path -> path + " cannot be mapped to a List",
			previous -> new ListInputGuardBuilder<>(previous, elementClass)
		);
	}

	@Override
	protected Set<T> makeUnmodifiableCopy(Collection<T> collection) {
		return Set.copyOf(collection);
	}

}
