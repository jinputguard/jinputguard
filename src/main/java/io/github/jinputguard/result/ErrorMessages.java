package io.github.jinputguard.result;

import io.github.jinputguard.result.errors.ErrorDetails;

public final class ErrorMessages {

	private ErrorMessages() {
		// Helper class
	}

	public static ErrorDetails generic(String msg) {
		return () -> msg;
	}

}
