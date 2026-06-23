package io.github.jinputguard;

import io.github.jinputguard.builder.base.NullStrategyBuilder;
import io.github.jinputguard.result.errors.ErrorMessage;
import io.github.jinputguard.result.errors.ValidationError;
import io.github.jinputguard.result.errors.ValidationError.GenericValidationError;
import jakarta.annotation.Nonnull;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Builder for {@link InputGuard}.
 * 
 * @param <IN>	The initial input value type
 * @param <OUT>	The output value type, may be different than the input type if some mapping were applied
 * @param <SELF>	The self type of this builder, used for fluent API
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
	 * Define the strategy to apply when the value is <code>null</code>.
	 * 
	 * @return	a new builder to define the null handling strategy
	 */
	NullStrategyBuilder<IN, OUT, SELF> ifNullThen();

	// ------------------------------------------------------------------------------------------------------------
	// SANITIZATION

	/**
	 * Sanitize the value using the given sanitization function.
	 * 
	 * @param sanitizationFunction	A function that takes the output value and returns a sanitized version of it.
	 * 
	 * @return	a new builder
	 */
	@Nonnull
	SELF sanitize(@Nonnull Function<OUT, OUT> sanitizationFunction);

	// ------------------------------------------------------------------------------------------------------------
	// VALIDATION

	/**
	 * Validate the value using the given validation function.
	 * The validation function takes the output value and a path as input, and returns an {@link ErrorMessage} if the value is invalid, or <code>null</code> if the value is valid.
	 * In case of failure, the guard will fail with a {@link ValidationFailure} containing the returned {@link ErrorMessage}.
	 * 
	 * @param validationFunction	The validation function to apply on the value and the path
	 * 
	 * @return	a new builder
	 * 
	 * @see ValidationFailure
	 */
	@Nonnull
	SELF validate(@Nonnull BiFunction<OUT, String, ErrorMessage> validationFunction);

	/**
	 * Validate the value using the given predicate.
	 * In case of failure, the guard will fail with a {@link ValidationFailure} containing a {@link ValidationError#GenericError} with the given error message.
	 * 
	 * @param validationPredicate	The test to apply on the value
	 * @param errorMessage			The error message to use if validation fails
	 * 
	 * @return	a new builder
	 * 
	 * @see ValidationFailure
	 * @see GenericValidationError
	 */
	@Nonnull
	SELF validate(@Nonnull Predicate<OUT> validationPredicate, @Nonnull String errorMessage);

	// ------------------------------------------------------------------------------------------------------------
	// MAPPING

	/**
	 * Map the output value of this guard into another type, and return a new {@link InputGuardBuilder} for this new type,
	 * using the provided function.
	 * The new builder will be of the same type as this one, i.e. it will have the same behavior and available methods.
	 * 
	 * @param <NEW_OUT>	The new output value type
	 * @param <B>		The new guard builder type
	 * 
	 * @param mappingFunction	The mapping function to apply to this guard's output value
	 * 
	 * @return	a new builder of type B
	 */
	@Nonnull
	<NEW_OUT, B extends InputGuardBuilder<IN, NEW_OUT, B>> InputGuardBuilder<IN, NEW_OUT, B> map(
		@Nonnull Function<OUT, NEW_OUT> mappingFunction
	);

	/**
	 * Map the output value of this guard into another type, and return a new {@link InputGuardBuilder} for this new type,
	 * using the provided function.
	 * The new builder will be created by the given builder function, which takes as input the new guard that would be created by applying the mapping function to this guard.
	 * This allows to create a new builder of a different type than this one, with different behavior and available methods if needed.
	 * 
	 * @param <NEW_OUT>	The new output value type
	 * @param <B>		The new guard builder type
	 * 
	 * @param mappingFunction	The mapping function to apply to this guard's output value
	 * @param builderFunction	The function to create the new builder, it takes as input the new guard that would be created by applying the mapping function to this guard
	 * 
	 * @return	a new builder of type B
	 */
	@Nonnull
	<NEW_OUT, B extends InputGuardBuilder<IN, NEW_OUT, B>> B map(
		@Nonnull Function<OUT, NEW_OUT> mappingFunction, @Nonnull Function<InputGuard<IN, NEW_OUT>, B> builderFunction
	);

	// ------------------------------------------------------------------------------------------------------------
	// SUB-GUARD

	/**
	 * Apply another guard on the output value of this guard, and return a new builder for this guard.
	 * The new builder will be of the same type as this one, i.e. it will have the same behavior and available methods.
	 * 
	 * @param guard	The guard to apply on the output value of this guard
	 * 
	 * @return	a new builder
	 */
	@Nonnull
	SELF apply(@Nonnull InputGuard<OUT, OUT> guard);

}
