package io.github.jinputguard.result;

import jakarta.annotation.Nullable;
import java.util.Objects;

/**
 * Represents the path to a value being processed by an {@link io.github.jinputguard.InputGuard}.
 * This is used for error reporting in case of failure, to indicate where in the input structure the failure occurred.
 * 
 * A path can be composed of multiple segments, each representing a property or an index in a collection.
 * For example, "user.address.street" represents a path to the "street" property of the "address" property of the "user" object.
 * Similarly, "myList[0]" represents a path to the first element of the "myList" collection.
 */
public sealed interface Path {

	/**
	 * Format the path as a string, using dot notation for properties and square brackets for indices.
	 * For example, a path representing the "street" property of the "address" property of the "user" object would be formatted as "user.address.street".
	 * A path representing the first element of the "myList" collection would be formatted as "myList[0]".
	 * 
	 * @return	the formatted path as a string
	 */
	String format();

	/**
	 * Create a new path starting with the given property name.
	 * 
	 * @param property	the property name to start the path with
	 * @return	a new path starting with the given property name
	 */
	static Path create(String property) {
		return new PropertyPath(null, property);
	}

	/**
	 * Create a new path starting with the given property name.
	 * This is a convenience method that allows to create a path using a simple property name.
	 * 
	 * @param property	the property name to start the path with
	 * @return	a new path starting with the given property name
	 */
	default Path inProperty(String property) {
		return new PropertyPath(this, property);
	}

	/**
	 * Create a new path representing an index in a collection.
	 * 
	 * @param index	the index to represent in the path, must be non-negative
	 * @return	a new path representing the given index in a collection
	 */
	default Path atIndex(int index) {
		return new IndexPath(this, index);
	}

	/**
	 * Create a new path representing an undefined index in a collection.
	 * This is used for collections that are not sequenced, such as sets or maps, where the index of an element cannot be determined.
	 * 
	 * @return	a new path representing an undefined index in a collection
	 */
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

		@Override
		public final String toString() {
			return format();
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

		@Override
		public final String toString() {
			return format();
		}

	}

}
