package io.github.jinputguard.builder.base.types.number;

import io.github.jinputguard.InputGuardBuilder;
import io.github.jinputguard.builder.base.AbstractValidationBuilder;
import io.github.jinputguard.builder.base.types.ObjectValidationError.ObjectMustBeEqualTo;
import io.github.jinputguard.builder.base.types.number.NumberValidationError.NumberMustBeBetween;
import io.github.jinputguard.builder.base.types.number.NumberValidationError.NumberMustBeGreaterOrEqualTo;
import io.github.jinputguard.builder.base.types.number.NumberValidationError.NumberMustBeGreaterThan;
import io.github.jinputguard.builder.base.types.number.NumberValidationError.NumberMustBeLowerOrEqualTo;
import io.github.jinputguard.builder.base.types.number.NumberValidationError.NumberMustBeLowerThan;
import jakarta.annotation.Nonnull;
import java.util.Objects;

/**
 * 
 * 
 *
 * @param <IN>
 * @param <T>
 * @param <B>
 * @param <SELF>
 */
public abstract class AbstractNumberValidationBuilder<IN, T extends Number, B extends InputGuardBuilder<IN, T, B>, SELF extends AbstractNumberValidationBuilder<IN, T, B, SELF>>
	extends AbstractValidationBuilder<IN, T, B, SELF> {

	private final @Nonnull NumberTester<T> numberTester;

	protected AbstractNumberValidationBuilder(B builder, NumberTester<T> numberTester) {
		super(builder);
		this.numberTester = Objects.requireNonNull(numberTester, "numberTester cannot be null");
	}

	// --------------------------------------------------------------------------------------------------------------------

	/**
	 * Validate that {@code input > ref}.
	 * If not, generates {@link NumberMustBeGreaterThan}.
	 * 
	 * @param ref	the comparison reference, cannot be <code>null</code>
	 */
	public SELF isGreaterThan(T ref) {
		builder = builder.validate(
			value -> !numberTester.isGreaterThan(value, ref)
				? new NumberValidationError.NumberMustBeGreaterThan(value, ref)
				: null
		);
		return cast();
	}

	/**
	 * Validate that {@code input >= ref}.
	 * If not, generates {@link NumberMustBeGreaterOrEqualTo}.
	 * 
	 * @param ref	the comparison reference, cannot be <code>null</code>
	 */
	public SELF isGreaterOrEqualTo(T ref) {
		builder = builder.validate(
			value -> !numberTester.isGreaterOrEqualTo(value, ref)
				? new NumberValidationError.NumberMustBeGreaterOrEqualTo(value, ref)
				: null
		);
		return cast();
	}

	/**
	 * Validate that {@code input < ref}.
	 * If not, generates {@link NumberMustBeLowerThan}.
	 * 
	 * @param ref	the comparison reference, cannot be <code>null</code>
	 */
	public SELF isLowerThan(T ref) {
		builder = builder.validate(
			value -> !numberTester.isLowerThan(value, ref)
				? new NumberValidationError.NumberMustBeLowerThan(value, ref)
				: null
		);
		return cast();
	}

	/**
	 * Validate that {@code input <= ref}.
	 * If not, generates {@link NumberMustBeLowerOrEqualTo}.
	 * 
	 * @param ref	the comparison reference, cannot be <code>null</code>
	 */
	public SELF isLowerOrEqualTo(T ref) {
		builder = builder.validate(
			value -> !numberTester.isLowerOrEqualTo(value, ref)
				? new NumberValidationError.NumberMustBeLowerOrEqualTo(value, ref)
				: null
		);
		return cast();
	}

	/**
	 * Validate that {@code input > 0}.
	 * If not, generates {@link NumberMustBeGreaterThan}.
	 */
	public SELF isPositive() {
		return isGreaterThan(numberTester.getZero());
	}

	/**
	 * Validate that {@code input >= 0}.
	 * If not, generates {@link NumberMustBeGreaterOrEqualTo}.
	 */
	public SELF isPositiveOrNul() {
		return isGreaterOrEqualTo(numberTester.getZero());
	}

	/**
	 * Validate that {@code input == 0}.
	 * If not, generates {@link ObjectMustBeEqualTo}.
	 */
	public SELF isZero() {
		return isEqualTo(numberTester.getZero());
	}

	/**
	 * Validate that {@code input < 0}.
	 * If not, generates {@link NumberMustBeLowerThan}.
	 */
	public SELF isNegative() {
		return isLowerThan(numberTester.getZero());
	}

	/**
	 * Validate that {@code input <= 0}.
	 * If not, generates {@link NumberMustBeLowerOrEqualTo}.
	 */
	public SELF isNegativeOrNul() {
		return isLowerOrEqualTo(numberTester.getZero());
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
	public SELF isBetween(T minInclusive, T maxInclusive) {
		Objects.requireNonNull(minInclusive, "min cannot be null");
		Objects.requireNonNull(maxInclusive, "max cannot be null");
		if (numberTester.isGreaterThan(minInclusive, maxInclusive)) {
			throw new IllegalArgumentException("Min value " + minInclusive + " is greater than max value " + maxInclusive);
		}
		builder = builder.validate(
			value -> !numberTester.isBetween(value, minInclusive, maxInclusive)
				? new NumberValidationError.NumberMustBeBetween(value, minInclusive, maxInclusive)
				: null
		);
		return cast();
	}

}
