package io.github.jinputguard;

import io.github.jinputguard.result.GuardFailure;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssertAlternative;

public class GuardResultAssert<T> extends AbstractAssert<GuardResultAssert<T>, GuardResult<T>> {

	private GuardResultAssert(GuardResult<T> actual) {
		super(actual, GuardResultAssert.class);
	}

	public static <T> GuardResultAssert<T> assertThat(GuardResult<T> actual) {
		return new GuardResultAssert<>(actual);
	}

	public GuardResultAssert<T> isSuccess() {
		Assertions.assertThat(actual.isSuccess())
			.as(descriptionText())
			.overridingErrorMessage(() -> "Expected guard result to be success, but is failure: " + actual.getFailure())
			.isTrue();
		return this;
	}

	public GuardResultAssert<T> isSuccess(T expectedValue) {
		isSuccess();
		Assertions.assertThat(actual.get())
			.as(descriptionText())
			.overridingErrorMessage("Expected process result to have value:\n  %s\nbut has:\n  %s", expectedValue, actual.get())
			.isEqualTo(expectedValue);
		return this;
	}

//	public GuardFailureAssert isFailure() {
//		Assertions.assertThat(actual.isFailure())
//			.as(descriptionText())
//			.overridingErrorMessage(() -> "Expected process result to be failure, but is success with value: " + actual.get())
//			.isTrue();
//		return GuardFailureAssert.assertThat(actual.getFailure());
//	}

	public ThrowableAssertAlternative<InputGuardFailureException> isFailure() {
		return Assertions.assertThatExceptionOfType(InputGuardFailureException.class)
			.isThrownBy(actual::getOrThrow);
	}

	public GuardResultAssert<T> isFailure(GuardFailure expected) {
		Assertions.assertThat(actual.isFailure())
			.as(descriptionText())
			.overridingErrorMessage(() -> "Expected process result to be failure, but is success with value: " + actual.get())
			.isTrue();
		Assertions.assertThat(actual.getFailure())
			.isEqualTo(expected);
		return this;
	}

}
