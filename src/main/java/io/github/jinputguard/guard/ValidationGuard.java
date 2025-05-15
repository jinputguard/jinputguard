package io.github.jinputguard.guard;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.result.GuardResult;
import io.github.jinputguard.result.ValidationError;
import io.github.jinputguard.result.ValidationFailure;
import jakarta.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Function;

public class ValidationGuard<T> implements InputGuard<T, T> {

	@Nonnull
	private final Function<T, ValidationError> validationFailureFunction;

	public ValidationGuard(
		@Nonnull Function<T, ValidationError> validationFailureFunction
	) {
		this.validationFailureFunction = Objects.requireNonNull(validationFailureFunction, "Validation failure function cannot be null");
	}

	@Override
	public GuardResult<T> process(T value) {
		var error = validationFailureFunction.apply(value);
		return error == null
			? GuardResult.success(value)
			: GuardResult.failure(new ValidationFailure(value, error));
	}

	@Override
	public String toString() {
		return "ValidationGuard -> " + validationFailureFunction.toString();
	}

}
