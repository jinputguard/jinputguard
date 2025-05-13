package io.github.jinputguard.result;

import io.github.jinputguard.result.MappingFailure;
import io.github.jinputguard.result.MultiFailure;
import io.github.jinputguard.result.Path;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MappingFailureTest {

	@Nested
	class GetMessage {

		@Test
		void nominal() {
			var cause = new Exception("cause");
			var failure = new MappingFailure(null, cause).atPath(Path.createPropertyPath("myVal"));

			MultiFailureAssert.assertThat(failure)
				.hasMessage("Mapping failed for myVal");
		}

	}

	@Nested
	class ToException {

		@Test
		void nominal() {
			var cause = new Exception("cause");
			var failure = new MappingFailure(null, cause);

			var actual = failure.toException();

			InputGuardFailureExceptionAssert.assertThat(actual)
				.hasMessage(failure.getMessage())
				.hasFailure(failure)
				.hasCause(cause);
		}

	}

	@Nested
	class Equality {

		@Test
		void test_equals_and_hash_code_onNull() {
			var failure = new MappingFailure(null, new Exception("cause"));
			Assertions.assertThat(failure).isNotEqualTo(null);
		}

		@Test
		void test_equals_and_hash_code_onOtherObjectType() {
			var failure = new MappingFailure(null, new Exception("cause"));
			Assertions.assertThat(failure).isNotEqualTo(new MultiFailure(null, List.of()));
		}

		@Test
		void test_equals_and_hash_code_onSameObject() {
			var failure = new MappingFailure(null, new Exception("cause"));
			Assertions.assertThat(failure).hasSameHashCodeAs(failure);
			Assertions.assertThat(failure).isEqualTo(failure);
		}

		@Test
		void test_equals_and_hash_code_onIdenticalObject() {
			var cause = new Exception("cause");
			var failure = new MappingFailure(null, cause);
			var otherFailure = new MappingFailure(null, cause);
			Assertions.assertThat(failure).hasSameHashCodeAs(otherFailure);
			Assertions.assertThat(failure).isEqualTo(otherFailure);
		}

		@Test
		void test_equals_and_hash_code_onNotSameValue() {
			var cause = new Exception("cause");
			var failure = new MappingFailure(null, new Exception("cause"));
			var otherFailure = new MappingFailure("test", new Exception("cause"));
			Assertions.assertThat(failure).isNotEqualTo(otherFailure);
		}

		@Test
		void test_equals_and_hash_code_onNotSameCause() {
			var failure = new MappingFailure(null, new Exception("cause1"));
			var otherFailure = new MappingFailure(null, new Exception("cause2"));
			Assertions.assertThat(failure).isNotEqualTo(otherFailure);
		}

		@Test
		void test_equals_and_hash_code_onNotSamePath() {
			var failure = new MappingFailure(null, new Exception("cause"));
			Assertions.assertThat(failure.atPath(Path.createPropertyPath("somePath")))
				.isNotEqualTo(failure.atPath(Path.createPropertyPath("anyOtherPath")));
		}

	}

}
