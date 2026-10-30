package io.github.jinputguard.builder.base.types;

import io.github.jinputguard.builder.base.AbstractValidationBuilder;
import io.github.jinputguard.failure.BaseValidationErrors;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringValidationBuilder<IN> extends AbstractValidationBuilder<IN, String, StringInputGuardBuilder<IN>, StringValidationBuilder<IN>> {

	public StringValidationBuilder(StringInputGuardBuilder<IN> builder) {
		super(builder);
	}

	/**
	 * Validates that the value is not empty.
	 * 
	 * @see String#isEmpty()
	 */
	public StringValidationBuilder<IN> isNotEmpty() {
		builder = notNullValueBuilder().validate(
			(value, path) -> value.isEmpty()
				? BaseValidationErrors.STRING_MUST_NOT_BE_EMPTY.toFailure(path)
				: null
		);
		return cast();
	}

	/**
	 * Validates that the value is not blank.
	 * 
	 * @see String#isBlank()
	 */
	public StringValidationBuilder<IN> isNotBlank() {
		builder = notNullValueBuilder().validate(
			(value, path) -> value.isBlank()
				? BaseValidationErrors.STRING_MUST_NOT_BE_BLANK.toFailure(path)
				: null
		);
		return cast();
	}

	/**
	 * Validates that the value's length is exactly given value.
	 * 
	 * @param length	The exact length, cannot be negative
	 * 
	 * @throws IllegalArgumentException 	If length is negative
	 * 
	 * @see String#length()
	 */
	public StringValidationBuilder<IN> hasLength(int length) {
		if (length < 0) {
			throw new IllegalArgumentException("length cannot be negative");
		}
		builder = notNullValueBuilder().validate(
			(value, path) -> value.length() == length
				? null
				: BaseValidationErrors.STRING_MUST_HAVE_EXACT_LENGTH.toFailure(path, length)
		);
		return cast();
	}

	/**
	 * Validates that the value's length is between given values.
	 * 
	 * @param minLength	The minimum length, inclusive, cannot be negative
	 * @param maxLength	The maximum length, inclusive, cannot be negative
	 * 
	 * @throws IllegalArgumentException 	If minLength or maxLength is negative
	 * 
	 * @see String#length()
	 */
	public StringValidationBuilder<IN> hasLengthBetween(int minLength, int maxLength) {
		if (minLength < 0 || maxLength < 0) {
			throw new IllegalArgumentException("minLength and maxLength cannot be negative");
		}
		if (minLength > maxLength) {
			throw new IllegalArgumentException("Min length " + minLength + " is greater than max length " + maxLength);
		}
		builder = notNullValueBuilder().validate(
			(value, path) -> value.length() >= minLength && value.length() <= maxLength
				? null
				: BaseValidationErrors.STRING_MUST_HAVE_LENGTH_BETWEEN.toFailure(path, minLength, maxLength)
		);
		return cast();
	}

	/**
	 * Validates that the value's length is at least given value.
	 * 
	 * @param minLength	The minimum length, inclusive, cannot be negative
	 * 
	 * @throws IllegalArgumentException 	If minLength is negative
	 * 
	 * @see String#length()
	 */
	public StringValidationBuilder<IN> hasMinLength(int minLength) {
		if (minLength < 0) {
			throw new IllegalArgumentException("minLength cannot be negative");
		}
		builder = notNullValueBuilder().validate(
			(value, path) -> value.length() < minLength
				? BaseValidationErrors.STRING_MUST_HAVE_MIN_LENGTH.toFailure(path, minLength)
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
	public StringValidationBuilder<IN> hasMaxLength(int maxLength) {
		if (maxLength < 0) {
			throw new IllegalArgumentException("maxLength cannot be negative");
		}
		builder = notNullValueBuilder().validate(
			(value, path) -> value.length() > maxLength
				? BaseValidationErrors.STRING_MUST_HAVE_MAX_LENGTH.toFailure(path, maxLength)
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
		builder = notNullValueBuilder().validate(
			(value, path) -> !pattern.matcher(value).matches()
				? BaseValidationErrors.STRING_MUST_MATCH_PATTERN.toFailure(path, pattern)
				: null
		);
		return cast();
	}

}
