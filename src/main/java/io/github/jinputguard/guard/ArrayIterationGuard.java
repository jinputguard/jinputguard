package io.github.jinputguard.guard;

import io.github.jinputguard.GuardFailure;
import io.github.jinputguard.GuardResult;
import io.github.jinputguard.InputGuard;
import io.github.jinputguard.failure.MultiGuardFailure;
import jakarta.annotation.Nonnull;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class ArrayIterationGuard<IN, OUT> implements InputGuard<IN[], OUT[]> {

	private final @Nonnull InputGuard<IN, OUT> elementGuard;
	private final @Nonnull Class<OUT> elementClass;

	public ArrayIterationGuard(@Nonnull InputGuard<IN, OUT> elementGuard, @Nonnull Class<OUT> elementClass) {
		this.elementGuard = Objects.requireNonNull(elementGuard, "elementGuard cannot be null");
		this.elementClass = Objects.requireNonNull(elementClass, "elementClass cannot be null");
	}

	@Override
	public GuardResult<OUT[]> processToResult(IN[] value, @Nonnull String path) {

		var failures = new ArrayList<GuardFailure>();
		var successes = new ArrayList<OUT>();
		for (int index = 0; index < value.length; index++) {
			var result = elementGuard.processToResult(value[index], path + "[" + index + "]");
			if (result.isFailure()) {
				failures.add(result.getFailure());
			} else if (failures.isEmpty()) { // short-circuit if we already have failures, no need to collect successes anymore
				successes.add(result.get());
			}
		}

		if (!failures.isEmpty()) {
			return GuardResult.failure(new MultiGuardFailure(path, failures));
		}

		@SuppressWarnings("unchecked")
		var newArray = (OUT[]) successes.toArray((OUT[]) Array.newInstance(elementClass, successes.size()));
		return GuardResult.success(newArray);
	}

}
