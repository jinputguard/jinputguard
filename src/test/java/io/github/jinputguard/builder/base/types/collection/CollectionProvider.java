package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.InputGuard;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public sealed interface CollectionProvider<C extends Collection<String>, B extends AbstractCollectionInputGuardBuilder<C, C, String, B>> {

	B initiateGuard();

	C buildMutableCollection(String... elements);

	boolean areDuplicatesAllowed();

	String getElementName(String path, int index);

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
		public List<String> buildMutableCollection(String... elements) {
			var coll = new ArrayList<String>(elements.length);
			for (var element : elements) {
				coll.add(element);
			}
			return coll;
		}

		@Override
		public boolean areDuplicatesAllowed() {
			return true;
		}

		@Override
		public String getElementName(String path, int index) {
			return path + "[" + index + "]";
		}

	}

	final class SetProvider<T> implements CollectionProvider<Set<String>, SetInputGuardBuilder<Set<String>, String>> {

		@Override
		public SetInputGuardBuilder<Set<String>, String> initiateGuard() {
			return InputGuard.builder().forSet(String.class);
		}

		@Override
		public Set<String> buildMutableCollection(String... elements) {
			var coll = new HashSet<String>(elements.length);
			for (var element : elements) {
				coll.add(element);
			}
			return coll;
		}

		@Override
		public boolean areDuplicatesAllowed() {
			return false;
		}

		@Override
		public String getElementName(String path, int index) {
			return path + "[?]";
		}

	}

}
