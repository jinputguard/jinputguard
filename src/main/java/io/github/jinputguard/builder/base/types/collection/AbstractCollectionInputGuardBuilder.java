package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.builder.base.AbstractInputGuardBuilder;
import io.github.jinputguard.failure.BaseValidationErrors;
import io.github.jinputguard.guard.CollectionIterationGuard;
import io.github.jinputguard.guard.InputGuards;
import io.github.jinputguard.guard.NullStrategyGuard.NullStrategy;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public abstract class AbstractCollectionInputGuardBuilder<IN, C extends Collection<T>, T, SELF extends AbstractCollectionInputGuardBuilder<IN, C, T, SELF>>
	extends AbstractInputGuardBuilder<IN, C, SELF> {

	protected AbstractCollectionInputGuardBuilder(InputGuard<IN, C> previous) {
		super(previous);
	}

	protected abstract Collector<T, ?, C> unmodifiableCollector();

	protected abstract C makeUnmodifiableCopy(Collection<T> collection);

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
	// VALIDATION

	/**
	 * Validates that the collection is not empty.
	 * 
	 * @see String#isEmpty()
	 */
	public SELF requireNotEmpty() {
		return requireNotNull().validate(
			(value, path) -> value.isEmpty()
				? BaseValidationErrors.COLLECTION_MUST_NOT_BE_EMPTY.toFailure(path)
				: null
		);
	}

	/**
	 * Validates that the collection has the given size.
	 * 
	 * @param size the expected size
	 */
	public SELF requireSize(int size) {
		if (size < 0) {
			throw new IllegalArgumentException("size cannot be negative");
		}
		return requireNotNull().validate(
			(value, path) -> value.size() == size
				? null
				: BaseValidationErrors.COLLECTION_SIZE_EXACT.toFailure(path, size)
		);
	}

	/**
	 * Validates that the collection has at least the given size.
	 * 
	 * @param size the maximum size
	 */
	public SELF requireMinSize(int size) {
		if (size < 0) {
			throw new IllegalArgumentException("size cannot be negative");
		}
		return requireNotNull().validate(
			(value, path) -> value.size() >= size
				? null
				: BaseValidationErrors.COLLECTION_SIZE_MIN.toFailure(path, size)
		);
	}

	/**
	 * Validates that the collection has at most the given size.
	 * 
	 * @param size the maximum size
	 */
	public SELF requireMaxSize(int size) {
		if (size < 0) {
			throw new IllegalArgumentException("size cannot be negative");
		}
		return requireNotNull().validate(
			(value, path) -> value.size() <= size
				? null
				: BaseValidationErrors.COLLECTION_SIZE_MAX.toFailure(path, size)
		);
	}

	/**
	 * Validates that the collection has a size between the given minimum and maximum size (inclusive).
	 * 
	 * @param minSize the minimum size
	 * @param maxSize the maximum size
	 */
	public SELF requireSizeBetween(int minSize, int maxSize) {
		if (minSize < 0 || maxSize < 0) {
			throw new IllegalArgumentException("minSize and maxSize cannot be negative");
		}
		if (minSize > maxSize) {
			throw new IllegalArgumentException("Min size " + minSize + " is greater than max size " + maxSize);
		}
		return requireNotNull().validate(
			(value, path) -> value.size() >= minSize && value.size() <= maxSize
				? null
				: BaseValidationErrors.COLLECTION_SIZE_BETWEEN.toFailure(path, minSize, maxSize)
		);
	}

	/**
	 * Validates that the collection does not contain duplicate elements, using their {@link Object#equals(Object)} method.
	 * 
	 * @see Collections#frequency(Collection, Object)
	 */
	public SELF requireNoDuplicateElements() {
		return requireNotNull().validate(
			(value, path) -> {
				if (value instanceof Set<?>) {
					return null; // Sets cannot contain duplicates
				}
				return value.stream()
					.collect(Collectors.groupingBy(e -> e, Collectors.counting()))
					.entrySet().stream()
					.anyMatch(entry -> entry.getValue() > 1)
						? BaseValidationErrors.COLLECTION_MUST_NOT_CONTAIN_DUPLICATE_ELEMENTS.toFailure(path)
						: null;
			}
		);
	}

	/**
	 * Validates that the collection does not contain duplicate elements according to the given matching function.
	 * 
	 * @param duplicateMatcher the matching function to use for comparing elements
	 */
	public SELF requireNoDuplicateElements(BiPredicate<T, T> duplicateMatcher) {
		if (duplicateMatcher == null) {
			throw new IllegalArgumentException("duplicateMatcher cannot be null");
		}
		return requireNotNull().validate(
			(value, path) -> value.stream().anyMatch(e1 -> value.stream().anyMatch(e2 -> e1 != e2 && duplicateMatcher.test(e1, e2)))
				? BaseValidationErrors.COLLECTION_MUST_NOT_CONTAIN_DUPLICATE_ELEMENTS.toFailure(path)
				: null
		);
	}

	/**
	 * Validates that the collection does not contain null elements.
	 */
	public SELF requireNoNullElements() {
		return processEach(InputGuards.nullStrategyGuard(NullStrategy.fail()));
	}

	// --------------------------------------------------------------------------------------------------------------------
	// ELEMENT ITERATION

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

	// --------------------------------------------------------------------------------------------------------------------
	// MAPPING

	/**
	 * Maps the input collection to an unmodifiable collection.
	 */
	public SELF makeUnmodifiableCopy() {
		return sanitize(coll -> makeUnmodifiableCopy(coll));
	}

}
