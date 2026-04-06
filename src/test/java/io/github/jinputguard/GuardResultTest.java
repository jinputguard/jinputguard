package io.github.jinputguard;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.jinputguard.result.DefaultGuardFailure;
import io.github.jinputguard.result.GuardFailure;
import io.github.jinputguard.result.errors.ErrorDetails;
import io.github.jinputguard.result.errors.ValidationError.GenericValidationError;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GuardResultTest {

	@Test
	void success() {
		var result = GuardResult.success("ok");

		assertThat(result.isSuccess()).isTrue();
		assertThat(result.get()).isEqualTo("ok");
		assertThat(result.getOrThrow()).isEqualTo("ok");

		assertThat(result.isFailure()).isFalse();
		assertThatIllegalStateException().isThrownBy(result::getFailure)
			.withMessage("Cannot get the failure as result is success, please first test with isSuccess()/isFailure()");
	}

	@Test
	void failure_without_path() {
		var cause = new RuntimeException("cause");
		var failure = new DefaultGuardFailure(null, new GenericValidationError("bad value"), cause);
		var result = GuardResult.failure(failure);

		assertThat(result.isSuccess()).isFalse();
		assertThatIllegalStateException().isThrownBy(result::get)
			.withMessage("Cannot get the value as result is failure, please first test with isSuccess()/isFailure()");
		assertThatExceptionOfType(InputGuardFailureException.class)
			.isThrownBy(result::getOrThrow)
			.withMessage("bad value")
			.withCause(cause)
			.extracting(InputGuardFailureException::getFailure).isEqualTo(failure);

		assertThat(result.isFailure()).isTrue();
		assertThat(result.getFailure()).isSameAs(failure);
	}

	@Test
	void failure_with_path() {
		var cause = new RuntimeException("cause");
		var failure = new DefaultGuardFailure("myVal", new GenericValidationError("is wrong"), cause);
		var result = GuardResult.failure(failure);

		assertThat(result.isSuccess()).isFalse();
		assertThatIllegalStateException().isThrownBy(result::get)
			.withMessage("Cannot get the value as result is failure, please first test with isSuccess()/isFailure()");
		assertThatExceptionOfType(InputGuardFailureException.class)
			.isThrownBy(result::getOrThrow)
			.withMessage("myVal is wrong")
			.withCause(cause)
			.extracting(InputGuardFailureException::getFailure).isEqualTo(failure);

		assertThat(result.isFailure()).isTrue();
		assertThat(result.getFailure()).isSameAs(failure);
	}

	@Test
	void getOrThrowShouldThrowInputGuardFailureException() {
		var failure = new DefaultGuardFailure("myVal", new GenericValidationError("err"));
		var r = GuardResult.failure(failure);
		InputGuardFailureException ex = assertThrows(InputGuardFailureException.class, r::getOrThrow);
		assertSame(failure, ex.getFailure());
	}

	@Test
	void getOrThrowWithMapperShouldThrowMappedException() {
		var failure = new DefaultGuardFailure("myVal", new GenericValidationError("err2"));
		var r = GuardResult.failure(failure);
		RuntimeException ex = assertThrows(RuntimeException.class, () -> r.getOrThrow(f -> new RuntimeException("errooooor")));
		assertEquals("errooooor", ex.getMessage());
	}

	@Nested
	class EqualsAndHashCodeTests {

		private static final GuardResult<String> BASE_SUCCESS_RESULT = GuardResult.success("x");

		private static final String BASE_PATH = "myVal";
		private static final ErrorDetails BASE_ERROR_DETAILS = new GenericValidationError("Some error!");
		private static final RuntimeException BASE_CAUSE = new RuntimeException("cause");
		private static final GuardFailure BASE_FAILURE = new DefaultGuardFailure(BASE_PATH, BASE_ERROR_DETAILS, BASE_CAUSE);
		private static final GuardResult<String> BASE_FAILED_RESULT = GuardResult.failure(BASE_FAILURE);

		@Test
		void test_equals_otherObject() {
			assertThat(BASE_SUCCESS_RESULT.equals(new Object())).isFalse();
			assertThat(BASE_FAILED_RESULT.equals(new Object())).isFalse();
		}

		@Test
		void test_equals_success_same() {
			var actual = GuardResult.success("x");
			assertThat(actual)
				.isEqualTo(BASE_SUCCESS_RESULT)
				.hasSameHashCodeAs(BASE_SUCCESS_RESULT);
		}

		@Test
		void test_equals_success_different() {
			var actual = GuardResult.success("y");
			assertThat(actual)
				.isNotEqualTo(BASE_SUCCESS_RESULT);
		}

		@Test
		void test_equals_success_idemPotency() {
			assertThat(BASE_SUCCESS_RESULT)
				.isEqualTo(BASE_SUCCESS_RESULT)
				.hasSameHashCodeAs(BASE_SUCCESS_RESULT);
		}

		@Test
		void test_equals_failure_same() {
			var actual = GuardResult.failure(BASE_FAILURE);
			assertThat(actual)
				.isEqualTo(BASE_FAILED_RESULT)
				.hasSameHashCodeAs(BASE_FAILED_RESULT);
		}

		@Test
		void test_equals_failure_differnt() {
			var actual = GuardResult.failure(new DefaultGuardFailure("other", new GenericValidationError("Other error!"), null));
			assertThat(actual)
				.isNotEqualTo(BASE_FAILED_RESULT);
		}

		@Test
		void test_equals_failure_idemPotency() {
			assertThat(BASE_FAILED_RESULT)
				.isEqualTo(BASE_FAILED_RESULT)
				.hasSameHashCodeAs(BASE_FAILED_RESULT);
		}

	}

	@Test
	void test_toString() {
		var resultSuccess = GuardResult.success("x");
		assertTrue(resultSuccess.toString().contains("Success"));

		var failure = new DefaultGuardFailure("myVal", new GenericValidationError("m"));
		var resultFailure = GuardResult.failure(failure);
		assertTrue(resultFailure.toString().contains("Failure"));
	}

}