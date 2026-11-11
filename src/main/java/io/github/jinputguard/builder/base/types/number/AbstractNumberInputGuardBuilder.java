package io.github.jinputguard.builder.base.types.number;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.builder.base.AbstractInputGuardBuilder;
import io.github.jinputguard.failure.BaseValidationErrors;
import jakarta.annotation.Nonnull;
import java.util.Objects;

public abstract class AbstractNumberInputGuardBuilder<IN, T extends Number, SELF extends AbstractNumberInputGuardBuilder<IN, T, SELF>>
	extends AbstractInputGuardBuilder<IN, T, SELF> {

	private final @Nonnull NumberTester<T> numberTester;

	protected AbstractNumberInputGuardBuilder(InputGuard<IN, T> previous, NumberTester<T> numberTester) {
		super(previous);
		this.numberTester = Objects.requireNonNull(numberTester, "numberTester cannot be null");
	}

	// --------------------------------------------------------------------------------------------------------------------
	// SANITIZATION

	public SELF clamp(T minInclusive, T maxInclusive) {
		Objects.requireNonNull(minInclusive, "min cannot be null");
		Objects.requireNonNull(maxInclusive, "max cannot be null");
		if (numberTester.isGreaterThan(minInclusive, maxInclusive)) {
			throw new IllegalArgumentException("Min value " + minInclusive + " is greater than max value " + maxInclusive);
		}
		return sanitizeOrNullFail(value -> clamp(minInclusive, maxInclusive, value));
	}

	public SELF clampMin(T minInclusive) {
		Objects.requireNonNull(minInclusive, "min cannot be null");
		return sanitizeOrNullFail(value -> clamp(minInclusive, numberTester.getMax(), value));
	}

	public SELF clampMax(T maxInclusive) {
		Objects.requireNonNull(maxInclusive, "max cannot be null");
		return sanitizeOrNullFail(value -> clamp(numberTester.getMin(), maxInclusive, value));
	}

	private T clamp(T minInclusive, T maxInclusive, T value) {
		if (numberTester.isLowerThan(value, minInclusive)) {
			return minInclusive;
		} else if (numberTester.isGreaterThan(value, maxInclusive)) {
			return maxInclusive;
		}
		return value;
	}

	// --------------------------------------------------------------------------------------------------------------------
	// VALIDATION

	/**
	 * Validate that {@code input > ref}.
	 * If not, generates {@link NumberMustBeGreaterThan}.
	 * 
	 * @param ref	the comparison reference, cannot be <code>null</code>
	 */
	public SELF requireGreaterThan(T ref) {
		return requireNotNull().validate(
			(value, path) -> numberTester.isGreaterThan(value, ref)
				? null
				: BaseValidationErrors.NUMBER_MUST_BE_GREATER_THAN.toFailure(path, ref)
		);
	}

	/**
	 * Validate that {@code input >= ref}.
	 * If not, generates {@link NumberMustBeGreaterOrEqualTo}.
	 * 
	 * @param ref	the comparison reference, cannot be <code>null</code>
	 */
	public SELF requireGreaterOrEqualTo(T ref) {
		return requireNotNull().validate(
			(value, path) -> numberTester.isGreaterOrEqualTo(value, ref)
				? null
				: BaseValidationErrors.NUMBER_MUST_BE_GREATER_OR_EQUAL_TO.toFailure(path, ref)
		);
	}

	/**
	 * Validate that {@code input < ref}.
	 * If not, generates {@link NumberMustBeLowerThan}.
	 * 
	 * @param ref	the comparison reference, cannot be <code>null</code>
	 */
	public SELF requireLowerThan(T ref) {
		return requireNotNull().validate(
			(value, path) -> numberTester.isLowerThan(value, ref)
				? null
				: BaseValidationErrors.NUMBER_MUST_BE_LOWER_THAN.toFailure(path, ref)
		);
	}

	/**
	 * Validate that {@code input <= ref}.
	 * If not, generates {@link NumberMustBeLowerOrEqualTo}.
	 * 
	 * @param ref	the comparison reference, cannot be <code>null</code>
	 */
	public SELF requireLowerOrEqualTo(T ref) {
		return requireNotNull().validate(
			(value, path) -> numberTester.isLowerOrEqualTo(value, ref)
				? null
				: BaseValidationErrors.NUMBER_MUST_BE_LOWER_OR_EQUAL_TO.toFailure(path, ref)
		);
	}

	/**
	 * Validate that {@code input > 0}.
	 * If not, generates {@link NumberMustBeGreaterThan}.
	 */
	public SELF requirePositive() {
		return requireGreaterThan(numberTester.getZero());
	}

	/**
	 * Validate that {@code input >= 0}.
	 * If not, generates {@link NumberMustBeGreaterOrEqualTo}.
	 */
	public SELF requirePositiveOrNul() {
		return requireGreaterOrEqualTo(numberTester.getZero());
	}

	/**
	 * Validate that {@code input == 0}.
	 * If not, generates {@link ObjectMustBeEqualTo}.
	 */
	public SELF requireEqualToZero() {
		return requireEqualTo(numberTester.getZero());
	}

	/**
	 * Validate that {@code input < 0}.
	 * If not, generates {@link NumberMustBeLowerThan}.
	 */
	public SELF requireNegative() {
		return requireLowerThan(numberTester.getZero());
	}

	/**
	 * Validate that {@code input <= 0}.
	 * If not, generates {@link NumberMustBeLowerOrEqualTo}.
	 */
	public SELF requireNegativeOrNul() {
		return requireLowerOrEqualTo(numberTester.getZero());
	}

	/**
	 * Validate that {@code minInclusive <= input <= maxInclusive}.
	 * If not, generates {@link NumberMustBeBetween}.
	 * 
	 * @param minInclusive	lower bound, cannot be <code>null</code>
	 * @param maxInclusive	upper bound, cannot be <code>null</code>
	 * 
	 * @throws IllegalArgumentException	if minInclusive > maxInclusive
	 */
	public SELF requireBetween(T minInclusive, T maxInclusive) {
		Objects.requireNonNull(minInclusive, "min cannot be null");
		Objects.requireNonNull(maxInclusive, "max cannot be null");
		if (numberTester.isGreaterThan(minInclusive, maxInclusive)) {
			throw new IllegalArgumentException("Min value " + minInclusive + " is greater than max value " + maxInclusive);
		}
		return requireNotNull().validate(
			(value, path) -> numberTester.isBetween(value, minInclusive, maxInclusive)
				? null
				: BaseValidationErrors.NUMBER_MUST_BE_BETWEEN.toFailure(path, minInclusive, maxInclusive)
		);
	}

	// --------------------------------------------------------------------------------------------------------------------
	// MAPPING

	/**
	 * Maps the input to an int value using {@link Number#intValue()}.
	 *
	 * @return A new IntegerInputGuardBuilder instance.
	 */
	public IntegerInputGuardBuilder<IN> mapToInt() {
		return map(Number::intValue, path -> path + " cannot be mapped to integer", IntegerInputGuardBuilder::new);
	}

	/**
	 * Maps the input to a long value using {@link Number#longValue()}.
	 *
	 * @return A new LongInputGuardBuilder instance.
	 */
	public LongInputGuardBuilder<IN> mapToLong() {
		return map(Number::longValue, path -> path + " cannot be mapped to long", LongInputGuardBuilder::new);
	}

	/**
	 * Maps the input to a float value using {@link Number#floatValue()}.
	 *
	 * @return A new FloatInputGuardBuilder instance.
	 */
	public FloatInputGuardBuilder<IN> mapToFloat() {
		return map(Number::floatValue, path -> path + " cannot be mapped to float", FloatInputGuardBuilder::new);
	}

	/**
	 * Maps the input to a double value using {@link Number#doubleValue()}.
	 *
	 * @return A new DoubleInputGuardBuilder instance.
	 */
	public DoubleInputGuardBuilder<IN> mapToDouble() {
		return map(Number::doubleValue, path -> path + " cannot be mapped to double", DoubleInputGuardBuilder::new);
	}

}
