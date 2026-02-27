package io.github.jinputguard.builder;

import io.github.jinputguard.GuardFailure;
import io.github.jinputguard.GuardResult;
import io.github.jinputguard.InputGuard;
import io.github.jinputguard.guard.ChainedGuard;
import io.github.jinputguard.guard.NoOpGuard;
import io.github.jinputguard.guard.NullStrategyGuard;
import io.github.jinputguard.guard.NullStrategyGuard.NullStrategy;
import io.github.jinputguard.guard.mapping.MappingFailure;
import io.github.jinputguard.result.DefaultGuardFailure;
import io.github.jinputguard.result.ErrorDetails;
import io.github.jinputguard.result.Path;
import jakarta.annotation.Nonnull;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Utility class to create various guards.
 */
public final class InputGuards {

	private InputGuards() {
		// Utility class
	}

	/**
	 * Creates a guard that will always return the same input, as a success.
	 * 
	 * @param <T>	The type of the input
	 * 
	 * @return	A guard ready to be used or combined with other guard(s)
	 */
	public static <T> InputGuard<T, T> noOpGuard() {
		return new NoOpGuard<>();
	}

	// ===========================================================================================================

	/**
	 * 
	 * @param <IN>
	 * @param <OUT>
	 * @param nullStrategy
	 * @return
	 */
	public static <IN, OUT> InputGuard<IN, OUT> nullStrategyGuard(NullStrategy<IN> nullStrategy, InputGuard<IN, OUT> nextGuard) {
		Objects.requireNonNull(nullStrategy, "NullStrategy cannot be null");
		Objects.requireNonNull(nextGuard, "Next guard cannot be null");
		return new NullStrategyGuard<>(nullStrategy, nextGuard);
	}

	// ===========================================================================================================

	/**
	 * Creates a guard that will sanitize/transform the input using the given function, and return the result as a success.
	 * 
	 * @param <T>					The type of the input
	 * 
	 * @param sanitizationFunction	The function to apply to the input
	 * 
	 * @return						A guard ready to be used or combined with other guard(s)
	 * 
	 * @see SanitizationGuard
	 */
	public static <T> InputGuard<T, T> sanitizationGuard(@Nonnull Function<T, T> sanitizationFunction) {
		Objects.requireNonNull(sanitizationFunction, "Sanitization function cannot be null");
		return (value, path) -> GuardResult.success(sanitizationFunction.apply(value));
	}

	// ===========================================================================================================

	/**
	 * Creates a guard that will validate the input against the given function.
	 * The validation function must return <code>null</code> if the input is valid,
	 * or an appropriate {@link ValidationFailure} if not.
	 * 
	 * @param <T>					The type of the input
	 * 
	 * @param validationFunction	The validation function, returns <code>null</code> if the input is valid or an appropriate {@link ValidationFailure} if not.
	 * 
	 * @return						A guard ready to be used or combined with other guard(s)
	 * 
	 * @see ValidationGuard
	 */
	public static <T> InputGuard<T, T> validationGuard(@Nonnull Function<T, ErrorDetails> validationFunction) {
		Objects.requireNonNull(validationFunction, "Validation function cannot be null");
		return (value, path) -> {
			var error = validationFunction.apply(value);
			return error == null
				? GuardResult.success(value)
				: GuardResult.failure(new DefaultGuardFailure(path, error));
		};
	}

	// ===========================================================================================================

	/**
	 * Creates a guard that will map the input into another type, using the given mapping function.
	 * <pre>
	 * InputGuard<String, String> stringOnlyProc = InputGuards.noOpGuard();
	 * InputGuard<String, Integer> stringToIntProc = InputGuards.mappingGuard(stringOnlyProc, Integer::parseInt);
	 * Integer i = stringToIntProc.process("123").get(); // i = 123
	 * </pre>
	 * 
	 * @param <IN>					The type of the input
	 * @param <OUT>					The type of the initial outpout (=the type to map from)
	 * @param <NEW_OUT>				The new type of the outpout, after mapping (=the type to map to)
	 * 
	 * @param initialGuard		The initial guard, from which the output will be mapped
	 * @param mappingFunction		The mapping function
	 * @param failureFunction		The failure function
	 * 
	 * @return						A guard ready to be used or combined with other guard(s)
	 * 
	 */
	public static <IN, OUT, NEW_OUT> InputGuard<IN, NEW_OUT> mappingGuard(
		@Nonnull InputGuard<IN, OUT> initialGuard, @Nonnull Function<OUT, NEW_OUT> mappingFunction
	) {
		return mappingGuard(initialGuard, mappingFunction, (path, ex) -> new MappingFailure(path, ex));
	}

	public static <IN, OUT, NEW_OUT> InputGuard<IN, NEW_OUT> mappingGuard(
		@Nonnull InputGuard<IN, OUT> initialGuard, @Nonnull Function<OUT, NEW_OUT> mappingFunction, @Nonnull BiFunction<Path, Throwable, GuardFailure> failureFunction
	) {

		Objects.requireNonNull(mappingFunction, "Mapping function cannot be null");
		Objects.requireNonNull(failureFunction, "Failure function cannot be null");
		InputGuard<OUT, NEW_OUT> mappingGuard = (value, path) -> {
			try {
				return GuardResult.success(mappingFunction.apply(value));
			} catch (Throwable ex) {
				return GuardResult.failure(failureFunction.apply(path, ex));
			}
		};
		return initialGuard.andThen(mappingGuard);
	}

	// ===========================================================================================================

	/**
	 * Creates a guard that chains the output of the first guard to the input of the second guard.
	 * 
	 * @param inputGuard	First guard of the chain, cannot be <code>null</code>
	 * @param secondGuard	Second guard of the chain, cannot be <code>null</code>
	 * 
	 * @return						A guard ready to be used or combined with other guard(s)
	 */
	public static <IN, OUT, NEW_OUT> InputGuard<IN, NEW_OUT> chainedGuard(
		@Nonnull InputGuard<IN, OUT> firstGuard, @Nonnull InputGuard<OUT, NEW_OUT> secondGuard
	) {
		return new ChainedGuard<>(firstGuard, secondGuard);
	}

}
