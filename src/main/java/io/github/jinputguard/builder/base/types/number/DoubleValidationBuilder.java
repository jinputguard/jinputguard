package io.github.jinputguard.builder.base.types.number;

public class DoubleValidationBuilder<IN> extends AbstractNumberValidationBuilder<IN, Double, DoubleInputGuardBuilder<IN>, DoubleValidationBuilder<IN>> {

	public DoubleValidationBuilder(DoubleInputGuardBuilder<IN> builder) {
		super(builder, DoubleTester.INSTANCE);
	}

}
