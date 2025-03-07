package io.github.jinputguard.builder.base;

import io.github.jinputguard.builder.InputGuardBuilder;

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

	public B then() {
		return builder;
	}

}
