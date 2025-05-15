package io.github.jinputguard;

import io.github.jinputguard.builder.Builder;
import io.github.jinputguard.result.GuardResult;
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
	 * 
	 * @param input
	 * @return
	 */
	@Nonnull
	GuardResult<OUT> process(@Nullable IN value);

	/**
	 * 
	 * @param value
	 * @param property
	 * @return
	 */
	@Nonnull
	default GuardResult<OUT> process(@Nullable IN value, @Nonnull String property) {
		return process(value).atPath(Path.createPropertyPath(property));
	}

	// ===========================================================================================================

	/**
	 * 
	 * @param <NEW_OUT>
	 * @param after
	 * @return
	 */
	@Nonnull
	default <NEW_OUT> InputGuard<IN, NEW_OUT> andThen(InputGuard<OUT, NEW_OUT> after) {
		Objects.requireNonNull(after, "after guard cannot be null");
		return InputGuards.chainedGuard(this, after);
	}

	/**
	 * 
	 * @param <NEW_IN>
	 * @param before
	 * @return
	 */
	@Nonnull
	default <NEW_IN> InputGuard<NEW_IN, OUT> compose(InputGuard<NEW_IN, IN> before) {
		Objects.requireNonNull(before, "before guard cannot be null");
		return InputGuards.chainedGuard(before, this);
	}

	// ===========================================================================================================

	/**
	 * 
	 * @return
	 */
	@Nonnull
	static Builder builder() {
		return Builder.INSTANCE;
	}

}
