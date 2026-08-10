package io.github.jinputguard.failure;

import io.github.jinputguard.GuardFailure;
import java.util.stream.Stream;

public enum BaseValidationErrors {

	// --------------------------------------------------------------------------
	// OBJECT

	OBJECT_MUST_NOT_BE_NULL ("%s must not be null"),

	OBJECT_MUST_BE_NULL ("%s must be null"),

	OBJECT_MUST_BE_INSTANCE_OF ("%s must be an instance of %s"),

	OBJECT_MUST_BE_EQUAL_TO ("%s must be equal to %s"),

	// --------------------------------------------------------------------------
	// STRING

	STRING_MUST_NOT_BE_EMPTY ("%s must not be empty"),

	STRING_IS_TOO_LONG ("%s is too long: %s chars max"),

	STRING_MUST_MATCH_PATTERN ("%s must match pattern %s"),

	// --------------------------------------------------------------------------
	// NUMBER

	NUMBER_MUST_BE_GREATER_THAN ("%s must be greater than %s"),

	NUMBER_MUST_BE_GREATER_OR_EQUAL_TO ("%s must be greater or equal to %s"),

	NUMBER_MUST_BE_LOWER_THAN ("%s must be lower than %s"),

	NUMBER_MUST_BE_LOWER_OR_EQUAL_TO ("%s must be lower or equal to %s"),

	NUMBER_MUST_BE_BETWEEN ("%s must be between %s and %s"),

	;

	private final String messageTemplate;

	private BaseValidationErrors(String messageTemplate) {
		this.messageTemplate = messageTemplate;
	}

	public GuardFailure toFailure(String path, Object... args) {
		return toFailure(path, null, args);
	}

	public GuardFailure toFailure(String path, Throwable cause, Object... args) {
		return new SimpleFailure(path, formatMessage(path, args), cause);
	}

	private String formatMessage(String path, Object... args) {
		return messageTemplate.formatted(
			Stream.concat(Stream.of(path), Stream.of(args)).toArray(Object[]::new)
		);
	}

}
