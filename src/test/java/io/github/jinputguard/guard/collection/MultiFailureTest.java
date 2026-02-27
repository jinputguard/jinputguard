package io.github.jinputguard.guard.collection;

import io.github.jinputguard.builder.base.types.ObjectValidationError;
import io.github.jinputguard.builder.base.types.StringValidationError;
import io.github.jinputguard.result.DefaultGuardFailure;
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
			var subFailure1 = new DefaultGuardFailure(path.in("subVal1"), new ObjectValidationError.ObjectIsNull());
			var subFailure2 = new DefaultGuardFailure(path.in("subVal2"), new StringValidationError.StringIsEmpty());
			var failure = new MultiFailure(List.of(subFailure1, subFailure2), path);

			MultiFailureAssert.assertThat(failure)
				.hasMessage(
					"multiple failures:\n"
						+ "  - myVal.subVal1 -> must not be null\n"
						+ "  - myVal.subVal2 -> must not be empty\n"
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
			Assertions.assertThat(failure).isNotEqualTo(new DefaultGuardFailure(Path.create("myVal"), new ObjectValidationError.ObjectIsNull()));
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
				List.of(new DefaultGuardFailure(Path.create("myVal"), new ObjectValidationError.ObjectIsNull())),
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
