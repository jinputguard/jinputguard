package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.InputGuards;
import io.github.jinputguard.util.Predicates;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ListInputGuardBuilder<IN, T> extends AbstractSequencedCollectionInputGuardBuilder<IN, List<T>, T, ListInputGuardBuilder<IN, T>> {

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

	@SuppressWarnings("unchecked")
	public <OUT> ListInputGuardBuilder<IN, OUT> filter(Predicate<T> elementFilter) {
		return filterAndProcessEach(elementFilter, (InputGuard<T, OUT>) InputGuards.noOpGuard(), Collectors.toUnmodifiableList());
	}

	@SuppressWarnings("unchecked")
	public <OUT> ListInputGuardBuilder<IN, OUT> filter(Predicate<T> elementFilter, Collector<OUT, ?, List<OUT>> collector) {
		return filterAndProcessEach(elementFilter, (InputGuard<T, OUT>) InputGuards.noOpGuard(), collector);
	}

	public <OUT> ListInputGuardBuilder<IN, OUT> processEach(InputGuard<T, OUT> elementGuard) {
		return filterAndProcessEach(Predicates.alwaysTrue(), elementGuard, Collectors.toUnmodifiableList());
	}

	public <OUT> ListInputGuardBuilder<IN, OUT> processEach(InputGuard<T, OUT> elementGuard, Collector<OUT, ?, List<OUT>> collector) {
		return filterAndProcessEach(Predicates.alwaysTrue(), elementGuard, collector, ListInputGuardBuilder::new);
	}

	public <OUT> ListInputGuardBuilder<IN, OUT> filterAndProcessEach(Predicate<T> elementFilter, InputGuard<T, OUT> elementGuard) {
		return filterAndProcessEach(elementFilter, elementGuard, Collectors.toUnmodifiableList());
	}

	public <OUT> ListInputGuardBuilder<IN, OUT> filterAndProcessEach(
		Predicate<T> elementFilter, InputGuard<T, OUT> elementGuard, Collector<OUT, ?, List<OUT>> collector
	) {
		return filterAndProcessEach(elementFilter, elementGuard, collector, ListInputGuardBuilder::new);
	}

}
