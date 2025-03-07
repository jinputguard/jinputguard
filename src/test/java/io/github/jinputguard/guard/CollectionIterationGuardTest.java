package io.github.jinputguard.guard;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.InputGuards;
import io.github.jinputguard.guard.CollectionIterationGuard;
import io.github.jinputguard.result.GuardResult;
import io.github.jinputguard.util.Predicates;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CollectionIterationGuardTest {

	@Test
	void itemGuard_cannot_be_null() {
		Predicate<Object> filter = Predicates.alwaysTrue();
		InputGuard<Object, Object> elementGuard = null;
		Collector<Object, ?, List<Object>> collector = Collectors.toList();
		Assertions.assertThatNullPointerException().isThrownBy(() -> new CollectionIterationGuard<>(filter, elementGuard, collector));
	}

	@Test
	void collector_cannot_be_null() {
		Predicate<Object> filter = Predicates.alwaysTrue();
		InputGuard<Object, Object> elementGuard = InputGuards.noOpGuard();
		Collector<Object, ?, List<Object>> collector = null;
		Assertions.assertThatNullPointerException().isThrownBy(() -> new CollectionIterationGuard<>(filter, elementGuard, collector));
	}

	@Nested
	class ToStringTest {

		@Test
		void nominal() {
			Predicate<Object> filter = Predicates.alwaysTrue();
			InputGuard<Object, Object> elementGuard = value -> GuardResult.success(value);
			Collector<Object, ?, Set<Object>> collector = Collectors.toSet();
			var guard = new CollectionIterationGuard<>(filter, elementGuard, collector);

			var actual = guard.toString();

			Assertions.assertThat(actual)
				.startsWith("CollectionIterationGuard")
				.contains("Filter: " + filter.toString())
				.contains("Guard: " + elementGuard.toString())
				.contains("Collector: " + collector.toString());
		}

	}

}
