package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.InputGuards;
import io.github.jinputguard.util.Predicates;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

	@SuppressWarnings("unchecked")
	public <OUT> SetInputGuardBuilder<IN, OUT> filter(Predicate<T> elementFilter) {
		return filterAndProcessEach(elementFilter, (InputGuard<T, OUT>) InputGuards.noOpGuard(), Collectors.toUnmodifiableSet());
	}

	@SuppressWarnings("unchecked")
	public <OUT> SetInputGuardBuilder<IN, OUT> filter(Predicate<T> elementFilter, Collector<OUT, ?, Set<OUT>> collector) {
		return filterAndProcessEach(elementFilter, (InputGuard<T, OUT>) InputGuards.noOpGuard(), collector);
	}

	public <OUT> SetInputGuardBuilder<IN, OUT> processEach(InputGuard<T, OUT> elementGuard) {
		return filterAndProcessEach(Predicates.alwaysTrue(), elementGuard, Collectors.toUnmodifiableSet());
	}

	public <OUT> SetInputGuardBuilder<IN, OUT> processEach(InputGuard<T, OUT> elementGuard, Collector<OUT, ?, Set<OUT>> collector) {
		return filterAndProcessEach(Predicates.alwaysTrue(), elementGuard, collector, SetInputGuardBuilder::new);
	}

	public <OUT> SetInputGuardBuilder<IN, OUT> filterAndProcessEach(Predicate<T> elementFilter, InputGuard<T, OUT> elementGuard) {
		return filterAndProcessEach(elementFilter, elementGuard, Collectors.toUnmodifiableSet());
	}

	public <OUT> SetInputGuardBuilder<IN, OUT> filterAndProcessEach(
		Predicate<T> elementFilter, InputGuard<T, OUT> elementGuard, Collector<OUT, ?, Set<OUT>> collector
	) {
		return filterAndProcessEach(elementFilter, elementGuard, collector, SetInputGuardBuilder::new);
	}

}
