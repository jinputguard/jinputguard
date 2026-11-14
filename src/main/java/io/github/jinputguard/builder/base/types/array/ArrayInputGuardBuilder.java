package io.github.jinputguard.builder.base.types.array;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.builder.base.AbstractInputGuardBuilder;
import io.github.jinputguard.failure.BaseValidationErrors;
import io.github.jinputguard.guard.ArrayIterationGuard;
import io.github.jinputguard.guard.InputGuards;
import io.github.jinputguard.guard.NullStrategyGuard.NullStrategy;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayInputGuardBuilder<IN, T> extends AbstractInputGuardBuilder<IN, T[], ArrayInputGuardBuilder<IN, T>> {

	protected final Class<T> elementClass;

	protected ArrayInputGuardBuilder(InputGuard<IN, T[]> previous, Class<T> elementClass) {
		super(previous);
		this.elementClass = Objects.requireNonNull(elementClass, "elementClass must not be null");
	}

	@Override
	protected ArrayInputGuardBuilder<IN, T> newInstance(InputGuard<IN, T[]> guard) {
		return new ArrayInputGuardBuilder<>(guard, elementClass);
	}

	public static <T> ArrayInputGuardBuilder<T[], T> newInstance(Class<T> elementClass) {
		return new ArrayInputGuardBuilder<>(InputGuards.noOpGuard(), elementClass);
	}

	// --------------------------------------------------------------------------------------------------------------------
	// SANITIZATION

	/**
	 * Filter the input array using the given predicate
	 * The result is collected into a new array.
	 * 
	 * @param elementFilter		Predicate to filter elements of the input array
	 * 
	 * @return a new builder for the resulting array
	 */
	@SuppressWarnings("unchecked")
	public ArrayInputGuardBuilder<IN, T> filter(Predicate<T> elementFilter) {
		if (elementFilter == null) {
			throw new IllegalArgumentException("elementFilter must not be null");
		}
		return sanitizeOrNullFail(value -> Stream.of(value).filter(elementFilter).toArray(newLen -> (T[]) Array.newInstance(elementClass, newLen)));
	}

	// --------------------------------------------------------------------------------------------------------------------
	// VALIDATION

	/**
	 * Validates that the array is not empty.
	 * 
	 */
	public ArrayInputGuardBuilder<IN, T> requireNotEmpty() {
		return requireNotNull().validate(
			(value, path) -> value.length == 0
				? BaseValidationErrors.ARRAY_MUST_NOT_BE_EMPTY.toFailure(path)
				: null
		);
	}

	/**
	 * Validates that the array has the given length.
	 * 
	 * @param length the expected length
	 */
	public ArrayInputGuardBuilder<IN, T> requireLength(int length) {
		if (length < 0) {
			throw new IllegalArgumentException("length cannot be negative");
		}
		return requireNotNull().validate(
			(value, path) -> value.length == length
				? null
				: BaseValidationErrors.ARRAY_LENGTH_EXACT.toFailure(path, length)
		);
	}

	/**
	 * Validates that the array has at least the given length.
	 * 
	 * @param length the maximum length
	 */
	public ArrayInputGuardBuilder<IN, T> requireMinLength(int length) {
		if (length < 0) {
			throw new IllegalArgumentException("length cannot be negative");
		}
		return requireNotNull().validate(
			(value, path) -> value.length >= length
				? null
				: BaseValidationErrors.ARRAY_LENGTH_MIN.toFailure(path, length)
		);
	}

	/**
	 * Validates that the array has at most the given length.
	 * 
	 * @param length the maximum length
	 */
	public ArrayInputGuardBuilder<IN, T> requireMaxLength(int length) {
		if (length < 0) {
			throw new IllegalArgumentException("length cannot be negative");
		}
		return requireNotNull().validate(
			(value, path) -> value.length <= length
				? null
				: BaseValidationErrors.ARRAY_LENGTH_MAX.toFailure(path, length)
		);
	}

	/**
	 * Validates that the array has a length between the given minimum and maximum length (inclusive).
	 * 
	 * @param minLength the minimum length
	 * @param maxLength the maximum length
	 */
	public ArrayInputGuardBuilder<IN, T> requireLengthBetween(int minLength, int maxLength) {
		if (minLength < 0 || maxLength < 0) {
			throw new IllegalArgumentException("minLength and maxLength cannot be negative");
		}
		if (minLength > maxLength) {
			throw new IllegalArgumentException("Min length " + minLength + " is greater than max length " + maxLength);
		}
		return requireNotNull().validate(
			(value, path) -> value.length >= minLength && value.length <= maxLength
				? null
				: BaseValidationErrors.ARRAY_LENGTH_BETWEEN.toFailure(path, minLength, maxLength)
		);
	}

	/**
	 * Validates that the array does not contain duplicate elements, using their {@link Object#equals(Object)} method.
	 * 
	 */
	public ArrayInputGuardBuilder<IN, T> requireNoDuplicateElements() {
		return requireNotNull().validate(
			(value, path) -> {
				return Stream.of(value)
					.collect(Collectors.groupingBy(e -> e, Collectors.counting()))
					.entrySet().stream()
					.anyMatch(entry -> entry.getValue() > 1)
						? BaseValidationErrors.ARRAY_MUST_NOT_CONTAIN_DUPLICATE_ELEMENTS.toFailure(path)
						: null;
			}
		);
	}

	/**
	 * Validates that the array does not contain duplicate elements according to the given matching function.
	 * 
	 * @param duplicateMatcher the matching function to use for comparing elements
	 */
	public ArrayInputGuardBuilder<IN, T> requireNoDuplicateElements(BiPredicate<T, T> duplicateMatcher) {
		if (duplicateMatcher == null) {
			throw new IllegalArgumentException("duplicateMatcher cannot be null");
		}
		return requireNotNull().validate(
			(value, path) -> Stream.of(value).anyMatch(e1 -> Stream.of(value).anyMatch(e2 -> e1 != e2 && duplicateMatcher.test(e1, e2)))
				? BaseValidationErrors.ARRAY_MUST_NOT_CONTAIN_DUPLICATE_ELEMENTS.toFailure(path)
				: null
		);
	}

	/**
	 * Validates that the array does not contain null elements.
	 */
	public ArrayInputGuardBuilder<IN, T> requireNoNullElements() {
		return processEach(InputGuards.nullStrategyGuard(NullStrategy.fail()));
	}

	// --------------------------------------------------------------------------------------------------------------------
	// ELEMENT ITERATION

	/**
	 * Process each element of the input array using the given guard.
	 * The result is collected into a new array.
	 * 
	 * @param elementGuard		Guard to process each element of the input array
	 * 
	 * @return a new builder for the resulting array
	 */
	public ArrayInputGuardBuilder<IN, T> processEach(InputGuard<T, T> elementGuard) {
		return apply(new ArrayIterationGuard<>(elementGuard, elementClass));
	}

	// --------------------------------------------------------------------------------------------------------------------
	// MAPPING

	/**
	 * Creates a copy of the input array.
	 * 
	 * @return a new builder for the copied array
	 */
	public ArrayInputGuardBuilder<IN, T> makeCopy() {
		return sanitize(array -> Arrays.copyOf(array, array.length));
	}

}
