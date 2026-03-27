package io.github.jinputguard;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jinputguard.result.DefaultGuardFailure;
import io.github.jinputguard.result.errors.ErrorDetails;
import io.github.jinputguard.result.errors.ValidationError.GenericValidationError;
import io.github.jinputguard.result.errors.WithEmbeddedCause;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GuardFailureTest {

	private static final String BASE_PATH = "myVal";
	private static final ErrorDetails BASE_ERROR_DETAILS = new GenericValidationError("Some error!");
	private static final RuntimeException BASE_CAUSE = new RuntimeException("cause");
	private static final GuardFailure BASE_FAILURE = new DefaultGuardFailure(BASE_PATH, BASE_ERROR_DETAILS, BASE_CAUSE);

	@Test
	void base_tests() {
		GuardFailureAssert.assertThat(BASE_FAILURE)
			.hasPathEqualTo(BASE_PATH)
			.hasMessage("Some error!")
			.hasSameCause(BASE_CAUSE);
	}

	@Test
	void test_errorDetailsWithEmbeddedCause_withoutCause() {
		var embeddedCause = new RuntimeException("embedded cause");
		var errorDetailsWithCause = new FakeErrorDetailsWithCause("Some error with cause!", embeddedCause);
		var actual = new DefaultGuardFailure(BASE_PATH, errorDetailsWithCause, null);

		GuardFailureAssert.assertThat(actual)
			.hasPathEqualTo(BASE_PATH)
			.hasMessage("Some error with cause!")
			.hasSameCause(embeddedCause)
			.causeAssert(causeAssert -> causeAssert.hasNoSuppressedExceptions());
	}

	@Test
	void test_errorDetailsWithEmbeddedCause_withCause() {
		var embeddedCause = new RuntimeException("embedded cause");
		var errorDetailsWithCause = new FakeErrorDetailsWithCause("Some error with cause!", embeddedCause);
		var actual = new DefaultGuardFailure(BASE_PATH, errorDetailsWithCause, BASE_CAUSE);

		GuardFailureAssert.assertThat(actual)
			.hasPathEqualTo(BASE_PATH)
			.hasMessage("Some error with cause!")
			.hasSameCause(BASE_CAUSE)
			.causeAssert(causeAssert -> causeAssert.hasSuppressedException(embeddedCause));
	}

	@Nested
	class EqualsAndHashCodeTests {

		@Test
		void test_equals_otherObject() {
			assertThat(BASE_FAILURE.equals(new Object())).isFalse();
		}

		@Test
		void test_equals_clone() {
			var failureClone = new DefaultGuardFailure(BASE_PATH, BASE_ERROR_DETAILS, BASE_CAUSE);
			assertThat(BASE_FAILURE)
				.isEqualTo(failureClone)
				.hasSameHashCodeAs(failureClone);
		}

		@Test
		void test_equals_idemPotency() {
			assertThat(BASE_FAILURE)
				.isEqualTo(BASE_FAILURE)
				.hasSameHashCodeAs(BASE_FAILURE);
		}

		@Test
		void test_equals_different_path() {
			var failureDiff = new DefaultGuardFailure("otherPath", BASE_ERROR_DETAILS, BASE_CAUSE);
			assertThat(failureDiff)
				.isNotEqualTo(BASE_FAILURE);
		}

		@Test
		void test_equals_different_errorDetails() {
			var failureDiff = new DefaultGuardFailure(BASE_PATH, new GenericValidationError("Other error!"), BASE_CAUSE);
			assertThat(failureDiff)
				.isNotEqualTo(BASE_FAILURE);
		}

		@Test
		void test_equals_different_cause() {
			var failureDiff = new DefaultGuardFailure(BASE_PATH, BASE_ERROR_DETAILS, new RuntimeException("other cause"));
			assertThat(failureDiff)
				.isNotEqualTo(BASE_FAILURE);
		}

		@Test
		void test_equals_errorDetails_with_same_cause() {
			var errorDetailsWithCause = new FakeErrorDetailsWithCause("Some error with cause!", BASE_CAUSE);
			var actual = new DefaultGuardFailure(BASE_PATH, errorDetailsWithCause, null);
			assertThat(actual)
				.isNotEqualTo(BASE_FAILURE);
		}

		@Test
		void test_equals_errorDetails_with_different_cause() {
			var errorDetailsWithCause = new FakeErrorDetailsWithCause("Some error!", new RuntimeException("other cause"));
			var actual = new DefaultGuardFailure(BASE_PATH, errorDetailsWithCause, null);
			assertThat(actual)
				.isNotEqualTo(BASE_FAILURE);
		}

	}

	private static class FakeErrorDetailsWithCause implements ErrorDetails, WithEmbeddedCause {

		private final String message;
		private final Throwable cause;

		public FakeErrorDetailsWithCause(String message, Throwable cause) {
			this.message = message;
			this.cause = cause;
		}

		@Override
		public String getMessage() {
			return message;
		}

		@Override
		public Throwable cause() {
			return cause;
		}

	}

}
