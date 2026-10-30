package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Nested
class SetMappingTest {

	@Test
	void nominal() {
		var setGuard = InputGuard.builder().forSet(String.class)
			.map(set -> set.stream().map(Integer::parseInt).collect(Collectors.toSet()), path -> "Invalid " + path)
			.build();

		var actualResult = setGuard.process(Set.of("0", "1", "2"), "myVal");

		GuardResultAssert.assertThat(actualResult)
			.isSuccess(Set.of(0, 1, 2));
	}

}