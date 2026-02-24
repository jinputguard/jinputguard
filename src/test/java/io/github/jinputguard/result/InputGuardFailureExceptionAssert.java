package io.github.jinputguard.result;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;

public class InputGuardFailureExceptionAssert extends AbstractThrowableAssert<InputGuardFailureExceptionAssert, InputGuardFailureException> {

	public InputGuardFailureExceptionAssert(InputGuardFailureException actual) {
		super(actual, InputGuardFailureExceptionAssert.class);
	}

	public static InputGuardFailureExceptionAssert assertThat(Throwable actual) {
		Assertions.assertThat(actual).isInstanceOf(InputGuardFailureException.class);
		return new InputGuardFailureExceptionAssert((InputGuardFailureException) actual);
	}

	public InputGuardFailureExceptionAssert hasMessage(Path path, String failureMessage) {
		hasMessage("Invalid " + path.format() + ": " + failureMessage);
		return this;
	}

}
