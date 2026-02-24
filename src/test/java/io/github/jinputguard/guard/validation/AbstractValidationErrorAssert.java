package io.github.jinputguard.guard.validation;

import io.github.jinputguard.guard.validation.ValidationError.CollectionIsEmpty;
import io.github.jinputguard.guard.validation.ValidationError.CustomValidationError;
import io.github.jinputguard.guard.validation.ValidationError.GenericValidationError;
import io.github.jinputguard.guard.validation.ValidationError.NumberMustBeBetween;
import io.github.jinputguard.guard.validation.ValidationError.NumberMustBeGreaterOrEqualTo;
import io.github.jinputguard.guard.validation.ValidationError.NumberMustBeGreaterThan;
import io.github.jinputguard.guard.validation.ValidationError.NumberMustBeLowerOrEqualTo;
import io.github.jinputguard.guard.validation.ValidationError.NumberMustBeLowerThan;
import io.github.jinputguard.guard.validation.ValidationError.ObjectIsNull;
import io.github.jinputguard.guard.validation.ValidationError.ObjectMustBeEqualTo;
import io.github.jinputguard.guard.validation.ValidationError.ObjectMustBeInstanceOf;
import io.github.jinputguard.guard.validation.ValidationError.StringIsEmpty;
import io.github.jinputguard.guard.validation.ValidationError.StringIsTooLong;
import io.github.jinputguard.guard.validation.ValidationError.StringMustBeParseableToInteger;
import io.github.jinputguard.guard.validation.ValidationError.StringMustMatchPattern;
import io.github.jinputguard.result.ErrorMessage;
import java.util.regex.Pattern;
import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.Assertions;

public class AbstractValidationErrorAssert<SELF extends AbstractValidationErrorAssert<SELF, T>, T extends ErrorMessage> extends AbstractObjectAssert<SELF, T> {

	public AbstractValidationErrorAssert(T actual, Class<?> selfType) {
		super(actual, selfType);
	}

	public SELF isCustomValidationError() {
		return isInstanceOf(CustomValidationError.class);
	}

	public SELF isGenericError(String expectedMessage) {
		var expected = new GenericValidationError(expectedMessage);
		return isEqualTo(expected);
	}

	public SELF isObjectIsNull() {
		var expected = new ObjectIsNull();
		return isEqualTo(expected);
	}

	public SELF isObjectMustBeInstanceOf(Class<?> actualClass, Class<?> expectedClass) {
		var expected = new ObjectMustBeInstanceOf(actualClass, expectedClass);
		return isEqualTo(expected);
	}

	public SELF isObjectMustBeEqualTo(Object ref) {
		var expected = new ObjectMustBeEqualTo(ref);
		return isEqualTo(expected);
	}

	public SELF isStringIsEmpty() {
		var expected = new StringIsEmpty();
		return isEqualTo(expected);
	}

	public SELF isStringIsTooLong(int currentLength, int maxLength) {
		var expected = new StringIsTooLong(currentLength, maxLength);
		return isEqualTo(expected);
	}

	public SELF isStringMustBeParseableToInteger() {
		var expected = new StringMustBeParseableToInteger();
		return isEqualTo(expected);
	}

	public SELF isStringMustMatchPattern(Pattern pattern) {
		var expected = new StringMustMatchPattern(pattern);
		return isEqualTo(expected);
	}

	public SELF isStringMustMatchPattern(String regex) {
		Assertions.assertThat(actual).isInstanceOfSatisfying(
			StringMustMatchPattern.class,
			error -> Assertions.assertThat(error.pattern().pattern()).isEqualTo(regex)
		);
		return myself;
	}

	public SELF isNumberMustBeGreaterOrEqualTo(Number current, Number ref) {
		var expected = new NumberMustBeGreaterOrEqualTo(current, ref);
		return isEqualTo(expected);
	}

	public SELF isNumberMustBeGreaterThan(Number current, Number ref) {
		var expected = new NumberMustBeGreaterThan(current, ref);
		return isEqualTo(expected);
	}

	public SELF isNumberMustBeLowerOrEqualTo(Number current, Number ref) {
		var expected = new NumberMustBeLowerOrEqualTo(current, ref);
		return isEqualTo(expected);
	}

	public SELF isNumberMustBeLowerThan(Number current, Number ref) {
		var expected = new NumberMustBeLowerThan(current, ref);
		return isEqualTo(expected);
	}

	public SELF isNumberMustBeBetween(Number current, Number min, Number max) {
		var expected = new NumberMustBeBetween(current, min, max);
		return isEqualTo(expected);
	}

	public SELF isCollectionIsEmpty() {
		var expected = new CollectionIsEmpty();
		return isEqualTo(expected);
	}

}
