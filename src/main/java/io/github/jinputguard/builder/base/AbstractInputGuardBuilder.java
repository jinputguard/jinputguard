package io.github.jinputguard.builder.base;

import io.github.jinputguard.GuardFailure;
import io.github.jinputguard.InputGuard;
import io.github.jinputguard.InputGuardBuilder;
import io.github.jinputguard.builder.base.types.ObjectInputGuardBuilder;
import io.github.jinputguard.failure.BaseValidationErrors;
import io.github.jinputguard.guard.InputGuards;
import jakarta.annotation.Nonnull;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public abstract class AbstractInputGuardBuilder<IN, OUT, SELF extends AbstractInputGuardBuilder<IN, OUT, SELF>> implements InputGuardBuilder<IN, OUT, SELF> {

	private final @Nonnull InputGuard<IN, OUT> guard;

	protected AbstractInputGuardBuilder(InputGuard<IN, OUT> previous) {
		this.guard = Objects.requireNonNull(previous);
	}

	@Override
	public InputGuard<IN, OUT> build() {
		return guard;
	}

	@Override
	@SuppressWarnings("unchecked")
	public NullStrategyBuilder<IN, OUT, SELF> ifNullThen() {
		return new NullStrategyBuilder<>((SELF) this);
	}

	// ------------------------------------------------------------------------------------------------------------
	// SANITIZATION

	@Override
	public SELF sanitize(UnaryOperator<OUT> sanitizationFunction) {
		return apply(InputGuards.sanitizationGuard(sanitizationFunction));
	}

	protected SELF sanitizeOrNullFail(UnaryOperator<OUT> sanitizationFunction) {
		return requireNotNull().sanitize(sanitizationFunction);
	}

	// ------------------------------------------------------------------------------------------------------------
	// VALIDATION

	@Override
	public SELF validate(@Nonnull BiFunction<OUT, String, GuardFailure> validationFunction) {
		return apply(InputGuards.validationGuard(validationFunction));
	}

	@Override
	public SELF validate(Predicate<OUT> validationPredicate, UnaryOperator<String> messageTemplateFunction) {
		return this.validate((value, path) -> validationPredicate.test(value) ? null : GuardFailure.from(path, messageTemplateFunction.apply(path)));
	}

	/**
	 * Validates that the value is not null.
	 * If the value is <code>null</code>, it will fail the validation.
	 */
	public final SELF requireNotNull() {
		return ifNullThen().fail();
	}

	/**
	 * Validates that the value is an instance of the given class.
	 * If the value is <code>null</code>, it will fail the validation.
	 */
	public final <T extends OUT> SELF requireInstanceOf(Class<T> clazz) {
		Objects.requireNonNull(clazz, "Expected class cannot be null");
		return requireNotNull().validate(
			(value, path) -> clazz.isInstance(value)
				? null
				: BaseValidationErrors.OBJECT_MUST_BE_INSTANCE_OF.toFailure(path, clazz.getName())
		);
	}

	/**
	 * Validates that the value is equal to the given other value.
	 * If the other value is <code>null</code>, it will validate that the value is also <code>null</code>.
	 */
	public final SELF requireEqualTo(OUT other) {
		if (other == null) {
			return validate(
				(value, path) -> value != null
					? BaseValidationErrors.OBJECT_MUST_BE_NULL.toFailure(path)
					: null
			);
		} else {
			return requireNotNull().validate(
				(value, path) -> Objects.equals(value, other)
					? null
					: BaseValidationErrors.OBJECT_MUST_BE_EQUAL_TO.toFailure(path, other)
			);
		}
	}

	// ------------------------------------------------------------------------------------------------------------
	// MAPPING

	@SuppressWarnings("unchecked")
	@Override
	public <NEW_OUT, B extends InputGuardBuilder<IN, NEW_OUT, B>> InputGuardBuilder<IN, NEW_OUT, B> map(
		Function<OUT, NEW_OUT> mappingFunction, @Nonnull UnaryOperator<String> failureMessageFunction
	) {
		return (InputGuardBuilder<IN, NEW_OUT, B>) map(mappingFunction, failureMessageFunction, ObjectInputGuardBuilder::new);
	}

	@Override
	public <NEW_OUT, B extends InputGuardBuilder<IN, NEW_OUT, B>> B map(
		@Nonnull Function<OUT, NEW_OUT> mappingFunction, @Nonnull UnaryOperator<String> failureMessageFunction, @Nonnull Function<InputGuard<IN, NEW_OUT>, B> builderFunction
	) {
		return builderFunction.apply(InputGuards.mappingGuard(this.build(), mappingFunction, failureMessageFunction));
	}

	// ------------------------------------------------------------------------------------------------------------
	// SUB-GUARD

	@Override
	public SELF apply(InputGuard<OUT, OUT> guard) {
		return newInstance(this.build().andThen(guard));
	}

	protected abstract SELF newInstance(InputGuard<IN, OUT> guard);

}
