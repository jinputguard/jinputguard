package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Nested
class ListSanitizationTest {

	@Test
	void nominal() {
		var listGuard = InputGuard.builder().forList(String.class)
			.sanitize(list -> list.stream().filter(str -> str.length() >= 2).toList())
			.build();

		var actualResult = listGuard.process(List.of("a", "bb", "ccc", "d", "eeee", "f", ""), "myVal");

		GuardResultAssert.assertThat(actualResult)
			.isSuccess(List.of("bb", "ccc", "eeee"));
	}

}