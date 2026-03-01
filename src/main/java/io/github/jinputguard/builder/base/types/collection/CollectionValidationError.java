package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.result.errors.ValidationError;

public sealed interface CollectionValidationError extends ValidationError {

	record CollectionIsEmpty() implements CollectionValidationError {

		@Override
		public String getMessage() {
			return "is empty";
		}

	}

}
