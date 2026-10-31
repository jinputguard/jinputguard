package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.InputGuard;
import java.util.List;

class ListValidationTest extends AbstractCollectionValidationTest<List<String>, ListInputGuardBuilder<List<String>, String>> {

	@Override
	ListInputGuardBuilder<List<String>, String> initiateGuard() {
		return InputGuard.builder().forList(String.class);
	}

	@Override
	List<String> buildCollection(String... elements) {
		return List.of(elements);
	}

	@Override
	public boolean allowDuplicates() {
		return true;
	}

}