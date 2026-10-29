package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.GuardFailure;
import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Nested
class ListValidationTest {

	@Test
	void nominal_success() {
		var listGuard = InputGuard.builder().forList(String.class)
			.validate((value, path) -> value.isEmpty() ? GuardFailure.from(path, "value is wrong") : null)
			.build();

		var actualResult = listGuard.process(List.of("a"), "myVal");

		GuardResultAssert.assertThat(actualResult)
			.isSuccess(List.of("a"));

		Assertions.assertThat(actualResult.get())
			.isUnmodifiable();
	}

	@Test
	void nominal_failure() {
		var listGuard = InputGuard.builder().forList(String.class)
			.validate((value, path) -> value.isEmpty() ? GuardFailure.from(path, "value is wrong") : null)
			.build();

		var actualResult = listGuard.process(List.of(), "myVal");

		GuardResultAssert.assertThat(actualResult)
			.isFailure()
			.hasPathEqualTo("myVal")
			.hasMessage("value is wrong")
			.hasNoCause();
	}

}