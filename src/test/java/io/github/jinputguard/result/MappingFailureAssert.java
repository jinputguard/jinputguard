package io.github.jinputguard.result;

import io.github.jinputguard.result.MappingFailure;
import java.util.function.Consumer;
import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;

/**
 * 
 * 
 *
 */
public class MappingFailureAssert extends GuardFailureAssert<MappingFailureAssert, MappingFailure> {

	private MappingFailureAssert(MappingFailure actual) {
		super(actual, MappingFailureAssert.class);
	}

	public static MappingFailureAssert assertThat(MappingFailure actual) {
		return new MappingFailureAssert(actual);
	}

	// -------------------------------------------------------------------------------------------
	// MESSAGE

	// -------------------------------------------------------------------------------------------
	// CAUSE

	public MappingFailureAssert causeSatisfies(Consumer<Throwable> consumer) {
		consumer.accept(actual.getCause());
		return this;
	}

	public MappingFailureAssert causeAssert(Consumer<AbstractThrowableAssert<?, Throwable>> consumer) {
		return causeSatisfies(cause -> consumer.accept(Assertions.assertThat(cause)));
	}

	public MappingFailureAssert hasNoCause() {
		return causeAssert(assertor -> assertor.isNull());
	}

	public MappingFailureAssert hasSameCause(Throwable expected) {
		return causeAssert(assertor -> assertor.isSameAs(expected));
	}

	public MappingFailureAssert hasCauseInstanceOf(Class<? extends Throwable> expected) {
		return causeAssert(assertor -> assertor.isInstanceOf(expected));
	}

	public MappingFailureAssert hasCauseMessage(String expected) {
		return causeAssert(assertor -> assertor.hasMessage(expected));
	}

}