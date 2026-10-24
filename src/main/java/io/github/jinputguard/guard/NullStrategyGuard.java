package io.github.jinputguard.guard;

import io.github.jinputguard.GuardResult;
import io.github.jinputguard.InputGuard;
import io.github.jinputguard.failure.BaseValidationErrors;
import jakarta.annotation.Nonnull;
import java.util.Objects;

public class NullStrategyGuard<IN, OUT> implements InputGuard<IN, OUT> {

	private final @Nonnull NullStrategy<IN> strategy;
	private final @Nonnull InputGuard<IN, OUT> nextGuard;

	public NullStrategyGuard(@Nonnull NullStrategy<IN> strategy, @Nonnull InputGuard<IN, OUT> nextGuard) {
		this.strategy = Objects.requireNonNull(strategy, "null strategy cannot be null");
		this.nextGuard = Objects.requireNonNull(nextGuard, "next guard cannot be null");
	}

	@Override
	public <NEW_OUT> InputGuard<IN, NEW_OUT> andThen(InputGuard<OUT, NEW_OUT> after) {
		Objects.requireNonNull(after, "after guard cannot be null");
		if (after instanceof NullStrategyGuard<OUT, NEW_OUT> nullStrategyGuard) {
			return new ChainedGuard<>(this, nullStrategyGuard);
		}
		return new NullStrategyGuard<>(strategy, nextGuard.andThen(after));
	}

	@Override
	public GuardResult<OUT> process(IN value, @Nonnull String path) {
		return switch (strategy) {
			case KeepProcessing<IN> str -> nextGuard.process(value, path);
			case ReturnNull<IN> str -> value == null ? GuardResult.success(null) : nextGuard.process(value, path);
			case Fail<IN> str -> value == null ? GuardResult.failure(BaseValidationErrors.OBJECT_MUST_NOT_BE_NULL.toFailure(path)) : nextGuard.process(value, path);
			case UseDefault<IN>(var defaultValue) -> nextGuard.process(value == null ? defaultValue : value, path);
		};
	}

	@SuppressWarnings("unused")
	public static sealed interface NullStrategy<T> {

		static <T> NullStrategy<T> keepProcessing() {
			return new KeepProcessing<>();
		}

		static <T> NullStrategy<T> returnNull() {
			return new ReturnNull<>();
		}

		static <T> NullStrategy<T> fail() {
			return new Fail<>();
		}

		static <T> NullStrategy<T> useDefault(@Nonnull T value) {
			return new UseDefault<>(value);
		}

	}

	private static record KeepProcessing<T>() implements NullStrategy<T> {

	}

	private static record ReturnNull<T>() implements NullStrategy<T> {

	}

	private static record Fail<T>() implements NullStrategy<T> {

	}

	private static record UseDefault<T>(@Nonnull T value) implements NullStrategy<T> {

		public UseDefault(@Nonnull T value) {
			this.value = Objects.requireNonNull(value, "default value cannot be null");
		}

	}

}
