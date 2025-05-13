package io.github.jinputguard.builder.base.types.number;

public interface NumberTester<T> {

	abstract T getZero();

	abstract T getMin();

	abstract T getMax();

	abstract boolean isLowerThan(T value, T ref);

	abstract boolean isGreaterThan(T value, T ref);

	abstract boolean isLowerOrEqualTo(T value, T ref);

	abstract boolean isGreaterOrEqualTo(T value, T ref);

	default boolean isBetween(T value, T min, T max) {
		return isGreaterOrEqualTo(value, min) && isLowerOrEqualTo(value, max);
	}

}

/**
 * 
 * 
 *
 */
final class IntegerTester implements NumberTester<Integer> {

	public static final IntegerTester INSTANCE = new IntegerTester();

	@Override
	public Integer getZero() {
		return 0;
	}

	@Override
	public Integer getMin() {
		return Integer.MIN_VALUE;
	}

	@Override
	public Integer getMax() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isLowerThan(Integer value, Integer ref) {
		return value.intValue() < ref.intValue();
	}

	@Override
	public boolean isLowerOrEqualTo(Integer value, Integer ref) {
		return value.intValue() <= ref.intValue();
	}

	@Override
	public boolean isGreaterThan(Integer value, Integer ref) {
		return value.intValue() > ref.intValue();
	}

	@Override
	public boolean isGreaterOrEqualTo(Integer value, Integer ref) {
		return value.intValue() >= ref.intValue();
	}

}

/**
 * 
 * 
 *
 */
final class LongTester implements NumberTester<Long> {

	public static final LongTester INSTANCE = new LongTester();

	@Override
	public Long getZero() {
		return 0L;
	}

	@Override
	public Long getMin() {
		return Long.MIN_VALUE;
	}

	@Override
	public Long getMax() {
		return Long.MAX_VALUE;
	}

	@Override
	public boolean isLowerThan(Long value, Long ref) {
		return value.longValue() < ref.longValue();
	}

	@Override
	public boolean isLowerOrEqualTo(Long value, Long ref) {
		return value.longValue() <= ref.longValue();
	}

	@Override
	public boolean isGreaterThan(Long value, Long ref) {
		return value.longValue() > ref.longValue();
	}

	@Override
	public boolean isGreaterOrEqualTo(Long value, Long ref) {
		return value.longValue() >= ref.longValue();
	}

}

/**
 * 
 * 
 *
 */
final class DoubleTester implements NumberTester<Double> {

	public static final DoubleTester INSTANCE = new DoubleTester();

	@Override
	public Double getZero() {
		return 0.;
	}

	@Override
	public Double getMin() {
		return -Double.MAX_VALUE;
	}

	@Override
	public Double getMax() {
		return Double.MAX_VALUE;
	}

	@Override
	public boolean isLowerThan(Double value, Double ref) {
		return value.doubleValue() < ref.doubleValue();
	}

	@Override
	public boolean isLowerOrEqualTo(Double value, Double ref) {
		return value.doubleValue() <= ref.doubleValue();
	}

	@Override
	public boolean isGreaterThan(Double value, Double ref) {
		return value.doubleValue() > ref.doubleValue();
	}

	@Override
	public boolean isGreaterOrEqualTo(Double value, Double ref) {
		return value.doubleValue() >= ref.doubleValue();
	}

}

/**
 * 
 * 
 *
 */
final class FloatTester implements NumberTester<Float> {

	public static final FloatTester INSTANCE = new FloatTester();

	@Override
	public Float getZero() {
		return 0f;
	}

	@Override
	public Float getMin() {
		return -Float.MAX_VALUE;
	}

	@Override
	public Float getMax() {
		return Float.MAX_VALUE;
	}

	@Override
	public boolean isLowerThan(Float value, Float ref) {
		return value.floatValue() < ref.floatValue();
	}

	@Override
	public boolean isLowerOrEqualTo(Float value, Float ref) {
		return value.floatValue() <= ref.floatValue();
	}

	@Override
	public boolean isGreaterThan(Float value, Float ref) {
		return value.floatValue() > ref.floatValue();
	}

	@Override
	public boolean isGreaterOrEqualTo(Float value, Float ref) {
		return value.floatValue() >= ref.floatValue();
	}

}