package io.github.jinputguard.result;

import io.github.jinputguard.result.GuardFailure;
import io.github.jinputguard.result.GuardResult;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

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
			.overridingErrorMessage(() -> "Expected process result to be success, but is failure: " + actual.getFailure())
			.isTrue();
		return this;
	}

	public GuardResultAssert<T> isSuccessWithValue(T expectedValue) {
		isSuccess();
		Assertions.assertThat(actual.get())
			.as(descriptionText())
			.overridingErrorMessage("Expected process result to have value:\n  %s\nbut has:\n  %s", expectedValue, actual.get())
			.isEqualTo(expectedValue);
		return this;
	}

	@SuppressWarnings("unchecked")
	public <A extends GuardFailureAssert<A, F>, F extends GuardFailure> GuardFailureAssert<A, F> isFailure() {
		Assertions.assertThat(actual.isFailure())
			.as(descriptionText())
			.overridingErrorMessage(() -> "Expected process result to be failure, but is success with value: " + actual.get())
			.isTrue();
		return (GuardFailureAssert<A, F>) GuardFailureAssert.assertThat(actual.getFailure());
	}

	@SuppressWarnings("unchecked")
	public <A extends GuardFailureAssert<A, F>, F extends GuardFailure> GuardFailureAssert<A, F> isFailure(GuardFailure expectedFailure) {
		return (GuardFailureAssert<A, F>) isFailure().isEqualTo(expectedFailure);
	}

}
