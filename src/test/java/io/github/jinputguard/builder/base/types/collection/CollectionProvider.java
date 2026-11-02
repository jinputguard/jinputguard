package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.InputGuard;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public sealed interface CollectionProvider<C extends Collection<String>, B extends AbstractCollectionInputGuardBuilder<C, C, String, B>> {

	B initiateGuard();

	C buildCollection(String... elements);

	boolean areDuplicatesAllowed();

	static CollectionProvider<List<String>, ListInputGuardBuilder<List<String>, String>> listProvider() {
		return new ListProvider();
	}

	static CollectionProvider<Set<String>, SetInputGuardBuilder<Set<String>, String>> setProvider() {
		return new SetProvider<>();
	}

	final class ListProvider implements CollectionProvider<List<String>, ListInputGuardBuilder<List<String>, String>> {

		@Override
		public ListInputGuardBuilder<List<String>, String> initiateGuard() {
			return InputGuard.builder().forList(String.class);
		}

		@Override
		public List<String> buildCollection(String... elements) {
			return List.of(elements);
		}

		@Override
		public boolean areDuplicatesAllowed() {
			return true;
		}

	}

	final class SetProvider<T> implements CollectionProvider<Set<String>, SetInputGuardBuilder<Set<String>, String>> {

		@Override
		public SetInputGuardBuilder<Set<String>, String> initiateGuard() {
			return InputGuard.builder().forSet(String.class);
		}

		@Override
		public Set<String> buildCollection(String... elements) {
			return Set.of(elements);
		}

		@Override
		public boolean areDuplicatesAllowed() {
			return false;
		}

	}

}
