package io.github.jinputguard.builder.base.types.collection;

import java.util.List;

public class ListValidationBuilder<IN, T> extends AbstractSequencedCollectionValidationBuilder<IN, List<T>, T, ListInputGuardBuilder<IN, T>, ListValidationBuilder<IN, T>> {

	public ListValidationBuilder(ListInputGuardBuilder<IN, T> builder) {
		super(builder);
	}

}
