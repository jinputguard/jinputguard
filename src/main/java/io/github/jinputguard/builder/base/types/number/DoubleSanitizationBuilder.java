package io.github.jinputguard.builder.base.types.number;

public class DoubleSanitizationBuilder<IN> extends AbstractNumberSanitizationBuilder<IN, Double, DoubleInputGuardBuilder<IN>, DoubleSanitizationBuilder<IN>> {

	public DoubleSanitizationBuilder(DoubleInputGuardBuilder<IN> builder) {
		super(builder, DoubleTester.INSTANCE);
	}

}
