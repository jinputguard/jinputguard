package io.github.jinputguard.builder.base.types.number;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

abstract class AbstractNumberMappingTest<T extends Number> extends AbstractNumberTest<T> {

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class MapToInt {

		InputGuard<T, Integer> guard = initiateGuard().mapToInt().build();

		Arguments[] mapToInt_arguments() {
			return new Arguments[] {
				// Value, ref, success
				Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getMinValue().intValue()),
				Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getMinusOne().intValue()),
				Arguments.of(getNumberProvider().getZero(), getNumberProvider().getZero().intValue()),
				Arguments.of(getNumberProvider().getOne(), getNumberProvider().getOne().intValue()),
				Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getMaxValue().intValue()),
			};
		}

		@ParameterizedTest
		@MethodSource("mapToInt_arguments")
		void mapToInt(T testedValue, int expectedValue) {
			GuardResultAssert.assertThat(guard.process(testedValue, "myVal")).as("mapToInt(" + testedValue + ")").isSuccess(expectedValue);
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class MapToLong {

		InputGuard<T, Long> guard = initiateGuard().mapToLong().build();

		Arguments[] mapToLong_arguments() {
			return new Arguments[] {
				// Value, ref, success
				Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getMinValue().longValue()),
				Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getMinusOne().longValue()),
				Arguments.of(getNumberProvider().getZero(), getNumberProvider().getZero().longValue()),
				Arguments.of(getNumberProvider().getOne(), getNumberProvider().getOne().longValue()),
				Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getMaxValue().longValue()),
			};
		}

		@ParameterizedTest
		@MethodSource("mapToLong_arguments")
		void mapToLong(T testedValue, long expectedValue) {
			GuardResultAssert.assertThat(guard.process(testedValue, "myVal")).as("mapToLong(" + testedValue + ")").isSuccess(expectedValue);
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class MapToFloat {

		InputGuard<T, Float> guard = initiateGuard().mapToFloat().build();

		Arguments[] mapToFloat_arguments() {
			return new Arguments[] {
				// Value, ref, success
				Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getMinValue().floatValue()),
				Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getMinusOne().floatValue()),
				Arguments.of(getNumberProvider().getZero(), getNumberProvider().getZero().floatValue()),
				Arguments.of(getNumberProvider().getOne(), getNumberProvider().getOne().floatValue()),
				Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getMaxValue().floatValue()),
			};
		}

		@ParameterizedTest
		@MethodSource("mapToFloat_arguments")
		void mapToFloat(T testedValue, float expectedValue) {
			GuardResultAssert.assertThat(guard.process(testedValue, "myVal")).as("mapToFloat(" + testedValue + ")").isSuccess(expectedValue);
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class MapToDouble {

		InputGuard<T, Double> guard = initiateGuard().mapToDouble().build();

		Arguments[] mapToDouble_arguments() {
			return new Arguments[] {
				// Value, ref, success
				Arguments.of(getNumberProvider().getMinValue(), getNumberProvider().getMinValue().doubleValue()),
				Arguments.of(getNumberProvider().getMinusOne(), getNumberProvider().getMinusOne().doubleValue()),
				Arguments.of(getNumberProvider().getZero(), getNumberProvider().getZero().doubleValue()),
				Arguments.of(getNumberProvider().getOne(), getNumberProvider().getOne().doubleValue()),
				Arguments.of(getNumberProvider().getMaxValue(), getNumberProvider().getMaxValue().doubleValue()),
			};
		}

		@ParameterizedTest
		@MethodSource("mapToDouble_arguments")
		void mapToDouble(T testedValue, double expectedValue) {
			GuardResultAssert.assertThat(guard.process(testedValue, "myVal")).as("mapToDouble(" + testedValue + ")").isSuccess(expectedValue);
		}

	}

}
