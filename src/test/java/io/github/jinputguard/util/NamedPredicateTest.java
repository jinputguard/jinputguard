package io.github.jinputguard.util;

import io.github.jinputguard.util.NamedPredicate;
import java.util.function.Predicate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class NamedPredicateTest {

	private static final Predicate<Integer> DELEGATE_TEST_0 = i -> i == 0;
	private static final Predicate<Integer> DELEGATE_TEST_1 = i -> i == 1;

	@Test
	void nominal() {
		var predicate = new NamedPredicate<>(DELEGATE_TEST_0, "Delegate0");

		Assertions.assertThat(predicate.test(-1)).isFalse();
		Assertions.assertThat(predicate.test(0)).isTrue();
		Assertions.assertThat(predicate.test(1)).isFalse();

		Assertions.assertThat(predicate.getName()).isEqualTo("Delegate0");
		Assertions.assertThat(predicate.toString()).isEqualTo("Delegate0");
	}

	@Test
	void and() {
		var predicate0 = new NamedPredicate<>(DELEGATE_TEST_0, "Delegate0");
		var predicate1 = new NamedPredicate<>(DELEGATE_TEST_1, "Delegate1");

		var predicate = predicate0.and(predicate1);

		Assertions.assertThat(predicate.test(-1)).isFalse();
		Assertions.assertThat(predicate.test(0)).isFalse();
		Assertions.assertThat(predicate.test(1)).isFalse();

		Assertions.assertThat(predicate.toString()).isEqualTo("(Delegate0 && Delegate1)");
	}

	@Test
	void or() {
		var predicate0 = new NamedPredicate<>(DELEGATE_TEST_0, "Delegate0");
		var predicate1 = new NamedPredicate<>(DELEGATE_TEST_1, "Delegate1");

		var predicate = predicate0.or(predicate1);

		Assertions.assertThat(predicate.test(-1)).isFalse();
		Assertions.assertThat(predicate.test(0)).isTrue();
		Assertions.assertThat(predicate.test(1)).isTrue();

		Assertions.assertThat(predicate.toString()).isEqualTo("(Delegate0 || Delegate1)");
	}

	@Test
	void negate() {
		var predicate0 = new NamedPredicate<>(DELEGATE_TEST_0, "Delegate0");

		var predicate = predicate0.negate();

		Assertions.assertThat(predicate.test(-1)).isTrue();
		Assertions.assertThat(predicate.test(0)).isFalse();
		Assertions.assertThat(predicate.test(1)).isTrue();

		Assertions.assertThat(predicate.toString()).isEqualTo("!Delegate0");
	}

}
