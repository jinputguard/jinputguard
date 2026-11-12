package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.GuardResultAssert;
import java.util.Collection;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

abstract class AbstractCollectionSanitizationTest<C extends Collection<String>, B extends AbstractCollectionInputGuardBuilder<C, C, String, B>>
	extends AbstractCollectionTest<C, B> {

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class Filter {

		@Test
		void when_null_filter_then_throw_IllegalArgumentException() {
			Assertions.assertThatIllegalArgumentException().isThrownBy(
				() -> initiateGuard().filter(null).build()
			);
		}

		@Test
		void nominal() {
			var listGuard = initiateGuard()
				.filter(value -> !value.isEmpty())
				.build();

			var actualResult = listGuard.processToResult(buildMutableCollection("", " ", " a", "b ", " c "), "myVal");

			GuardResultAssert.assertThat(actualResult)
				.isSuccess(buildMutableCollection(" ", " a", "b ", " c "));

			Assertions.assertThat(actualResult.get())
				.isUnmodifiable();
		}

	}

}
