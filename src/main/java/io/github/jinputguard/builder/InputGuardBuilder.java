package io.github.jinputguard.builder;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.builder.base.NullStrategyBuilder;
import io.github.jinputguard.result.ValidationError;
import io.github.jinputguard.result.ValidationError.GenericError;
import io.github.jinputguard.result.ValidationFailure;
import jakarta.annotation.Nonnull;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @param <IN>
 * @param <OUT>
 */
public interface InputGuardBuilder<IN, OUT, SELF extends InputGuardBuilder<IN, OUT, SELF>> {

	/**
	 * Build the final {@link InputGuard}.
	 * 
	 * @return
	 */
	@Nonnull
	InputGuard<IN, OUT> build();

	/**
	 * Defines a strategy to handle <code>null</code> value.
	 * 
	 * @return	a builder for <code>null</code> strategies
	 */
	NullStrategyBuilder<IN, OUT, SELF> ifNullThen();

	/**
	 * Sanitize the value: any transformation function can be applied.
	 * 
	 * @param sanitizationFunction	Any transformation function, to be applied to the value
	 * 
	 * @return	a new builder
	 */
	@Nonnull
	SELF sanitize(@Nonnull Function<OUT, OUT> sanitizationFunction);

	/**
	 * Validate the value using the given validation function.
	 * 
	 * @param validationFunction	A function that returns <code>null</code> if the value is valid, a {@link ValidationFailure} otherwise.
	 * 
	 * @return	a new builder
	 * 
	 * @see ValidationFailure
	 * @see	ValidationError
	 */
	@Nonnull
	SELF validate(@Nonnull Function<OUT, ValidationError> validationFunction);

	/**
	 * Validate the value using the given predicate.
	 * In case of failure, the guard will fail with a {@link ValidationFailure}  containing a {@link ValidationError#GenericError} with the given error message.
	 * 
	 * @param validationPredicate	The test to apply on the value
	 * @param errorMessage			The error message
	
	 * @return	a new builder
	 * 
	 * @see ValidationFailure
	 * @see GenericError
	 */
	@Nonnull
	SELF validate(@Nonnull Predicate<OUT> validationPredicate, @Nonnull String errorMessage);

	/**
	 * Validate the value using the given predicate.
	 * In case of failure, the guard will fail with a {@link ValidationFailure}  containing a {@link ValidationError#GenericError} with the given error message.
	 * 
	 * @param validationPredicate	The test to apply on the value
	 * @param errorMessageFunction	The error message function
	
	 * @return	a new builder
	 * 
	 * @see ValidationFailure
	 * @see GenericError
	 */
	@Nonnull
	SELF validate(@Nonnull Predicate<OUT> validationPredicate, @Nonnull Function<OUT, String> errorMessageFunction);

	/**
	 * Apply the given guard, i.e. include it into the current one.
	 * 
	 * @param guard	The guard to apply to the value.
	 * 
	 * @return	a new builder
	 */
	@Nonnull
	SELF apply(@Nonnull InputGuard<OUT, OUT> guard);

	/**
	 * Map the output value of this guard into another type, and return a new {@link InputGuardBuilder} for this new type.
	 * 
	 * @param <NEW_OUT>	The new output value type
	 * 
	 * @param targetClass		The target class to map to
	 * @param mappingFunction	The mapping function to apply to this guard's output value
	 * 
	 * @return	a new builder
	 */
	@Nonnull
	<NEW_OUT, B extends InputGuardBuilder<IN, NEW_OUT, B>> InputGuardBuilder<IN, NEW_OUT, B> map(
		@Nonnull Function<OUT, NEW_OUT> mappingFunction
	);

	/**
	 * Map the output value of this guard into another type, and return a new {@link InputGuardBuilder} for this new type,
	 * using the provided function.
	 * 
	 * @param <NEW_OUT>	The new output value type
	 * @param <B>		The new guard builder type
	 * 
	 * @param mappingFunction	The mapping function to apply to this guard's output value
	 * @param builderFunction	The function to create a new guard builder from the created guard
	 * @return
	 */
	@Nonnull
	<NEW_OUT, B extends InputGuardBuilder<IN, NEW_OUT, B>> B map(
		@Nonnull Function<OUT, NEW_OUT> mappingFunction, @Nonnull Function<InputGuard<IN, NEW_OUT>, B> builderFunction
	);

}
