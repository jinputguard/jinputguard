package io.github.jinputguard.builder.base.types;

import io.github.jinputguard.builder.base.AbstractSanitizationBuilder;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringSanitizationBuilder<IN> extends AbstractSanitizationBuilder<IN, String, StringInputGuardBuilder<IN>, StringSanitizationBuilder<IN>> {

	public StringSanitizationBuilder(StringInputGuardBuilder<IN> builder) {
		super(builder);
	}

	/**
	 * Apply {@link String#strip()}
	 */
	public StringSanitizationBuilder<IN> strip() {
		builder = builder.sanitize(String::strip);
		return cast();
	}

	/**
	 * Apply {@link String#toUpperCase()}
	 */
	public StringSanitizationBuilder<IN> toUpperCase() {
		builder = builder.sanitize(String::toUpperCase);
		return cast();
	}

	/**
	 * Apply {@link String#toLowerCase()}
	 */
	public StringSanitizationBuilder<IN> toLowerCase() {
		builder = builder.sanitize(String::toLowerCase);
		return cast();
	}

	/**
	 * Add the given prefix if the value does not already starts with.
	 * 
	 * @param prefix The prefix, cannot be <code>null</code>
	 * 
	 * @see String#startsWith(String)
	 */
	public StringSanitizationBuilder<IN> prefix(String prefix) {
		Objects.requireNonNull(prefix, "prefix cannot be null");
		builder = builder.sanitize(value -> value.startsWith(prefix) ? value : prefix + value);
		return cast();
	}

	/**
	 * Add the given suffix if the value does not already ends with.
	 * 
	 * @param suffix The suffix, cannot be <code>null</code>
	 * 
	 * @see String#endsWith(String)
	 */
	public StringSanitizationBuilder<IN> suffix(String suffix) {
		Objects.requireNonNull(suffix, "suffix cannot be null");
		builder = builder.sanitize(value -> value.endsWith(suffix) ? value : value + suffix);
		return cast();
	}

	/**
	 * Apply similar behavior than {@link String#replace(char, char)}
	 * 
	 * @param oldChar	The old character
	 * @param newChar	The new character
	 */
	public StringSanitizationBuilder<IN> replace(char oldChar, char newChar) {
		builder = builder.sanitize(value -> value.replace(oldChar, newChar));
		return cast();
	}

	/**
	 * Apply similar behavior than {@link String#replace(CharSequence, CharSequence)}
	 * 
	 * @param  target 		The sequence of char values to be replaced, cannot be <code>null</code>
	 * @param  replacement 	The replacement sequence of char values, cannot be <code>null</code>
	 */
	public StringSanitizationBuilder<IN> replace(CharSequence target, CharSequence replacement) {
		Objects.requireNonNull(target, "target cannot be null");
		Objects.requireNonNull(replacement, "replacement cannot be null");
		builder = builder.sanitize(value -> value.replace(target, replacement));
		return cast();
	}

	/**
	 * Apply similar behavior than {@link String#replaceAll(String, String)}
	 * 
	 * @param   regex		The regular expression to which this string is to be matched, cannot be <code>null</code>
	 * @param   replacement	The string to be substituted for each match, cannot be <code>null</code>
	 * 
	 * @throws  PatternSyntaxException	If the expression's syntax is invalid
	 */
	public StringSanitizationBuilder<IN> replaceAll(String regex, String replacement) {
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
	public StringSanitizationBuilder<IN> replaceAll(Pattern pattern, String replacement) {
		Objects.requireNonNull(pattern, "pattern cannot be null");
		Objects.requireNonNull(replacement, "replacement cannot be null");
		builder = builder.sanitize(value -> pattern.matcher(value).replaceAll(replacement));
		return cast();
	}

	/**
	 * Apply similar behavior than {@link String#replaceFirst(String, String)}
	 * 
	 * @param   regex		The regular expression to which this string is to be matched, cannot be <code>null</code>
	 * @param   replacement	The string to be substituted for the first match, cannot be <code>null</code>
	 * 
	 * @throws  PatternSyntaxException	If the expression's syntax is invalid
	 */
	public StringSanitizationBuilder<IN> replaceFirst(String regex, String replacement) {
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
	public StringSanitizationBuilder<IN> replaceFirst(Pattern pattern, String replacement) {
		Objects.requireNonNull(pattern, "pattern cannot be null");
		Objects.requireNonNull(replacement, "replacement cannot be null");
		builder = builder.sanitize(value -> pattern.matcher(value).replaceFirst(replacement));
		return cast();
	}

}
