package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SetMappingTest extends AbstractCollectionMappingTest<Set<String>, SetInputGuardBuilder<Set<String>, String>> {

	@Override
	CollectionProvider<Set<String>, SetInputGuardBuilder<Set<String>, String>> getCollectionProvider() {
		return CollectionProvider.setProvider();
	}

	@Test
	void nominal() {
		var setGuard = InputGuard.builder().forSet(String.class)
			.map(set -> set.stream().map(Integer::parseInt).collect(Collectors.toSet()), path -> "Invalid " + path)
			.build();

		var actualResult = setGuard.processToResult(Set.of("0", "1", "2"), "myVal");

		GuardResultAssert.assertThat(actualResult)
			.isSuccess(Set.of(0, 1, 2));
	}

	@Nested
	class MapToUnmodifiableList {

		@Test
		void nominal() {
			var listGuard = initiateGuard()
				.mapToUnmodifiableList()
				.build();

			var actualResult = listGuard.processToResult(buildMutableCollection("a", "b", "c"), "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isSuccess(List.of("a", "b", "c"));

			Assertions.assertThat(actualResult.get())
				.isUnmodifiable();
		}

	}

}