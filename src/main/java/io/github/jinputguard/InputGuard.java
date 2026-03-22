package io.github.jinputguard;

import io.github.jinputguard.builder.InputGuards;
import io.github.jinputguard.result.Path;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.Objects;

/**
 * A guard that will process an input: sanitization, validation and mapping, any number of times, in any order.
 * 
 * <pre>
 * InputGuard<String, String> guard = InputGuard.builder().forClass(String.class)
 *   .validate(value -> value == null ? new ValidationFailure.ObjectIsNull() : null)
 *   .sanitize(String::strip)
 *   .build();
 * GuardResult<String> result = guard.process(" some input  ");
 * String safeValue = result.getOrThrow(); // "some input"
 * </pre>
 * 
 * The same behavior can be obtained using handy pre-defined builders, discoverable using your IDE auto-completion feature.
 * The above example can then be simplified into:
 * <pre>
 * InputGuard<String, String> guard = InputGuard.builder().forString()  // "forString()" instead of "forClass(String.class)"
 *   .validateThat().isNotNull().then()
 *   .sanitize().strip().then()
 *   .build();
 * GuardResult<String> result = guard.process(" some input  ");
 * String safeValue = result.getOrThrow(); // "some input"
 * </pre>
 * 
 * 
 * @param <IN>	The initial input value type
 * @param <OUT>	The output value type, may be different than the input type if some mapping were applied
 */
@FunctionalInterface
public interface InputGuard<IN, OUT> {

	/**
	 * Process the given input value and return the result of the processing, which may be a success or a failure.
	 * 
	 * @param value	the input value to process, may be <code>null</code> if the guard is configured to allow null values
	 * @param path	the path to the value being processed, used for error reporting in case of failure
	 * @return	the result of the processing, containing either the processed value or the failure details
	 */
	@Nonnull
	GuardResult<OUT> process(@Nullable IN value, @Nonnull Path path);

	/**
	 * Process the given input value and return the result of the processing, which may be a success or a failure.
	 * This is a convenience method that uses a simple property name as the path for error reporting.
	 * 
	 * @param value	the input value to process, may be <code>null</code> if the guard is configured to allow null values
	 * @param property	the property name to use as the path for error reporting in case of failure
	 * @return	the result of the processing, containing either the processed value or the failure details
	 */
	@Nonnull
	default GuardResult<OUT> process(@Nullable IN value, @Nonnull String property) {
		return process(value, Path.create(property));
	}

	// ===========================================================================================================

	/**
	 * Chain this guard with another guard that will be applied after this one, if this one succeeds.
	 * 
	 * @param <NEW_OUT> the output type of the resulting chained guard, which is the output type of the "after" guard
	 * @param after the guard to apply after this one, if this one succeeds
	 * @return a new guard that represents the chaining of this guard and the "after" guard
	 */
	@Nonnull
	default <NEW_OUT> InputGuard<IN, NEW_OUT> andThen(InputGuard<OUT, NEW_OUT> after) {
		Objects.requireNonNull(after, "after guard cannot be null");
		return InputGuards.chainedGuard(this, after);
	}

	/**
	 * Chain this guard with another guard that will be applied before this one, if the other one succeeds.
	 * 
	 * @param <NEW_IN> the input type of the resulting chained guard, which is the input type of the "before" guard
	 * @param before the guard to apply before this one, if the other one succeeds
	 * @return a new guard that represents the chaining of the "before" guard and this guard
	 */
	@Nonnull
	default <NEW_IN> InputGuard<NEW_IN, OUT> compose(InputGuard<NEW_IN, IN> before) {
		Objects.requireNonNull(before, "before guard cannot be null");
		return InputGuards.chainedGuard(before, this);
	}

	// ===========================================================================================================

	/**
	 * Create a new builder to define an input guard.
	 * 
	 * @return	a new builder
	 */
	@Nonnull
	static BuilderFactory builder() {
		return BuilderFactory.INSTANCE;
	}

}
