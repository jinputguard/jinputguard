package io.github.jinputguard.result.errors;

/**
 * A failure because of a validation issue. 
 * It is again a sealed interface, allowing switch pattern matching for a complete failure handling.
 * 
 * @see ValidationGuard
 */
public interface MappingError extends ErrorDetails {

	/**
	 * A simple validation error that has just a message. 
	 */
	record MappingExceptionError(Throwable cause) implements MappingError, WithEmbeddedCause {

		@Override
		public String getMessage(String path) {
			return "invalid value";
		}

	}

}