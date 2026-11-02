package io.github.jinputguard.builder.base.types.collection;

import java.util.List;

public class ListSanitizationBuilder<IN, T> extends AbstractCollectionSanitizationBuilder<IN, List<T>, T, ListInputGuardBuilder<IN, T>, ListSanitizationBuilder<IN, T>> {

	public ListSanitizationBuilder(ListInputGuardBuilder<IN, T> builder) {
		super(builder);
	}

}
