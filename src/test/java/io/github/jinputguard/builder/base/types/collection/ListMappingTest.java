package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ListMappingTest {

	@Test
	void nominal() {
		var listGuard = InputGuard.builder().forList(String.class)
			.map(list -> list.stream().map(Integer::parseInt).toList(), path -> "Invalid " + path)
			.build();

		var actualResult = listGuard.process(List.of("0", "1", "2"), "myVal");

		GuardResultAssert.assertThat(actualResult)
			.isSuccess(List.of(0, 1, 2));

		Assertions.assertThat(actualResult.get())
			.isUnmodifiable();
	}

}