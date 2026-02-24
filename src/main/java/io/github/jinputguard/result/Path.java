package io.github.jinputguard.result;

import jakarta.annotation.Nullable;
import java.util.Objects;

public sealed interface Path {

	/**
	 * Format this path into a human readable form: "lastName", "myList[0]", etc.
	 * 
	 * @return
	 */
	String format();

	/**
	 * Create a new path at root (no property, no index).
	 * 
	 * @return
	 */
	static Path create(String property) {
		return new PropertyPath(null, property);
	}

	default Path in(String property) {
		return new PropertyPath(this, property);
	}

	default Path atIndex(int index) {
		return new IndexPath(this, index);
	}

	default Path atUndefinedIndex() {
		return new IndexPath(this, -1);
	}

	record PropertyPath(Path parent, String property) implements Path {

		public PropertyPath(@Nullable Path parent, String property) {
			this.parent = parent;
			this.property = Objects.requireNonNull(property, "property path cannot be null");
		}

		@Override
		public String format() {
			if (parent == null) {
				return property;
			}
			return parent.format() + "." + property;
		}

	}

	record IndexPath(Path parent, int index) implements Path {

		public IndexPath(Path parent, int index) {
			this.parent = Objects.requireNonNull(parent, "parent path cannot be null");
			this.index = index;
		}

		@Override
		public String format() {
			return parent.format() + getIndexPrint();
		}

		private final String getIndexPrint() {
			return index < 0 ? "[?]" : "[" + index + "]";
		}

	}

}
