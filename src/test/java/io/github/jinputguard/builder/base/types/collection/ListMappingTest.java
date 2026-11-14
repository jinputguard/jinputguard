package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import java.util.List;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ListMappingTest extends AbstractCollectionMappingTest<List<String>, ListInputGuardBuilder<List<String>, String>> {

	@Override
	CollectionProvider<List<String>, ListInputGuardBuilder<List<String>, String>> getCollectionProvider() {
		return CollectionProvider.listProvider();
	}

	@Test
	void nominal() {
		var listGuard = InputGuard.builder().forListOf(String.class)
			.map(list -> list.stream().map(Integer::parseInt).toList(), path -> "Invalid " + path)
			.build();

		var actualResult = listGuard.processToResult(List.of("0", "1", "2"), "myVal");

		GuardResultAssert.assertThat(actualResult)
			.isSuccess(List.of(0, 1, 2));

		Assertions.assertThat(actualResult.get())
			.isUnmodifiable();
	}

	@Nested
	class MapToUnmodifiableSet {

		@Test
		void nominal() {
			var listGuard = initiateGuard()
				.mapToUnmodifiableSet()
				.build();

			var actualResult = listGuard.processToResult(buildMutableCollection("a", "b", "c"), "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isSuccess(Set.of("a", "b", "c"));

			Assertions.assertThat(actualResult.get())
				.isUnmodifiable();
		}

	}

}