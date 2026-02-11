package io.github.jinputguard.guard.collection;

import io.github.jinputguard.GuardFailure;
import io.github.jinputguard.guard.collection.MultiFailure;
import io.github.jinputguard.result.GuardFailureAssert;
import java.util.List;
import java.util.function.Consumer;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ListAssert;

/**
 * 
 * 
 *
 */
public class MultiFailureAssert extends GuardFailureAssert<MultiFailureAssert, MultiFailure> {

	private MultiFailureAssert(MultiFailure actual) {
		super(actual, MultiFailureAssert.class);
	}

	public static MultiFailureAssert assertThat(MultiFailure actual) {
		return new MultiFailureAssert(actual);
	}

	// -------------------------------------------------------------------------------------------
	// DESCRIPTION

	public MultiFailureAssert failuresSatisfies(Consumer<List<GuardFailure>> consumer) {
		consumer.accept(actual.getFailures());
		return myself;
	}

	public MultiFailureAssert failuresAssert(Consumer<ListAssert<GuardFailure>> consumer) {
		return failuresSatisfies(failures -> consumer.accept(Assertions.assertThat(failures)));
	}

}