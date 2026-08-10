package io.github.jinputguard.builder.base;

import io.github.jinputguard.InputGuardBuilder;
import io.github.jinputguard.failure.BaseValidationErrors;
import java.util.Objects;

public abstract class AbstractValidationBuilder<IN, T, B extends InputGuardBuilder<IN, T, B>, SELF extends AbstractValidationBuilder<IN, T, B, SELF>>
	extends AbstractIntermediateWithReturnBuilder<IN, T, B, SELF> {

	protected AbstractValidationBuilder(B builder) {
		super(builder);
	}

	/**
	 * Validate that {@code input != null}.
	 * If not, generates {@link ObjectIsNull}.
	 */
	public final SELF isNotNull() {
		builder = notNullValueBuilder();
		return cast();
	}

	/**
	 * Validate that {@code clazz.isInstance(input)}.
	 * If not, generates {@link ObjectMustBeInstanceOf}.
	 * 
	 * @param other	the clas reference, cannot be <code>null</code>
	 */
	public final <U extends T> SELF isInstanceOf(Class<U> clazz) {
		Objects.requireNonNull(clazz, "Expected class cannot be null");
		builder = notNullValueBuilder().validate(
			(value, path) -> !clazz.isInstance(value)
				? BaseValidationErrors.OBJECT_MUST_BE_INSTANCE_OF.toFailure(path, clazz.getName())
				: null
		);
		return cast();
	}

	/**
	 * Validate that {@code Objects.equals(input, other)}.
	 * If not, generates {@link ObjectMustBeEqualTo}.
	 * 
	 * @param other	the comparison reference, may be <code>null</code>
	 */
	public final SELF isEqualTo(T other) {
		if (other == null) {
			builder = builder.validate(
				(value, path) -> value != null
					? BaseValidationErrors.OBJECT_MUST_BE_NULL.toFailure(path)
					: null
			);
		} else {
			builder = notNullValueBuilder().validate(
				(value, path) -> !Objects.equals(value, other)
					? BaseValidationErrors.OBJECT_MUST_BE_EQUAL_TO.toFailure(path, other)
					: null
			);
		}
		return cast();
	}

}
