package io.github.jinputguard.builder.base.types;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.builder.base.AbstractInputGuardBuilder;
import io.github.jinputguard.builder.base.types.number.DoubleInputGuardBuilder;
import io.github.jinputguard.builder.base.types.number.FloatInputGuardBuilder;
import io.github.jinputguard.builder.base.types.number.IntegerInputGuardBuilder;
import io.github.jinputguard.builder.base.types.number.LongInputGuardBuilder;
import io.github.jinputguard.guard.InputGuards;
import java.util.UUID;

public class StringInputGuardBuilder<IN> extends AbstractInputGuardBuilder<IN, String, StringInputGuardBuilder<IN>> {

	public StringInputGuardBuilder(InputGuard<IN, String> previous) {
		super(previous);
	}

	@Override
	protected StringInputGuardBuilder<IN> newInstance(InputGuard<IN, String> guard) {
		return new StringInputGuardBuilder<>(guard);
	}

	public static StringInputGuardBuilder<String> newInstance() {
		return new StringInputGuardBuilder<>(InputGuards.noOpGuard());
	}

	public StringSanitizationBuilder<IN> sanitize() {
		return new StringSanitizationBuilder<>(this);
	}

	public StringValidationBuilder<IN> validateThat() {
		return new StringValidationBuilder<>(this);
	}

	/**
	 * Parse the input to an int value using {@link Integer#parseInt(String)}.
	 *
	 * @return A new IntegerInputGuardBuilder instance.
	 */
	public IntegerInputGuardBuilder<IN> parseToInt() {
		return map(Integer::parseInt, path -> path + " cannot be parsed to integer", IntegerInputGuardBuilder::new);
	}

	/**
	 * Parse the input to a long value using {@link Long#parseLong(String)}.
	 *
	 * @return A new LongInputGuardBuilder instance.
	 */
	public LongInputGuardBuilder<IN> parseToLong() {
		return map(Long::parseLong, path -> path + " cannot be parsed to long", LongInputGuardBuilder::new);
	}

	/**
	 * Parse the input to a float value using {@link Float#parseFloat(String)}.
	 *
	 * @return A new FloatInputGuardBuilder instance.
	 */
	public FloatInputGuardBuilder<IN> parseToFloat() {
		return map(Float::parseFloat, path -> path + " cannot be parsed to float", FloatInputGuardBuilder::new);
	}

	/**
	 * Parse the input to a double value using {@link Double#parseDouble(String)}.
	 *
	 * @return A new DoubleInputGuardBuilder instance.
	 */
	public DoubleInputGuardBuilder<IN> parseToDouble() {
		return map(Double::parseDouble, path -> path + " cannot be parsed to double", DoubleInputGuardBuilder::new);
	}

	/**
	 * Parse the input to a UUID value using {@link UUID#fromString(String)}.
	 *
	 * @return A new UUIDInputGuardBuilder instance.
	 */
	public UUIDInputGuardBuilder<IN> parseToUUID() {
		return map(UUID::fromString, path -> path + " cannot be parsed to UUID", UUIDInputGuardBuilder::new);
	}

}
