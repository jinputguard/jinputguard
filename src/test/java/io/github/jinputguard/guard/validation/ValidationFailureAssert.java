package io.github.jinputguard.guard.validation;

import io.github.jinputguard.result.ErrorMessage;
import io.github.jinputguard.result.GuardFailureAssert;
import io.github.jinputguard.result.Path;
import java.util.function.Consumer;

/**
 * 
 * 
 *
 */
public class ValidationFailureAssert extends GuardFailureAssert<ValidationFailureAssert, ValidationFailure> {

	private ValidationFailureAssert(ValidationFailure actual) {
		super(actual, ValidationFailureAssert.class);
	}

	public static ValidationFailureAssert assertThat(ValidationFailure actual) {
		return new ValidationFailureAssert(actual);
	}

	public ValidationFailureAssert hasValidationMessage(Path path, String validationErrorMsg) {
		return messageAssert(assertor -> assertor.isEqualTo("Invalid " + path.format() + ": " + validationErrorMsg));
	}

	// -------------------------------------------------------------------------------------------
	// ERROR

	public ValidationFailureAssert errorSatisfies(Consumer<ErrorMessage> consumer) {
		consumer.accept(actual.getError());
		return myself;
	}

	public ValidationFailureAssert errorAssert(Consumer<ValidationErrorAssert> consumer) {
		return errorSatisfies(error -> consumer.accept(ValidationErrorAssert.assertThat(error)));
	}

}