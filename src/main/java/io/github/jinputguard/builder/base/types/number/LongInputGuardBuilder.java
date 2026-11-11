package io.github.jinputguard.builder.base.types.number;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.guard.InputGuards;

public class LongInputGuardBuilder<IN> extends AbstractNumberInputGuardBuilder<IN, Long, LongInputGuardBuilder<IN>> {

	public LongInputGuardBuilder(InputGuard<IN, Long> previous) {
		super(previous, LongTester.INSTANCE);
	}

	@Override
	protected LongInputGuardBuilder<IN> newInstance(InputGuard<IN, Long> guard) {
		return new LongInputGuardBuilder<>(guard);
	}

	public static LongInputGuardBuilder<Long> newInstance() {
		return new LongInputGuardBuilder<>(InputGuards.noOpGuard());
	}

}
