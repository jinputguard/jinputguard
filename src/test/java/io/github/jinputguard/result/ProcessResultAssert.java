package io.github.jinputguard.result;

import io.github.jinputguard.result.GuardFailure;
import io.github.jinputguard.result.GuardResult;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class ProcessResultAssert<T> extends AbstractAssert<ProcessResultAssert<T>, GuardResult<T>> {

	private ProcessResultAssert(GuardResult<T> actual) {
		super(actual, ProcessResultAssert.class);
	}

	public static <T> ProcessResultAssert<T> assertThat(GuardResult<T> actual) {
		return new ProcessResultAssert<>(actual);
	}

	public ProcessResultAssert<T> isSuccess() {
		Assertions.assertThat(actual.isSuccess())
			.as(descriptionText())
			.overridingErrorMessage(() -> "Expected process result to be success, but is failure: " + actual.getFailure())
			.isTrue();
		return this;
	}

	public ProcessResultAssert<T> isSuccessWithValue(T expectedValue) {
		isSuccess();
		Assertions.assertThat(actual.get())
			.as(descriptionText())
			.overridingErrorMessage("Expected process result to have value:\n  %s\nbut has:\n  %s", expectedValue, actual.get())
			.isEqualTo(expectedValue);
		return this;
	}

	@SuppressWarnings("unchecked")
	public <A extends ProcessFailureAssert<A, F>, F extends GuardFailure> ProcessFailureAssert<A, F> isFailure() {
		Assertions.assertThat(actual.isFailure())
			.as(descriptionText())
			.overridingErrorMessage(() -> "Expected process result to be failure, but is success with value: " + actual.get())
			.isTrue();
		return (ProcessFailureAssert<A, F>) ProcessFailureAssert.assertThat(actual.getFailure());
	}

	@SuppressWarnings("unchecked")
	public <A extends ProcessFailureAssert<A, F>, F extends GuardFailure> ProcessFailureAssert<A, F> isFailure(GuardFailure expectedFailure) {
		return (ProcessFailureAssert<A, F>) isFailure().isEqualTo(expectedFailure);
	}

}
