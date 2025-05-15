package io.github.jinputguard.util;

import jakarta.annotation.Nonnull;
import java.util.function.Predicate;

public final class Predicates {

	private Predicates() {

	}

	private static final NamedPredicate<?> ALWAYS_TRUE = new NamedPredicate<>(value -> true, "Always true");

	@Nonnull
	@SuppressWarnings("unchecked")
	public static <T> Predicate<T> alwaysTrue() {
		return (Predicate<T>) ALWAYS_TRUE;
	}

}
