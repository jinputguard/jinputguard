package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.GuardResultAssert;
import java.util.Collection;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

abstract class AbstractCollectionMappingTest<C extends Collection<String>, B extends AbstractCollectionInputGuardBuilder<C, C, String, B>>
	extends AbstractCollectionTest<C, B> {

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class MapToUnmodifiable {

		@Test
		void nominal() {
			var listGuard = initiateGuard()
				.mapToUnmodifiable()
				.build();

			var actualResult = listGuard.processToResult(buildCollection("", " ", " a", "b ", " c "), "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isSuccess(buildCollection("", " ", " a", "b ", " c "));

			Assertions.assertThat(actualResult.get())
				.isUnmodifiable();
		}

	}

}
