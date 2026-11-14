package io.github.jinputguard.builder.base.types.array;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ArrayMappingTest {

	@Nested
	class MakeCopy {

		@Test
		void nominal() {
			var guard = InputGuard.builder().forArrayOf(String.class)
				.makeCopy()
				.build();

			var initialArray = new String[] { "a", "b", "c" };
			var actualResult = guard.processToResult(initialArray, "myVal");

			var expectedArray = new String[] { "a", "b", "c" };
			GuardResultAssert.assertThat(actualResult)
				.isSuccess(expectedArray)
				.isNotSameAs(initialArray);
		}

	}

}
