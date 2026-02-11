package io.github.jinputguard.guard.mapping;

import io.github.jinputguard.guard.collection.MultiFailure;
import io.github.jinputguard.guard.collection.MultiFailureAssert;
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
			var failure = new MappingFailure(Path.root().in("myVal"), cause);

			MultiFailureAssert.assertThat(failure)
				.hasMessage("Mapping failed for myVal");
		}

	}

	@Nested
	class Equality {

		@Test
		void test_equals_and_hash_code_onNull() {
			var failure = new MappingFailure(Path.root(), new Exception("cause"));
			Assertions.assertThat(failure).isNotEqualTo(null);
		}

		@Test
		void test_equals_and_hash_code_onOtherObjectType() {
			var failure = new MappingFailure(Path.root(), new Exception("cause"));
			Assertions.assertThat(failure).isNotEqualTo(new MultiFailure(List.of(), Path.root()));
		}

		@Test
		void test_equals_and_hash_code_onSameObject() {
			var failure = new MappingFailure(Path.root(), new Exception("cause"));
			Assertions.assertThat(failure).hasSameHashCodeAs(failure);
			Assertions.assertThat(failure).isEqualTo(failure);
		}

		@Test
		void test_equals_and_hash_code_onIdenticalObject() {
			var cause = new Exception("cause");
			var failure = new MappingFailure(Path.root(), cause);
			var otherFailure = new MappingFailure(Path.root(), cause);
			Assertions.assertThat(failure).hasSameHashCodeAs(otherFailure);
			Assertions.assertThat(failure).isEqualTo(otherFailure);
		}

		@Test
		void test_equals_and_hash_code_onNotSameValue() {
			var cause = new Exception("cause");
			var failure = new MappingFailure(Path.root(), new Exception("cause"));
			var otherFailure = new MappingFailure(Path.root(), new Exception("cause"));
			Assertions.assertThat(failure).isNotEqualTo(otherFailure);
		}

		@Test
		void test_equals_and_hash_code_onNotSameCause() {
			var failure = new MappingFailure(Path.root(), new Exception("cause1"));
			var otherFailure = new MappingFailure(Path.root(), new Exception("cause2"));
			Assertions.assertThat(failure).isNotEqualTo(otherFailure);
		}

		@Test
		void test_equals_and_hash_code_onNotSamePath() {
			var exception = new Exception("cause");
			var failure1 = new MappingFailure(Path.root().in("f1"), exception);
			var failure2 = new MappingFailure(Path.root().in("f2"), exception);
			Assertions.assertThat(failure1).isNotEqualTo(failure2);
		}

	}

}
