package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.GuardFailure;
import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import java.util.Set;
import org.junit.jupiter.api.Test;

class SetValidationTest {

	@Test
	void nominal_success() {
		var setGuard = InputGuard.builder().forSet(String.class)
			.validate((value, path) -> value.isEmpty() ? GuardFailure.from(path, "value is wrong") : null)
			.build();

		var actualResult = setGuard.process(Set.of("a"), "myVal");

		GuardResultAssert.assertThat(actualResult)
			.isSuccess(Set.of("a"));
	}

	@Test
	void nominal_failure() {
		var setGuard = InputGuard.builder().forSet(String.class)
			.validate((value, path) -> value.isEmpty() ? GuardFailure.from(path, "value is wrong") : null)
			.build();

		var actualResult = setGuard.process(Set.of(), "myVal");

		GuardResultAssert.assertThat(actualResult)
			.isFailure()
			.hasPathEqualTo("myVal")
			.hasMessage("value is wrong")
			.hasNoCause();
	}

}