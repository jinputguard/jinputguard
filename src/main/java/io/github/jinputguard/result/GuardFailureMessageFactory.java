package io.github.jinputguard.result;

import jakarta.annotation.Nullable;
import java.util.stream.Collectors;

public class GuardFailureMessageFactory {

	private static final GuardFailureMessageFactory INSTANCE = new GuardFailureMessageFactory();

	public static GuardFailureMessageFactory getInstance() {
		return INSTANCE;
	}

	public String buildMessage(GuardFailure failure) {
		return buildMessage(failure, null);
	}

	public String buildMessage(GuardFailure failure, @Nullable String parentPath) {
		StringBuilder sb = new StringBuilder();
		buildMessageRecursive(failure, parentPath, sb, 0);
		return sb.toString().strip();
	}

	private void buildMessageRecursive(GuardFailure failure, @Nullable String parentPath, StringBuilder sb, int indentLevel) {
		switch (failure) {
			case DefaultGuardFailure f -> sb.append(buildMessage(f, parentPath).indent(indentLevel));
			case MultiGuardFailure f -> sb.append(buildMessage(f, indentLevel).indent(indentLevel));
			case SubElementGuardFailure f -> sb.append(buildMessage(f).indent(indentLevel));
		}
	}

	private String buildMessage(DefaultGuardFailure failure, @Nullable String parentPath) {
		var path = buildPath(parentPath, failure.getPath());
		return failure.getMessage(path);
	}

	private String buildPath(String parentPath, String currentPath) {
		if (parentPath == null) {
			return currentPath;
		}
		if (currentPath == null) {
			return parentPath;
		}
		return parentPath + "." + currentPath;
	}

	private String buildMessage(MultiGuardFailure failure, int indentLevel) {
		return "multiple errors:\n"
			+ failure.getFailures().stream()
				.map(this::buildMessage)
				.map(subMsg -> ("- " + subMsg).indent(indentLevel + 2))
				.collect(Collectors.joining());
	}

	private String buildMessage(SubElementGuardFailure failure) {
		return switch (failure) {
			case IndexedElementGuardFailure f -> buildMessage(f);
			case NonIndexedElementGuardFailure f -> buildMessage(f);
		};
	}

	private String buildMessage(IndexedElementGuardFailure failure) {
		return buildMessage(failure.getElementFailure());
	}

	private String buildMessage(NonIndexedElementGuardFailure failure) {
		return buildMessage(failure.getElementFailure());
	}

}
