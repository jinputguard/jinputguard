package io.github.jinputguard.guard.validation;

import io.github.jinputguard.guard.validation.ValidationError;

public class ValidationErrorAssert extends AbstractValidationErrorAssert<ValidationErrorAssert, ValidationError> {

	public ValidationErrorAssert(ValidationError actual) {
		super(actual, ValidationErrorAssert.class);
	}

	public static ValidationErrorAssert assertThat(ValidationError actual) {
		return new ValidationErrorAssert(actual);
	}

}
