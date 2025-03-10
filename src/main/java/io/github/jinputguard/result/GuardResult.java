package io.github.jinputguard.result;

import io.github.jinputguard.InputGuard;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.Objects;

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
public class GuardResult<T> {

	private final @Nullable T value;
	private final @Nullable GuardFailure failure;

	private GuardResult(@Nullable T value, @Nullable GuardFailure failure) {
		this.value = value;
		this.failure = failure;
	}

	/**
	 * Create a successful guard result.
	 * 
	 * @param <OUT> Expected type of the value
	 * @param value	The value
	 * 
	 * @return	A successful guard result
	 */
	@Nonnull
	public static <OUT> GuardResult<OUT> success(@Nullable OUT value) {
		return new GuardResult<OUT>(value, null);
	}

	/**
	 * Create a failed guard result, using the given failure reason.
	 * 
	 * @param <OUT> Expected type of the value (not used in this method)
	 * @param value	The failure, cannot be <code>null</code>
	 * 
	 * @return	A failed guard result
	 */
	@Nonnull
	public static <OUT> GuardResult<OUT> failure(@Nonnull GuardFailure failure) {
		Objects.requireNonNull(failure, "failure cannot be null");
		return new GuardResult<>(null, failure);
	}

	// ------------------------------------------------------------------------------------------------------------

	/**
	 * Test if this result is a success.
	 * 
	 * @return	<code>true</code> if this result is a success, <code>false</code> if it is a failure
	 */
	public boolean isSuccess() {
		return failure == null;
	}

	/**
	 * Test if this result is a failure.
	 * 
	 * @return	<code>true</code> if this result is a failure, <code>false</code> if it is a success
	 */
	public boolean isFailure() {
		return !isSuccess();
	}

	/**
	 * Get the value if guard has succeed.
	 * 
	 * @return	The processed value
	 * 
	 * @throws IllegalStateException if guard result is failure
	 */
	@Nonnull
	public T get() {
		if (isFailure()) {
			throw new IllegalStateException("Cannot get the value as result is failure, please first test with isSuccess()/isFailure()");
		}
		return value;
	}

	/**
	 * Get the value if guard has succeed, or throws an exception if failure.
	 * 
	 * @return
	 */
	@Nonnull
	public T getOrThrow() {
		if (isFailure()) {
			throw new InputGuardFailureException(failure);
		}
		return value;
	}

	/**
	 * Get the failure if guard has failed.
	 * 
	 * @return	The failure
	 * 
	 * @throws IllegalStateException if guard result is success
	 * 
	 * @see GuardFailure
	 */
	@Nonnull
	public GuardFailure getFailure() {
		if (isSuccess()) {
			throw new IllegalStateException("Cannot get the failure as result is success, please first test with isSuccess()/isFailure()");
		}
		return failure;
	}

	// ===========================================================================================================

	/**
	 * Modify the path of the result to place it under the given path.
	 * See {@link Path#atPath(Path)}) for more details.
	 * 
	 * @param path	The path that comes over the existing path
	 * 
	 * @return		A new result
	 * 
	 * @see Path
	 */
	@Nonnull
	public GuardResult<T> atPath(@Nonnull Path path) {
		Objects.requireNonNull(path, "path cannot be null");
		if (isSuccess()) {
			return this;
		}
		return new GuardResult<>(value, failure.atPath(path));
	}

	// ===========================================================================================================

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
		GuardResult<?> other = (GuardResult<?>) obj;
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
