package io.github.jinputguard.guard.collection;

import io.github.jinputguard.guard.validation.ValidationError;
import io.github.jinputguard.guard.validation.ValidationFailure;
import io.github.jinputguard.result.Path;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MultiFailureTest {

	@Nested
	class GetMessage {

		@Test
		void nominal() {
			var path = Path.create("myVal");
			var subFailure1 = new ValidationFailure(new ValidationError.ObjectIsNull(), path.in("subVal1"));
			var subFailure2 = new ValidationFailure(new ValidationError.StringIsEmpty(), path.in("subVal2"));
			var failure = new MultiFailure(List.of(subFailure1, subFailure2), path);

			MultiFailureAssert.assertThat(failure)
				.hasMessage(
					"Multiple failures while processing myVal:\n"
						+ "  - Invalid myVal.subVal1: must not be null\n"
						+ "  - Invalid myVal.subVal2: must not be empty\n"
				);
		}

	}

	@Nested
	class Equality {

		@Test
		void test_equals_and_hash_code_onNull() {
			var failure = new MultiFailure(List.of(), Path.create("myVal"));
			Assertions.assertThat(failure).isNotEqualTo(null);
		}

		@Test
		void test_equals_and_hash_code_onOtherObjectType() {
			var failure = new MultiFailure(List.of(), Path.create("myVal"));
			Assertions.assertThat(failure).isNotEqualTo(new ValidationFailure(new ValidationError.ObjectIsNull(), Path.create("myVal")));
		}

		@Test
		void test_equals_and_hash_code_onSameObject() {
			var failure = new MultiFailure(List.of(), Path.create("myVal"));
			Assertions.assertThat(failure).hasSameHashCodeAs(failure);
			Assertions.assertThat(failure).isEqualTo(failure);
		}

		@Test
		void test_equals_and_hash_code_onIdenticalObject() {
			var failure = new MultiFailure(List.of(), Path.create("myVal"));
			var otherFailure = new MultiFailure(List.of(), Path.create("myVal"));
			Assertions.assertThat(failure).hasSameHashCodeAs(otherFailure);
			Assertions.assertThat(failure).isEqualTo(otherFailure);
		}

		@Test
		void test_equals_and_hash_code_onNotSameValidationError() {
			var failure = new MultiFailure(List.of(), Path.create("myVal"));
			var otherFailure = new MultiFailure(
				List.of(new ValidationFailure(new ValidationError.ObjectIsNull(), Path.create("myVal"))),
				Path.create("myVal")
			);
			Assertions.assertThat(failure).isNotEqualTo(otherFailure);
		}

		@Test
		void test_equals_and_hash_code_onNotSamePath() {
			var failure1 = new MultiFailure(List.of(), Path.create("myVal1"));
			var failure2 = new MultiFailure(List.of(), Path.create("myVal2"));
			Assertions.assertThat(failure1).isNotEqualTo(failure2);
		}

	}

}
