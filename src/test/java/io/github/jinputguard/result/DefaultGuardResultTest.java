package io.github.jinputguard.result;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.jinputguard.GuardFailure;
import io.github.jinputguard.GuardResult;
import io.github.jinputguard.InputGuardFailureException;
import io.github.jinputguard.result.errors.ValidationError.GenericValidationError;
import org.junit.jupiter.api.Test;

public class DefaultGuardResultTest {

	@Test
	void successResultShouldReturnValue() {
		GuardResult<String> r = DefaultGuardResult.success("ok");
		assertTrue(r.isSuccess());
		assertFalse(r.isFailure());
		assertEquals("ok", r.get());
		assertEquals("ok", r.getOrThrow());
	}

	@Test
	void failureResultShouldReturnFailure() {
		GuardFailure failure = new DefaultGuardFailure(Path.create("root"), new GenericValidationError("bad"));
		GuardResult<String> r = DefaultGuardResult.failure(failure);
		assertTrue(r.isFailure());
		assertFalse(r.isSuccess());
		assertSame(failure, r.getFailure());
		assertThrows(IllegalStateException.class, r::get);
	}

	@Test
	void getOrThrowShouldThrowInputGuardFailureException() {
		GuardFailure failure = new DefaultGuardFailure(Path.create("p"), new GenericValidationError("err"));
		GuardResult<String> r = DefaultGuardResult.failure(failure);
		InputGuardFailureException ex = assertThrows(InputGuardFailureException.class, r::getOrThrow);
		assertSame(failure, ex.getFailure());
	}

	@Test
	void getOrThrowWithMapperShouldThrowMappedException() {
		GuardFailure failure = new DefaultGuardFailure(Path.create("p2"), new GenericValidationError("err2"));
		GuardResult<String> r = DefaultGuardResult.failure(failure);
		RuntimeException ex = assertThrows(RuntimeException.class, () -> r.getOrThrow(f -> new RuntimeException(f.getMessage())));
		assertEquals("err2", ex.getMessage());
	}

	@Test
	void equalsAndHashCodeAndToString() {
		GuardResult<String> s1 = DefaultGuardResult.success("x");
		GuardResult<String> s2 = DefaultGuardResult.success("x");
		assertEquals(s1, s2);
		assertEquals(s1.hashCode(), s2.hashCode());
		assertTrue(s1.toString().contains("Success"));

		GuardFailure failure = new DefaultGuardFailure(Path.create("a"), new GenericValidationError("m"));
		GuardResult<String> f1 = DefaultGuardResult.failure(failure);
		GuardResult<String> f2 = DefaultGuardResult.failure(failure);
		assertEquals(f1, f2);
		assertEquals(f1.hashCode(), f2.hashCode());
		assertTrue(f1.toString().contains("Failure"));
	}

}