package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.builder.base.AbstractInputGuardBuilder;
import java.util.Collection;

public abstract class AbstractCollectionInputGuardBuilder<IN, C extends Collection<T>, T, SELF extends AbstractCollectionInputGuardBuilder<IN, C, T, SELF>>
	extends AbstractInputGuardBuilder<IN, C, SELF> {

	protected AbstractCollectionInputGuardBuilder(InputGuard<IN, C> previous) {
		super(previous);
	}

	public abstract <V extends AbstractCollectionSanitizationBuilder<IN, C, T, SELF, V>> V sanitize();

	public abstract <V extends AbstractCollectionValidationBuilder<IN, C, T, SELF, V>> V validateThat();

}
