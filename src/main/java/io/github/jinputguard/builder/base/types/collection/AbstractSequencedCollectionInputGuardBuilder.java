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

	public abstract <V extends AbstractSequencedCollectionValidationBuilder<IN, C, T, SELF, V>> V validateThat();

	/**
	 * Filter the input collection using the given predicate, and process each element using the given guard.
	 * The result is collected into a new collection using the given collector.
	 * 
	 * @param elementFilter		Predicate to filter elements of the input collection
	 * @param elementGuard		Guard to process each element of the input collection
	 * @param collector			Collector to collect the processed elements into a new collection
	 * @param builderFunction	Function to create a new builder for the resulting collection
	 * 
	 * @return a new builder for the resulting collection
	 */
	public <B extends AbstractSequencedCollectionInputGuardBuilder<IN, C_OUT, OUT, B>, C_OUT extends SequencedCollection<OUT>, OUT> B filterAndProcessEach(
		Predicate<T> elementFilter, InputGuard<T, OUT> elementGuard, Collector<OUT, ?, C_OUT> collector, Function<InputGuard<IN, C_OUT>, B> builderFunction
	) {
		InputGuard<C, C_OUT> forEachGuard = new SequencedCollectionIterationGuard<>(elementFilter, elementGuard, collector);
		return builderFunction.apply(build().andThen(forEachGuard));
	}

}
