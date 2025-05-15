package io.github.jinputguard.builder.base.types.number;

public class FloatSanitizationBuilder<IN> extends AbstractNumberSanitizationBuilder<IN, Float, FloatInputGuardBuilder<IN>, FloatSanitizationBuilder<IN>> {

	public FloatSanitizationBuilder(FloatInputGuardBuilder<IN> builder) {
		super(builder, FloatTester.INSTANCE);
	}

	@Override
	public FloatSanitizationBuilder<IN> abs() {
		builder = builder.sanitize(value -> Math.abs(value));
		return cast();
	}

}
