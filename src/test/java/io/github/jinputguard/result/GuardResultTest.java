package io.github.jinputguard.result;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GuardResultTest {

	private static final GuardResult<Object> SUCCESS_WITH_NULL = GuardResult.success(null);
	private static final Object OBJECT = new Object();
	private static final GuardResult<Object> SUCCESS_WITH_ANY = GuardResult.success(OBJECT);

	private static final ValidationError ERROR = new ValidationError.ObjectIsNull();
	private static final GuardFailure FAILURE = new ValidationFailure(OBJECT, ERROR);
	private static final GuardResult<Object> FAILURE_WITH_ANY = GuardResult.failure(FAILURE);

	@Nested
	class StaticFactory {

		@Test
		void sucess() {
			GuardResultAssert.assertThat(GuardResult.success(""))
				.isSuccessWithValue("");
		}

		@Test
		void failure() {
			var error = new ValidationError.ObjectIsNull();
			var failure = new ValidationFailure(null, error);
			GuardResultAssert.assertThat(GuardResult.failure(failure))
				.isFailure(failure);
		}

	}

	@Nested
	class IsSuccess {

		@Test
		void when_success_with_null_then_true() {
			Assertions.assertThat(SUCCESS_WITH_NULL.isSuccess()).isTrue();
		}

		@Test
		void when_success_with_anyObject_then_true() {
			Assertions.assertThat(SUCCESS_WITH_ANY.isSuccess()).isTrue();
		}

		@Test
		void when_failure_with_anyReason_then_false() {
			Assertions.assertThat(FAILURE_WITH_ANY.isSuccess()).isFalse();
		}

	}

	@Nested
	class IsFailure {

		@Test
		void when_success_with_null_then_false() {
			Assertions.assertThat(SUCCESS_WITH_NULL.isFailure()).isFalse();
		}

		@Test
		void when_success_with_anyObject_then_false() {
			Assertions.assertThat(SUCCESS_WITH_ANY.isFailure()).isFalse();
		}

		@Test
		void when_failure_with_anyReason_then_true() {
			Assertions.assertThat(FAILURE_WITH_ANY.isFailure()).isTrue();
		}

	}

	@Nested
	class Get {

		@Test
		void when_success_with_null_then_null() {
			Assertions.assertThat(SUCCESS_WITH_NULL.get()).isNull();
		}

		@Test
		void when_success_with_anyObject_then_object() {
			Assertions.assertThat(SUCCESS_WITH_ANY.get()).isSameAs(OBJECT);
		}

		@Test
		void when_failure_with_anyReason_then_exception() {
			Assertions.assertThatIllegalStateException()
				.isThrownBy(() -> FAILURE_WITH_ANY.get());
		}

	}

	@Nested
	class GetOrThrow {

		@Test
		void when_success_with_null_then_null() {
			Assertions.assertThat(SUCCESS_WITH_NULL.getOrThrow()).isNull();
		}

		@Test
		void when_success_with_anyObject_then_object() {
			Assertions.assertThat(SUCCESS_WITH_ANY.getOrThrow()).isSameAs(OBJECT);
		}

		@Test
		void when_failure_with_anyReason_then_exception() {
			Assertions.assertThatExceptionOfType(InputGuardFailureException.class)
				.isThrownBy(() -> FAILURE_WITH_ANY.getOrThrow())
				.extracting(InputGuardFailureException::getFailure).isEqualTo(FAILURE);
		}

	}

	@Nested
	class GetFailure {

		@Test
		void when_success_with_null_then_null() {
			Assertions.assertThatIllegalStateException()
				.isThrownBy(() -> SUCCESS_WITH_NULL.getFailure());
		}

		@Test
		void when_success_with_anyObject_then_object() {
			Assertions.assertThatIllegalStateException()
				.isThrownBy(() -> SUCCESS_WITH_ANY.getFailure());
		}

		@Test
		void when_failure_with_anyReason_then_exception() {
			Assertions.assertThat(FAILURE_WITH_ANY.getFailure()).isSameAs(FAILURE);
		}

	}

	@Nested
	class AtPath {

		@Test
		void when_success() {
			var path = Path.createPropertyPath("value").atIndex(0);
			var actualResult = SUCCESS_WITH_ANY.atPath(path);
			GuardResultAssert.assertThat(actualResult)
				.isSuccessWithValue(SUCCESS_WITH_ANY.get());
		}

		@Test
		void when_failure() {
			var path = Path.createPropertyPath("value").atIndex(0);
			var actualResult = FAILURE_WITH_ANY.atPath(path);
			GuardResultAssert.assertThat(actualResult)
				.isFailure(FAILURE_WITH_ANY.getFailure().atPath(path));
		}

	}

	@Nested
	class ToString {

		@Test
		void when_success_with_null() {
			Assertions.assertThat(SUCCESS_WITH_NULL.toString())
				.isEqualTo("GuardResult<Success>: null");
		}

		@Test
		void when_success_with_anyObject() {
			Assertions.assertThat(SUCCESS_WITH_ANY.toString())
				.isEqualTo("GuardResult<Success>: " + OBJECT.toString());
		}

		@Test
		void when_failure_with_anyReason() {
			Assertions.assertThat(FAILURE_WITH_ANY.toString())
				.isEqualTo("GuardResult<Failure>: " + FAILURE.toString());
		}

	}

	@Nested
	class Equality {

		@Test
		void test_equals_and_hash_code_onNull() {
			Assertions.assertThat(SUCCESS_WITH_NULL).isNotEqualTo(null);
			Assertions.assertThat(SUCCESS_WITH_ANY).isNotEqualTo(null);
			Assertions.assertThat(FAILURE_WITH_ANY).isNotEqualTo(null);
		}

		@Test
		void test_equals_and_hash_code_onOtherObjectType() {
			Assertions.assertThat(SUCCESS_WITH_NULL).isNotEqualTo(OBJECT);
			Assertions.assertThat(SUCCESS_WITH_ANY).isNotEqualTo(OBJECT);
			Assertions.assertThat(FAILURE_WITH_ANY).isNotEqualTo(OBJECT);
		}

		@Test
		void test_equals_and_hash_code_onSameObject() {
			Assertions.assertThat(SUCCESS_WITH_NULL).hasSameHashCodeAs(SUCCESS_WITH_NULL);
			Assertions.assertThat(SUCCESS_WITH_NULL).isEqualTo(SUCCESS_WITH_NULL);

			Assertions.assertThat(SUCCESS_WITH_ANY).hasSameHashCodeAs(SUCCESS_WITH_ANY);
			Assertions.assertThat(SUCCESS_WITH_ANY).isEqualTo(SUCCESS_WITH_ANY);

			Assertions.assertThat(FAILURE_WITH_ANY).hasSameHashCodeAs(FAILURE_WITH_ANY);
			Assertions.assertThat(FAILURE_WITH_ANY).isEqualTo(FAILURE_WITH_ANY);
		}

		@Test
		void test_equals_and_hash_code_onIdenticalObject() {
			var otherSuccessNull = GuardResult.success(null);
			Assertions.assertThat(SUCCESS_WITH_NULL).hasSameHashCodeAs(otherSuccessNull);
			Assertions.assertThat(SUCCESS_WITH_NULL).isEqualTo(otherSuccessNull);

			var otherSuccessAnyObject = GuardResult.success(OBJECT);
			Assertions.assertThat(SUCCESS_WITH_ANY).hasSameHashCodeAs(otherSuccessAnyObject);
			Assertions.assertThat(SUCCESS_WITH_ANY).isEqualTo(otherSuccessAnyObject);

			var otherFailure = GuardResult.failure(FAILURE);
			Assertions.assertThat(FAILURE_WITH_ANY).hasSameHashCodeAs(otherFailure);
			Assertions.assertThat(FAILURE_WITH_ANY).isEqualTo(otherFailure);

		}

		@Test
		void test_equals_and_hash_code_onNotSameSuccessValue() {
			Assertions.assertThat(SUCCESS_WITH_ANY).isNotEqualTo(SUCCESS_WITH_NULL);
		}

		@Test
		void test_equals_and_hash_code_onNotSameStatus() {
			Assertions.assertThat(SUCCESS_WITH_ANY).isNotEqualTo(FAILURE_WITH_ANY);
		}

		@Test
		void test_equals_and_hash_code_onNotSamePath() {
			Assertions.assertThat(SUCCESS_WITH_ANY.atPath(Path.createPropertyPath("somePath")))
				.isEqualTo(SUCCESS_WITH_ANY.atPath(Path.createPropertyPath("anyOtherPath")));
		}

		@Test
		void test_equals_and_hash_code_onNotSameFailure_() {
			var otherDifferent = GuardResult.failure(new ValidationFailure(null, new ValidationError.StringIsEmpty()));
			Assertions.assertThat(FAILURE_WITH_ANY).isNotEqualTo(otherDifferent);
		}

	}

}
