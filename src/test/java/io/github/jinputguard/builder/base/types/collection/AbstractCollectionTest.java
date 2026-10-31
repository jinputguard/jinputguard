package io.github.jinputguard.builder.base.types.collection;

import java.util.Collection;

abstract class AbstractCollectionTest<C extends Collection<String>, B extends AbstractCollectionInputGuardBuilder<C, C, String, B>> {

	abstract B initiateGuard();

	abstract C buildCollection(String... elements);

	abstract boolean allowDuplicates();

}
