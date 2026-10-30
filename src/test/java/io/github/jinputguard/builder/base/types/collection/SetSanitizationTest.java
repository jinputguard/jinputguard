package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class SetSanitizationTest {

	@Test
	void nominal() {
		var setGuard = InputGuard.builder().forSet(String.class)
			.sanitize(set -> set.stream().filter(str -> str.length() >= 2).collect(Collectors.toSet()))
			.build();

		var actualResult = setGuard.process(Set.of("a", "bb", "ccc", "d", "eeee", "f", ""), "myVal");

		GuardResultAssert.assertThat(actualResult)
			.isSuccess(Set.of("bb", "ccc", "eeee"));
	}

}