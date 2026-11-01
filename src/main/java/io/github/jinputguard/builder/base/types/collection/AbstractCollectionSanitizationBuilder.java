package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.builder.base.AbstractSanitizationBuilder;
import io.github.jinputguard.guard.CollectionIterationGuard;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;

public abstract class AbstractCollectionSanitizationBuilder<IN, C extends Collection<T>, T, B extends AbstractCollectionInputGuardBuilder<IN, C, T, B>, SELF extends AbstractCollectionSanitizationBuilder<IN, C, T, B, SELF>>
	extends AbstractSanitizationBuilder<IN, C, B, SELF> {

	protected AbstractCollectionSanitizationBuilder(B builder) {
		super(builder);
	}

//	/**
//	 * Filter the input collection using the given predicate, and process each element using the given guard.
//	 * The result is collected into a new collection.
//	 * 
//	 * @param elementFilter		Predicate to filter elements of the input list
//	 * @param elementGuard		Guard to process each element of the input list
//	 * 
//	 * @return a new builder for the resulting list
//	 */
//	public abstract <B_OUT extends AbstractCollectionInputGuardBuilder<IN, C_OUT, OUT, B_OUT>, C_OUT extends Collection<OUT>, OUT> B_OUT filter(Predicate<T> elementFilter);
//
//	/**
//	 * Process each element of the input collection using the given guard.
//	 * The result is collected into a new list.
//	 * 
//	 * @param elementGuard		Guard to process each element of the input list
//	 * 
//	 * @return a new builder for the resulting list
//	 */
//	public abstract <B_OUT extends AbstractCollectionInputGuardBuilder<IN, C_OUT, OUT, B_OUT>, C_OUT extends Collection<OUT>, OUT> B_OUT filter(
//		Predicate<T> elementFilter, Collector<OUT, ?, C_OUT> collector
//	);

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
	public <B_OUT extends AbstractCollectionInputGuardBuilder<IN, C_OUT, OUT, B_OUT>, C_OUT extends Collection<OUT>, OUT> B_OUT filterAndProcessEach(
		Predicate<T> elementFilter, InputGuard<T, OUT> elementGuard, Collector<OUT, ?, C_OUT> collector, Function<InputGuard<IN, C_OUT>, B_OUT> builderFunction
	) {
		InputGuard<C, C_OUT> forEachGuard = new CollectionIterationGuard<>(elementFilter, elementGuard, collector);
		return builderFunction.apply(builder.build().andThen(forEachGuard));
	}

}
