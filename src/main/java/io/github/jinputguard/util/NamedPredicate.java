package io.github.jinputguard.util;

import jakarta.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * A predicate that has a name, to identify it from other predicates.
 */
public class NamedPredicate<T> implements Predicate<T> {

	@Nonnull
	private final Predicate<T> delegate;

	@Nonnull
	private final String name;

	public NamedPredicate(Predicate<T> delegate, String name) {
		this.delegate = Objects.requireNonNull(delegate, "Delegate predicate cannot be null");
		this.name = Objects.requireNonNull(name, "Name cannot be null");
	}

	@Override
	public Predicate<T> and(Predicate<? super T> other) {
		return new NamedPredicate<>(delegate.and(other), "(" + this + " && " + other.toString() + ")");
	}

	@Override
	public Predicate<T> or(Predicate<? super T> other) {
		return new NamedPredicate<>(delegate.or(other), "(" + this + " || " + other.toString() + ")");
	}

	@Override
	public Predicate<T> negate() {
		return new NamedPredicate<>(delegate.negate(), "!" + this);
	}

	@Override
	public boolean test(T t) {
		return delegate.test(t);
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

}
