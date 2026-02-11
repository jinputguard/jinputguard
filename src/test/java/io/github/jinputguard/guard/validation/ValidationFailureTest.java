package io.github.jinputguard.guard.validation;

import io.github.jinputguard.guard.collection.MultiFailure;
import io.github.jinputguard.result.Path;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ValidationFailureTest {

	@Nested
	class Equality {

		@Test
		void test_equals_and_hash_code_onNull() {
			var failure = new ValidationFailure(new ValidationError.ObjectIsNull(), Path.create("myVal"));
			Assertions.assertThat(failure).isNotEqualTo(null);
		}

		@Test
		void test_equals_and_hash_code_onOtherObjectType() {
			var failure = new ValidationFailure(new ValidationError.ObjectIsNull(), Path.create("myVal"));
			Assertions.assertThat(failure).isNotEqualTo(new MultiFailure(List.of(), Path.create("myVal")));
		}

		@Test
		void test_equals_and_hash_code_onSameObject() {
			var failure = new ValidationFailure(new ValidationError.ObjectIsNull(), Path.create("myVal"));
			Assertions.assertThat(failure).hasSameHashCodeAs(failure);
			Assertions.assertThat(failure).isEqualTo(failure);
		}

		@Test
		void test_equals_and_hash_code_onIdenticalObject() {
			var failure = new ValidationFailure(new ValidationError.ObjectIsNull(), Path.create("myVal"));
			var otherFailure = new ValidationFailure(new ValidationError.ObjectIsNull(), Path.create("myVal"));
			Assertions.assertThat(failure).hasSameHashCodeAs(otherFailure);
			Assertions.assertThat(failure).isEqualTo(otherFailure);
		}

		@Test
		void test_equals_and_hash_code_onNotSameValidationError() {
			var failure = new ValidationFailure(new ValidationError.ObjectIsNull(), Path.create("myVal"));
			var otherFailure = new ValidationFailure(new ValidationError.CollectionIsEmpty(), Path.create("myVal"));
			Assertions.assertThat(failure).isNotEqualTo(otherFailure);
		}

	}

}
