package io.github.jinputguard.guard.validation;

import io.github.jinputguard.result.ErrorMessage;

public class ValidationErrorAssert extends AbstractValidationErrorAssert<ValidationErrorAssert, ErrorMessage> {

	public ValidationErrorAssert(ErrorMessage actual) {
		super(actual, ValidationErrorAssert.class);
	}

	public static ValidationErrorAssert assertThat(ErrorMessage actual) {
		return new ValidationErrorAssert(actual);
	}

}
