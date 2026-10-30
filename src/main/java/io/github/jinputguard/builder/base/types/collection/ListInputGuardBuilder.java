package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.guard.InputGuards;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ListInputGuardBuilder<IN, T> extends AbstractSequencedCollectionInputGuardBuilder<IN, List<T>, T, ListInputGuardBuilder<IN, T>> {

	public ListInputGuardBuilder(InputGuard<IN, List<T>> previous) {
		super(previous);
	}

	@Override
	protected ListInputGuardBuilder<IN, T> newInstance(InputGuard<IN, List<T>> guard) {
		return new ListInputGuardBuilder<>(guard);
	}

	public static <T> ListInputGuardBuilder<List<T>, T> newInstance() {
		return new ListInputGuardBuilder<>(InputGuards.noOpGuard());
	}

	/**
	 * Filter the input list using the given predicate, and process each element using the given guard.
	 * The result is collected into a new list.
	 * 
	 * @param elementFilter		Predicate to filter elements of the input list
	 * @param elementGuard		Guard to process each element of the input list
	 * 
	 * @return a new builder for the resulting list
	 */
	@SuppressWarnings("unchecked")
	public <OUT> ListInputGuardBuilder<IN, OUT> filter(Predicate<T> elementFilter) {
		return filterAndProcessEach(elementFilter, (InputGuard<T, OUT>) InputGuards.noOpGuard(), Collectors.toUnmodifiableList());
	}

	/**
	 * Process each element of the input list using the given guard.
	 * The result is collected into a new list.
	 * 
	 * @param elementGuard		Guard to process each element of the input list
	 * 
	 * @return a new builder for the resulting list
	 */
	@SuppressWarnings("unchecked")
	public <OUT> ListInputGuardBuilder<IN, OUT> filter(Predicate<T> elementFilter, Collector<OUT, ?, List<OUT>> collector) {
		return filterAndProcessEach(elementFilter, (InputGuard<T, OUT>) InputGuards.noOpGuard(), collector);
	}

	/**
	 * Process each element of the input list using the given guard.
	 * The result is collected into a new list.
	 * 
	 * @param elementGuard		Guard to process each element of the input list
	 * 
	 * @return a new builder for the resulting list
	 */
	public <OUT> ListInputGuardBuilder<IN, OUT> processEach(InputGuard<T, OUT> elementGuard) {
		return filterAndProcessEach(value -> true, elementGuard, Collectors.toUnmodifiableList());
	}

	/**
	 * Process each element of the input list using the given guard.
	 * The result is collected into a new list.
	 * 
	 * @param elementGuard		Guard to process each element of the input list
	 * @param collector			Collector to collect the processed elements into a new list
	 * 
	 * @return a new builder for the resulting list
	 */
	public <OUT> ListInputGuardBuilder<IN, OUT> processEach(InputGuard<T, OUT> elementGuard, Collector<OUT, ?, List<OUT>> collector) {
		return filterAndProcessEach(value -> true, elementGuard, collector, ListInputGuardBuilder::new);
	}

	/**
	 * Filter the input list using the given predicate, and process each element using the given guard.
	 * The result is collected into a new list.
	 * 
	 * @param elementFilter		Predicate to filter elements of the input list
	 * @param elementGuard		Guard to process each element of the input list
	 * 
	 * @return a new builder for the resulting list
	 */
	public <OUT> ListInputGuardBuilder<IN, OUT> filterAndProcessEach(Predicate<T> elementFilter, InputGuard<T, OUT> elementGuard) {
		return filterAndProcessEach(elementFilter, elementGuard, Collectors.toUnmodifiableList());
	}

	/**
	 * Filter the input list using the given predicate, and process each element using the given guard.
	 * The result is collected into a new list.
	 * 
	 * @param elementFilter		Predicate to filter elements of the input list
	 * @param elementGuard		Guard to process each element of the input list
	 * @param collector			Collector to collect the processed elements into a new list
	 * 
	 * @return a new builder for the resulting list
	 */
	public <OUT> ListInputGuardBuilder<IN, OUT> filterAndProcessEach(
		Predicate<T> elementFilter, InputGuard<T, OUT> elementGuard, Collector<OUT, ?, List<OUT>> collector
	) {
		return filterAndProcessEach(elementFilter, elementGuard, collector, ListInputGuardBuilder::new);
	}

}
