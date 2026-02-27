package io.github.jinputguard.result;

public final class ErrorMessages {

	private ErrorMessages() {
		// Helper class
	}

	public static ErrorDetails generic(String msg) {
		return () -> msg;
	}

}
