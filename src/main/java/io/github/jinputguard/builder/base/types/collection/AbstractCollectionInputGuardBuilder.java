package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.builder.base.AbstractInputGuardBuilder;
import io.github.jinputguard.guard.CollectionIterationGuard;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;

public abstract class AbstractCollectionInputGuardBuilder<IN, C extends Collection<T>, T, SELF extends AbstractCollectionInputGuardBuilder<IN, C, T, SELF>>
	extends AbstractInputGuardBuilder<IN, C, SELF> {

	protected AbstractCollectionInputGuardBuilder(InputGuard<IN, C> previous) {
		super(previous);
	}

	public <B extends AbstractCollectionInputGuardBuilder<IN, C_OUT, OUT, B>, C_OUT extends Collection<OUT>, OUT> B filterAndProcessEach(
		Predicate<T> elementFilter, InputGuard<T, OUT> elementGuard, Collector<OUT, ?, C_OUT> collector, Function<InputGuard<IN, C_OUT>, B> builderFunction
	) {
		InputGuard<C, C_OUT> forEachGuard = new CollectionIterationGuard<>(elementFilter, elementGuard, collector);
		return builderFunction.apply(build().andThen(forEachGuard));
	}

}
