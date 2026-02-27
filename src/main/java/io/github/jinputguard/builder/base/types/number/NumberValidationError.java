package io.github.jinputguard.builder.base.types.number;

import io.github.jinputguard.result.errors.ValidationError;

public interface NumberValidationError extends ValidationError {

	record NumberMustBeGreaterThan(Number value, Number ref) implements ValidationError {

		@Override
		public String getMessage() {
			return "must be > " + ref;
		}

	}

	record NumberMustBeGreaterOrEqualTo(Number value, Number ref) implements ValidationError {

		@Override
		public String getMessage() {
			return "must be >= " + ref;
		}

	}

	record NumberMustBeLowerThan(Number value, Number ref) implements ValidationError {

		@Override
		public String getMessage() {
			return "must be < " + ref;
		}

	}

	record NumberMustBeLowerOrEqualTo(Number value, Number ref) implements ValidationError {

		@Override
		public String getMessage() {
			return "must be <= " + ref;
		}

	}

	record NumberMustBeBetween(Number value, Number min, Number max) implements ValidationError {

		@Override
		public String getMessage() {
			return "must be between " + min + " and " + max;
		}

	}

}
