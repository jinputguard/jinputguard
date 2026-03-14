package io.github.jinputguard.guard.collection;

import io.github.jinputguard.GuardResult;
import io.github.jinputguard.InputGuard;
import io.github.jinputguard.result.DefaultGuardFailure;
import io.github.jinputguard.result.Path;
import io.github.jinputguard.result.errors.MultiError;
import jakarta.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CollectionIterationGuard<C_IN extends Collection<T>, T, C_OUT extends Collection<OUT>, OUT> implements InputGuard<C_IN, C_OUT> {

	private final @Nonnull Predicate<T> elementFilter;
	private final @Nonnull InputGuard<T, OUT> elementGuard;
	private final @Nonnull Collector<OUT, ?, C_OUT> collector;

	public CollectionIterationGuard(@Nonnull Predicate<T> elementFilter, @Nonnull InputGuard<T, OUT> elementGuard, @Nonnull Collector<OUT, ?, C_OUT> collector) {
		this.elementFilter = Objects.requireNonNull(elementFilter, "elementFilter cannot be null");
		this.elementGuard = Objects.requireNonNull(elementGuard, "elementGuard cannot be null");
		this.collector = Objects.requireNonNull(collector, "collector cannot be null");
	}

	@Override
	public GuardResult<C_OUT> process(C_IN value, @Nonnull Path path) {
		var resultMap = value.stream()
			.filter(elementFilter)
			.map(elem -> elementGuard.process(elem, path.atUndefinedIndex()))
			.collect(Collectors.groupingBy(GuardResult::isSuccess));

		var failures = resultMap.getOrDefault(false, List.of()).stream()
			.map(GuardResult::getFailure)
			.toList();
		if (!failures.isEmpty()) {
			var error = new MultiError(failures);
			return GuardResult.failure(new DefaultGuardFailure(path, error));
		}

		var newCollection = resultMap.getOrDefault(true, List.of()).stream()
			.map(GuardResult::get)
			.collect(collector);

		return GuardResult.success(newCollection);
	}

	@Override
	public String toString() {
		return "CollectionIterationGuard\n"
			+ ("Filter: " + elementFilter.toString()).indent(2)
			+ ("Collector: " + collector.toString()).indent(2)
			+ ("Guard: " + elementGuard.toString()).indent(2);
	}

}
