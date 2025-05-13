package io.github.jinputguard.builder;

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

public final class Builder {

	public static final Builder INSTANCE = new Builder();

	private Builder() {
		// 
	}

	@SuppressWarnings("unused")
	public <T> ObjectInputGuardBuilder<T, T> forClass(Class<T> clazz) {
		return ObjectInputGuardBuilder.newInstance();
	}

	public StringInputGuardBuilder<String> forString() {
		return StringInputGuardBuilder.newInstance();
	}

	public IntegerInputGuardBuilder<Integer> forInteger() {
		return IntegerInputGuardBuilder.newInstance();
	}

	public LongInputGuardBuilder<Long> forLong() {
		return LongInputGuardBuilder.newInstance();
	}

	public DoubleInputGuardBuilder<Double> forDouble() {
		return DoubleInputGuardBuilder.newInstance();
	}

	public FloatInputGuardBuilder<Float> forFloat() {
		return FloatInputGuardBuilder.newInstance();
	}

	@SuppressWarnings("unused")
	public <T> ListInputGuardBuilder<List<T>, T> forList(Class<T> elementClass) {
		return ListInputGuardBuilder.newInstance();
	}

	@SuppressWarnings("unused")
	public <T> SetInputGuardBuilder<Set<T>, T> forSet(Class<T> elementClass) {
		return SetInputGuardBuilder.newInstance();
	}

}
