package io.github.jinputguard.builder.base.types;

import io.github.jinputguard.builder.base.AbstractValidationBuilder;
import io.github.jinputguard.result.ValidationError;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringValidationBuilder<IN> extends AbstractValidationBuilder<IN, String, StringInputGuardBuilder<IN>, StringValidationBuilder<IN>> {

	public StringValidationBuilder(StringInputGuardBuilder<IN> builder) {
		super(builder);
	}

	public StringValidationBuilder<IN> canBeParsedToInteger() {
		builder = builder.validate(
			value -> {
				try {
					@SuppressWarnings("unused")
					var i = Integer.parseInt(value);
					return null;
				} catch (NumberFormatException e) {
					return new ValidationError.StringMustBeParseableToInteger();
				}
			}
		);
		return cast();
	}

	/**
	 * Validates that the value is not empty.
	 * 
	 * @see String#isEmpty()
	 */
	public StringValidationBuilder<IN> isNotEmpty() {
		builder = builder.validate(
			value -> value.isEmpty()
				? new ValidationError.StringIsEmpty()
				: null
		);
		return cast();
	}

	/**
	 * Validates that the value's length is no more than given value.
	 * 
	 * @param maxLength	The maximum length, inclusive, cannot be negative
	 * 
	 * @throws IllegalArgumentException 	If maxLength is negative
	 * 
	 * @see String#length()
	 */
	public StringValidationBuilder<IN> isMaxLength(int maxLength) {
		if (maxLength < 0) {
			throw new IllegalArgumentException("maxLength cannot be negative");
		}
		builder = builder.validate(
			value -> value.length() > maxLength
				? new ValidationError.StringIsTooLong(value.length(), maxLength)
				: null
		);
		return cast();
	}

	/**
	 * Validates that the entire value matches the regex.
	 * 
	 * @param pattern	The regex to match, cannot be <code>null</code>
	 * 
	 * @see Matcher#matches()
	 * 
	 * @throws  PatternSyntaxException	If the expression's syntax is invalid
	 */
	public StringValidationBuilder<IN> matches(String regex) {
		Objects.requireNonNull(regex, "regex cannot be null");
		return matches(Pattern.compile(regex));
	}

	/**
	 * Validates that the entire value matches the pattern.
	 * 
	 * @param pattern	The pattern to match, cannot be <code>null</code>
	 * 
	 * @see Matcher#matches()
	 */
	public StringValidationBuilder<IN> matches(Pattern pattern) {
		Objects.requireNonNull(pattern, "pattern cannot be null");
		builder = builder.validate(
			value -> !pattern.matcher(value).matches()
				? new ValidationError.StringMustMatchPattern(pattern)
				: null
		);
		return cast();
	}

}
