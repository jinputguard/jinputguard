package io.github.jinputguard.builder.base.types.number;

public class IntegerSanitizationBuilder<IN> extends AbstractNumberSanitizationBuilder<IN, Integer, IntegerInputGuardBuilder<IN>, IntegerSanitizationBuilder<IN>> {

	public IntegerSanitizationBuilder(IntegerInputGuardBuilder<IN> builder) {
		super(builder, IntegerTester.INSTANCE);
	}

	@Override
	public IntegerSanitizationBuilder<IN> abs() {
		builder = builder.sanitize(value -> Math.abs(value));
		return cast();
	}

}
