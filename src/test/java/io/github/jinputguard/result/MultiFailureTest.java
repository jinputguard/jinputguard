package io.github.jinputguard.result;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MultiFailureTest {

	@Nested
	class GetMessage {

		@Test
		void nominal() {
			var subFailure1 = new ValidationFailure("val1", new ValidationError.ObjectIsNull()).atPath(Path.createPropertyPath("subVal1"));
			var subFailure2 = new ValidationFailure("val2", new ValidationError.StringIsEmpty()).atPath(Path.createPropertyPath("subVal2"));
			var failure = new MultiFailure("valRoot", List.of(subFailure1, subFailure2)).atPath(Path.createPropertyPath("myVal"));

			MultiFailureAssert.assertThat(failure)
				.hasMessage(
					"Multiple failures while processing myVal:\n"
						+ "  - Invalid myVal.subVal1: must not be null\n"
						+ "  - Invalid myVal.subVal2: must not be empty\n"
				);
		}

	}

	@Nested
	class ToException {

		@Test
		void nominal() {
			var failure = new MultiFailure(null, List.of());

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
			var failure = new MultiFailure(null, List.of());
			Assertions.assertThat(failure).isNotEqualTo(null);
		}

		@Test
		void test_equals_and_hash_code_onOtherObjectType() {
			var failure = new MultiFailure(null, List.of());
			Assertions.assertThat(failure).isNotEqualTo(new ValidationFailure(null, new ValidationError.ObjectIsNull()));
		}

		@Test
		void test_equals_and_hash_code_onSameObject() {
			var failure = new MultiFailure(null, List.of());
			Assertions.assertThat(failure).hasSameHashCodeAs(failure);
			Assertions.assertThat(failure).isEqualTo(failure);
		}

		@Test
		void test_equals_and_hash_code_onIdenticalObject() {
			var failure = new MultiFailure(null, List.of());
			var otherFailure = new MultiFailure(null, List.of());
			Assertions.assertThat(failure).hasSameHashCodeAs(otherFailure);
			Assertions.assertThat(failure).isEqualTo(otherFailure);
		}

		@Test
		void test_equals_and_hash_code_onNotSameValue() {
			var failure = new MultiFailure(null, List.of());
			var otherFailure = new MultiFailure("test", List.of());
			Assertions.assertThat(failure).isNotEqualTo(otherFailure);
		}

		@Test
		void test_equals_and_hash_code_onNotSameValidationError() {
			var failure = new MultiFailure(null, List.of());
			var otherFailure = new MultiFailure(null, List.of(new ValidationFailure(null, new ValidationError.ObjectIsNull())));
			Assertions.assertThat(failure).isNotEqualTo(otherFailure);
		}

		@Test
		void test_equals_and_hash_code_onNotSamePath() {
			var failure = new MultiFailure(null, List.of());
			Assertions.assertThat(failure.atPath(Path.createPropertyPath("somePath")))
				.isNotEqualTo(failure.atPath(Path.createPropertyPath("anyOtherPath")));
		}

	}

}
