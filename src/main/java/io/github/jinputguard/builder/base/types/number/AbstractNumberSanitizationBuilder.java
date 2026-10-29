package io.github.jinputguard.builder.base.types.number;

import io.github.jinputguard.InputGuardBuilder;
import io.github.jinputguard.builder.base.AbstractSanitizationBuilder;
import jakarta.annotation.Nonnull;
import java.util.Objects;

/**
 * 
 * 
 *
 * @param <IN>
 * @param <T>
 * @param <B>
 */
public abstract class AbstractNumberSanitizationBuilder<IN, T extends Number, B extends InputGuardBuilder<IN, T, B>, SELF extends AbstractNumberSanitizationBuilder<IN, T, B, SELF>>
	extends AbstractSanitizationBuilder<IN, T, B, SELF> {

	private final @Nonnull NumberTester<T> numberTester;

	protected AbstractNumberSanitizationBuilder(B builder, NumberTester<T> numberTester) {
		super(builder);
		this.numberTester = Objects.requireNonNull(numberTester, "numberTester cannot be null");
	}

	// --------------------------------------------------------------------------------------------------------------------

	public SELF clamp(T minInclusive, T maxInclusive) {
		Objects.requireNonNull(minInclusive, "min cannot be null");
		Objects.requireNonNull(maxInclusive, "max cannot be null");
		if (numberTester.isGreaterThan(minInclusive, maxInclusive)) {
			throw new IllegalArgumentException("Min value " + minInclusive + " is greater than max value " + maxInclusive);
		}
		builder = builder.sanitize(value -> clamp(minInclusive, maxInclusive, value));
		return cast();
	}

	public SELF clampMin(T minInclusive) {
		Objects.requireNonNull(minInclusive, "min cannot be null");
		builder = builder.sanitize(value -> clamp(minInclusive, numberTester.getMax(), value));
		return cast();
	}

	public SELF clampMax(T maxInclusive) {
		Objects.requireNonNull(maxInclusive, "max cannot be null");
		builder = builder.sanitize(value -> clamp(numberTester.getMin(), maxInclusive, value));
		return cast();
	}

	private T clamp(T minInclusive, T maxInclusive, T value) {
		System.out.println("--------------");
		System.out.println("minInclusive: " + minInclusive);
		System.out.println("maxInclusive: " + maxInclusive);
		System.out.println("value: " + value);
		if (numberTester.isLowerThan(value, minInclusive)) {
			System.out.println("value is lower than minInclusive, returning minInclusive: " + minInclusive);
			return minInclusive;
		} else if (numberTester.isGreaterThan(value, maxInclusive)) {
			System.out.println("value is greater than maxInclusive, returning maxInclusive: " + maxInclusive);
			return maxInclusive;
		}
		System.out.println("value is within range, returning value: " + value);
		return value;
	}

	public abstract SELF abs();

}
