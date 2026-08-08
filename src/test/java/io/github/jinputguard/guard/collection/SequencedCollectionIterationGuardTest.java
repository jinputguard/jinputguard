package io.github.jinputguard.guard.collection;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.builder.InputGuards;
import io.github.jinputguard.guard.SequencedCollectionIterationGuard;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class SequencedCollectionIterationGuardTest {

	@Test
	void elementFilter_cannot_be_null() {
		Predicate<Object> filter = null;
		InputGuard<Object, Object> elementGuard = InputGuards.noOpGuard();
		Collector<Object, ?, List<Object>> collector = Collectors.toList();
		Assertions.assertThatNullPointerException().isThrownBy(() -> new SequencedCollectionIterationGuard<>(filter, elementGuard, collector));
	}

	@Test
	void elementGuard_cannot_be_null() {
		Predicate<Object> filter = obj -> true;
		InputGuard<Object, Object> elementGuard = null;
		Collector<Object, ?, List<Object>> collector = Collectors.toList();
		Assertions.assertThatNullPointerException().isThrownBy(() -> new SequencedCollectionIterationGuard<>(filter, elementGuard, collector));
	}

	@Test
	void collector_cannot_be_null() {
		Predicate<Object> filter = obj -> true;
		InputGuard<Object, Object> elementGuard = InputGuards.noOpGuard();
		Collector<Object, ?, List<Object>> collector = null;
		Assertions.assertThatNullPointerException().isThrownBy(() -> new SequencedCollectionIterationGuard<>(filter, elementGuard, collector));
	}

}
