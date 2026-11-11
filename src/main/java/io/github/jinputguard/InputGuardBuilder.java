package io.github.jinputguard;

import io.github.jinputguard.builder.base.NullStrategyBuilder;
import io.github.jinputguard.builder.base.types.StringInputGuardBuilder;
import io.github.jinputguard.guard.InputGuards;
import jakarta.annotation.Nonnull;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

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
	SELF sanitize(@Nonnull UnaryOperator<OUT> sanitizationFunction);

	// ------------------------------------------------------------------------------------------------------------
	// VALIDATION

	/**
	 * Validate the value using the given validation function.
	 * In case of failure, the guard will fail with a {@link GuardFailure} created by the validation function.
	 * 
	 * @param validationFunction	A function that takes the output value and the path, and returns a {@link GuardFailure} if the value is invalid, or <code>null</code> if the value is valid
	 * 
	 * @return	a new builder
	 */
	@Nonnull
	SELF validate(@Nonnull BiFunction<OUT, String, GuardFailure> validationFunction);

	/**
	 * Validate the value using the given predicate.
	 * In case of failure, the guard will fail with a {@link ValidationFailure} containing a {@link ValidationError#GenericError} with the given error message.
	 * 
	 * @param validationPredicate	The test to apply on the value, returning <code>true</code> if the value is valid, or <code>false</code> if the value is invalid
	 * @param errorMessageFunction	A function that takes the path as input and returns an error message to use in case of failure
	 * 
	 * @return	a new builder
	 * 
	 * @see ValidationFailure
	 * @see GenericValidationError
	 */
	@Nonnull
	SELF validate(@Nonnull Predicate<OUT> validationPredicate, @Nonnull UnaryOperator<String> errorMessageFunction);

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
	 * @param failureMessageFunction	The function to create a failure message in case the mapping function throws an exception, it takes as input the path of the value being processed
	 * 
	 * @return	a new builder of type B
	 */
	@Nonnull
	<NEW_OUT, B extends InputGuardBuilder<IN, NEW_OUT, B>> InputGuardBuilder<IN, NEW_OUT, B> map(
		@Nonnull Function<OUT, NEW_OUT> mappingFunction, @Nonnull UnaryOperator<String> failureMessageFunction
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
	 * @param failureMessageFunction	The function to create a failure message in case the mapping function throws an exception, it takes as input the path of the value being processed
	 * @param builderFunction	The function to create the new builder, it takes as input the new guard that would be created by applying the mapping function to this guard
	 * 
	 * @return	a new builder of type B
	 */
	@Nonnull
	<NEW_OUT, B extends InputGuardBuilder<IN, NEW_OUT, B>> B map(
		@Nonnull Function<OUT, NEW_OUT> mappingFunction, @Nonnull UnaryOperator<String> failureMessageFunction, @Nonnull Function<InputGuard<IN, NEW_OUT>, B> builderFunction
	);

	/**
	 * Map the output of this builder to a String by calling the {@code toString()} method on the value, and return a new {@link StringInputGuardBuilder}.
	 * 
	 * @return a new builder of type {@link StringInputGuardBuilder}
	 */
	public default StringInputGuardBuilder<IN> mapToString() {
		return new StringInputGuardBuilder<>(InputGuards.mappingGuard(this.build(), Object::toString, p -> p + " cannot be mapped to String"));
	}

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
