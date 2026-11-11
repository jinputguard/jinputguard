package io.github.jinputguard.builder.base.types;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.builder.base.AbstractInputGuardBuilder;
import io.github.jinputguard.builder.base.types.number.DoubleInputGuardBuilder;
import io.github.jinputguard.builder.base.types.number.FloatInputGuardBuilder;
import io.github.jinputguard.builder.base.types.number.IntegerInputGuardBuilder;
import io.github.jinputguard.builder.base.types.number.LongInputGuardBuilder;
import io.github.jinputguard.guard.InputGuards;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

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

	public StringValidationBuilder<IN> validateThat() {
		return new StringValidationBuilder<>(this);
	}

	// --------------------------------------------------------------------------------------------------------------------
	// SANITIZATION

	/**
	 * Apply {@link String#strip()}
	 */
	public StringInputGuardBuilder<IN> strip() {
		return sanitizeOrNullFail(String::strip);
	}

	/**
	 * Apply {@link String#toUpperCase()}
	 */
	public StringInputGuardBuilder<IN> toUpperCase() {
		return sanitizeOrNullFail(String::toUpperCase);
	}

	/**
	 * Apply {@link String#toLowerCase()}
	 */
	public StringInputGuardBuilder<IN> toLowerCase() {
		return sanitizeOrNullFail(String::toLowerCase);
	}

	/**
	 * Add the given prefix if the value does not already starts with.
	 * 
	 * @param prefix The prefix, cannot be <code>null</code>
	 * 
	 * @see String#startsWith(String)
	 */
	public StringInputGuardBuilder<IN> prefix(String prefix) {
		Objects.requireNonNull(prefix, "prefix cannot be null");
		return sanitizeOrNullFail(value -> value.startsWith(prefix) ? value : prefix + value);
	}

	/**
	 * Add the given suffix if the value does not already ends with.
	 * 
	 * @param suffix The suffix, cannot be <code>null</code>
	 * 
	 * @see String#endsWith(String)
	 */
	public StringInputGuardBuilder<IN> suffix(String suffix) {
		Objects.requireNonNull(suffix, "suffix cannot be null");
		return sanitizeOrNullFail(value -> value.endsWith(suffix) ? value : value + suffix);
	}

	/**
	 * Apply similar behavior than {@link String#replace(char, char)}
	 * 
	 * @param oldChar	The old character
	 * @param newChar	The new character
	 */
	public StringInputGuardBuilder<IN> replace(char oldChar, char newChar) {
		return sanitizeOrNullFail(value -> value.replace(oldChar, newChar));
	}

	/**
	 * Apply similar behavior than {@link String#replace(CharSequence, CharSequence)}
	 * 
	 * @param  target 		The sequence of char values to be replaced, cannot be <code>null</code>
	 * @param  replacement 	The replacement sequence of char values, cannot be <code>null</code>
	 */
	public StringInputGuardBuilder<IN> replace(CharSequence target, CharSequence replacement) {
		Objects.requireNonNull(target, "target cannot be null");
		Objects.requireNonNull(replacement, "replacement cannot be null");
		return sanitizeOrNullFail(value -> value.replace(target, replacement));
	}

	/**
	 * Apply similar behavior than {@link String#replaceAll(String, String)}
	 * 
	 * @param   regex		The regular expression to which this string is to be matched, cannot be <code>null</code>
	 * @param   replacement	The string to be substituted for each match, cannot be <code>null</code>
	 * 
	 * @throws  PatternSyntaxException	If the expression's syntax is invalid
	 */
	public StringInputGuardBuilder<IN> replaceAll(String regex, String replacement) {
		Objects.requireNonNull(regex, "regex cannot be null");
		Objects.requireNonNull(replacement, "replacement cannot be null");
		return replaceAll(Pattern.compile(regex), replacement);
	}

	/**
	 * Apply similar behavior than {@link String#replaceAll(String, String)}
	 * 
	 * @param   pattern		The patternto which this string is to be matched, cannot be <code>null</code>
	 * @param   replacement	The string to be substituted for each match, cannot be <code>null</code>
	 */
	public StringInputGuardBuilder<IN> replaceAll(Pattern pattern, String replacement) {
		Objects.requireNonNull(pattern, "pattern cannot be null");
		Objects.requireNonNull(replacement, "replacement cannot be null");
		return sanitizeOrNullFail(value -> pattern.matcher(value).replaceAll(replacement));
	}

	/**
	 * Apply similar behavior than {@link String#replaceFirst(String, String)}
	 * 
	 * @param   regex		The regular expression to which this string is to be matched, cannot be <code>null</code>
	 * @param   replacement	The string to be substituted for the first match, cannot be <code>null</code>
	 * 
	 * @throws  PatternSyntaxException	If the expression's syntax is invalid
	 */
	public StringInputGuardBuilder<IN> replaceFirst(String regex, String replacement) {
		Objects.requireNonNull(regex, "regex cannot be null");
		Objects.requireNonNull(replacement, "replacement cannot be null");
		return replaceFirst(Pattern.compile(regex), replacement);
	}

	/**
	 * Apply similar behavior than {@link String#replaceFirst(String, String)}
	 * 
	 * @param   pattern		The pattern to which this string is to be matched, cannot be <code>null</code>
	 * @param   replacement	The string to be substituted for the first match, cannot be <code>null</code>
	 */
	public StringInputGuardBuilder<IN> replaceFirst(Pattern pattern, String replacement) {
		Objects.requireNonNull(pattern, "pattern cannot be null");
		Objects.requireNonNull(replacement, "replacement cannot be null");
		return sanitizeOrNullFail(value -> pattern.matcher(value).replaceFirst(replacement));
	}

	// --------------------------------------------------------------------------------------------------------------------
	// MAPPING

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
