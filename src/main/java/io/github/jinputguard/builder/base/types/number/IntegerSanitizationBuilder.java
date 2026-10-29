package io.github.jinputguard.builder.base.types.number;

public class IntegerSanitizationBuilder<IN> extends AbstractNumberSanitizationBuilder<IN, Integer, IntegerInputGuardBuilder<IN>, IntegerSanitizationBuilder<IN>> {

	public IntegerSanitizationBuilder(IntegerInputGuardBuilder<IN> builder) {
		super(builder, IntegerTester.INSTANCE);
	}

}
