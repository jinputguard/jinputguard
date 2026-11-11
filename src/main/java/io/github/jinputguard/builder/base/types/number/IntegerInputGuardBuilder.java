package io.github.jinputguard.builder.base.types.number;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.guard.InputGuards;

public class IntegerInputGuardBuilder<IN> extends AbstractNumberInputGuardBuilder<IN, Integer, IntegerInputGuardBuilder<IN>> {

	public IntegerInputGuardBuilder(InputGuard<IN, Integer> previous) {
		super(previous, IntegerTester.INSTANCE);
	}

	@Override
	protected IntegerInputGuardBuilder<IN> newInstance(InputGuard<IN, Integer> guard) {
		return new IntegerInputGuardBuilder<>(guard);
	}

	public static IntegerInputGuardBuilder<Integer> newInstance() {
		return new IntegerInputGuardBuilder<>(InputGuards.noOpGuard());
	}

}
