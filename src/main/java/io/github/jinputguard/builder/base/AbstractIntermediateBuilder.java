package io.github.jinputguard.builder.base;

import io.github.jinputguard.builder.InputGuardBuilder;
import java.util.Objects;

/**
 * 
 * 
 *
 * @param <IN>
 * @param <T>
 * @param <B>
 */
abstract class AbstractIntermediateBuilder<IN, T, B extends InputGuardBuilder<IN, T, B>, SELF extends AbstractIntermediateBuilder<IN, T, B, SELF>> {

	protected B builder;

	protected AbstractIntermediateBuilder(B builder) {
		super();
		this.builder = Objects.requireNonNull(builder);
	}

	@SuppressWarnings("unchecked")
	protected final SELF cast() {
		return (SELF) this;
	}

}
