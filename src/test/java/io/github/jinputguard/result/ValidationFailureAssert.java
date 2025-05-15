package io.github.jinputguard.result;

import io.github.jinputguard.result.Path;
import io.github.jinputguard.result.ValidationError;
import io.github.jinputguard.result.ValidationFailure;
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

	public ValidationFailureAssert hasValidationMessage(String validationErrorMsg) {
		return this.hasValidationMessage(Path.root(), validationErrorMsg);
	}

	public ValidationFailureAssert hasValidationMessage(Path path, String validationErrorMsg) {
		return messageAssert(assertor -> assertor.isEqualTo("Invalid " + path.format() + ": " + validationErrorMsg));
	}

	// -------------------------------------------------------------------------------------------
	// ERROR

	public ValidationFailureAssert errorSatisfies(Consumer<ValidationError> consumer) {
		consumer.accept(actual.getError());
		return myself;
	}

	public ValidationFailureAssert errorAssert(Consumer<ValidationErrorAssert> consumer) {
		return errorSatisfies(error -> consumer.accept(ValidationErrorAssert.assertThat(error)));
	}

}