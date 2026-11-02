package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.builder.base.AbstractSanitizationBuilder;
import java.util.Collection;
import java.util.function.Predicate;

public abstract class AbstractCollectionSanitizationBuilder<IN, C extends Collection<T>, T, B extends AbstractCollectionInputGuardBuilder<IN, C, T, B>, SELF extends AbstractCollectionSanitizationBuilder<IN, C, T, B, SELF>>
	extends AbstractSanitizationBuilder<IN, C, B, SELF> {

	protected AbstractCollectionSanitizationBuilder(B builder) {
		super(builder);
	}

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
		builder = builder.sanitize(value -> value.stream().filter(elementFilter).collect(builder.unmodifiableCollector()));
		return cast();
	}

}
