package io.github.jinputguard.builder.base.types;

import io.github.jinputguard.guard.validation.ValidationError;
import java.util.regex.Pattern;

public interface StringValidationError extends ValidationError {

	record StringIsEmpty() implements StringValidationError {

		@Override
		public String getMessage() {
			return "must not be empty";
		}

	}

	record StringIsTooLong(int currentLength, int maxLength) implements StringValidationError {

		@Override
		public String getMessage() {
			return "must be " + maxLength + " chars max, but is " + currentLength;
		}

	}

	record StringMustBeParseableToInteger() implements StringValidationError {

		@Override
		public String getMessage() {
			return "is not parseable to Integer";
		}

	}

	record StringMustMatchPattern(Pattern pattern) implements StringValidationError {

		@Override
		public String getMessage() {
			return "must match pattern " + pattern.pattern();
		}

	}

}
