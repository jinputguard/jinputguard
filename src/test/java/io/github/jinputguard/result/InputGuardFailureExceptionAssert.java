package io.github.jinputguard.result;

import java.util.function.Consumer;
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

	public InputGuardFailureExceptionAssert hasPathSatisfing(Consumer<PathAssert> assertor) {
		assertor.accept(PathAssert.assertThat(actual.getPath()));
		return this;
	}

	public InputGuardFailureExceptionAssert hasPath(Path expected) {
		return hasPathSatisfing(assertor -> assertor.isEqualTo(expected));
	}

	public InputGuardFailureExceptionAssert hasMessage(Path path, String failureMessage) {
		hasMessage("Invalid " + path.format() + ": " + failureMessage);
		return this;
	}

}
