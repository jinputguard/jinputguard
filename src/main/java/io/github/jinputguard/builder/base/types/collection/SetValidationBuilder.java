package io.github.jinputguard.builder.base.types.collection;

import java.util.Set;

public class SetValidationBuilder<IN, T> extends AbstractCollectionValidationBuilder<IN, Set<T>, T, SetInputGuardBuilder<IN, T>, SetValidationBuilder<IN, T>> {

	public SetValidationBuilder(SetInputGuardBuilder<IN, T> builder) {
		super(builder);
	}

}
