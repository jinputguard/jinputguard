package io.github.jinputguard.builder.base.types.number;

public class LongValidationBuilder<IN> extends AbstractNumberValidationBuilder<IN, Long, LongInputGuardBuilder<IN>, LongValidationBuilder<IN>> {

	public LongValidationBuilder(LongInputGuardBuilder<IN> builder) {
		super(builder, LongTester.INSTANCE);
	}

}
