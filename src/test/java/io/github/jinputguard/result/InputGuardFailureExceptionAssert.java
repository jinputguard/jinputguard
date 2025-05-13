package io.github.jinputguard.result;

import io.github.jinputguard.result.InputGuardFailureException;
import io.github.jinputguard.result.Path;
import io.github.jinputguard.result.GuardFailure;
import java.util.function.Consumer;
import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;

public class InputGuardFailureExceptionAssert extends AbstractThrowableAssert<InputGuardFailureExceptionAssert, InputGuardFailureException> {

	public InputGuardFailureExceptionAssert(InputGuardFailureException actual) {
		super(actual, InputGuardFailureExceptionAssert.class);
	}

	public static InputGuardFailureExceptionAssert assertThat(RuntimeException actual) {
		Assertions.assertThat(actual).isInstanceOf(InputGuardFailureException.class);
		return new InputGuardFailureExceptionAssert((InputGuardFailureException) actual);
	}

	public InputGuardFailureExceptionAssert hasFailureSatisfing(Consumer<GuardFailureAssert<?, ?>> assertor) {
		assertor.accept(GuardFailureAssert.assertThat(actual.getFailure()));
		return this;
	}

	public InputGuardFailureExceptionAssert hasFailure(GuardFailure expected) {
		return hasFailureSatisfing(assertor -> assertor.isEqualTo(expected));
	}

	public InputGuardFailureExceptionAssert hasMessage(Path path, String failureMessage) {
		hasMessage("Invalid " + path.format() + ": " + failureMessage);
		return this;
	}

}
