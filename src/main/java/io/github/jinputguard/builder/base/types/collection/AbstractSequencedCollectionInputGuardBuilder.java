package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.builder.base.AbstractInputGuardBuilder;
import io.github.jinputguard.guard.SequencedCollectionIterationGuard;
import java.util.SequencedCollection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;

public abstract class AbstractSequencedCollectionInputGuardBuilder<IN, C extends SequencedCollection<T>, T, SELF extends AbstractSequencedCollectionInputGuardBuilder<IN, C, T, SELF>>
	extends AbstractInputGuardBuilder<IN, C, SELF> {

	protected AbstractSequencedCollectionInputGuardBuilder(InputGuard<IN, C> previous) {
		super(previous);
	}

	public <B extends AbstractSequencedCollectionInputGuardBuilder<IN, C_OUT, OUT, B>, C_OUT extends SequencedCollection<OUT>, OUT> B filterAndProcessEach(
		Predicate<T> elementFilter, InputGuard<T, OUT> elementGuard, Collector<OUT, ?, C_OUT> collector, Function<InputGuard<IN, C_OUT>, B> builderFunction
	) {
		InputGuard<C, C_OUT> forEachProcess = new SequencedCollectionIterationGuard<>(elementFilter, elementGuard, collector);
		return builderFunction.apply(build().andThen(forEachProcess));
	}

}
