package io.github.jinputguard.builder.base.types.collection;

import java.util.Collection;

abstract class AbstractCollectionTest<C extends Collection<String>, B extends AbstractCollectionInputGuardBuilder<C, C, String, B>> {

	abstract CollectionProvider<C, B> getCollectionProvider();

	final B initiateGuard() {
		return getCollectionProvider().initiateGuard();
	}

	final C buildCollection(String... elements) {
		return getCollectionProvider().buildCollection(elements);
	}

	final boolean areDuplicatesAllowed() {
		return getCollectionProvider().areDuplicatesAllowed();
	}

}
