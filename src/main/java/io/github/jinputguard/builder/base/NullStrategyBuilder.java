package io.github.jinputguard.builder.base;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.InputGuards;
import io.github.jinputguard.builder.InputGuardBuilder;
import io.github.jinputguard.guard.NullStrategyGuard.NullStrategy;
import io.github.jinputguard.result.GuardFailure;
import io.github.jinputguard.result.GuardResult;

public class NullStrategyBuilder<IN, OUT, B extends InputGuardBuilder<IN, OUT, B>>
	extends AbstractIntermediateBuilder<IN, OUT, B, NullStrategyBuilder<IN, OUT, B>> {

	public NullStrategyBuilder(B builder) {
		super(builder);
	}

	/**
	 * Continue process of <code>null</code> value. This may raise NullPointerException inside process.
	 * This is the default behavior when no null strategy is defined.
	 * 
	 * @return the calling builder
	 */
	public final B process() {
		builder = builder.apply(InputGuards.nullStrategyGuard(NullStrategy.process(), InputGuards.noOpGuard()));
		return builder;
	}

	/**
	 * Skip the process of <code>null</code> value, meaning a <code>null</code> input will provide a <code>null</code> output.
	 * 
	 * @return the calling builder
	 */
	public final B skipProcess() {
		builder = builder.apply(InputGuards.nullStrategyGuard(NullStrategy.skipProcess(), InputGuards.noOpGuard()));
		return builder;
	}

	/**
	 * Fail the process of <code>null</code> value, meaning the {@link GuardResult} will be a failure with cause {@link GuardFailure.ValidationFailure.ObjectIsNull}.
	 * 
	 * @return the calling builder
	 */
	public final B fail() {
		builder = builder.apply(InputGuards.nullStrategyGuard(NullStrategy.fail(), InputGuards.noOpGuard()));
		return builder;
	}

	/**
	 * Use the provided default value if the input value is <code>null</code>.
	 * 
	 * @param defaultValue	The default value to use in case of <code>null</code> input, cannot be <code>null</code>.
	 * 
	 * @return the calling builder
	 */
	@SuppressWarnings("unchecked")
	public final B useDefault(IN defaultValue) {
		builder = builder.apply((InputGuard<OUT, OUT>) InputGuards.nullStrategyGuard(NullStrategy.useDefault(defaultValue), InputGuards.noOpGuard()));
		return builder;
	}

}
