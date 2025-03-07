package io.github.jinputguard.result;

import io.github.jinputguard.result.ValidationError.CollectionIsEmpty;
import io.github.jinputguard.result.ValidationError.NumberMustBeBetween;
import io.github.jinputguard.result.ValidationError.NumberMustBeGreaterOrEqualTo;
import io.github.jinputguard.result.ValidationError.NumberMustBeGreaterThan;
import io.github.jinputguard.result.ValidationError.NumberMustBeLowerOrEqualTo;
import io.github.jinputguard.result.ValidationError.NumberMustBeLowerThan;
import io.github.jinputguard.result.ValidationError.ObjectIsNull;
import io.github.jinputguard.result.ValidationError.ObjectMustBeEqualTo;
import io.github.jinputguard.result.ValidationError.ObjectMustBeInstanceOf;
import io.github.jinputguard.result.ValidationError.StringIsEmpty;
import io.github.jinputguard.result.ValidationError.StringIsTooLong;
import io.github.jinputguard.result.ValidationError.StringMustBeParseableToInteger;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ValidationErrorTest {

	// ===========================================================================
	// OBJECT

	@Nested
	class ObjectIsNullTest {

		@Test
		void getConstraintMessage() {
			var constraint = new ObjectIsNull();
			Assertions.assertThat(constraint.getConstraintMessage())
				.isEqualTo("must not be null");
		}

	}

	@Nested
	class ObjectMustBeInstanceOfTest {

		@Test
		void getConstraintMessage_nullCurrentClass() {
			var constraint = new ObjectMustBeInstanceOf(null, String.class);
			Assertions.assertThat(constraint.getConstraintMessage())
				.isEqualTo("is not an instance of java.lang.String, but is null");
		}

		@Test
		void getConstraintMessage() {
			var constraint = new ObjectMustBeInstanceOf(Object.class, String.class);
			Assertions.assertThat(constraint.getConstraintMessage())
				.isEqualTo("is not an instance of java.lang.String, but is instance of java.lang.Object");
		}

	}

	@Nested
	class ObjectMustBeEqualToTest {

		@Test
		void getConstraintMessage() {
			var expected = new Object();
			var constraint = new ObjectMustBeEqualTo(expected);
			Assertions.assertThat(constraint.getConstraintMessage())
				.isEqualTo("is not equals to " + expected.toString());
		}

	}

	// ===========================================================================
	// STRING

	@Nested
	class StringIsEmptyTest {

		@Test
		void getConstraintMessage() {
			var constraint = new StringIsEmpty();
			Assertions.assertThat(constraint.getConstraintMessage())
				.isEqualTo("must not be empty");
		}

	}

	@Nested
	class StringIsTooLongTest {

		@Test
		void getConstraintMessage() {
			var constraint = new StringIsTooLong(6, 5);
			Assertions.assertThat(constraint.getConstraintMessage())
				.isEqualTo("must be 5 chars max, but is 6");
		}

	}

	@Nested
	class StringMustBeParseableToIntegerTest {

		@Test
		void getConstraintMessage() {
			var constraint = new StringMustBeParseableToInteger();
			Assertions.assertThat(constraint.getConstraintMessage())
				.isEqualTo("is not parseable to Integer");
		}

	}

	// ===========================================================================
	// NUMBER

	@Nested
	class NumberMustBeGreaterThanTest {

		@Test
		void getConstraintMessage() {
			var constraint = new NumberMustBeGreaterThan(5, 4);
			Assertions.assertThat(constraint.getConstraintMessage())
				.isEqualTo("must be > 4");
		}

	}

	@Nested
	class NumberMustBeGreaterOrEqualToTest {

		@Test
		void getConstraintMessage() {
			var constraint = new NumberMustBeGreaterOrEqualTo(5, 4);
			Assertions.assertThat(constraint.getConstraintMessage())
				.isEqualTo("must be >= 4");
		}

	}

	@Nested
	class NumberMustBeLowerThanTest {

		@Test
		void getConstraintMessage() {
			var constraint = new NumberMustBeLowerThan(5, 4);
			Assertions.assertThat(constraint.getConstraintMessage())
				.isEqualTo("must be < 4");
		}

	}

	@Nested
	class NumberMustBeLowerOrEqualToTest {

		@Test
		void getConstraintMessage() {
			var constraint = new NumberMustBeLowerOrEqualTo(5, 4);
			Assertions.assertThat(constraint.getConstraintMessage())
				.isEqualTo("must be <= 4");
		}

	}

	@Nested
	class NumberMustBeBetweenTest {

		@Test
		void getConstraintMessage() {
			var constraint = new NumberMustBeBetween(5, 3, 4);
			Assertions.assertThat(constraint.getConstraintMessage())
				.isEqualTo("must be between 3 and 4");
		}

	}

	// ===========================================================================
	// COLLECTION

	@Nested
	class CollectionIsEmptyTest {

		@Test
		void getConstraintMessage() {
			var constraint = new CollectionIsEmpty();
			Assertions.assertThat(constraint.getConstraintMessage())
				.isEqualTo("is empty");
		}

	}

}
