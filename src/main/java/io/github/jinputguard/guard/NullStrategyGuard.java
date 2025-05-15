package io.github.jinputguard.guard;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.result.GuardResult;
import io.github.jinputguard.result.ValidationFailure;
import io.github.jinputguard.result.ValidationError.ObjectIsNull;
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
	public GuardResult<OUT> process(IN value) {
		return switch (strategy) {
			case Process<IN> str -> nextGuard.process(value);
			case SkipProcess<IN> str -> value == null ? GuardResult.success(null) : nextGuard.process(value);
			case Fail<IN> str -> value == null ? GuardResult.failure(new ValidationFailure(value, new ObjectIsNull())) : nextGuard.process(value);
			case UseDefault<IN>(var defaultValue) -> value == null ? nextGuard.process(defaultValue) : nextGuard.process(value);
		};
	}

	@Override
	public String toString() {
		return "NullStrategyGuard / " + strategy + "\n"
			+ nextGuard.toString().indent(2);
	}

	@SuppressWarnings("unused")
	public static sealed interface NullStrategy<T> {

		static <T> NullStrategy<T> process() {
			return new Process<>();
		}

		static <T> NullStrategy<T> skipProcess() {
			return new SkipProcess<>();
		}

		static <T> NullStrategy<T> fail() {
			return new Fail<>();
		}

		static <T> NullStrategy<T> useDefault(@Nonnull T value) {
			return new UseDefault<>(value);
		}

	}

	private static record Process<T>() implements NullStrategy<T> {

	}

	private static record SkipProcess<T>() implements NullStrategy<T> {

	}

	private static record Fail<T>() implements NullStrategy<T> {

	}

	private static record UseDefault<T>(@Nonnull T value) implements NullStrategy<T> {

		public UseDefault(@Nonnull T value) {
			this.value = Objects.requireNonNull(value, "default value cannot be null");
		}

	}

}
