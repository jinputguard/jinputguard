package io.github.jinputguard.guard;

import io.github.jinputguard.GuardResult;
import io.github.jinputguard.InputGuard;
import io.github.jinputguard.failure.MultiGuardFailure;
import jakarta.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.SequencedCollection;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CollectionIterationGuard<C_IN extends Collection<T>, T, C_OUT extends Collection<OUT>, OUT> implements InputGuard<C_IN, C_OUT> {

	private final @Nonnull InputGuard<T, OUT> elementGuard;
	private final @Nonnull Collector<OUT, ?, C_OUT> collector;

	public CollectionIterationGuard(@Nonnull InputGuard<T, OUT> elementGuard, @Nonnull Collector<OUT, ?, C_OUT> collector) {
		this.elementGuard = Objects.requireNonNull(elementGuard, "elementGuard cannot be null");
		this.collector = Objects.requireNonNull(collector, "collector cannot be null");
	}

	@Override
	public GuardResult<C_OUT> processToResult(C_IN value, @Nonnull String path) {
		if (value instanceof SequencedCollection<?>) {
			return internalProcessSequenced(value, path);
		} else {
			return internalProcess(value, path);
		}
	}

	public GuardResult<C_OUT> internalProcess(C_IN value, @Nonnull String path) {
		var resultMap = value.stream()
			.map(elem -> elementGuard.processToResult(elem, path + "[?]"))
			.collect(Collectors.groupingBy(GuardResult::isSuccess));

		var failures = resultMap.getOrDefault(false, List.of()).stream()
			.map(GuardResult::getFailure)
			.toList();
		if (!failures.isEmpty()) {
			return GuardResult.failure(new MultiGuardFailure(path, failures));
		}

		var newCollection = resultMap.getOrDefault(true, List.of()).stream()
			.map(GuardResult::get)
			.collect(collector);

		return GuardResult.success(newCollection);
	}

	private GuardResult<C_OUT> internalProcessSequenced(C_IN value, @Nonnull String path) {

		var iter = value.iterator();
		var resultMap = IntStream.range(0, value.size())
			.mapToObj(index -> elementGuard.processToResult(iter.next(), path + "[" + index + "]"))
			.collect(Collectors.groupingBy(GuardResult::isSuccess));

		var failures = resultMap.getOrDefault(false, List.of())
			.stream()
			.map(GuardResult::getFailure)
			.toList();
		if (!failures.isEmpty()) {
			return GuardResult.failure(new MultiGuardFailure(path, failures));
		}

		var newCollection = resultMap.getOrDefault(true, List.of()).stream()
			.map(GuardResult::get)
			.collect(collector);
		return GuardResult.success(newCollection);
	}

}
