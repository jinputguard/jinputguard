package io.github.jinputguard.examples;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.result.GuardFailure;
import io.github.jinputguard.result.ValidationError;
import io.github.jinputguard.result.ValidationFailure;
import io.github.jinputguard.result.ValidationError.CustomValidationError;

public class CustomValidationErrors {

	public static void main(String[] args) {

		/* 
		 * Configure a guard with some validations, returning various types of failure depending on the input
		 */
		var guard = InputGuard.builder().forString()
			.validate(value -> value.equals("1") ? new MyAppCustomValidationError.CustomError1() : null)
			.validate(value -> value.equals("2") ? new MyAppCustomValidationError.CustomError2() : null)
			.validate(value -> value.equals("3") ? new AnotherCustomError() : null)
			.validate(value -> value.equals("4") ? new ValidationError.ObjectIsNull() : null)
			.sanitize(value -> value.equals("5") ? Integer.valueOf(null).toString() : value) // Integer.valueOf(null) will throw NPE
			.sanitize(value -> value + "-OK")
			.build();

		processAndPrint(guard, "0"); // 0-OK
		processAndPrint(guard, "1"); // Failed with MyApp custom failure 1
		processAndPrint(guard, "2"); // Failed with MyApp custom failure 2
		processAndPrint(guard, "3"); // Failed with another custom validation error
		processAndPrint(guard, "4"); // Failed with a built-in validation error
		processAndPrint(guard, "5"); // Failed with other type of error
	}

	private static void processAndPrint(InputGuard<String, String> guard, String value) {
		var result = guard.process(value);
		if (result.isSuccess()) {
			System.out.println(value + " --> " + result.get());
		} else {
			System.out.println(value + " --> " + handleProcessFailure(result.getFailure()));
		}
	}

	private static String handleProcessFailure(GuardFailure failure) {
		if (failure instanceof ValidationFailure validationFailure) {
			return handleCustomValidationFailure(validationFailure);
		}
		return "Failed with other type of failure";
	}

	private static String handleCustomValidationFailure(ValidationFailure failure) {
		return switch (failure.getError()) {
			case MyAppCustomValidationError myAppFail -> switch (myAppFail) {
				case MyAppCustomValidationError.CustomError1 myAppFail1 -> "Failed with MyApp custom failure 1";
				case MyAppCustomValidationError.CustomError2 myAppFail2 -> "Failed with MyApp custom failure 2";
			};
			case CustomValidationErrors otherCustom -> "Failed with another custom validation failure";
			default -> "Failed with a built-in validation failure";
		};
	}

	/**
	 * A custom validation failure interface, extending {@link CustomValidationErrors}
	 * and declaring two sub-failure objects: {@link CustomError1} and {@link CustomError2}.
	 * 
	 *
	 */
	public sealed interface MyAppCustomValidationError extends CustomValidationError {

		record CustomError1() implements MyAppCustomValidationError {

			@Override
			public String getConstraintMessage() {
				return "Failed with MyApp custom error 1";
			}

		}

		record CustomError2() implements MyAppCustomValidationError {

			@Override
			public String getConstraintMessage() {
				return "Failed with MyApp custom error 2";
			}

		}

	}

	/**
	 * Another custom validation failure object, implementing {@link CustomValidationErrors}.
	 * 
	 *
	 */
	public record AnotherCustomError() implements CustomValidationError {

		@Override
		public String getConstraintMessage() {
			return "Failed with another custom validation error";
		}

	}

}
