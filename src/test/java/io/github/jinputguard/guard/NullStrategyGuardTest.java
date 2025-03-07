package io.github.jinputguard.guard;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.InputGuards;
import io.github.jinputguard.guard.NullStrategyGuard;
import io.github.jinputguard.guard.NullStrategyGuard.NullStrategy;
import io.github.jinputguard.result.ProcessResultAssert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class NullStrategyGuardTest {

	@Test
	void strategy_cannot_be_null() {
		NullStrategy<Object> strategy = null;
		InputGuard<Object, Object> guard = InputGuards.noOpGuard();
		Assertions.assertThatNullPointerException().isThrownBy(() -> new NullStrategyGuard<>(strategy, guard));
	}

	@Test
	void guard_cannot_be_null() {
		NullStrategy<Object> strategy = NullStrategy.process();
		InputGuard<Object, Object> guard = null;
		Assertions.assertThatNullPointerException().isThrownBy(() -> new NullStrategyGuard<>(strategy, guard));
	}

	@Nested
	class ProcessStrategyTest {

		private static InputGuard<String, String> NOOP_GUARD;
		private static InputGuard<String, String> OP_GUARD;

		@BeforeAll
		static void setup() {
			NOOP_GUARD = new NullStrategyGuard<>(NullStrategy.process(), InputGuards.noOpGuard());
			OP_GUARD = new NullStrategyGuard<>(
				NullStrategy.process(), InputGuards.sanitizationGuard(String::strip)
			);
		}

		@Test
		void null_case_notProcessed() {
			var actual = NOOP_GUARD.process(null);

			ProcessResultAssert.assertThat(actual).isSuccessWithValue(null);
		}

		@Test
		void null_case_processed() {
			Assertions.assertThatNullPointerException()
				.isThrownBy(() -> OP_GUARD.process(null));
		}

		@Test
		void nonNull_case() {
			var actual = OP_GUARD.process(" val ");

			ProcessResultAssert.assertThat(actual).isSuccessWithValue("val");
		}

	}

	@Nested
	class SkipProcessStrategyTest {

		private static InputGuard<String, String> GUARD;

		@BeforeAll
		static void setup() {
			GUARD = new NullStrategyGuard<>(NullStrategy.skipProcess(), InputGuards.noOpGuard());
		}

		@Test
		void null_case() {
			var actual = GUARD.process(null);

			ProcessResultAssert.assertThat(actual).isSuccessWithValue(null);
		}

		@Test
		void nonNull_case() {
			var actual = GUARD.process("val");

			ProcessResultAssert.assertThat(actual).isSuccessWithValue("val");
		}

	}

	@Nested
	class FailStrategyTest {

		private static InputGuard<String, String> GUARD;

		@BeforeAll
		static void setup() {
			GUARD = new NullStrategyGuard<>(NullStrategy.fail(), InputGuards.noOpGuard());
		}

		@Test
		void null_case() {
			var actual = GUARD.process(null);

			ProcessResultAssert.assertThat(actual)
				.isFailure()
				.isValidationFailure()
				.errorAssert(errorAssert -> errorAssert.isObjectIsNull());
		}

		@Test
		void nonNull_case() {
			var actual = GUARD.process("val");

			ProcessResultAssert.assertThat(actual).isSuccessWithValue("val");
		}

	}

	@Nested
	class UseDefaultStrategyTest {

		private static final String DEFAULT_VALUE = "def";
		private static InputGuard<String, String> GUARD;

		@BeforeAll
		static void setup() {
			GUARD = new NullStrategyGuard<>(NullStrategy.useDefault(DEFAULT_VALUE), InputGuards.noOpGuard());
		}

		@Test
		void null_case() {
			var actual = GUARD.process(null);

			ProcessResultAssert.assertThat(actual).isSuccessWithValue(DEFAULT_VALUE);
		}

		@Test
		void nonNull_case() {
			var actual = GUARD.process("val");

			ProcessResultAssert.assertThat(actual).isSuccessWithValue("val");
		}

	}

	@Nested
	class ToStringTest {

		@Test
		void nominal() {
			var nextGuard = InputGuards.noOpGuard();
			var guard = new NullStrategyGuard<>(NullStrategy.process(), nextGuard);

			var actual = guard.toString();

			Assertions.assertThat(actual)
				.startsWith("NullStrategyGuard")
				.contains(NullStrategy.process().toString())
				.contains(nextGuard.toString());
		}

	}

}
