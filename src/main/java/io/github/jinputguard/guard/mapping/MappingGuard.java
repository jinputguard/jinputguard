package io.github.jinputguard.guard.mapping;

import io.github.jinputguard.GuardResult;
import io.github.jinputguard.InputGuard;
import io.github.jinputguard.result.Path;
import jakarta.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Function;

public class MappingGuard<OUT, NEW_OUT> implements InputGuard<OUT, NEW_OUT> {

	private final @Nonnull Function<OUT, NEW_OUT> mappingFunction;

	public MappingGuard(Function<OUT, NEW_OUT> mappingFunction) {
		this.mappingFunction = Objects.requireNonNull(mappingFunction, "Mapping function cannot be null");
	}

	@Override
	public GuardResult<NEW_OUT> process(OUT value, @Nonnull Path path) {
		try {
			NEW_OUT outValue = mappingFunction.apply(value);
			return GuardResult.success(outValue);
		} catch (Throwable e) {
			return GuardResult.failure(new MappingFailure(path, e));
		}
	}

	@Override
	public String toString() {
		return "MappingGuard -> " + mappingFunction.toString();
	}

}
