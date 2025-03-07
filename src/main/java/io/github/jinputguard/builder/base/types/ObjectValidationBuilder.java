package io.github.jinputguard.builder.base.types;

import io.github.jinputguard.builder.base.AbstractValidationBuilder;

public class ObjectValidationBuilder<IN, OUT> extends AbstractValidationBuilder<IN, OUT, ObjectInputGuardBuilder<IN, OUT>, ObjectValidationBuilder<IN, OUT>> {

	public ObjectValidationBuilder(ObjectInputGuardBuilder<IN, OUT> builder) {
		super(builder);
	}

}
