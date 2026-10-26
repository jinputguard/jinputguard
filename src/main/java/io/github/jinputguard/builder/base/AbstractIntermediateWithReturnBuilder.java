package io.github.jinputguard.builder.base;

import io.github.jinputguard.InputGuardBuilder;
import io.github.jinputguard.failure.BaseValidationErrors;

/**
 * 
 * 
 *
 * @param <IN>
 * @param <T>
 * @param <B>
 */
abstract class AbstractIntermediateWithReturnBuilder<IN, T, B extends InputGuardBuilder<IN, T, B>, SELF extends AbstractIntermediateWithReturnBuilder<IN, T, B, SELF>>
	extends AbstractIntermediateBuilder<IN, T, B, SELF> {

	protected AbstractIntermediateWithReturnBuilder(B builder) {
		super(builder);
	}

	protected final B notNullValueBuilder() {
		return builder.validate(
			(value, path) -> value == null
				? BaseValidationErrors.OBJECT_MUST_NOT_BE_NULL.toFailure(path)
				: null
		);
	}

	public B then() {
		return builder;
	}

}
