package io.github.jinputguard.builder.base.types.number;

sealed interface NumberProvider<T extends Number> {

	T getMinValue();

	T getMinusOne();

	T getZero();

	T getOne();

	T getMaxValue();

	static NumberProvider<Integer> forInteger() {
		return SimpleProvider.INTEGER;
	}

	static NumberProvider<Long> forLong() {
		return SimpleProvider.LONG;
	}

	static NumberProvider<Float> forFloat() {
		return SimpleProvider.FLOAT;
	}

	static NumberProvider<Double> forDouble() {
		return SimpleProvider.DOUBLE;
	}

	final class SimpleProvider<T extends Number> implements NumberProvider<T> {

		// Math.abs() for some MIN_VALUE returns MIN_VALUE for Integer and Long (see javadoc of {@link java.lang.Math#abs(int)}
		private static final SimpleProvider<Integer> INTEGER = new SimpleProvider<Integer>(Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE);
		private static final SimpleProvider<Long> LONG = new SimpleProvider<Long>(Long.MIN_VALUE, -1L, 0L, 1L, Long.MAX_VALUE);
		private static final SimpleProvider<Float> FLOAT = new SimpleProvider<Float>(Float.MIN_VALUE, -1f, 0f, 1f, Float.MAX_VALUE);
		private static final SimpleProvider<Double> DOUBLE = new SimpleProvider<Double>(Double.MIN_VALUE, -1d, 0d, 1d, Double.MAX_VALUE);

		private final T minValue;
		private final T minusOne;
		private final T zero;
		private final T one;
		private final T maxValue;

		private SimpleProvider(T minValue, T minusOne, T zero, T one, T maxValue) {
			this.minValue = minValue;
			this.minusOne = minusOne;
			this.zero = zero;
			this.one = one;
			this.maxValue = maxValue;
		}

		@Override
		public T getMinValue() {
			return minValue;
		}

		@Override
		public T getMinusOne() {
			return minusOne;
		}

		@Override
		public T getZero() {
			return zero;
		}

		@Override
		public T getOne() {
			return one;
		}

		@Override
		public T getMaxValue() {
			return maxValue;
		}

	}

}
