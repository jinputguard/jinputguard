package io.github.jinputguard.result;

import io.github.jinputguard.guard.ValidationGuard;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.regex.Pattern;

/**
 * A failure because of a validation issue. 
 * It is again a sealed interface, allowing switch pattern matching for a complete failure handling.
 * 
 * @see ValidationGuard
 */
public sealed interface ValidationError {

	String getConstraintMessage();

	// ===========================================================================
	// CUSTOM

	/**
	 * Extend this interface to declare your own custom validation failures.
	 * See online documentation and examples for more.
	 */
	non-sealed interface CustomValidationError extends ValidationError {

	}

	// ===========================================================================
	// OBJECT

	record ObjectIsNull() implements ValidationError {

		@Override
		public String getConstraintMessage() {
			return "must not be null";
		}

	}

	record ObjectMustBeInstanceOf(@Nullable Class<?> currentClass, @Nonnull Class<?> expectedClass) implements ValidationError {

		@Override
		public String getConstraintMessage() {
			return currentClass == null
				? "is not an instance of " + expectedClass.getName() + ", but is null"
				: "is not an instance of " + expectedClass.getName() + ", but is instance of " + currentClass.getName();
		}

	}

	record ObjectMustBeEqualTo(Object expected) implements ValidationError {

		@Override
		public String getConstraintMessage() {
			return "is not equals to " + expected;
		}

	}

	// ===========================================================================
	// STRING

	record StringIsEmpty() implements ValidationError {

		@Override
		public String getConstraintMessage() {
			return "must not be empty";
		}

	}

	record StringIsTooLong(int currentLength, int maxLength) implements ValidationError {

		@Override
		public String getConstraintMessage() {
			return "must be " + maxLength + " chars max, but is " + currentLength;
		}

	}

	record StringMustBeParseableToInteger() implements ValidationError {

		@Override
		public String getConstraintMessage() {
			return "is not parseable to Integer";
		}

	}

	record StringMustMatchPattern(Pattern pattern) implements ValidationError {

		@Override
		public String getConstraintMessage() {
			return "must match pattern " + pattern.pattern();
		}

	}

	// ===========================================================================
	// NUMBER

	record NumberMustBeGreaterThan(Number value, Number ref) implements ValidationError {

		@Override
		public String getConstraintMessage() {
			return "must be > " + ref;
		}

	}

	record NumberMustBeGreaterOrEqualTo(Number value, Number ref) implements ValidationError {

		@Override
		public String getConstraintMessage() {
			return "must be >= " + ref;
		}

	}

	record NumberMustBeLowerThan(Number value, Number ref) implements ValidationError {

		@Override
		public String getConstraintMessage() {
			return "must be < " + ref;
		}

	}

	record NumberMustBeLowerOrEqualTo(Number value, Number ref) implements ValidationError {

		@Override
		public String getConstraintMessage() {
			return "must be <= " + ref;
		}

	}

	record NumberMustBeBetween(Number value, Number min, Number max) implements ValidationError {

		@Override
		public String getConstraintMessage() {
			return "must be between " + min + " and " + max;
		}

	}

	// ===========================================================================
	// COLLECTION

	record CollectionIsEmpty() implements ValidationError {

		@Override
		public String getConstraintMessage() {
			return "is empty";
		}

	}

}