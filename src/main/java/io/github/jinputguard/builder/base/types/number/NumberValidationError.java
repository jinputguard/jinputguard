package io.github.jinputguard.builder.base.types.number;

import io.github.jinputguard.result.errors.ValidationError;

public sealed interface NumberValidationError extends ValidationError {

	record NumberMustBeGreaterThan(Number value, Number ref) implements NumberValidationError {

		@Override
		public String getMessage() {
			return "must be > " + ref;
		}

	}

	record NumberMustBeGreaterOrEqualTo(Number value, Number ref) implements NumberValidationError {

		@Override
		public String getMessage() {
			return "must be >= " + ref;
		}

	}

	record NumberMustBeLowerThan(Number value, Number ref) implements NumberValidationError {

		@Override
		public String getMessage() {
			return "must be < " + ref;
		}

	}

	record NumberMustBeLowerOrEqualTo(Number value, Number ref) implements NumberValidationError {

		@Override
		public String getMessage() {
			return "must be <= " + ref;
		}

	}

	record NumberMustBeBetween(Number value, Number min, Number max) implements NumberValidationError {

		@Override
		public String getMessage() {
			return "must be between " + min + " and " + max;
		}

	}

}
