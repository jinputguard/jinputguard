package io.github.jinputguard.builder.base.types.number;

public class LongSanitizationBuilder<IN> extends AbstractNumberSanitizationBuilder<IN, Long, LongInputGuardBuilder<IN>, LongSanitizationBuilder<IN>> {

	public LongSanitizationBuilder(LongInputGuardBuilder<IN> builder) {
		super(builder, LongTester.INSTANCE);
	}

	@Override
	public LongSanitizationBuilder<IN> abs() {
		builder = builder.sanitize(value -> Math.abs(value));
		return cast();
	}

}
