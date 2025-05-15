package io.github.jinputguard.builder.base.types;

import io.github.jinputguard.builder.base.AbstractSanitizationBuilder;

public class ObjectSanitizationBuilder<IN, OUT> extends AbstractSanitizationBuilder<IN, OUT, ObjectInputGuardBuilder<IN, OUT>, ObjectSanitizationBuilder<IN, OUT>> {

	public ObjectSanitizationBuilder(ObjectInputGuardBuilder<IN, OUT> builder) {
		super(builder);
	}

}
