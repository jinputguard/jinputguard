package io.github.jinputguard.guard.validation;

import io.github.jinputguard.result.ErrorDetails;

public class ValidationErrorAssert extends AbstractValidationErrorAssert<ValidationErrorAssert, ErrorDetails> {

	public ValidationErrorAssert(ErrorDetails actual) {
		super(actual, ValidationErrorAssert.class);
	}

	public static ValidationErrorAssert assertThat(ErrorDetails actual) {
		return new ValidationErrorAssert(actual);
	}

}
