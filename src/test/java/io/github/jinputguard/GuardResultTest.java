package io.github.jinputguard;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.jinputguard.GuardFailure;
import io.github.jinputguard.GuardResult;
import io.github.jinputguard.InputGuardFailureException;
import io.github.jinputguard.result.DefaultGuardFailure;
import io.github.jinputguard.result.Path;
import io.github.jinputguard.result.errors.ValidationError.GenericValidationError;
import org.junit.jupiter.api.Test;

class GuardResultTest {

	@Test
	void success() {
		var result = GuardResult.success("ok");

		assertThat(result.isSuccess()).isTrue();
		assertThat(result.get()).isEqualTo("ok");
		assertThat(result.getOrThrow()).isEqualTo("ok");

		assertThat(result.isFailure()).isFalse();
		assertThatIllegalStateException().isThrownBy(() -> result.getFailure());
	}

	@Test
	void failure() {
		var cause = new RuntimeException("cause");
		var failure = new DefaultGuardFailure(Path.create("root"), new GenericValidationError("bad"), cause);
		var result = GuardResult.failure(failure);

		assertThat(result.isSuccess()).isFalse();
		assertThatIllegalStateException().isThrownBy(() -> result.get());
		assertThatExceptionOfType(InputGuardFailureException.class)
			.isThrownBy(() -> result.getOrThrow())
			.withMessageContaining("bad")
			.withCause(cause);

		assertThat(result.isFailure()).isTrue();
		assertThat(result.getFailure()).isSameAs(failure);
	}

	@Test
	void getOrThrowShouldThrowInputGuardFailureException() {
		GuardFailure failure = new DefaultGuardFailure(Path.create("p"), new GenericValidationError("err"));
		GuardResult<String> r = GuardResult.failure(failure);
		InputGuardFailureException ex = assertThrows(InputGuardFailureException.class, r::getOrThrow);
		assertSame(failure, ex.getFailure());
	}

	@Test
	void getOrThrowWithMapperShouldThrowMappedException() {
		GuardFailure failure = new DefaultGuardFailure(Path.create("p2"), new GenericValidationError("err2"));
		GuardResult<String> r = GuardResult.failure(failure);
		RuntimeException ex = assertThrows(RuntimeException.class, () -> r.getOrThrow(f -> new RuntimeException(f.getMessage())));
		assertEquals("err2", ex.getMessage());
	}

	@Test
	void equalsAndHashCodeAndToString() {
		GuardResult<String> s1 = GuardResult.success("x");
		GuardResult<String> s2 = GuardResult.success("x");
		assertEquals(s1, s2);
		assertEquals(s1.hashCode(), s2.hashCode());
		assertTrue(s1.toString().contains("Success"));

		GuardFailure failure = new DefaultGuardFailure(Path.create("a"), new GenericValidationError("m"));
		GuardResult<String> f1 = GuardResult.failure(failure);
		GuardResult<String> f2 = GuardResult.failure(failure);
		assertEquals(f1, f2);
		assertEquals(f1.hashCode(), f2.hashCode());
		assertTrue(f1.toString().contains("Failure"));
	}

}