package io.github.jinputguard.builder.base.types.number;

public class FloatValidationBuilder<IN> extends AbstractNumberValidationBuilder<IN, Float, FloatInputGuardBuilder<IN>, FloatValidationBuilder<IN>> {

	public FloatValidationBuilder(FloatInputGuardBuilder<IN> builder) {
		super(builder, FloatTester.INSTANCE);
	}

}
