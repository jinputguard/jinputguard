package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.InputGuard;
import java.util.Set;

class SetValidationTest extends AbstractCollectionValidationTest<Set<String>, SetInputGuardBuilder<Set<String>, String>> {

	@Override
	SetInputGuardBuilder<Set<String>, String> initiateGuard() {
		return InputGuard.builder().forSet(String.class);
	}

	@Override
	Set<String> buildCollection(String... elements) {
		return Set.of(elements);
//		// Do not use Set.of(...) because it throws IllegalArgumentException when duplicate elements are present
//		// (which is required for some tests)
//		var set = new HashSet<String>();
//		for (var e : elements) {
//			set.add(e);
//		}
//		return set;
	}

	@Override
	protected boolean allowDuplicates() {
		return false;
	}

}