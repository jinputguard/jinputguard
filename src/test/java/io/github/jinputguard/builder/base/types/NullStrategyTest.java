package io.github.jinputguard.builder.base.types;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class NullStrategyTest {

	@Test
	void when_no_nullStrategy_then_nullIsProcessed() {
		var guard = InputGuard.builder().forClass(Object.class)
			// no null strategy
			.sanitize(value -> value + "-1")
			.build();

		var actualResult = guard.process(null, "myVal");

		GuardResultAssert.assertThat(actualResult).isSuccess("null-1");
	}

	@Test
	void keepProcessing() {
		var guard = InputGuard.builder().forClass(Object.class)
			.ifNullThen().keepProcessing()
			.sanitize(value -> value + "-1")
			.build();

		GuardResultAssert.assertThat(guard.process(null, "myVal")).isSuccess("null-1");
		GuardResultAssert.assertThat(guard.process("val", "myVal")).isSuccess("val-1");
	}

	@Test
	void returnNull() {
		var guard = InputGuard.builder().forClass(Object.class)
			.ifNullThen().returnNull()
			.sanitize(value -> value + "-1")
			.build();

		GuardResultAssert.assertThat(guard.process(null, "myVal")).isSuccess(null);
		GuardResultAssert.assertThat(guard.process("val", "myVal")).isSuccess("val-1");
	}

	@Test
	void fail() {
		var guard = InputGuard.builder().forClass(Object.class)
			.ifNullThen().fail()
			.sanitize(value -> value + "-1")
			.build();

		GuardResultAssert.assertThat(guard.process(null, "myVal")).isFailure();
		GuardResultAssert.assertThat(guard.process("val", "myVal")).isSuccess("val-1");
	}

	@Test
	void useDefault() {
		var guard = InputGuard.builder().forClass(Object.class)
			.ifNullThen().useDefault("default")
			.sanitize(value -> value + "-1")
			.build();

		GuardResultAssert.assertThat(guard.process(null, "myVal")).isSuccess("default-1");
		GuardResultAssert.assertThat(guard.process("val", "myVal")).isSuccess("val-1");
	}

	@Test
	void when_useDefault_with_null_then_NPE() {
		Assertions.assertThatNullPointerException()
			.isThrownBy(
				() -> InputGuard.builder().forClass(Object.class)
					.ifNullThen().useDefault(null) // null default value is not allowed
					.build()
			);
	}

	@Test
	void mixedStrategies_1() {
		var guard = InputGuard.builder().forClass(Object.class)
			.ifNullThen().returnNull()
			.ifNullThen().keepProcessing()
			.ifNullThen().returnNull()
			.sanitize(value -> value + "-1")
			.build();

		GuardResultAssert.assertThat(guard.process(null, "myVal")).isSuccess(null);
		GuardResultAssert.assertThat(guard.process("val", "myVal")).isSuccess("val-1");
	}

	@Test
	void mixedStrategies_2() {
		var guard = InputGuard.builder().forClass(Object.class)
			.ifNullThen().returnNull()
			.ifNullThen().keepProcessing()
			.ifNullThen().useDefault("plop")
			.ifNullThen().returnNull()
			.ifNullThen().keepProcessing()
			.sanitize(value -> value + "-1")
			.build();

		GuardResultAssert.assertThat(guard.process(null, "myVal")).isSuccess("plop-1");
		GuardResultAssert.assertThat(guard.process("val", "myVal")).isSuccess("val-1");
	}

	@Test
	void mixedStrategies_3() {
		var guard = InputGuard.builder().forClass(Object.class)
			.ifNullThen().returnNull()
			.sanitize(value -> value + "-1")
			.ifNullThen().keepProcessing()
			.sanitize(value -> value + "-2")
			.ifNullThen().returnNull()
			.sanitize(value -> value + "-3")
			.ifNullThen().keepProcessing()
			.sanitize(value -> value + "-4")
			.ifNullThen().useDefault("default")
			.sanitize(value -> value + "-5")
			.build();

		GuardResultAssert.assertThat(guard.process(null, "myVal")).isSuccess("null-2-3-4-5");
		GuardResultAssert.assertThat(guard.process("val", "myVal")).isSuccess("val-1-2-3-4-5");
	}

}