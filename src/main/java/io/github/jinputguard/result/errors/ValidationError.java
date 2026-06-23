package io.github.jinputguard.result.errors;

/**
 * A failure because of a validation issue. 
 * It is again a sealed interface, allowing switch pattern matching for a complete failure handling.
 * 
 * @see ValidationGuard
 */
public interface ValidationError extends ErrorMessage {

	// ===========================================================================
	// CUSTOM

	/**
	 * Extend this interface to declare your own custom validation failures.
	 * See online documentation and examples for more.
	 */
	interface CustomValidationError extends ValidationError {

	}

	// ===========================================================================
	// GENERIC

	/**
	 * A simple validation error that has just a message. 
	 */
	record GenericValidationError(String msg) implements ValidationError {

		@Override
		public String getMessage() {
			return msg;
		}

	}

}