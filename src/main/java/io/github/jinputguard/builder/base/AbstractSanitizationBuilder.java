package io.github.jinputguard.builder.base;

import io.github.jinputguard.builder.InputGuardBuilder;
import java.util.function.Function;

public abstract class AbstractSanitizationBuilder<IN, T, B extends InputGuardBuilder<IN, T, B>, SELF extends AbstractSanitizationBuilder<IN, T, B, SELF>>
	extends AbstractIntermediateWithReturnBuilder<IN, T, B, SELF> {

	protected AbstractSanitizationBuilder(B builder) {
		super(builder);
	}

	/**
	 * Apply the given function to the input.
	 * 
	 * @param function	Any custom function to apply, <code>null</code> return must be handled appropriately
	 * 
	 * @return this sanitization builder
	 */
	public SELF apply(Function<T, T> function) {
		builder = builder.sanitize(function);
		return cast();
	}

}
