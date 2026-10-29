package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Nested
class ListFilterTest {

	@Test
	void nominal() {
		Predicate<String> filter = value -> !value.isEmpty();
		var listGuard = InputGuard.builder().forList(String.class)
			.filter(filter)
			.build();

		var actualResult = listGuard.process(List.of("", " ", " a", "b ", " c "), "myVal");

		GuardResultAssert.assertThat(actualResult)
			.isSuccess(List.of(" ", " a", "b ", " c "));

		Assertions.assertThat(actualResult.get())
			.isUnmodifiable();
	}

	@Test
	void nominal_withCollector() {
		Predicate<String> filter = value -> !value.isEmpty();
		var listGuard = InputGuard.builder().forList(String.class)
			.filter(filter, Collectors.toCollection(() -> new LinkedList<>()))
			.build();

		var actualResult = listGuard.process(List.of("", " ", " a", "b ", " c "), "myVal");

		GuardResultAssert.assertThat(actualResult)
			.isSuccess(List.of(" ", " a", "b ", " c "));

		Assertions.assertThat(actualResult.get())
			.isExactlyInstanceOf(LinkedList.class);
	}

}