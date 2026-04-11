package io.github.jinputguard.builder.base.types;

import io.github.jinputguard.result.errors.ValidationError;
import java.util.regex.Pattern;

public sealed interface StringValidationError extends ValidationError {

	record StringIsEmpty() implements StringValidationError {

		@Override
		public String getMessage(String path) {
			return path + " must not be empty";
		}

	}

	record StringIsTooLong(int currentLength, int maxLength) implements StringValidationError {

		@Override
		public String getMessage(String path) {
			return path + " must be " + maxLength + " chars max, but is " + currentLength;
		}

	}

	record StringMustBeParseableToInteger() implements StringValidationError {

		@Override
		public String getMessage(String path) {
			return path + " is not parseable to Integer";
		}

	}

	record StringMustMatchPattern(Pattern pattern) implements StringValidationError {

		@Override
		public String getMessage(String path) {
			return path + " must match pattern " + pattern.pattern();
		}

	}

}
