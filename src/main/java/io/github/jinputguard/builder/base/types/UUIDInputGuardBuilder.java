package io.github.jinputguard.builder.base.types;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.builder.base.AbstractInputGuardBuilder;
import io.github.jinputguard.guard.InputGuards;
import java.util.UUID;

public class UUIDInputGuardBuilder<IN> extends AbstractInputGuardBuilder<IN, UUID, UUIDInputGuardBuilder<IN>> {

	public UUIDInputGuardBuilder(InputGuard<IN, UUID> previous) {
		super(previous);
	}

	@Override
	protected UUIDInputGuardBuilder<IN> newInstance(InputGuard<IN, UUID> guard) {
		return new UUIDInputGuardBuilder<>(guard);
	}

	public static UUIDInputGuardBuilder<UUID> newInstance() {
		return new UUIDInputGuardBuilder<>(InputGuards.noOpGuard());
	}

	public UUIDValidationBuilder<IN> validateThat() {
		return new UUIDValidationBuilder<>(this);
	}

}
