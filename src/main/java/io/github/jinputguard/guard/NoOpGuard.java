package io.github.jinputguard.guard;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.result.GuardResult;
import java.util.Objects;

/**
 * Default guard that does nothing with the value.
 * 
 * @param <T>
 */
public class NoOpGuard<T> implements InputGuard<T, T> {

	@Override
	public GuardResult<T> process(T value) {
		return GuardResult.success(value);
	}

	@Override
	public <NEW_OUT> InputGuard<T, NEW_OUT> andThen(InputGuard<T, NEW_OUT> after) {
		Objects.requireNonNull(after, "after guard cannot be null");
		return after;
	}

	@Override
	public <NEW_IN> InputGuard<NEW_IN, T> compose(InputGuard<NEW_IN, T> before) {
		Objects.requireNonNull(before, "before guard cannot be null");
		return before;
	}

	@Override
	public String toString() {
		return "NoOpGuard";
	}

}
