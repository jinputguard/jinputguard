package io.github.jinputguard.result;

import io.github.jinputguard.GuardFailure;
import io.github.jinputguard.guard.collection.MultiFailure;
import io.github.jinputguard.guard.collection.MultiFailureAssert;
import io.github.jinputguard.guard.mapping.MappingFailure;
import io.github.jinputguard.guard.mapping.MappingFailureAssert;
import io.github.jinputguard.guard.validation.ValidationFailure;
import io.github.jinputguard.guard.validation.ValidationFailureAssert;
import java.util.function.Consumer;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.AbstractStringAssert;
import org.assertj.core.api.Assertions;

public class GuardFailureAssert<SELF extends GuardFailureAssert<SELF, T>, T extends GuardFailure> extends AbstractAssert<SELF, T> {

	private GuardFailureAssert(T actual) {
		super(actual, GuardFailureAssert.class);
	}

	protected GuardFailureAssert(T actual, Class<SELF> assertClass) {
		super(actual, assertClass);
	}

	public static <A extends GuardFailureAssert<A, T>, T extends GuardFailure> GuardFailureAssert<A, T> assertThat(T actual) {
		return new GuardFailureAssert<>(actual);
	}

	// -------------------------------------------------------------------------------------------
	// MESSAGE

	public SELF messageSatisfies(Consumer<String> consumer) {
		consumer.accept(actual.getMessage());
		return myself;
	}

	public SELF messageAssert(Consumer<AbstractStringAssert<?>> consumer) {
		return messageSatisfies(value -> consumer.accept(Assertions.assertThat(value)));
	}

	public SELF hasMessage(String expected) {
		return messageAssert(assertor -> assertor.isEqualTo(expected));
	}

	public SELF messageStartWith(String expected) {
		return messageAssert(assertor -> assertor.startsWith(expected));
	}

	// -------------------------------------------------------------------------------------------
	// PATH

	public SELF pathSatifies(Consumer<Path> consumer) {
		consumer.accept(actual.getPath());
		return myself;
	}

	public SELF pathAssert(Consumer<PathAssert> consumer) {
		return pathSatifies(path -> consumer.accept(PathAssert.assertThat(path)));
	}

	public SELF hasPathEqualTo(String expected) {
		return pathSatifies(path -> Assertions.assertThat(path.format()).isEqualTo(expected));
	}

	public SELF hasPathEqualTo(Path expected) {
		return pathAssert(assertor -> assertor.isEqualTo(expected));
	}

	public SELF hasRootPath() {
		return pathAssert(assertor -> assertor.isRoot());
	}

	// -------------------------------------------------------------------------------------------
	// SPECIFIC SUBTYPES

	public MappingFailureAssert isMappingFailure() {
		if (actual instanceof MappingFailure fail) {
			return MappingFailureAssert.assertThat(fail);
		}
		return Assertions.fail("Failure expected to be " + MappingFailure.class + ", but is " + actual.getClass() + ":\n" + actual);
	}

	public MultiFailureAssert isMultiFailure() {
		if (actual instanceof MultiFailure fail) {
			return MultiFailureAssert.assertThat(fail);
		}
		return Assertions.fail("Failure expected to be " + MultiFailure.class + ", but is " + actual.getClass() + ":\n" + actual);
	}

	public ValidationFailureAssert isValidationFailure() {
		if (actual instanceof ValidationFailure fail) {
			return ValidationFailureAssert.assertThat(fail);
		}
		return Assertions.fail("Failure expected to be " + ValidationFailure.class + ", but is " + actual.getClass() + ":\n" + actual);
	}

}
