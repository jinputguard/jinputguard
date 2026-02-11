package io.github.jinputguard;

import io.github.jinputguard.result.DefaultGuardResult;
import io.github.jinputguard.result.InputGuardFailureException;
import io.github.jinputguard.result.Path;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

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
public interface GuardResult<T> {

	/**
	 * Test if this result is a success.
	 * 
	 * @return	<code>true</code> if this result is a success, <code>false</code> if it is a failure
	 */
	boolean isSuccess();

	/**
	 * Test if this result is a failure.
	 * 
	 * @return	<code>true</code> if this result is a failure, <code>false</code> if it is a success
	 */
	boolean isFailure();

	/**
	 * Get the value if guard has succeed.
	 * 
	 * @return	The processed value
	 * 
	 * @throws IllegalStateException if guard result is failure
	 */
	@Nonnull
	T get();

	/**
	 * Get the value if guard has succeed, or throws an exception if failure.
	 * 
	 * @return	The result value
	 * 
	 * @throws InputGuardFailureException if guard failed
	 */
	@Nonnull
	T getOrThrow();

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
	GuardFailure getFailure();

	// ------------------------------------------------------------------------------------------------------------

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
	public GuardResult<T> atPath(@Nonnull Path path);

	// ------------------------------------------------------------------------------------------------------------

	/**
	 * Create a successful guard result.
	 * 
	 * @param <OUT> Expected type of the value
	 * @param value	The value
	 * 
	 * @return	A successful guard result
	 */
	@Nonnull
	static <OUT> GuardResult<OUT> success(@Nullable OUT value) {
		return DefaultGuardResult.success(value);
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
	static <OUT> GuardResult<OUT> failure(@Nonnull GuardFailure failure) {
		return DefaultGuardResult.failure(failure);
	}

}
