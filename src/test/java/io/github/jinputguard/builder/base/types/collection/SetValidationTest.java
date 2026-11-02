package io.github.jinputguard.builder.base.types.collection;

import java.util.Set;

class SetValidationTest extends AbstractCollectionValidationTest<Set<String>, SetInputGuardBuilder<Set<String>, String>> {

	@Override
	CollectionProvider<Set<String>, SetInputGuardBuilder<Set<String>, String>> getCollectionProvider() {
		return CollectionProvider.setProvider();
	}

}