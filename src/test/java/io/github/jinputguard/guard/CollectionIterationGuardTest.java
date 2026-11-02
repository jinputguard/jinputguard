package io.github.jinputguard.guard;

import io.github.jinputguard.InputGuard;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CollectionIterationGuardTest {

	@Test
	void itemGuard_cannot_be_null() {
		InputGuard<Object, Object> elementGuard = null;
		Collector<Object, ?, List<Object>> collector = Collectors.toList();
		Assertions.assertThatNullPointerException().isThrownBy(() -> new CollectionIterationGuard<>(elementGuard, collector));
	}

	@Test
	void collector_cannot_be_null() {
		InputGuard<Object, Object> elementGuard = InputGuards.noOpGuard();
		Collector<Object, ?, List<Object>> collector = null;
		Assertions.assertThatNullPointerException().isThrownBy(() -> new CollectionIterationGuard<>(elementGuard, collector));
	}

}
