package io.github.jinputguard.builder.base.types.collection;

import java.util.Set;

public class SetSanitizationBuilder<IN, T> extends AbstractCollectionSanitizationBuilder<IN, Set<T>, T, SetInputGuardBuilder<IN, T>, SetSanitizationBuilder<IN, T>> {

	public SetSanitizationBuilder(SetInputGuardBuilder<IN, T> builder) {
		super(builder);
	}

}
