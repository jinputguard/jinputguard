package io.github.jinputguard.result;

import io.github.jinputguard.GuardFailure;
import io.github.jinputguard.GuardResult;
import io.github.jinputguard.InputGuard;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.Objects;
import java.util.function.Function;

/**
 * The result produced by a {@link InputGuard}, either successful or failed.
 * 
 * The state of a result can be checked using {@link #isSuccess()} and {@link #isFailure()} methods.
 *
 * If successful, the processed value can be obtained using {@link #get()}. Note that this method will throw 
 * an {@link IllegalStateException} if result is failure.
 * 
 * @param <T>
 */
public class DefaultGuardResult<T> implements GuardResult<T> {

	private final @Nullable T value;
	private final @Nullable GuardFailure failure;

	public DefaultGuardResult(@Nullable T value, @Nullable GuardFailure failure) {
		this.value = value;
		this.failure = failure;
	}

	@Nonnull
	public static <OUT> GuardResult<OUT> success(@Nullable OUT value) {
		return new DefaultGuardResult<OUT>(value, null);
	}

	@Nonnull
	public static <OUT> GuardResult<OUT> failure(@Nonnull GuardFailure failure) {
		Objects.requireNonNull(failure, "failure cannot be null");
		return new DefaultGuardResult<>(null, failure);
	}

	// ------------------------------------------------------------------------------------------------------------

	@Override
	public boolean isSuccess() {
		return failure == null;
	}

	@Override
	public boolean isFailure() {
		return failure != null;
	}

	@Nonnull
	@Override
	public T get() {
		if (isFailure()) {
			throw new IllegalStateException("Cannot get the value as result is failure, please first test with isSuccess()/isFailure()");
		}
		return value;
	}

	@Nonnull
	@Override
	public GuardFailure getFailure() {
		if (isSuccess()) {
			throw new IllegalStateException("Cannot get the failure as result is success, please first test with isSuccess()/isFailure()");
		}
		return failure;
	}

	@Nonnull
	@Override
	public T getOrThrow() {
		return getOrThrow(failure -> new InputGuardFailureException(failure));
	}

	@Nonnull
	@Override
	public <X extends Throwable> T getOrThrow(@Nonnull Function<GuardFailure, X> failureMapper) throws X {
		if (isFailure()) {
			throw failureMapper.apply(failure);
		}
		return value;
	}

	// ------------------------------------------------------------------------------------------------------------

	@Override
	public int hashCode() {
		return Objects.hash(value, failure);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultGuardResult<?> other = (DefaultGuardResult<?>) obj;
		return Objects.equals(failure, other.failure)
			&& Objects.equals(value, other.value);
	}

	@Override
	public String toString() {
		return "GuardResult" +
			(isSuccess()
				? "<Success>: " + value
				: "<Failure>: " + failure);
	}

}
