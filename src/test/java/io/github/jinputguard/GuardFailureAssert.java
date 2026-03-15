package io.github.jinputguard;

import io.github.jinputguard.GuardFailure;
import java.util.function.Consumer;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.AbstractStringAssert;
import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;

public class GuardFailureAssert extends AbstractAssert<GuardFailureAssert, GuardFailure> {

	private GuardFailureAssert(GuardFailure actual) {
		super(actual, GuardFailureAssert.class);
	}

	public static GuardFailureAssert assertThat(GuardFailure actual) {
		return new GuardFailureAssert(actual);
	}

	// -------------------------------------------------------------------------------------------
	// MESSAGE

	public GuardFailureAssert messageSatisfies(Consumer<String> consumer) {
		consumer.accept(actual.getMessage());
		return myself;
	}

	public GuardFailureAssert messageAssert(Consumer<AbstractStringAssert<?>> consumer) {
		return messageSatisfies(value -> consumer.accept(Assertions.assertThat(value)));
	}

	public GuardFailureAssert hasMessage(String expected) {
		return messageAssert(assertor -> assertor.isEqualTo(expected));
	}

	public GuardFailureAssert messageStartsWith(String expected) {
		return messageAssert(assertor -> assertor.startsWith(expected));
	}

	// -------------------------------------------------------------------------------------------
	// PATH

	public GuardFailureAssert pathSatifies(Consumer<String> consumer) {
		consumer.accept(actual.getPath());
		return myself;
	}

	public GuardFailureAssert pathAssert(Consumer<AbstractStringAssert<?>> consumer) {
		return pathSatifies(path -> consumer.accept(Assertions.assertThat(path)));
	}

	public GuardFailureAssert hasPathEqualTo(String expected) {
		return pathSatifies(path -> Assertions.assertThat(path).isEqualTo(expected));
	}

	// -------------------------------------------------------------------------------------------
	// CAUSE

	public GuardFailureAssert causeSatisfies(Consumer<Throwable> consumer) {
		consumer.accept(actual.getCause());
		return myself;
	}

	public GuardFailureAssert causeAssert(Consumer<AbstractThrowableAssert<?, ?>> consumer) {
		return causeSatisfies(value -> consumer.accept(Assertions.assertThat(value)));
	}

	public GuardFailureAssert hasSameCause(Throwable expected) {
		return causeAssert(assertor -> assertor.isSameAs(expected));
	}

	public <X extends Throwable> GuardFailureAssert hasCauseInstanceOf(Class<X> expected) {
		return causeAssert(assertor -> assertor.isInstanceOf(expected));
	}

}
