package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Nested
class SetFilterTest {

	@Test
	void nominal() {
		Predicate<String> filter = value -> !value.isEmpty();
		var setGuard = InputGuard.builder().forSet(String.class)
			.filter(filter)
			.build();

		var actualResult = setGuard.process(Set.of("", " ", " a", "b ", " c "), "myVal");

		GuardResultAssert.assertThat(actualResult)
			.isSuccess(Set.of(" ", " a", "b ", " c "));

		Assertions.assertThat(actualResult.get())
			.isUnmodifiable();
	}

	@Test
	void nominal_withCollector() {
		Predicate<String> filter = value -> !value.isEmpty();
		var setGuard = InputGuard.builder().forSet(String.class)
			.filter(filter, Collectors.toCollection(() -> new HashSet<>()))
			.build();

		var actualResult = setGuard.process(Set.of("", " ", " a", "b ", " c "), "myVal");

		GuardResultAssert.assertThat(actualResult)
			.isSuccess(Set.of(" ", " a", "b ", " c "));

		Assertions.assertThat(actualResult.get())
			.isExactlyInstanceOf(HashSet.class);
	}

}