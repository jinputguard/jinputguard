package io.github.jinputguard;

import io.github.jinputguard.builder.base.types.ObjectInputGuardBuilder;
import io.github.jinputguard.builder.base.types.StringInputGuardBuilder;
import io.github.jinputguard.builder.base.types.collection.ListInputGuardBuilder;
import io.github.jinputguard.builder.base.types.collection.SetInputGuardBuilder;
import io.github.jinputguard.builder.base.types.number.DoubleInputGuardBuilder;
import io.github.jinputguard.builder.base.types.number.FloatInputGuardBuilder;
import io.github.jinputguard.builder.base.types.number.IntegerInputGuardBuilder;
import io.github.jinputguard.builder.base.types.number.LongInputGuardBuilder;
import java.util.List;
import java.util.Set;

/**
 * Factory for builders of {@link InputGuard}.
 * 
 * This factory provides convenient methods to create builders for common types, such as strings, numbers and collections.
 * For more specialized builders, use the generic {@link #forClass(Class)} method.
 */
public final class BuilderFactory {

	static final BuilderFactory INSTANCE = new BuilderFactory();

	private BuilderFactory() {
		// Helper class, no need to instantiate
	}

	// ------------------------------------------------------------------------------------------------------------
	// OBJECTS

	/**
	 * Create a generic builder for the given class.
	 * For more specialized builders, use the specific methods (e.g. {@link #forString()} for strings, {@link #forInteger()} for integers, etc.).
	 * 
	 * @param clazz	the class of the object to be guarded
	 * @param <T>	the type of the object to be guarded
	 * 
	 * @return	a builder for the given class
	 */
	@SuppressWarnings("unused")
	public <T> ObjectInputGuardBuilder<T, T> forClass(Class<T> clazz) {
		return ObjectInputGuardBuilder.newInstance();
	}

	/**
	 * Create a builder for strings.
	 * 
	 * @return	a builder for strings
	 */
	public StringInputGuardBuilder<String> forString() {
		return StringInputGuardBuilder.newInstance();
	}

	// ------------------------------------------------------------------------------------------------------------
	// NUMBERS

	/**
	 * Create a builder for integers.
	 * 
	 * @return	a builder for integers
	 */
	public IntegerInputGuardBuilder<Integer> forInteger() {
		return IntegerInputGuardBuilder.newInstance();
	}

	/**
	 * Create a builder for longs.
	 * 
	 * @return	a builder for longs
	 */
	public LongInputGuardBuilder<Long> forLong() {
		return LongInputGuardBuilder.newInstance();
	}

	/**
	 * Create a builder for doubles.
	 * 
	 * @return	a builder for doubles
	 */
	public DoubleInputGuardBuilder<Double> forDouble() {
		return DoubleInputGuardBuilder.newInstance();
	}

	/**
	 * Create a builder for floats.
	 * 
	 * @return	a builder for floats
	 */
	public FloatInputGuardBuilder<Float> forFloat() {
		return FloatInputGuardBuilder.newInstance();
	}

	// ------------------------------------------------------------------------------------------------------------
	// COLLECTIONS

	/**
	 * Create a builder for lists of the given element type.
	 * 
	 * @param elementClass	the class of the elements in the list
	 * @param <T>			the type of the elements in the list
	 * 
	 * @return	a builder for lists of the given element type
	 */
	@SuppressWarnings("unused")
	public <T> ListInputGuardBuilder<List<T>, T> forList(Class<T> elementClass) {
		return ListInputGuardBuilder.newInstance();
	}

	/**
	 * Create a builder for sets of the given element type.
	 * 
	 * @param elementClass	the class of the elements in the set
	 * @param <T>			the type of the elements in the set
	 * 
	 * @return	a builder for sets of the given element type
	 */
	@SuppressWarnings("unused")
	public <T> SetInputGuardBuilder<Set<T>, T> forSet(Class<T> elementClass) {
		return SetInputGuardBuilder.newInstance();
	}

}
