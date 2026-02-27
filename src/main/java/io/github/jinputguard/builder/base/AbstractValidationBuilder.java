package io.github.jinputguard.builder.base;

import io.github.jinputguard.InputGuardBuilder;
import io.github.jinputguard.builder.base.types.ObjectValidationError;
import io.github.jinputguard.builder.base.types.ObjectValidationError.ObjectIsNull;
import io.github.jinputguard.builder.base.types.ObjectValidationError.ObjectMustBeEqualTo;
import io.github.jinputguard.builder.base.types.ObjectValidationError.ObjectMustBeInstanceOf;
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
		builder = builder.validate(
			value -> value == null
				? new ObjectValidationError.ObjectIsNull()
				: null
		);
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
		builder = builder.validate(
			value -> !clazz.isInstance(value)
				? new ObjectValidationError.ObjectMustBeInstanceOf(value == null ? null : value.getClass(), clazz)
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
		builder = builder.validate(
			value -> !Objects.equals(value, other)
				? new ObjectValidationError.ObjectMustBeEqualTo(other)
				: null
		);
		return cast();
	}

}
