package io.github.jinputguard.builder.base.types.collection;

import java.util.List;

class ListValidationTest extends AbstractCollectionValidationTest<List<String>, ListInputGuardBuilder<List<String>, String>> {

	@Override
	CollectionProvider<List<String>, ListInputGuardBuilder<List<String>, String>> getCollectionProvider() {
		return CollectionProvider.listProvider();
	}

}