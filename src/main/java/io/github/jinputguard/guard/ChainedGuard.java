package io.github.jinputguard.guard;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.result.GuardResult;
import jakarta.annotation.Nonnull;
import java.util.Objects;

public class ChainedGuard<IN, OUT, NEW_OUT> implements InputGuard<IN, NEW_OUT> {

	private final @Nonnull InputGuard<IN, OUT> firstGuard;
	private final @Nonnull InputGuard<OUT, NEW_OUT> secondGuard;

	public ChainedGuard(@Nonnull InputGuard<IN, OUT> firstGuard, @Nonnull InputGuard<OUT, NEW_OUT> secondGuard) {
		this.firstGuard = Objects.requireNonNull(firstGuard, "first guard cannot be null");
		this.secondGuard = Objects.requireNonNull(secondGuard, "second guard cannot be null");
	}

	@Override
	public GuardResult<NEW_OUT> process(IN value) {
		var resultOut = firstGuard.process(value);
		if (resultOut.isFailure()) {
			return GuardResult.failure(resultOut.getFailure());
		}
		var outValue = resultOut.get();
		var resultNewOut = secondGuard.process(outValue);
		return resultNewOut;
	}

	@Override
	public <NEW_OUT2> InputGuard<IN, NEW_OUT2> andThen(InputGuard<NEW_OUT, NEW_OUT2> after) {
		Objects.requireNonNull(after, "after guard cannot be null");
		return new ChainedGuard<>(firstGuard, secondGuard.andThen(after));
	}

	@Override
	public String toString() {
		return "ChainedGuard\n"
			+ firstGuard.toString().indent(2)
			+ secondGuard.toString().indent(2);
	}

}
