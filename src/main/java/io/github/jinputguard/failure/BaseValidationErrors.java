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

	STRING_MUST_NOT_BE_BLANK ("%s must not be blank"),

	STRING_MUST_HAVE_EXACT_LENGTH ("%s must be exactly %s characters long"),

	STRING_MUST_HAVE_LENGTH_BETWEEN ("%s must be between %s and %s characters long"),

	STRING_MUST_HAVE_MIN_LENGTH ("%s must be at least %s characters long"),

	STRING_MUST_HAVE_MAX_LENGTH ("%s must be at most %s characters long"),

	STRING_MUST_MATCH_PATTERN ("%s must match pattern %s"),

	// --------------------------------------------------------------------------
	// UUID

	UUID_MUST_NOT_BE_NIL ("%s must not be the nil UUID"),

	UUID_MUST_BE_EXACT_VERSION ("%s must be UUID v%s"),

	// --------------------------------------------------------------------------
	// NUMBER

	NUMBER_MUST_BE_GREATER_THAN ("%s must be greater than %s"),

	NUMBER_MUST_BE_GREATER_OR_EQUAL_TO ("%s must be greater than or equal to %s"),

	NUMBER_MUST_BE_LOWER_THAN ("%s must be less than %s"),

	NUMBER_MUST_BE_LOWER_OR_EQUAL_TO ("%s must be less than or equal to %s"),

	NUMBER_MUST_BE_BETWEEN ("%s must be between %s and %s"),

	// --------------------------------------------------------------------------
	// ARRAY

	ARRAY_MUST_NOT_BE_EMPTY ("%s must not be empty"),

	ARRAY_LENGTH_EXACT ("%s must have exactly %s elements"),

	ARRAY_LENGTH_MIN ("%s must have at least %s elements"),

	ARRAY_LENGTH_MAX ("%s must have at most %s elements"),

	ARRAY_LENGTH_BETWEEN ("%s must have between %s and %s elements"),

	ARRAY_MUST_NOT_CONTAIN_DUPLICATE_ELEMENTS ("%s contains duplicate elements"),

	// --------------------------------------------------------------------------
	// COLLECTION

	COLLECTION_MUST_NOT_BE_EMPTY ("%s must not be empty"),

	COLLECTION_SIZE_EXACT ("%s must have exactly %s elements"),

	COLLECTION_SIZE_MIN ("%s must have at least %s elements"),

	COLLECTION_SIZE_MAX ("%s must have at most %s elements"),

	COLLECTION_SIZE_BETWEEN ("%s must have between %s and %s elements"),

	COLLECTION_MUST_NOT_CONTAIN_DUPLICATE_ELEMENTS ("%s contains duplicate elements"),

	;

	private final String messageTemplate;

	private BaseValidationErrors(String messageTemplate) {
		this.messageTemplate = messageTemplate;
	}

	public GuardFailure toFailure(String path, Object... args) {
		return toFailure(path, null, args);
	}

	public GuardFailure toFailure(String path, Throwable cause, Object... args) {
		return GuardFailure.from(path, formatMessage(path, args), cause);
	}

	private String formatMessage(String path, Object... args) {
		return messageTemplate.formatted(
			Stream.concat(Stream.of(path), Stream.of(args)).toArray(Object[]::new)
		);
	}

}
