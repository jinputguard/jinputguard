package io.github.jinputguard.builder.base;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.InputGuardBuilder;
import io.github.jinputguard.builder.InputGuards;
import io.github.jinputguard.builder.base.types.ObjectInputGuardBuilder;
import io.github.jinputguard.guard.validation.ValidationError;
import io.github.jinputguard.result.ErrorDetails;
import jakarta.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractInputGuardBuilder<IN, OUT, SELF extends AbstractInputGuardBuilder<IN, OUT, SELF>> implements InputGuardBuilder<IN, OUT, SELF> {

	private final @Nonnull InputGuard<IN, OUT> guard;

	protected AbstractInputGuardBuilder(InputGuard<IN, OUT> previous) {
		this.guard = Objects.requireNonNull(previous);
	}

	protected abstract SELF newInstance(InputGuard<IN, OUT> guard);

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
	public SELF sanitize(Function<OUT, OUT> sanitizationFunction) {
		return apply(InputGuards.sanitizationGuard(sanitizationFunction));
	}

	// ------------------------------------------------------------------------------------------------------------
	// VALIDATION

	@Override
	public SELF validate(@Nonnull Function<OUT, ErrorDetails> validationFunction) {
		return apply(InputGuards.validationGuard(validationFunction));
	}

	@Override
	public SELF validate(Predicate<OUT> validationPredicate, Function<OUT, String> errorMessageFunction) {
		return this.validate(value -> validationPredicate.test(value) ? null : new ValidationError.GenericValidationError(errorMessageFunction.apply(value)));
	}

	@Override
	public SELF validate(Predicate<OUT> validationPredicate, String errorMessage) {
		return this.validate(validationPredicate, value -> errorMessage);
	}

	// ------------------------------------------------------------------------------------------------------------
	// MAPPING

	@SuppressWarnings("unchecked")
	@Override
	public <NEW_OUT, B extends InputGuardBuilder<IN, NEW_OUT, B>> InputGuardBuilder<IN, NEW_OUT, B> map(
		Function<OUT, NEW_OUT> mappingFunction
	) {
		return (InputGuardBuilder<IN, NEW_OUT, B>) map(mappingFunction, ObjectInputGuardBuilder::new);
	}

	@Override
	public <NEW_OUT, B extends InputGuardBuilder<IN, NEW_OUT, B>> B map(
		@Nonnull Function<OUT, NEW_OUT> mappingFunction, @Nonnull Function<InputGuard<IN, NEW_OUT>, B> builderFunction
	) {
		return builderFunction.apply(InputGuards.mappingGuard(this.build(), mappingFunction));
	}

	// ------------------------------------------------------------------------------------------------------------
	// SUB-GUARD

	@Override
	public SELF apply(InputGuard<OUT, OUT> guard) {
		return newInstance(this.build().andThen(guard));
	}

}
