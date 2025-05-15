package io.github.jinputguard.result;

import io.github.jinputguard.result.MultiFailure;
import io.github.jinputguard.result.Path;
import io.github.jinputguard.result.ValidationError;
import io.github.jinputguard.result.ValidationFailure;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ValidationFailureTest {

	@Nested
	class ToException {

		@Test
		void nominal() {
			var failure = new ValidationFailure(null, new ValidationError.ObjectIsNull());

			var actual = failure.toException();

			InputGuardFailureExceptionAssert.assertThat(actual)
				.hasMessage(failure.getMessage())
				.hasFailure(failure)
				.hasNoCause();
		}

	}

	@Nested
	class Equality {

		@Test
		void test_equals_and_hash_code_onNull() {
			var failure = new ValidationFailure(null, new ValidationError.ObjectIsNull());
			Assertions.assertThat(failure).isNotEqualTo(null);
		}

		@Test
		void test_equals_and_hash_code_onOtherObjectType() {
			var failure = new ValidationFailure(null, new ValidationError.ObjectIsNull());
			Assertions.assertThat(failure).isNotEqualTo(new MultiFailure(null, List.of()));
		}

		@Test
		void test_equals_and_hash_code_onSameObject() {
			var failure = new ValidationFailure(null, new ValidationError.ObjectIsNull());
			Assertions.assertThat(failure).hasSameHashCodeAs(failure);
			Assertions.assertThat(failure).isEqualTo(failure);
		}

		@Test
		void test_equals_and_hash_code_onIdenticalObject() {
			var failure = new ValidationFailure(null, new ValidationError.ObjectIsNull());
			var otherFailure = new ValidationFailure(null, new ValidationError.ObjectIsNull());
			Assertions.assertThat(failure).hasSameHashCodeAs(otherFailure);
			Assertions.assertThat(failure).isEqualTo(otherFailure);
		}

		@Test
		void test_equals_and_hash_code_onNotSameValue() {
			var failure = new ValidationFailure(null, new ValidationError.ObjectIsNull());
			var otherFailure = new ValidationFailure("test", new ValidationError.ObjectIsNull());
			Assertions.assertThat(failure).isNotEqualTo(otherFailure);
		}

		@Test
		void test_equals_and_hash_code_onNotSameValidationError() {
			var failure = new ValidationFailure(null, new ValidationError.ObjectIsNull());
			var otherFailure = new ValidationFailure(null, new ValidationError.CollectionIsEmpty());
			Assertions.assertThat(failure).isNotEqualTo(otherFailure);
		}

		@Test
		void test_equals_and_hash_code_onNotSamePath() {
			var failure = new ValidationFailure(null, new ValidationError.ObjectIsNull());
			Assertions.assertThat(failure.atPath(Path.createPropertyPath("somePath")))
				.isNotEqualTo(failure.atPath(Path.createPropertyPath("anyOtherPath")));
		}

	}

}
