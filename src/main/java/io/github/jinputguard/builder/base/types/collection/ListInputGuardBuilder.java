package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.guard.InputGuards;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
	protected Collector<T, ?, List<T>> unmodifiableCollector() {
		return Collectors.toUnmodifiableList();
	}

	public SetInputGuardBuilder<IN, T> mapToUnmodifiableSet() {
		return map(
			Set::copyOf,
			path -> path + " cannot be mapped to a Set",
			SetInputGuardBuilder::new
		);
	}

	@Override
	protected List<T> makeUnmodifiableCopy(Collection<T> collection) {
		return List.copyOf(collection);
	}

}
