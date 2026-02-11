package io.github.jinputguard.result;

public class ValidationErrorAssert extends AbstractValidationErrorAssert<ValidationErrorAssert, ValidationError> {

	public ValidationErrorAssert(ValidationError actual) {
		super(actual, ValidationErrorAssert.class);
	}

	public static ValidationErrorAssert assertThat(ValidationError actual) {
		return new ValidationErrorAssert(actual);
	}

}
