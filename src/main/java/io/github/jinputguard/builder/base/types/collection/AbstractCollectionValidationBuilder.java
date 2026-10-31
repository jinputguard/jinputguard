package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.builder.base.AbstractValidationBuilder;
import io.github.jinputguard.failure.BaseValidationErrors;
import java.util.Collection;
import java.util.Collections;
import java.util.function.BiPredicate;

public class AbstractCollectionValidationBuilder<IN, C extends Collection<T>, T, B extends AbstractCollectionInputGuardBuilder<IN, C, T, B>, SELF extends AbstractCollectionValidationBuilder<IN, C, T, B, SELF>>
	extends AbstractValidationBuilder<IN, C, B, SELF> {

	protected AbstractCollectionValidationBuilder(B builder) {
		super(builder);
	}

	/**
	 * Validates that the collection is not empty.
	 * 
	 * @see String#isEmpty()
	 */
	public SELF isNotEmpty() {
		builder = notNullValueBuilder().validate(
			(value, path) -> value.isEmpty()
				? BaseValidationErrors.COLLECTION_MUST_NOT_BE_EMPTY.toFailure(path)
				: null
		);
		return cast();
	}

	/**
	 * Validates that the collection has the given size.
	 * 
	 * @param size the expected size
	 */
	public SELF hasSize(int size) {
		if (size < 0) {
			throw new IllegalArgumentException("size cannot be negative");
		}
		builder = notNullValueBuilder().validate(
			(value, path) -> value.size() == size
				? null
				: BaseValidationErrors.COLLECTION_SIZE_EXACT.toFailure(path, size)
		);
		return cast();
	}

	/**
	 * Validates that the collection has at least the given size.
	 * 
	 * @param size the maximum size
	 */
	public SELF hasMinSize(int size) {
		if (size < 0) {
			throw new IllegalArgumentException("size cannot be negative");
		}
		builder = notNullValueBuilder().validate(
			(value, path) -> value.size() >= size
				? null
				: BaseValidationErrors.COLLECTION_SIZE_MIN.toFailure(path, size)
		);
		return cast();
	}

	/**
	 * Validates that the collection has at most the given size.
	 * 
	 * @param size the maximum size
	 */
	public SELF hasMaxSize(int size) {
		if (size < 0) {
			throw new IllegalArgumentException("size cannot be negative");
		}
		builder = notNullValueBuilder().validate(
			(value, path) -> value.size() <= size
				? null
				: BaseValidationErrors.COLLECTION_SIZE_MAX.toFailure(path, size)
		);
		return cast();
	}

	/**
	 * Validates that the collection has a size between the given minimum and maximum size (inclusive).
	 * 
	 * @param minSize the minimum size
	 * @param maxSize the maximum size
	 */
	public SELF hasSizeBetween(int minSize, int maxSize) {
		if (minSize < 0 || maxSize < 0) {
			throw new IllegalArgumentException("minSize and maxSize cannot be negative");
		}
		if (minSize > maxSize) {
			throw new IllegalArgumentException("Min size " + minSize + " is greater than max size " + maxSize);
		}
		builder = notNullValueBuilder().validate(
			(value, path) -> value.size() >= minSize && value.size() <= maxSize
				? null
				: BaseValidationErrors.COLLECTION_SIZE_BETWEEN.toFailure(path, minSize, maxSize)
		);
		return cast();
	}

	/**
	 * Validates that the collection does not contain duplicate elements, using their {@link Object#equals(Object)} method.
	 * 
	 * @see Object#equals(Object)
	 */
	public SELF hasNoDuplicateElements() {

		builder = notNullValueBuilder().validate(
			(value, path) -> value.stream().anyMatch(i -> Collections.frequency(value, i) > 1)
				? BaseValidationErrors.COLLECTION_MUST_NOT_CONTAIN_DUPLICATE_ELEMENTS.toFailure(path)
				: null
		);
		return cast();
//		return hasNoDuplicateElements(Object::equals);
	}

	/**
	 * Validates that the collection does not contain duplicate elements according to the given matching function.
	 * 
	 * @param duplicateMatcher the matching function to use for comparing elements
	 */
	public SELF hasNoDuplicateElements(BiPredicate<T, T> duplicateMatcher) {
		if (duplicateMatcher == null) {
			throw new IllegalArgumentException("duplicateMatcher cannot be null");
		}
		builder = notNullValueBuilder().validate(
			(value, path) -> value.stream().anyMatch(e1 -> value.stream().anyMatch(e2 -> e1 != e2 && duplicateMatcher.test(e1, e2)))
				? BaseValidationErrors.COLLECTION_MUST_NOT_CONTAIN_DUPLICATE_ELEMENTS.toFailure(path)
				: null
		);
		return cast();
	}

}
