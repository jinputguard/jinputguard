package io.github.jinputguard.builder.base.types.number;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.builder.base.AbstractInputGuardBuilder;

public abstract class AbstractNumberInputGuardBuilder<IN, T extends Number, SELF extends AbstractNumberInputGuardBuilder<IN, T, SELF>>
	extends AbstractInputGuardBuilder<IN, T, SELF> {

	protected AbstractNumberInputGuardBuilder(InputGuard<IN, T> previous) {
		super(previous);
	}

	public abstract <S extends AbstractNumberSanitizationBuilder<IN, T, SELF, S>> S sanitize();

	public abstract <V extends AbstractNumberValidationBuilder<IN, T, SELF, V>> V validateThat();

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
