package io.github.jinputguard.result;

import io.github.jinputguard.result.ValidationError;
import io.github.jinputguard.result.ValidationError.CollectionIsEmpty;
import io.github.jinputguard.result.ValidationError.CustomValidationError;
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
import io.github.jinputguard.result.ValidationError.StringMustMatchPattern;
import java.util.regex.Pattern;
import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.Assertions;

public class AbstractValidationErrorAssert<SELF extends AbstractValidationErrorAssert<SELF, T>, T extends ValidationError> extends AbstractObjectAssert<SELF, T> {

	public AbstractValidationErrorAssert(T actual, Class<?> selfType) {
		super(actual, selfType);
	}

	public SELF isCustomValidationError() {
		return isInstanceOf(CustomValidationError.class);
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
