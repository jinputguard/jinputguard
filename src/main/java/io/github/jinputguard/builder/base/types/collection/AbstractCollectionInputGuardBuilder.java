package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.builder.base.AbstractInputGuardBuilder;
import io.github.jinputguard.guard.CollectionIterationGuard;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collector;

public abstract class AbstractCollectionInputGuardBuilder<IN, C extends Collection<T>, T, SELF extends AbstractCollectionInputGuardBuilder<IN, C, T, SELF>>
	extends AbstractInputGuardBuilder<IN, C, SELF> {

	protected AbstractCollectionInputGuardBuilder(InputGuard<IN, C> previous) {
		super(previous);
	}

	protected abstract Collector<T, ?, C> unmodifiableCollector();

	public abstract <V extends AbstractCollectionValidationBuilder<IN, C, T, SELF, V>> V validateThat();

	// --------------------------------------------------------------------------------------------------------------------
	// SANITIZATION

	/**
	 * Filter the input collection using the given predicate
	 * The result is collected into a new collection.
	 * 
	 * @param elementFilter		Predicate to filter elements of the input collection
	 * 
	 * @return a new builder for the resulting collection
	 */
	public SELF filter(Predicate<T> elementFilter) {
		if (elementFilter == null) {
			throw new IllegalArgumentException("elementFilter must not be null");
		}
		return sanitizeOrNullFail(value -> value.stream().filter(elementFilter).collect(unmodifiableCollector()));
	}

	// --------------------------------------------------------------------------------------------------------------------
	// ITERATION

	/**
	 * Process each element of the input collection using the given guard.
	 * The result is collected into a new collection.
	 * 
	 * @param elementGuard		Guard to process each element of the input collection
	 * 
	 * @return a new builder for the resulting collection
	 */
	public SELF processEach(InputGuard<T, T> elementGuard) {
		return apply(new CollectionIterationGuard<>(elementGuard, unmodifiableCollector()));
	}

}
