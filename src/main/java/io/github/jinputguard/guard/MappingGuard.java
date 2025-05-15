package io.github.jinputguard.guard;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.result.MappingFailure;
import io.github.jinputguard.result.GuardResult;
import jakarta.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Function;

public class MappingGuard<OUT, NEW_OUT> implements InputGuard<OUT, NEW_OUT> {

	private final @Nonnull Function<OUT, NEW_OUT> mappingFunction;

	public MappingGuard(Function<OUT, NEW_OUT> mappingFunction) {
		this.mappingFunction = Objects.requireNonNull(mappingFunction, "Mapping function cannot be null");
	}

	@Override
	public GuardResult<NEW_OUT> process(OUT value) {
		try {
			NEW_OUT outValue = mappingFunction.apply(value);
			return GuardResult.success(outValue);
		} catch (Exception e) {
			return GuardResult.failure(new MappingFailure(value, e));
		}
	}

	@Override
	public String toString() {
		return "MappingGuard -> " + mappingFunction.toString();
	}

}
