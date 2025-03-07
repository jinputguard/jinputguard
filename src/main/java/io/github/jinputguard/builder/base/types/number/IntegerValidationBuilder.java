package io.github.jinputguard.builder.base.types.number;

public class IntegerValidationBuilder<IN> extends AbstractNumberValidationBuilder<IN, Integer, IntegerInputGuardBuilder<IN>, IntegerValidationBuilder<IN>> {

	public IntegerValidationBuilder(IntegerInputGuardBuilder<IN> builder) {
		super(builder, IntegerTester.INSTANCE);
	}

}
