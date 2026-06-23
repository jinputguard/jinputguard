package io.github.jinputguard.builder.base.types;

import io.github.jinputguard.result.errors.ValidationError;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public sealed interface ObjectValidationError extends ValidationError {

	record ObjectIsNull() implements ObjectValidationError {

		@Override
		public String getMessage() {
			return "must not be null";
		}

	}

	record ObjectMustBeInstanceOf(@Nullable Class<?> currentClass, @Nonnull Class<?> expectedClass) implements ObjectValidationError {

		@Override
		public String getMessage() {
			return currentClass == null
				? "is not an instance of " + expectedClass.getName() + ", but is null"
				: "is not an instance of " + expectedClass.getName() + ", but is instance of " + currentClass.getName();
		}

	}

	record ObjectMustBeEqualTo(Object expected) implements ObjectValidationError {

		@Override
		public String getMessage() {
			return "is not equals to " + expected;
		}

	}

}
